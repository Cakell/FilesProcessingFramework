package filesprocessing;

import java.io.File;

public class SourceDirectory {
    private File directory;

    /**
     * Creates a new instance of SourceDirectory (which represents the directory of files which
     * will be filtered and ordered) of the given abstract pathname.
     * @param directoryPath the abstract pathname of the Source Directory.
     */
    public SourceDirectory(String directoryPath) {
        directory = new File(directoryPath);
    }

    /**
     * @return the directory of SourceDirectory.
     */
    public File getDirectory() {
        return directory;
    }
}