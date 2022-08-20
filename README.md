### Build and Run
Execute Maven package command at the project's root directory:
``` Bash
$ mvn package
```
An executable .jar file will be created at the project's target directory.
To run the app, execute the following command at the directory where the .jar is located:
``` Bash
$ java -jar 1337-downloader-1.jar
```
The files will be downloaded at the jar's root directory.
### Solution breakdown

### Testing
To run the tests, execute Maven's test command at the project's root directory:
``` Bash
$ mvn clean test
```
### Shortcuts
Due to the time constraint of 3-6 hr, I've left this unresolved:
#### Known issues
* It doesn't handle files from external resources like Cloudflare
#### TODOs
* Handle exceptions in a useful way for the user
* Better design for the Extractor class (different impl. extend abstract class)
* System test with real environment
* Better handle the case if threads takes too long to execute through
* Better concurrency for pages that contain a small amount or no files
* Individual progress bars with bytes left to download
