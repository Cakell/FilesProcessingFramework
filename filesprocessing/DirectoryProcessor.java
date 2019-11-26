package filesprocessing;

import filesprocessing.type_II_errors.InvalidUsage;
import filesprocessing.type_II_errors.TypeIIError;

public class DirectoryProcessor {

    private static final int SOURCE_DIRECTORY = 0, COMMANDS_FILE = 1;

    private static SourceDirectory sourceDir;
    private static CommandsFile commandsFile;

    /**
     * /**
     * Given the abstract paths of a SourceDirectory and a CommandsFile,runs the commandsFile over
     * the sourceDirectory - which means it filters and orders its files according to the commands
     * written in the files.
     * @param args An array holding the abstract paths of the SourceDirectory and the CommandsFile.
     * @throws TypeIIError if there is any vital error during the run of DirectoryProcessor.
     */
    public static void main(String[] args) throws TypeIIError {
        if (args.length != 2)
            throw new InvalidUsage("Error: Invalid Usage. Please enter exactly 2 arguments of" +
                                    " abstract paths which don't contain any spaces");
        try {
            sourceDir = new SourceDirectory(args[SOURCE_DIRECTORY]);
            commandsFile = new CommandsFile(args[COMMANDS_FILE]);
            commandsFile.run(sourceDir);
        } catch (TypeIIError e) {
            System.err.println("ERROR: " + e.getMessage());
        }
    }
}