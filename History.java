import java.io.Serializable;

/**
 * Another small wrapper class which contains wins and losses for a matchup/synergy
 * which is updated whenever the data is being collected (in these files it is simply read)
 */
public class History implements Serializable{
	public int wins;
	public int losses;

	public History() {
		wins = 0;
		losses = 0;
	}

	public double getPerc() {
		return ((double) wins)/((double) wins + losses);
	}

	public void won() {
		this.wins += 1;
	}

	public void lost() {
		this.losses += 1;
	}
}
