# 370 project


<h4>Instruction to setup DataBase</h4>
Download MySQL(version 8.X.XX) and install. Remember your username and password for MySQL.<br>
Use terminal or some other client tools like MySQL Workbench to connect your MySQL.<br>
open the database.txt and copy&paste the content into terminal or client tools executing to add the new database<br>
and data into your MySQL.<br>

<h4>Instruction to setup Backend</h4>
Download STS4(Spring Tools 4 for Eclipse The all-new Spring Tool Suite 4.) from https://spring.io/tools<br>
Extract and then open the SpringToolSuite4.exe. Find a new empty workspace and launch.<br>
File-> import -> type maven choose Existing Maven Projects then Next -> In Root Directory browse<br>
our backend like 370-project\370ProjectBackend\370project then Finish. <br>
(you may need to wait a few minutes for maven downloading dependencies)<br>
Now you can see 370project in your Package Explorer<br>
Java version we use is 1.8.0 (please make sure you have set Home Path and Environment for Java)<br>
Find application.yaml(/370project/src/main/resources/application.yaml) change username and password to your <br>
MySQL username and password (make sure there is a space between key/value like username: root)<br>
Now you can run our project.<br>
Find Application.java(/370project/src/main/java/com/sms/boot/Application.java) <br>
right click -> run as -> Java Application<br>
Our Backend is running now if evenything is OK<br>

<h4>Instruction to setup Frontend</h4>
1, You may need to install nodejs(https://nodejs.org/en/) in your computer first if you don't have one.Please install LTS version.<br>

2, You need to download my 370ProjectFrontend in our gitlab repository.<br>

3, Open your terminal, and go to the path you place your 370ProjectFrontend.<br>

4, Use COMMAND LINE <br>
```console
cd 370ProjectFrontend
```

5, The first time you run the react app, you may start with installization.<br>
USE COMMAND LINE <br>
```console
npm install
```

6, After you finished the installization, you can launch our react app by use COMMAND LINE <br>
```console
npm start
```

7, You can open a browser(Chrome best), and go the http://localhost:3000/.<br>
The first time you launch the react app may take a while for browser to load our web page.<br>


<h4>Instruction to setup Testing</h4> 
Open the backend(make sure you have already set up)<br>
Find AllControllerTest.java(/370project/src/test/java/com/sms/boot/AllControllerTest.java)<br>
Before run it, make sure database is in initial state which means redo <br>
copy&paste the content in database.txt into terminal or client tools executing to add the new database<br>
and data into your MySQL and get start to run Junit test.<br>
right click -> run as -> JUnit Test<br>
There would be no failure if evenything is ok<br>
Or you can run different tests one by one if you want (don't forget redo copy&paste database.txt) but the order of  <br>
EventConrtollerTest -> CalenderControllerTest -> ShareControllerTest should not be reversed<br>