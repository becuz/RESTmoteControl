RESTmoteControl
===============
*Your house and his devices use the power of the computer!*


Currently Mac and Linux not supported.

Minimum Usage:

- The run the server, run the org.zooper.becuz.restmote.Main class
- Open with a browser the url logged in the console. Should be http://machineip:9898/client/index.html
- You can optionally configure media paths editing file src/main/java/paths
- To be able to control applications, you need to add them in PopulateDb.java or have the default ones installed. Right now the default hardcoded configuration, and the tests, assumes winamp, smplayer and irfanview are installed on the machine. Check ModelFactoryWindows.java.
- To run JUnit tests: AllTests.java 