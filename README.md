<hr />

<p>Latest update: 10/22/2015</p>

<p>Patch 5.20</p>

<p>Information Update</p>

<hr />

<p>IF YOU ENCOUNTER A BUG/ERROR DURING USAGE, PLEASE FILL OUT THE FORM AT:
http://goo.gl/forms/se0EBI49lx
This will provide me with all the necessary information to fix the error
as soon as possible.</p>

<p>If you have any questions, comments, concerns, or want to offer your help/ideas, feel free to contact me at chant.kevin@yahoo.com</p>

<p>This program was designed to use data gathered with the Riot API: https://developer.riotgames.com, and I will add a data updating program to this repository within a few days.</p>

<p>Preface: I'm currently about to hit the second wave of midterms and as such I won't have as much time as I'd like to completely overhaul my code in terms of readability
as well as go back and fix the reliance on searching the backend text of other websites. I still plan on working through the next few weeks, but updates will probably be slower and lower-priority fixes won't be implemented for a while.</p>

<p>Important notes for use:
<uol>
<li>ONLY HAS DATA FOR NA currently (will work with other regions but regional variations will not be accounted for)</li>
<li>DO NOT MODIFY FILES IN THE FOLDER ".data", if they have been changed the code may not run, or may return garbage values</li>
<li>How to run:
  <ol>
  <li>Ensure that you have Java SE 7 installed. Java 8 may work, but I do not guarantee it as some methods/implementations did change between the versions. Download: http://tinyurl.com/cnafy3t</li> 
  <li>Get this repository: Either manually download each .java file and the files within each folder or use "git clone https://github.com/Kevin-Chant/LoL-ChampionSelectGUI.git" from the command line with git installed. [How to install git](http://tinyurl.com/pb2tqt4)</li>
  <li>From the command line within the directory you copied to run "javac *.java" to compile and prepare the files. If the error "javac is not recognized as an internal or external command" appears, look into updating the environment variable PATH ([Oracle tutorial](http://tinyurl.com/q8wfejk))</li>
  <li>Then run "java ChampionSelectGUI" to start the GUI. You will be asked to input a few fields initially, then the main League-style GUI will run.</li>
  <li>Click the champions in the central pane to ban and pick, noting that the left team is blue, and right is red (THIS IS DIFFERENT FROM LEAGUE'S CONVENTION AND IS SUBJECT TO UPDATE IN THE NEAR FUTURE)</li>
  </ol>
  </li>
  </uol>
  </p>

<p>Program Development Information (process) for those interested:
    
I started this project at the end of the Spring 2015 semester as a fun idea of "what if I could tell someone what is optimal for them to play." At the time I had no real idea how to combine the data, and I didn't use nearly the depth of stats I do now (I was basically just comparing the global winrate of a champion and the user's winrate).

But, that got me started on developing the GUI and getting all the grunt work out of the way (getting every champion's 32x32 and 64x64 pixel square), and I continued into the summer, teaching myself about the javax.swing suite along the way. A few weeks into summer, my job lifeguarding started up right around the time I hit a roadblock on not knowing how to continue the GUI (how to put a champion's icon into the banned or picked area), so I nearly abandoned the project.

A few weeks ago I decided to start again and see if I could revisit and finish up what I had started, but had to overhaul almost the whole thing. I sat down with a few friends (and a statistics major or two) to try to figure out what calculation I should be running to determine a scoring methodology, and after quite a few failed attempts at encompassing the diminishing returns nature of the data, finally settles on using the output of a Sigmoid function:</p>

<p>[This was the function we chose](http://artint.info/figures/ch07/sigmoidc.gif), and what we did was essentially calculate a "horizontal offset" for each ally and enemy champion. The win rates (global and champion combination) went on the Y axis, and from those we determined the correspoding X values, and subtracted to find the offset.

We then stated that the total effect of the other 9 characters was simply the sum of these horizontal offsets with the default X value passed back through the function to find a percentage. This is where the relationship between the concepts of "scores" and "winrates" gets a bit fuzzy. Because the output value is just a winrate run both ways through the function, it resembles a winrate, but isn't truly one. It is, however, a good relative score between multiple champions.

After the calculations had been set up, I had to start collecting data from the
Riot API (which I hadn't used before and had planned not to use, but the data
simply wasn't available online), which was a project all on its own, and is why
the data set is currently limited to 10,000 games total (for perspective:
Lolking's bottom lane synergy chart has over 60,000 matches played with ONE
COMBINATION for a month), though should rise as soon as I can demonstrate that
this program is worthy of a production key.
</p>

<p>Now that the data and program are in place, what next?</p>

<p>There's always room for improvement in making the code easier to read, which would
help it be a learning tool for people interested in league and/or statistical
analysis, and bug fixing, as well as minor tune-ups in ease of use and aesthetics.
However, unless the prediction formulas turn out to be flawed in some way I don't
forsee much change beyond these first few weeks or months. I'll definitely look
towards packaging all of this into one executable file, and maybe if it makes
sense, create a website where this runs as an applet.
Though who knows what the future will hold?</p>

<p>If you've gotten this far, thanks for reading! If you have any questions or even just want to give me feedback, I'd love an email! Hopefully this was at the very least an interesting project for you to see, but maybe even something useful.</p>

