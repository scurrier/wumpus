
public class Map {
	private Paths[] s;
	static Paths paths(int exit1, int exit2, int exit3) { return new Paths(new int[]{exit1,exit2,exit3}); }

	public Map() {
		this.s = new Paths[] {
				paths(2,5,8),		paths(1,3,10),		paths(2,4,12),		paths(3,5,14),		paths(1,4,6),
				paths(5,7,15),		paths(6,8,17),		paths(1,7,9),		paths(8,10,18),		paths(2,9,11),
				paths(10,12,19),	paths(3,11,13),		paths(12,14,20),	paths(4,13,15),		paths(6,14,16),
				paths(15,17,20),	paths(7,16,18),		paths(9,17,19),		paths(11,18,20),	paths(13,16,19)};
	}

	public boolean isValidMove(int startLocation, int endLocation) {
		return s[startLocation-1].canGetToRoom(endLocation);
	}

	public Paths getRoomExits(int room) {
		return s[room-1];
	}
}