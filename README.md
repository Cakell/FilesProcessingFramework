# FilesProcessingFramework

A flexible framework for working with files.

Filters the files in a given directory according to various conditions, and orders the
filenames that passed the filtering according to various properties.
The program is invoked from the command line as follows:

```
java filesprocessing.DirectoryProcessor sourcedir commandfile
```
Where:

* sourcedir is a directory name, in the form of a path (e.g., “./myhws/homework1/” or “./myhws/homework1”).
  This directory is referred to in the following as Source Directory.
  sourcedir can be either absolute or relative to where we run the program from.

* commandfile is a name of a file, also in the form of a relative or absolute path (e.g., ./scripts/Commands1.txt).
  This file is referred to in the following as Commands File. It is a text file that contains sections,
  wherein each section contains a FILTER and an ORDER subsections (see section 4).
  The FILTER sub-section includes filters which are used to select a subset of the files in the Source Directory.
  The ORDER sub-section indicates in which order the files’ names should be printed.


## Files description  


    DirectoryProcessor.java -   Contains the main method that runs the project.

    SourceDirectory.java -      Contains the SourceDirectory class, which represents the directory of files
                                being filtered & ordered - according to the commands of the 'CommandsFile'.

    CommandsFile.java -         Contains the CommandsFile class, which represents the text file in which
                                there are one or more sections of filter & order commands.

    Section.java -              Contains the Section class, which represents a single section
                                of a filter & order.

    Order.java -                Contains the Order class, which implements the different order types.

package 'filters':
    
    Filter.java -           Contains the Filter class, which uses the different filter types to
                            filter the files in the current section.

    GreaterThan.java -      Implements the 'GreaterThan' filter.
    Between.java -          Implements the 'Between' filter.
    SmallerThan.java -      Implements the 'SmallerThan' filter.
    FileName.java -         Implements the 'File' filter.
    Contains.java -         Implements the 'Contains' filter.
    Prefix.java -           Implements the 'Prefix' filter.
    Suffix.java -           Implements the 'Suffix' filter.
    Writable.java -         Implements the 'Writable' filter.
    Executable.java -       Implements the 'Executable' filter.
    Hidden.java -           Implements the 'Hidden' filter.
    All.java -              Implements the 'All' filter.

package 'warnings':
    
    Warning.java -                      Implements the 'Type I Error - Warning', which is thrown when a line
                                        holding a filter / order description has an illegal value.

    BadNameWarning.java -               Thrown when the filter / order description has an illegal value.
    BadFilterNameWarning.java -         Thrown when the filter description has a filter type that dosen't exist
    BadOrderNameWarning.java -          Thrown when the order description has an order type that dosen't exist
    BadParametersWarning.java -         Thrown when the filter description has an illegal value.
    BadYesNoParametersWarning.java -    Thrown when one of the filters: Writable, Executable or Hidden
                                        has any other value than YES or NO.
    NegativeSizeWarning.java -          Thrown when when one of the filters: GreaterThan, Between or SmallerThan
                                        has a value of a negative number.
    IllegalBetweenValuesWarning.java -  Thrown when Between's values are reversed (the lower limit is
                                        strictly greater than the upper limit).

package 'type_II_errors':
    
    TypeIIError.java -              Thrown when there is any vital error during the run of DirectoryProcessor,
                                    such as an IO exception, Invalid Uasge of the progrm arguments, or a bad
                                    format of the CommandsFile.

    InvalidUsage.java -             Thrown when there is an invalid usage of the program arguments, which means
                                    it received any other number but two arguments.
    CommandsFileIOException.java -  Thrown when there is an IOException when trying to access the
                                    CommandsFile.
    CommandsFileBadFormat.java -    Thrown when CommandsFile has a bad format, such as a missing sub-section.
    BadSubSectionName.java -        Thrown when CommandsFile has a bad sub-section name (i.e., not FILTER/ORDER).

## Design   

The design of the project is based mostly on composition, but it also uses various design
patterns such as private classes, factories and a state machine.

As opposed to my original plan, as reflected in my UML diagram, in which I planned to have a package
containing the various types of the Orders (as I have done with the different types of filters), so that
each Order will have its own class - in fact I've chosen a slightly different design.

The different Order types were kept as constant 'Comparator<File>' fields of the 'Order' class.
Thus, when the 'compare' method of 'Type' and 'Size' needs to compare two "equal" files (and therefore
should be ordered by the 'Abs' comparator) - it can easily use the 'ABS_COMPARATOR', which is a constant
field of the class.

### Design Patterns

As mentioned above, I used the factory design pattern, in order to parse the lines containing the
description of the Filter / Order that should be used in the section.

Also, I used a state machine when parsing the CommandsFile into 'Sections', in order to
determine when the words "FILTER" and "ORDER" should be considered as a new sub-section,
and when they should be considered as a 'bad FILTER / ORDER name'.

### Modularity & Extendibility

Note that the big 'problem' of translating a CommandsFile to Filter / Order commands and execute them
on the given SourceDirectory, have been modularized into several classes - each taking care of
the functionality of only the sub-problems relating to him.
For instance, in order to build the hierarchy between the different classes, it was important that
'CommandsFile' will divide its text into Sections, and then 'Section' will send the Filter / Order
description-lines to be interpret by their own classes - 'Filter' & 'Order'.

Also, I created many different types of exceptions that extend Warning or TypeIIException, in order to
maintain the extendability of the project - if suddenly a small change should be done in the project,
it can be done by changing a small area in the code.
