import java.util.Comparator;

/**
 * A simple utility comparator to create an ordered PriorityQueue for the
 * results of processing the data.
 */
public class ScoredChampionComparator implements Comparator<ScoredChampion> {
	public int compare(ScoredChampion sC1, ScoredChampion sC2) {
		double diff = sC1.winRate - sC2.winRate;
		if (diff != 0) {
			return (int) (diff/Math.abs(diff));
		}
		return sC1.champion.compareTo(sC2.champion);
	}

	public boolean equals(ScoredChampion sC1, ScoredChampion sC2) {
		return (sC1.winRate == sC2.winRate && sC1.champion.equals(sC2.champion));
	}
}