import java.text.DecimalFormat;

/**
 *
 */
public class ScoredChampion implements Comparable<ScoredChampion> {
	public double winRate;
	public String champion;

	public ScoredChampion(String champion, double wR) {
		this.champion = champion;
		this.winRate = wR;
	}

	public String toString() {
		DecimalFormat df = new DecimalFormat("###.##");
		return champion + ", " + df.format(winRate*100) + "%";
	}

	public int compareTo(ScoredChampion other) {
		double diff = this.winRate - other.winRate;
		if (diff != 0) {
			return (int) (diff/Math.abs(diff));
		}
		return this.champion.compareTo(other.champion);
	}
}