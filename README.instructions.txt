

Whether you are using Eclipse or Android Studio, it is usually best to ensure that your environment is up-to-date with the latest versions of the tools, before trying to import any projects into it.


Instructions for Eclipse:

Make sure you've already setup Eclipse with the Android Development Tools (ADT) and have downloaded the appropriate Android platforms (e.g., Lollipop 5.0).
Create an Eclipse workspace if you don't already have one.
In Eclipse, use File -> Import...
Expand General and choose 'Existing Projects into Workspace'. Press Next.
Click on 'Select archive file:'
Press the Browse... button next to this entry to get a file chooser dialog.
Navigate to the directory/folder where the download project zip file is, choose the desired zip file, and press Open.
By default all projects are checked, so uncheck any that you do not want at this time; you can always add them later.
Press the Finish button.
You may get errors if the Android platform the project wants is not available in your environment. You can then either change the project to use a different platform, or go to the SDK Manager and download the missing platform.
If you get other errors on the project, try cleaning it and rebuilding it.



Instructions for Android Studio:

Make sure you've already setup Android Studio and have downloaded the appropriate Android platforms (e.g., Lollipop 5.0).
You will also need to unzip the source code file from our web site, so you can see the directories for each project for the chapter you're interested in.
In Android Studio, press Import Project...
Now navigate to the project folders that you unzipped to.
Select the project you want to import and press OK.
Android Studio will now present a few popup dialogs, such as:
    Import Destination Directory
    Dependency and Gradle fixes
You should be able to simply select the defaults, although if Android Studio needs the location of the Android SDK, or Java JDK, you will need to supply those.
When you press Finish, Android Studio will import the source code and configure Gradle to be able to build this project. Again, if you are prompted with a dialog, you should be able to accept the defaults.
