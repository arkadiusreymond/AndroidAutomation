# AndroidAutomation

### Step by step

#### I. Android Studio download and Instalation

1. Download dan install Android Studio, you can download from [here](https://developer.android.com/studio/) 

2. After you finish download Android Studio, Install and setup your android studio. In this stage there is a sdk installation process, make sure this process is successful, this process will take a long time. See image bellow
if you get an error in the sdk installation process, maybe step 3, 4 or 5 will help you

<img width="689" alt="screen shot 2018-10-21 at 12 14 23" src="https://user-images.githubusercontent.com/11746963/47263394-e01ade00-d52a-11e8-923c-b1ce7cce5e76.png">

3. If you get an error like the picture below during the sdk installation process, you just click the retry button

<img width="329" alt="screen shot 2018-10-21 at 12 16 52" src="https://user-images.githubusercontent.com/11746963/47263406-4b64b000-d52b-11e8-83d2-f12128fdd0de.png">

4. If you get the HAXM Installation pop-up as shown below, you just need to enter the password and click OK

<img width="367" alt="screen shot 2018-10-21 at 12 19 36" src="https://user-images.githubusercontent.com/11746963/47263432-cc23ac00-d52b-11e8-8cfb-3ab0294d29db.png">

5. If you get a system extension blocked popup, you just click "Open Securyty Preference" and click "Allow" as shown below

<img width="699" alt="screen shot 2018-10-21 at 12 26 31" src="https://user-images.githubusercontent.com/11746963/47263477-959a6100-d52c-11e8-9256-89a051753a5f.png">

#### II. Download and install Java 8 
1. Download and install Java 8 from [here](https://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html) Download according to the platform you are using

<img width="535" alt="screen shot 2018-10-21 at 12 29 30" src="https://user-images.githubusercontent.com/11746963/47263505-00e43300-d52d-11e8-88f3-7405a1c80369.png"> 

2. Check the installation process was successful, by entering the following command on your terminal
```bash
java -version
```

#### III. Set up Environtment Variables for Java and Android Home

1. If your machine uses mac, and still uses the default terminal, you can simply add the environment variable to the .bash_profile file. Open ypur terminal and type 
```bash
nano ~/.bash_profile
```

2. Add this command to the .bash_profile file
```bash
export JAVA_HOME=$(/usr/libexec/java_home)
export ANDROID_HOME=/Users/<yourmachinename>/Library/Android/sdk/
export PATH=$PATH:$ANDROID_HOME
export PATH=$PATH:$ANDROID_HOME/tools
export PATH=$PATH:$ANDROID_HOME/platform-tools
export PATH="/usr/local/bin:$PATH"
export PATH=$JAVA_HOME/bin:$PATH
```

3. Save and exit from nano, and load the updated bash_profile, with the command
```bash
source ~/.bash_profile
```

4. check by running the adb command in the terminal
```bash
adb
```

#### IV Prepare an Android Emulator

1. If you have previously made an Android project, open any project and enter into the editor, if not, it's simple, just create an empty project and follow the instructions until it enters the editor, as shown below

<img width="754" alt="screen shot 2018-10-21 at 22 53 57" src="https://user-images.githubusercontent.com/11746963/47269160-4aac3800-d584-11e8-9e88-5252a31dcd1b.png">

2. Click tools, and click AVD Manager. 
Don't worry if you don't find the Avd Manager menu under the Tools menu, if you first create an Android Studio project, there will be gradle sync proces which usually can be seen in the lower right corner, if the process is complete the Avd Manager menu will appear under the Tools menu

3. Click "Create Virtual Device"

<img width="582" alt="screen shot 2018-10-21 at 23 03 41" src="https://user-images.githubusercontent.com/11746963/47269264-9dd2ba80-d585-11e8-9fc5-704263e41ff0.png">

4. Select Nexus 4, in this tutorial we use this emulator as the default emulator, after which we can use any emulator

<img width="642" alt="screen shot 2018-10-21 at 23 05 49" src="https://user-images.githubusercontent.com/11746963/47269290-e68a7380-d585-11e8-8a81-b0a87c6a728e.png">

5. Download Lollipop

<img width="492" alt="screen shot 2018-10-21 at 23 09 41" src="https://user-images.githubusercontent.com/11746963/47269339-7fb98a00-d586-11e8-8a68-18575e91a3c1.png">

6. If you have finished downloading, select Lollipop and click Next

<img width="555" alt="screen shot 2018-10-21 at 23 11 56" src="https://user-images.githubusercontent.com/11746963/47269365-e8a10200-d586-11e8-9785-bc5d8f170a39.png">

7. Then click finish

<img width="549" alt="screen shot 2018-10-21 at 23 15 03" src="https://user-images.githubusercontent.com/11746963/47269398-41709a80-d587-11e8-9883-0d5f74426283.png">

8. You can run your emulator directly from the Android studio, or by calling it via the terminal by following these steps
Enter 
```bash
cd $ ANDROID_HOME/tools
```
to check which emulator has been created, run this command
```bash
./emulator -list-avds
```
to run the emulator, run this command
```bash
./emulator @<your_emulator_name>
```


#### V Make Sure the Locator Element Works

1. You can confirm this by running this command in the terminal
```bash
$ANDROID_HOME/tools/bin/uiautomatorviewer
```

<img width="800" alt="screen shot 2018-10-22 at 02 54 48" src="https://user-images.githubusercontent.com/11746963/47271699-e8fcc580-d5a5-11e8-99c9-391869497deb.png">

#### VI. Install NPM and Appium

1. Before you install appium, first you must install npm, if using mac you can download from [here](https://nodejs.org/dist/v8.12.0/node-v8.12.0.pkg)
if your machine is not a mac, you can choose according to your platform [here](https://nodejs.org/en/download/)

2. Open the installation file, and follow it according to the order until the installation process is complete

3. Check the installation process by running the npm command on your terminal, or check the node version

```bash
npm
```
or
```bash
node -v
```
4. Install appium by running this command on your terminal

```bash
sudo npm install -g appium@1.9.1 --unsafe-perm=true --allow-root
```

5. Check the installation process by check the appium version on your terminal
```bash
appium -v
```

6. Install appium doctor by running this command on your terminal
```bash
Sudo npm install -g appium-doctor --unsafe-perm=true --allow-root
```

7. Check the installation process by running the appium doctor command on your terminal
```bash
appium-doctor
``` 

#### VII. Instal IntelliJ IDEA

1. If you use mac download the community version [here](https://www.jetbrains.com/idea/download/download-thanks.html?platform=mac&code=IIC) , or if it's not a mac, you can download it according to the platform [here](https://www.jetbrains.com/idea/download/)

#### VIII. Clone this Repository, and First Run

1.


 
