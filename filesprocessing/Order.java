package filesprocessing;

import java.io.File;
import java.util.Arrays;
import java.util.Comparator;
import filesprocessing.warnings.BadOrderNameWarning;

public class Order {

    private static final char DOT = '.';

    private static final String
            ABS = "abs",
            TYPE = "type",
            SIZE = "size",
            REVERSE = "REVERSE",
            EMPTY_EXTENSION = "",
            SEPARATOR = "#";

    private static final int ORDER_NAME = 0,
            BIGGER = 1,
            SMALLER = -1,
            NO_DOT_IN_NAME = -1,
            INDEX_AFTER_DOT = 1,
            LAST_INDEX = 1;

    // This is a constant representing the abs comparator, by which we order files
    // according to their absolute name.
    public static final Comparator<File> ABS_COMPARATOR = new Comparator<File>() {
        @Override
        public int compare(File file1, File file2) {
            return file1.getAbsolutePath().compareTo(file2.getAbsolutePath());
        }
    };
    // This is a constant representing the type comparator, by which we order files according to their type.
    public static final Comparator<File> TYPE_COMPARATOR = new Comparator<File>() {
        @Override
        public int compare(File file1, File file2) {
            int comparison = getFileType(file1.getName()).compareTo(getFileType(file2.getName()));
            if (comparison != 0)
                return comparison;

            // in case their type is equal
            return ABS_COMPARATOR.compare(file1, file2);
        }
    };
    // This is a constant representing the size comparator, by which we order files according to their size.
    public static final Comparator<File> SIZE_COMPARATOR = new Comparator<File>() {
        @Override
        public int compare(File file1, File file2) {
            if (file1.length() > file2.length())
                return BIGGER;
            if (file1.length() < file2.length())
                return SMALLER;

            // in case their size is equal
            return ABS_COMPARATOR.compare(file1, file2);
        }
    };

    private boolean reverse = false;
    private Comparator<File> order = ABS_COMPARATOR;

    /**
     * Creates a new Order instance with the default order - the 'Abs' Comparator.
     */
    public Order () {}

    /**
     * Creates a new Order according to the order description given by the current Section in the
     * Commands File.
     * @param orderDescription The description of the order given by the current Section in the
     *                          Commands File.
     * @throws BadOrderNameWarning if orderDescription equals to neither of the order types.
     */
    public Order(String orderDescription) throws BadOrderNameWarning {
        parseOrderDescription(orderDescription);
    }

    /**
     * Given a filtered File-array, print the array ordered according to the class' order field.
     * @param filteredFiles an array of files - the files in the SourceDirectory that passed the filter.
     */
    public void orderFiles(File[] filteredFiles) {
        Arrays.sort(filteredFiles, order);
        if (reverse) {
            reverseSortedArray(filteredFiles);
        }
        printFiles(filteredFiles);
    }

    /**
     * Prints the filtered files After they have been ordered.
     * @param orderedFiles the array of the filtered & ordered files of the current section.
     */
    private void printFiles(File[] orderedFiles) {
        for (int i = 0; i < orderedFiles.length; i++)
             System.out.println(orderedFiles[i].getName());
    }

    /**
     * Translates the description of the order and creates a specific order
     * from 3 different types of possible orders.
     * @param orderDescription The description of the order given by the Commands File.
     * @throws BadOrderNameWarning if orderDescription equals to neither of the order types.
     */
    private void parseOrderDescription(String orderDescription) throws BadOrderNameWarning {
        String[] description = orderDescription.split(SEPARATOR);
        if (description[description.length - LAST_INDEX].equals(REVERSE))
            reverse = true;
        switch (description[ORDER_NAME]) {
            case ABS:
                order = ABS_COMPARATOR;
                break;

            case TYPE:
                order = TYPE_COMPARATOR;
                break;

            case SIZE:
                order = SIZE_COMPARATOR;
                break;

            default:
                throw new BadOrderNameWarning();
        }
    }

    /**
     * Given a name of a file, returns the file's extension (type).
     * @param fileName a name of a file, of which we find out its type.
     * @return the file's extension (type).
     */
    private static String getFileType(String fileName) {
        int dotIndex = fileName.lastIndexOf(DOT);
        if (dotIndex == NO_DOT_IN_NAME)
            return EMPTY_EXTENSION;
        else
            return fileName.substring(dotIndex + INDEX_AFTER_DOT);
    }

    /**
     * Given a sorted array, reverses the order of the array.
     * @param sortedArray a sorted array to reverse.
     */
    private static void reverseSortedArray(File[] sortedArray) {
        // I used twice in this method in "Magic Numbers", but since I didn't find
        // any meaningful names for the numbers, i kept them in meaningful variables.
        int middleIndex = sortedArray.length / 2;
        for (int i = 0; i < middleIndex; i++) {
            File temp = sortedArray[i];
            int reversedIndex = sortedArray.length - (i + 1);
            sortedArray[i] = sortedArray[reversedIndex];
            sortedArray[reversedIndex] = temp;
        }
    }
}