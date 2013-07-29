import java.util.Random;


public class Selector {
	public Random random;

	public Selector(Random random) {
		this.random = random;
	}

	public int pickRoom() {
		return random.nextInt(20) + 1;
	}

	public int pickPath() {
		return random.nextInt(3) + 1;
	}

	public int pickMove() {
		return random.nextInt(4) + 1;
	}
}