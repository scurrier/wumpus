public class MapItemLocations {
	public int[] mapItemLocations;
	public int[] copyOfMapItemlocations;

	public MapItemLocations() {
		this.mapItemLocations = new int[7];
		this.copyOfMapItemlocations = new int[7];
	}

	private void randomizeMapItemLocations(Selector selector) {
		for (int j = 1; j <= 6; ++j) {
			mapItemLocations[j] = selector.pickRoom(); // 175 l(j) = fna(0)
			copyOfMapItemlocations[j] = mapItemLocations[j]; // 180 m(j) = l(j)
		}
	}

	public boolean crossover() {
		for (int j = 1; j <= 6; ++j) {
			for (int k = 1; k <= 6; ++k) {
				if (j == k)
					continue;
				if (mapItemLocations[j] == mapItemLocations[k])
					return true;
			}
		}
		return false;
	}

	public void placeItemsOnMap(Selector selector) {
		do {
			randomizeMapItemLocations(selector);
		} while (crossover());
	}
}