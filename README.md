RESTmoteControl
===============
*Your house and his devices use the power of the computer!*

RES(T)mote Control is a "framework" that helps you to control your pc remotely, ideally from a mobile device like a smartphone or an Arduino, but potentially from any Http client.
Architectually it's a Java stand-alone HttpServer with a simple REST API to run commands on the machine where it's running.

You can make calls like:

- *POST  http://server/api/pc/keyboard/Hello%20World*
- *PUT 	http://server/api/pc/mouse/912x22*
- *POST	http://server/api/pc/mouse/buttons/1/click*

and other calls to browse/open files, setting system volume, shoutdown the machine.

You can use it as a media remote control from your couch, calling things like:

- *POST		http://server/api/apps/winamp/control/PAUSE*
- *DELETE	http://server/api/apps/pid/1234*

and therefore build straightforwardly an Arduino Lazy Couch that stops the movie when you stand up.

It's easy to configure your own controller, defining customized actions responding to a call like *POST http://server/api/apps/yourapp/control/yourDefinedControl* that could for example type Alt+F4, or do a save through a Ctrl+S.

The server hosts also a ready, but evolving, Mobile Web App written with jQueryMobile (client/index.html). 
We are developing a simple Swing UI to set the server and define controls (MainUI.java).

# Roadmap
- Support Mac and Raspberry Pi
- Usb and Bluetooth support
- Possibility to define more articolated sequences of controls
- Plugins mechanism to define elaborated services, like logging, notification, two-ways interaction with a local application (opposed to the one defined above that is a one-way comunication through shortcut and mouse events)
- Arduino plugin
- Processing plugin
- Cloud shared repository of Controls and Plugins.
- Smartphone customizable native widgets and apps
- Arduino client library

RESTMote controls is build with Java, Jersey, Grizzly, Hibernate, Swing, jQueryMobile and experiments with Android, Arduino, and Processing.
There are project files to open it with Eclipse or NetBeans. No Maven (yet).


# Simple Usage
- The run the server, run the org.zooper.becuz.restmote.RestmoteMainNoUI or MainUI class
- Open with a browser the url logged in the console (Main) or defined in the UI (MainUI). Should be http://machineip:9898/client/index.html
- You can optionally configure media paths editing file src/main/java/paths (Main) or through the UI (MainUI)
- To be able to control applications, you need to add them in PopulateDb.java or have the default ones installed. Right now the default hardcoded configuration, and the tests, assumes winamp, smplayer and irfanview (Windows) or Rhythmbox, smplayer and Gnome Image Viewer (Linux) are installed on the machine. Check ModelFactory*.java.
- To run JUnit tests: AllTests.java

Have fun!
