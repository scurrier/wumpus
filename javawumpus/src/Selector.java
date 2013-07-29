import java.util.Random;


public class Selector {
	public Random random;

	public Selector(Random random) {
		this.random = random;
	}

	public int fnA(Wumpus wumpus) {
		return random.nextInt(20) + 1;
	}

	public int fnB(Wumpus wumpus) {
		return random.nextInt(3) + 1;
	}

	public int fnC(Wumpus wumpus) {
		return random.nextInt(4) + 1;
	}
}