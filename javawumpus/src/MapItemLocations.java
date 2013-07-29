
public class MapItemLocations {
	public int[] mapItemLocations;
	public int[] copyOfMapItemlocations;

	public MapItemLocations() {
		this.mapItemLocations = new int[7];
		this.copyOfMapItemlocations = new int[7];
	}

	public void randomizeMapItemLocations(Selector selector) {
		for (int j = 1; j <= 6; ++j) {
			mapItemLocations[j] = selector.pickRoom();																	// 175 l(j) = fna(0)
			copyOfMapItemlocations[j] = mapItemLocations[j];																	// 180 m(j) = l(j)
		}
	}
}