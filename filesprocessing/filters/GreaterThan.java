package filesprocessing.filters;

import java.io.File;
import java.io.FileFilter;

import filesprocessing.warnings.BadParametersWarning;
import filesprocessing.warnings.NegativeSizeWarning;

public class GreaterThan implements FileFilter{

    private double someSizeInKiloBytes;

    /**
     * Filters files whose size is strictly greater than the given number of k-bytes.
     * @param someSizeInKiloBytes the size limit, of which only files with greater size will be filtered.
     * @throws BadParametersWarning if the given size limit is not a double or it is a negative number.
     */
    public GreaterThan(String someSizeInKiloBytes) throws BadParametersWarning {
        try {
            this.someSizeInKiloBytes = Double.parseDouble(someSizeInKiloBytes);
        }
        catch (NumberFormatException e) {
            throw new BadParametersWarning();
        }
        if (this.someSizeInKiloBytes < Filter.POSITIVE_SIZE_LIMIT)
            throw new NegativeSizeWarning();
    }

    /**
     * Tests whether or not the specified abstract pathname should be included in a pathname list.
     * @param pathname  The abstract pathname to be tested
     * @return True if and only if the size of the file (in KiloBytes) denoted by this
     * abstract pathname is Greater than the filter's given size.
     */
    @Override
    public boolean accept(File pathname) {
        double fileSize = pathname.length();
        double sizeInKiloBytes = fileSize / Filter.BYTES_TO_KILO_BYTES;
        return sizeInKiloBytes > someSizeInKiloBytes;
    }
}
