package filesprocessing.filters;

import java.io.File;
import java.io.FileFilter;
import filesprocessing.warnings.Warning;
import filesprocessing.warnings.BadFilterNameWarning;

public class Filter implements FileFilter {

    private static final String
            GREATER_THAN = "greater_than",
            BETWEEN      = "between",
            SMALLER_THAN = "smaller_than",
            FILE         = "file",
            CONTAINS     = "contains",
            PREFIX       = "prefix",
            SUFFIX       = "suffix",
            WRITABLE     = "writable",
            EXECUTABLE   = "executable",
            HIDDEN       = "hidden",
            ALL          = "all",
            NOT          = "NOT";

    private static final String SEPARATOR = "#";
    private static final int LAST_INDEX = 1;

    private static final int
            FILTER_NAME         = 0,
            VALUE1              = 1,
            VALUE2              = 2;

    static final String YES = "YES", NO = "NO";
    static final int BYTES_TO_KILO_BYTES = 1024, POSITIVE_SIZE_LIMIT = 0;

    private boolean not = false;
    private FileFilter filter = new All();

    /**
     * Creates a new Filter instance with the default filter - the 'All' filter.
     */
    public Filter() {}

    /**
     * Creates a new Filter according to the filter description given by the current Section in the
     * Commands File.
     * @param filterDescription The description of the filter given by the current Section in the
     *                          Commands File.
     * @throws Warning if filterDescription has one or more illegal values.
     */
    public Filter(String filterDescription) throws Warning {
        try {
            parseFilterDescription(filterDescription);
        }
        catch (Warning w) {
            throw w;
        }
    }

    /**
     * Given a directory, returns an array of the files in the directory that satisfies the class' filter.
     * @param directory the abstract pathname of the given Source Directory whose files we filter.
     * @return an array of the files in the directory that satisfies the class' filter.
     */
    public File[] filterFiles(File directory) {
        return directory.listFiles(this);
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * This happans if and only if the file denoted by this abstract pathname exists and is a normal file,
     * and this file satisfies the filter given by the Commands File.
     * @param pathname  The abstract pathname to be tested
     * @return  True if pathname should be included. False otherwise.
     */
    @Override
    public boolean accept(File pathname) {
        if (pathname.isDirectory())
            return false;
        if (not)
            return !filter.accept(pathname);
        return filter.accept(pathname);
    }

    /**
     * Translates the description of the filter and creates a specific filter
     * from 11 different types of possible filters.
     * @param filterDescription The description of the filter given by the Commands File.
     * @throws Warning if filterDescription has one or more illegal values.
     */
    private void parseFilterDescription(String filterDescription) throws Warning {
        String[] description = filterDescription.split(SEPARATOR);
        if (description[description.length - LAST_INDEX].equals(NOT))
            not = true;
        switch (description[FILTER_NAME]) {

            case GREATER_THAN:
                filter = new GreaterThan(description[VALUE1]);
                break;

            case BETWEEN:
                filter = new Between(description[VALUE1], description[VALUE2]);
                break;

            case SMALLER_THAN:
                filter = new SmallerThan(description[VALUE1]);
                break;

            case FILE:
                filter = new FileName(description[VALUE1]);
                break;

            case CONTAINS:
                filter = new Contains(description[VALUE1]);
                break;

            case PREFIX:
                filter = new Prefix(description[VALUE1]);
                break;

            case SUFFIX:
                filter = new Suffix(description[VALUE1]);
                break;

            case WRITABLE:
                filter = new Writable(description[VALUE1]);
                break;

            case EXECUTABLE:
                filter = new Executable(description[VALUE1]);
                break;

            case HIDDEN:
                filter = new Hidden(description[VALUE1]);
                break;

            case ALL:
                filter = new All();
                break;

            default:
                throw new BadFilterNameWarning();
        }
    }
}