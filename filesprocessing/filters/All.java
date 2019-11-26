package filesprocessing.filters;

import java.io.File;
import java.io.FileFilter;

/**
 * This 'Filter' in fact dosen't filter any file - since all files pass this filter.
 */
public class All implements FileFilter{

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname  The abstract pathname to be tested
     * @return Returns always true, since this filter is satisfied by all files.
     *
     */
    @Override
    public boolean accept(File pathname) {
        return true;
    }
}