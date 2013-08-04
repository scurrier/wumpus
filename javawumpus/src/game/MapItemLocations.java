package game;
import uio.UserInteraction;

public class MapItemLocations {
	private int[] mapItemLocations;
	private int[] copyOfMapItemlocations;

	public MapItemLocations() {
		this.mapItemLocations = new int[7];
		this.copyOfMapItemlocations = new int[7];
	}

	public MapItemLocations(int[] mapItems) {
		this();
		this.mapItemLocations = mapItems;
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

	public void randomize(Selector selector) {
		do {
			randomizeMapItemLocations(selector);
		} while (crossover());
	}

	private void randomizeMapItemLocations(Selector selector) {
		for (int j = 1; j <= 6; ++j) {
			mapItemLocations[j] = selector.pickRoom();
			copyOfMapItemlocations[j] = mapItemLocations[j];
		}
	}

	void reset() {
		for (int j = 1; j <= 6; ++j) {
			mapItemLocations[j] = copyOfMapItemlocations[j];
		}
	}

	void setWumpus(int newLocation) {
		mapItemLocations[2] = newLocation;
	}

	int getWumpus() {
		return mapItemLocations[2];
	}

	boolean isWumpus(int location) {
		return location == getWumpus();
	}

	public int getPlayer() {
		return mapItemLocations[1];
	}

	void setPlayer(int newLocation) {
		mapItemLocations[1] = newLocation;
	}

	boolean isBat(int newLocation) {
		return newLocation == mapItemLocations[5] || newLocation == mapItemLocations[6];
	}

	boolean isPit(int newLocation) {
		return newLocation == mapItemLocations[3] || newLocation == mapItemLocations[4];
	}

	public void printNearbyItemHints(UserInteraction ui, Paths roomExits) {
		for (int j = 2; j <= 6; ++j) {
			if (roomExits.canGetToRoom(mapItemLocations[j]))
				printItemNearbyPlayerHint(ui, j - 1);
			
		}
	}

	private void printItemNearbyPlayerHint(UserInteraction ui, int itemType) {
		switch(itemType) {																			// 610 on j-1 goto 615,625,x,635,635
				case 1: ui.println("I SMELL A WUMPUS!"); break;
				case 2:
				case 3: ui.println("I FEEL A DRAFT"); break;
				case 4:
				case 5: ui.println("BATS NEARBY!"); break;
				};
	}
}