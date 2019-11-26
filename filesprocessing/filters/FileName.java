package filesprocessing.filters;

import java.io.File;
import java.io.FileFilter;

public class FileName implements FileFilter{

    private String someFileName;

    /**
     * Filters files according to their name.
     * @param someFileName the String of the file that will pass the filter (only such one can exist).
     */
    public FileName(String someFileName) {
        this.someFileName = someFileName;
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname  The abstract pathname to be tested
     * @return True if and only if the filter's given name equals the name of the file
     * denoted by this abstract pathname.
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.getName().equals(someFileName);
    }
}