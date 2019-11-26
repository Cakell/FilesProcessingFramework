package filesprocessing.filters;

import java.io.File;
import java.io.FileFilter;

public class Contains implements FileFilter{

    private String containedString;

    /**
     * Filters files according to their name's sub-String.
     * @param containedString the String that only files whose name contain - will pass the filter.
     */
    public Contains(String containedString) {
        this.containedString = containedString;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname  The abstract pathname to be tested
     * @return True if and only if the filter's given name is contained in the name of the file
     * denoted by this abstract pathname.
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().contains(containedString);
    }
}