import java.io.File;
import java.io.BufferedReader;
import java.util.Scanner;
import java.net.URL;
import java.io.InputStreamReader;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.BufferedWriter;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Collections;
import java.util.Map;
import java.util.ArrayList;
import java.util.TreeSet;
import java.util.Comparator;
import java.io.FileInputStream;
import java.util.Date;

public class ChampStatisticReader {

    public static HashMap<String, Double> collectGlobalStatistics(String region) {
        HashMap<String, Double> result = new HashMap<String, Double>();
        int regionVal = 0;
        switch (region) {
            case("NA"):
                regionVal = 1;
                break;
            case("EUW"):
                regionVal = 2;
                break;
            case("EUNE"):
                regionVal = 3;
                break;
            case("BR"):
                regionVal = 4;
                break;
            case("TR"):
                regionVal = 5;
                break;
            case("RU"):
                regionVal = 6;
                break;
            case("LAN"):
                regionVal = 7;
                break;
            case("LAS"):
                regionVal = 8;
                break;
            case("OCE"):
                regionVal = 9;
                break;
            case("KR"):
                regionVal = 10;
                break;
            default:
                break;
        }
        try {
            String URL = "http://loldb.gameguyz.com/statistics/winRate/" + regionVal + "/0/2/0/0/30";
            URL website = new URL(URL);
            Scanner input = new Scanner(website.openStream());
            boolean winRate = false;
            String curr;
            String currChamp = "";
            while (input.hasNextLine()) {
                curr = input.nextLine();
                if (curr.contains("dname")) {
                    currChamp = curr.split("\"")[1];
                }
                if (winRate) {
                    result.put(currChamp, Double.parseDouble(curr.split("\"")[1].split("%")[0]));
                    winRate = false;
                }
                if (curr.contains("ar ar3")) {
                    winRate = true;
                }
            }
            Helpers.saveGlobalData(result, region);
            Helpers.saveGlobalUpdateTime(region, new Date());
            return result;
        } catch (Exception e) {
            System.out.println("There was an error compiling the data.");
            System.out.println(e);
            return result;
        }
    }

    public static HashMap<String, Double> collectUserStatistics(String summonerName, String region, int minGames) {
        HashMap<String, String> DICTIONARY = new HashMap<String,String>();
        try {
            String encoder = "UTF-8";
            FileInputStream in = new FileInputStream(".data/championlists/Champ_dictionary.txt");
            BufferedReader input = new BufferedReader(new InputStreamReader(in, encoder));
            String curr;
            while ((curr = input.readLine()) != null) {
                DICTIONARY.put(curr.split(",")[0], curr.split(",")[1].split("\n")[0]);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        HashMap<String, Double> result = new HashMap<String, Double>();
        try {
            String[] parts = summonerName.split(" ");
            summonerName = "";
            for (String s : parts) {
                summonerName += s;
            }
            String urlRegion = region;
            if (region.equals("kr")) {
                urlRegion = "";
            } else {
                urlRegion += ".";
            }
            URL URL = new URL("http://" + urlRegion.toLowerCase() +"op.gg/summoner/champions/userName=" + summonerName);
            String encoder = "UTF-8";
            InputStream in = URL.openConnection().getInputStream();
            BufferedReader input = new BufferedReader(new InputStreamReader(in, encoder));
            boolean reachedData = false;
            String currChamp = "";
            String curr;
            int currWins = 0;
            int currLosses = 0;
            int line = 0;
            while (((curr = input.readLine()) != null) && (!curr.contains("</table"))) {
                line += 1;
                if (!reachedData) {
                    if (curr.contains("ChampionStatsTable")) {
                        reachedData = true;            
                    }
                    continue;
                }
                if (curr.contains("ChampionName Cell")) {
                    currChamp = curr.split(">")[1].split("<")[0];
                    if (DICTIONARY.containsKey(currChamp)){
                        currChamp = DICTIONARY.get(currChamp);
                    } else {
                        System.out.println("Oops, Dictionary does not contain the translation of: " + currChamp + " (line " + line + ")");
                    }
                    continue;
                }
                if (curr.contains("Text Left")) {
                    curr = curr.split(">")[1].split("<")[0];
                    curr = curr.substring(0, curr.length()-1);
                    currWins = Integer.parseInt(curr);
                    continue;
                }
                if (curr.contains("Text Right")) {
                    curr = curr.split(">")[1].split("<")[0];
                    curr = curr.substring(0, curr.length()-1);
                    currLosses = Integer.parseInt(curr);
                }
                if (curr.contains("span class")) {
                    if ((currWins + currLosses) > minGames) result.put(currChamp, Double.parseDouble(curr.split(">")[1].split("%")[0]));
                    continue;
                }
            }
        } catch (Exception e) {
            System.out.println("There was an error compiling the data.");
            e.printStackTrace();
            return result;
        }
        Helpers.saveUserData(result, summonerName, region);
        Helpers.saveUserUpdateTime(summonerName, region, new Date());
        return result;
    }
}
