package filesprocessing;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.ArrayList;
import filesprocessing.filters.Filter;
import filesprocessing.type_II_errors.CommandsFileBadFormat;
import filesprocessing.type_II_errors.BadSubSectionName;
import filesprocessing.type_II_errors.CommandsFileIOException;
import filesprocessing.type_II_errors.TypeIIError;
import filesprocessing.warnings.Warning;

public class CommandsFile {

    private static final String FILTER_HEADER = "FILTER";
    private static final String ORDER_HEADER = "ORDER";

    private enum SectionState {
        GOT_FILTER_HEADER,
        GOT_FILTER,
        GOT_ORDER_HEADER,
        GOT_ORDER,
    }

    private String path;
    private List<Section> sections;

    private SectionState currentSectionState;
    private Section currentSection;

    /**
     * Creates a new CommandsFile instance from the given abstract pathname;
     * @param filePath the abstract pathname of the Commands File.
     */
    public CommandsFile(String filePath){
        path = filePath;
        sections = new ArrayList<>();
        currentSectionState = SectionState.GOT_ORDER;
        currentSection = null;
    }

    /**
     * parses a single line in the CommandsFile, and determines, according to a state machine,
     * whether the line should be interpret as a header of a new sub-section (FILTER / ORDER)
     * or as the description of a sub-section
     * @param command the command that is currently being parsed.
     * @param lineNumber the line number in the file of the command mentioned above.
     * @throws TypeIIError if there is an invalid input in the CommandsFile
     */
    public void parseCommand(String command, int lineNumber) throws TypeIIError {
        switch (currentSectionState) {
            case GOT_FILTER_HEADER:
                // if no filter is given set the default filter
                if (command.equals(ORDER_HEADER)) {
                    currentSection.addWarning(new Warning(lineNumber));
                    currentSectionState = SectionState.GOT_ORDER_HEADER;
                }
                // parse the command and try to create the Filter
                else {
                    try {
                        currentSection.setFilter(new Filter(command));
                    }
                    catch (Warning w) {
                        currentSection.addWarning(new Warning(lineNumber));
                    }
                    finally {
                        currentSectionState = SectionState.GOT_FILTER;
                    }
                }
                break;

            case GOT_FILTER:
                // expect only the ORDER command
                if (command.equals(ORDER_HEADER)) {
                    currentSectionState = SectionState.GOT_ORDER_HEADER;
                }
                else {
                    throw new CommandsFileBadFormat("ORDER subsection is missing");
                }
                break;

            case GOT_ORDER_HEADER:
                // if no order is given set the default order
                if (command.equals(FILTER_HEADER)) {
                    currentSection.setOrder(new Order());
                    sections.add(currentSection);
                    currentSection = new Section();
                    currentSectionState = SectionState.GOT_FILTER_HEADER;
                }
                // parse the command and try to create the ORDER
                else {
                    try {
                        currentSection.setOrder(new Order(command));
                        sections.add(currentSection);
                    }
                    catch (Warning w) {
                        currentSection.addWarning(new Warning(lineNumber));
                        currentSection.setOrder(new Order());
                        sections.add(currentSection);
                    }
                    finally {
                        currentSectionState = SectionState.GOT_ORDER;
                    }
                }
                break;

            case GOT_ORDER:
                if (command.equals(FILTER_HEADER)) {
                    currentSection = new Section();
                    currentSectionState = SectionState.GOT_FILTER_HEADER;
                }
                else {
                    throw new BadSubSectionName("bad subsection name");
                }
                break;

            default:
                throw new CommandsFileBadFormat("FILTER subsection is missing");
        }
    }

    /**
     * Divides the text of the commands in the Commands File into one or more sections,
     * each contains a single filter and a single order.
     * @throws TypeIIError  if there is an I/O exception when reading the CommandsFile
     *                      or its content is invalid due to a BadSubSectionName or a BadFormat.
     */
    public void parse() throws TypeIIError {
        List<String> lines = null;
        try {
            lines = Files.readAllLines(Paths.get(path));
        }
        catch (IOException e) {
            throw new CommandsFileIOException(e.getMessage());
        }
        int lineNumber = 1;
        for (String line : lines) {
            parseCommand(line.trim(), lineNumber++);
        }
    }

    /**
     * For each section of the commands file, filters and orders the files in the source
     * directory according to the commands in the Commands File.
     * @param sourceDir the given Source Directory whose files we filter, order and print.
     * @throws TypeIIError  if there is an I/O exception when reading the CommandsFile
     *                      or its content is invalid due to a BadSubSectionName or a BadFormat.
     */
    public void run(SourceDirectory sourceDir) throws TypeIIError {
        parse();
        for (Section s : sections) {
            s.execute(sourceDir);
        }
    }
}