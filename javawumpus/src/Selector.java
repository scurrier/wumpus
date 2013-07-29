import java.util.Random;


public class Selector {
	public Random random;

	public Selector(Random random) {
		this.random = random;
	}

	public int pickRoom(Wumpus wumpus) {
		return random.nextInt(20) + 1;
	}

	public int pickPath(Wumpus wumpus) {
		return random.nextInt(3) + 1;
	}

	public int pickMove(Wumpus wumpus) {
		return random.nextInt(4) + 1;
	}
}