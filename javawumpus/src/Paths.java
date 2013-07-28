
public class Paths {

	private int[] s;
	public Paths(int[] roomExits) {
		s = roomExits;
	}
	public int room(int i) {
		return s[i];
	}

}
