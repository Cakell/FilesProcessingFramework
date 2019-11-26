package filesprocessing;

import filesprocessing.filters.Filter;
import filesprocessing.warnings.Warning;
import java.util.List;
import java.util.ArrayList;

public class Section {

    private Filter filter;
    private Order order;
    private List<Warning> warnings;

    /**
     * Creates a new instance of a Section initialized with the default filter,
     * the default order, and an empty warnings list.
     */
    public Section() {
        this.filter = new Filter();
        this.order = new Order();
        this.warnings = new ArrayList<>();
    }

    /**
     * Sets the filter of the section.
     * @param filter A Filter instance that will be used to filter the files in the section.
     */
    public void setFilter(Filter filter) {
        this.filter = filter;
    }

    /**
     * Sets the order of the section.
     * @param order An Order instance that will be used to order the files in the section.
     */
    public void setOrder(Order order) {
        this.order = order;
    }

    /**
     * In case there is an illegal value in the CommandsFile in a line of a filter / order description,
     * adds a warning (type I error) to the list of warnings of the section,
     * that will be printed before the section's ordered files
     * @param warning
     */
    public void addWarning(Warning warning) {
        this.warnings.add(warning);
    }

    /**
     * Prints the warnings of the section.
     */
    private void printWarnings() {
        for (Warning w : warnings) {
            w.printWarning();
        }
    }

    /**
     * Filters and orders the files in the source directory according to the commands in the section.
     * @param sourceDir the given Source Directory whose files we filter, order and print.
     */
    public void execute(SourceDirectory sourceDir) {
        printWarnings();
        order.orderFiles(filter.filterFiles(sourceDir.getDirectory()));
    }
}