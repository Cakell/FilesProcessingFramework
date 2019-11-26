package filesprocessing.filters;

import java.io.File;
import java.io.FileFilter;

public class Prefix implements FileFilter{

    private String prefix;

    /**
     * Filters files according the the prefix of their name.
     * @param prefix the String that only files whose name start with - will pass the filter.
     */
    public Prefix(String prefix) {
        this.prefix = prefix;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname  The abstract pathname to be tested
     * @return True if and only if the name of the file denoted by this abstract pathname
     * starts with the filter's given name.
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().startsWith(prefix);
    }
}