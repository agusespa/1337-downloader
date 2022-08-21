# Welcome to the tretton37.com downloader
A CLI app to automatically download all the html code and its resources.  
The file structure will be created relative to the directory where you execute the app and the structure will mirror the directory tree of the website.  
The app prints information of the execution process, but you should expect a large number of files to be created.
### Build and Run
To use the app, you first have to clone or download this repository to your machine.  
To compile it, execute Maven package command at the project's root directory:
``` Bash
$ mvn package
```
An executable .jar file will be created at the project's target directory. That file contains the whole app so you can move it to any directory you prefer.  
To run the app, execute the following command at the directory where the .jar is located:
``` Bash
$ java -jar 1337-downloader-1.jar
```
The files will be downloaded at the jar's root directory.
## Solution breakdown
The app combines aspects of scripting programming with some OOP principles.  
I wanted to have a nimble working program in a short development time but also sufficient granularity of the methods in order to have them tested and achieve a more robust and maintainable app where features could be added easily.
#### Execution flow
1. Read the contents of the web page
2. Extract the links to other pages and the file resources paths
3. Download those files
4. Recursively call to the links extracted
#### Concurrency
Each web page contains a large number of files that can be downloaded asynchronously, so I've gathered each file download thread in a pool to increase performance.
### External libraries used
* Maven plugins for test execution and packaging all classes in a single executable JAR.
* JUnit for testing.
### Testing
I've included minimal unit testing to ensure proper execution of vital actions, like parsing the links and creating the directories, which would help when coding more complex features.
To run the tests, execute Maven's test command at the project's root directory:
``` Bash
$ mvn clean test
```
### Shortcuts taken
Due to the time constraint of 3-6 hr, I've left these unresolved:
#### Known issues
* It doesn't handle files from external resources like Cloudflare
#### TODOs
* Better design for the Extractor class (different impl. for each html tag, extend abstract class)
* Better concurrency for pages that contain a small amount of files
* Individual progress bars with bytes left to download
* Appropriate unit test coverage and integration testing
