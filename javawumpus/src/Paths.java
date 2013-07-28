
public class Paths {

	private int[] s;
	public Paths(int[] roomExits) {
		s = roomExits;
	}
	public int room(int i) {
		return s[i];
	}
	public boolean canGetToRoom(int endLocation) {
		for (int i = 1; i <= 3; ++i) {
			if (s[i] == endLocation)
				return true;
		}
		return false;
	}

}
