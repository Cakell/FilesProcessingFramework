package filesprocessing.filters;

import java.io.File;
import java.io.FileFilter;

import filesprocessing.warnings.BadParametersWarning;
import filesprocessing.warnings.IllegalBetweenValuesWarning;

public class Between implements FileFilter{

    private double sizeLowerLimit, sizeUpperLimit;

    /**
     * Filters files whose size is between (inclusive) the given numbers (in k-bytes).
     * @param sizeLowerLimit the lower limit of the filtered files size.
     * @param sizeUpperLimit the upper limit of the filtered files size.
     * @throws BadParametersWarning  if the lower & upper limits have illegal values, which means that
     *                               at least one of them is not from type 'double', or the lower limit
     *                               is strictly greater than the upper limit
     */
    public Between(String sizeLowerLimit, String sizeUpperLimit) throws BadParametersWarning {
        try {
            this.sizeLowerLimit = Double.parseDouble(sizeLowerLimit);
            this.sizeUpperLimit = Double.parseDouble(sizeUpperLimit);
        }
        catch (NumberFormatException e) {
            throw new BadParametersWarning();
        }
        if (this.sizeLowerLimit > this.sizeUpperLimit)
            throw new IllegalBetweenValuesWarning();
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname  The abstract pathname to be tested
     * @return True if and only if the size of the file (in KiloBytes) denoted by this
     * abstract pathname is Between (inclusive) the given sizes.
     */
    @Override
    public boolean accept(File pathname) {
        double fileSize = pathname.length();
        double sizeInKiloBytes = fileSize / Filter.BYTES_TO_KILO_BYTES;
        return sizeInKiloBytes >= sizeLowerLimit && sizeInKiloBytes <= sizeUpperLimit;
    }
}
