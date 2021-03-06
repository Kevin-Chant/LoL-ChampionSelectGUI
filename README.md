<p>Latest update: 11/20/2015</p>

<p>Patch 5.22</p>

<p>Check out the <a href = "http://kevin-chant.github.io/LoL-ChampionSelectGUI">official website</a>! (Work in Progress)</p>

<hr />

<p>IF YOU ENCOUNTER A BUG/ERROR DURING USAGE, PLEASE FILL OUT THE <a href="http://goo.gl/forms/se0EBI49lx">BUG-REPORTING FORM</a>.


This will provide me with all the necessary information to fix the error
as soon as possible.</p>

<p>If you have any questions, comments, concerns, or want to offer your help/ideas, feel free to contact me at chant.kevin@yahoo.com</p>

<p>This program was designed to use data gathered with the <a href="https://developer.riotgames.com">Riot API</a>, and I will add a data updating program to this repository within a few days.</p>

<p>Preface: I'm currently about to hit the second wave of midterms and as such I won't have as much time as I'd like to completely overhaul my code in terms of readability
as well as go back and fix the reliance on searching the backend text of other websites. I still plan on working through the next few weeks, but updates will probably be slower and lower-priority fixes won't be implemented for a while.</p>

<p>Important notes for use:
<uol>
<li>Only has matchup information for NA currently (will work with other regions but regional variations will not be accounted for)</li>
<li>DO NOT MODIFY INTERNAL FILES IN THE JAR, especilly those in ".data". if they have been changed the code may not run, or may return garbage values</li>
<li>How to run:
  <ol>
  <li>Ensure that you have Java SE 7 installed. Java 8 may work, but I do not guarantee it as some methods/implementations did change between the versions. <a href ="http://tinyurl.com/cnafy3t">Download Java 7</a></li> 
  <li>Download the program: Save LoL-ChampionSelectHelper.jar somewhere on your computer where you don't mind it creating a data folder</li>
  <li>Simply double click the file and you're good to go! If the error "javac is not recognized as an internal or external command" appears, look into updating the environment variable PATH (<a href="http://tinyurl.com/q8wfejk">Oracle Tutorial</a>)</li>
  <li>You will be asked to input a few fields initially, then the main League-style GUI will run.</li>
  <li>Click the champions in the central pane to ban and pick, noting that the left team is blue, and right is red (THIS IS DIFFERENT FROM LEAGUE'S CONVENTION AND IS SUBJECT TO UPDATE IN THE NEAR FUTURE)</li>
  <li>After you're done selecting, a pop-up will appear detailing the top 5 recommendations (not in order) alongside their scores.</li>
  </ol>
  </li>
<li>If the program freezes, exits unexpectedly, or otherwise errors, run the Debug.bat script to see the error output to the command line. This will be useful in creating a helpful bug-reporting form </li>
  </uol>
  </p>

<p>How the algorithm actually works:</p>

<p>The program determines a set of champions that could be played (factoring in your role this game and what has been picked/banned), then goes through each one and calculates a score. This score is determined by how each ally/enemy pick affects the winrate of the champion, as well as the user's winrate (assuming a minimum number of ranked games has been played). After going through every possibility, it shows the top 5 scored champions to the user as its recommendations.</p>

<p>Program Development Information (process) for those interested:</p>
    
<p>I started this project at the end of the Spring 2015 semester as a fun idea of "what if I could tell someone what is optimal for them to play." At the time I had no real idea how to combine the data, and I didn't use nearly the depth of stats I do now (I was basically just comparing the global winrate of a champion and the user's winrate).</p>

<p>But, that got me started on developing the GUI and getting all the grunt work out of the way (getting every champion's 32x32 and 64x64 pixel square), and I continued into the summer, teaching myself about the javax.swing suite along the way. A few weeks into summer, my job lifeguarding started up right around the time I hit a roadblock on not knowing how to continue the GUI (how to put a champion's icon into the banned or picked area), so I nearly abandoned the project.</p>

<p>A few weeks ago I decided to start again and see if I could revisit and finish up what I had started, but had to overhaul almost the whole thing. I sat down with a few friends (and a statistics major or two) to try to figure out what calculation I should be running to determine a scoring methodology, and after quite a few failed attempts at encompassing the diminishing returns nature of the data, finally settles on using the output of a Sigmoid function:</p>

<p><a href="http://artint.info/figures/ch07/sigmoidc.gif">This was the function we chose</a>, and what we did was essentially calculate a "horizontal offset" for each ally and enemy champion. The win rates (global and champion combination) went on the Y axis, and from those we determined the correspoding X values, and subtracted to find the offset.</p>

<p>We then stated that the total effect of the other 9 characters was simply the sum of these horizontal offsets with the default X value passed back through the function to find a percentage. This is where the relationship between the concepts of "scores" and "winrates" gets a bit fuzzy. Because the output value is just a winrate run both ways through the function, it resembles a winrate, but isn't truly one. It is, however, a good relative score between multiple champions.</p>

<p>After the calculations had been set up, I had to start collecting data from the Riot API (which I hadn't used before and had planned not to use, but the data simply wasn't available online), which was a project all on its own, and is why the data set is currently limited to 10,000 games total (for perspective: Lolking's bottom lane synergy chart has over 60,000 matches played with ONE COMBINATION for a month), though should rise as soon as I can demonstrate that this program is worthy of a production key.</p>

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

