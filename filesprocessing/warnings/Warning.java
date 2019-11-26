package filesprocessing.warnings;

public class Warning extends Exception {

    private int lineNumber = 0;

    /**
     * Creates a default Warning, with the default value of lineNumber.
     * This constructor is used whenever a warning is thrown from any other
     * scope than CommandsFile's scope, that sends the lineNumber to its relevant Section.
     */
    public Warning() {}

    /**
     * Creates a new Warning with the given lineNumber.
     * This constructor is used whenever a warning is thrown CommandsFile's scope, and
     * sends the lineNumber to its relevant Section.
     * @param lineNumber the number of line in the CommandsFile in which the Warning was found.
     */
    public Warning(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * Prints the warning to the output screen.
     */
    public void printWarning() {
        System.out.println("Warning in line " + lineNumber);
    }
}