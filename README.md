# XmlDataManager

In presented case database is setup in "data" folder. Each file in directories: External and Internal is a separate .xml file containing data about one person.

## Installation

Copy the repository to your local machine.
Use Maven to build dependencies.
Then run `XmlDataManagerApplication.java` file.

## Usage

The application allows to read data from .xml files and display it in console. It also allows to add new data to the files.
Allows to modify or delete files. Allows to search for data in files giving back path to correct file.

## TODO

- [ ] Create XmlService interface
- [ ] Scan directories for files instead of hardcoding them
- [ ] Simplyfy code inside XmlService
