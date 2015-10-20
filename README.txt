************************************************************
Latest update: 10/19/2015
Patch 5.20
Initial Push
************************************************************
If you have any questions, comments, concerns, or want to offer your help/ideas, feel free to contact me at chant.kevin@yahoo.com

This program was designed to use data gathered with the Riot API: https://developer.riotgames.com, and I will add a data updating program to this repository within a few days.

Important notes for use:
*ONLY HAS DATA FOR NA currently (will work with other regions but regional variations will not be accounted for)
*DO NOT MODIFY FILES IN THE FOLDER ".data", if they have been changed the code may not run, or may return garbage values
*How to run:
	1. Ensure that you have Java SE 7 installed. Java 8 may work, but I do not guarantee it as some methods/implementations did change between the versions
	2. Get this repository: Either manually download each .java file and folder or use "git clone https://github.com/Kevin-Chant/LoL-ChampionSelectGUI.git" from the command line with git installed.
	3. From the command line within the directory you copied to run "javac *.java" to compile and prepare the files
	4. Then run "java ChampionSelectGUI" to start the GUI. You will be asked to input a few fields initially, then the main League-style GUI will run.
	5. Click the champions in the central pane to ban and pick, noting that the left team is blue, and right is red (THIS IS DIFFERENT FROM LEAGUE'S CONVENTION AND IS SUBJECT TO UPDATE IN THE NEAR FUTURE)

Program info: