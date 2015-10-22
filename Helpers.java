import java.util.HashMap;
import java.io.FileInputStream;
import java.io.ObjectInputStream;
import java.io.FileOutputStream;
import java.io.ObjectOutputStream;
import java.util.Date;

/**
 * A class of static helper methods that involve either reading or writing local data files
 * as well as combineAlphabetical which creates an alphabetized key to be used in the History maps
 */
public class Helpers {
	public static String combineAlphabetical(String s1, String s2) {
		if (s1.compareTo(s2) < 0) {
			return s1 + "," + s2;
		}
		return s2 + "," + s1;
	}

	public static HashMap<String, History> loadHistMap(boolean ally) {
		try {
			FileInputStream fis = null;
			if (ally) {
				fis = new FileInputStream(".data/history data/.allyHistMap.ser");
			} else {
				fis = new FileInputStream(".data/history data/.enemyHistMap.ser");
			}
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, History> map = (HashMap<String, History>) ois.readObject();
			ois.close();
			fis.close();
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	public static HashMap<String, Double> loadUserData(String summonerName, String region) {
		try {
			FileInputStream fis = new FileInputStream(".data/user/" + region + "/" + summonerName + ".ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, Double> map = (HashMap<String, Double>) ois.readObject();
			ois.close();
			fis.close();
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	public static HashMap<String, Double> loadGlobalData(String region) {
		try {
			FileInputStream fis = new FileInputStream(".data/global/" + region + ".ser");
			ObjectInputStream ois = new ObjectInputStream(fis);
			HashMap<String, Double> map = (HashMap<String, Double>) ois.readObject();
			ois.close();
			fis.close();
			return map;
		} catch (Exception e) {
			return null;
		}
	}

	public static void saveUserData(HashMap<String, Double> map, String summonerName, String region) {
		try {
			FileOutputStream fos = new FileOutputStream(".data/user/" + region + "/" + summonerName + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveGlobalData(HashMap<String, Double> map, String region) {
		try {
			FileOutputStream fos = new FileOutputStream(".data/global/" + region + ".ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(map);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveUserUpdateTime(String summonerName, String region, Date d) {
		try {
			FileOutputStream fos = new FileOutputStream(".data/user/" + region + "/" + summonerName + "updatetime.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(d);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void saveGlobalUpdateTime(String region, Date d) {
		try {
			FileOutputStream fos = new FileOutputStream(".data/global/" + region + "updatetime.ser");
			ObjectOutputStream oos = new ObjectOutputStream(fos);
			oos.writeObject(d);
			oos.close();
			fos.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}