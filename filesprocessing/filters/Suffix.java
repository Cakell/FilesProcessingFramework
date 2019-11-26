package filesprocessing.filters;

import java.io.File;
import java.io.FileFilter;

public class Suffix implements FileFilter{

    private String suffix;

    /**
     * Filters files according the the suffix of their name.
     * @param suffix the String that only files whose name end with - will pass the filter.
     */
    public Suffix(String suffix) {
        this.suffix = suffix;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname  The abstract pathname to be tested
     * @return True if and only if the name of the file denoted by this abstract pathname
     * ends with the filter's given name.
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().endsWith(suffix);
    }
}
