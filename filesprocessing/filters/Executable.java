package filesprocessing.filters;

import java.io.File;
import java.io.FileFilter;
import filesprocessing.warnings.BadYesNoParametersWarning;

public class Executable implements FileFilter{

    private boolean yes;

    /**
     * Filters files according to their execution permission.
     * @param yesOrNo Yes - only executable files will pass the filter.
     *                No - only unexecutable files will pass the filter.
     * @throws BadYesNoParametersWarning if 'yesOrNo' is any other String but YES or NO
     */
    public Executable(String yesOrNo) throws BadYesNoParametersWarning {
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
     * @return True if and only if the file denoted by this abstract pathname has a execution permission.
     */
    @Override
    public boolean accept(File pathname) {
        return pathname.canExecute() == yes;
    }
}