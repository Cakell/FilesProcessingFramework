package filesprocessing.filters;

import java.io.File;
import java.io.FileFilter;
import filesprocessing.warnings.BadYesNoParametersWarning;

public class Hidden implements FileFilter{

    private boolean yes;

    /**
     * Filters files according to their their hidden/unhidden status.
     * @param yesOrNo Yes - only hidden files will pass the filter.
     *                No - only unhidden files will pass the filter.
     * @throws BadYesNoParametersWarning if 'yesOrNo' is any other String but YES or NO
     */
    public Hidden(String yesOrNo) throws BadYesNoParametersWarning {
        if (yesOrNo.equals(Filter.YES))
            yes = true;
        else if (yesOrNo.equals(Filter.NO))
            yes = false;
        else
            throw new BadYesNoParametersWarning();
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname  The abstract pathname to be tested
     * @return True if and only if the file denoted by this abstract pathname is hidden.
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.isHidden() == yes;
    }
}