
public class Map {
	private int[][] s;

	public Map() {
		this.s = new int[][] {{0,0,0,0},
				{0,2,5,8},		{0,1,3,10},		{0,2,4,12},		{0,3,5,14},		{0,1,4,6},
				{0,5,7,15},		{0,6,8,17},		{0,1,7,9},		{0,8,10,18},	{0,2,9,11},
				{0,10,12,19},	{0,3,11,13},	{0,12,14,20},	{0,4,13,15},	{0,6,14,16},
				{0,15,17,20},	{0,7,16,18},	{0,9,17,19},	{0,11,18,20},	{0,13,16,19}};
	}

	public boolean isValidMove(int startLocation, int endLocation) {
		for (int i = 1; i <= 3; ++i) {
			if (s[startLocation][i] == endLocation)
				return true;
		}
		return false;
	}

	public int[] getRoomExits(int room) {
		return s[room];
	}
}