import java.util.ArrayList;

public class Calculations {
	public static double calculateWinMultiplier(ArrayList<History> allyHists, ArrayList<History> enemyHists, double globalWin) {
		double differentialSum = 0;
		double xpos = inv(globalWin);
		for (History aH : allyHists) {
			double perc = aH.getPerc();
			differentialSum += (inv(perc) - xpos);
		}
		for (History eH : enemyHists) {
			double perc = eH.getPerc();
			differentialSum += (inv(perc) - xpos);
		}
		return sig(xpos + differentialSum);
	}

	public static double sig(double x) {
		return 1/(1 + Math.pow(Math.E, -x));
	}

	public static double inv(double perc) {
		perc = perc/100;
		return Math.log(perc/(((double) 1)-perc));

	}
}