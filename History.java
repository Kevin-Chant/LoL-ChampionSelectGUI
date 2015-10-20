import java.io.Serializable;

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
