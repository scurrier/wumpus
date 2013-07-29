import java.io.IOException;
import java.util.Random;


public class Wumpus {

	char i$ = '\0';
	private Map map = new Map();
	protected MapItemLocations items = new MapItemLocations();
	private int availableArrows = 5;
	protected UserInteraction ui = new UserInteraction();

	public Selector selector = new Selector(new Random());
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Wumpus game = new Wumpus();
		try {
			game.run();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void run() throws IOException {
		int f = 0;
		ui.giveInstructionsIfNeeded();
		placeItemsOnMap();
		do { 
			availableArrows = 5;
			ui.gameStartHeader();
			do {
				printPlayerStatus();
				if (1 == ui.getMoveShootChoiceFromPlayer())
					f = shoot();
				else
					f = movePlayerToLocation(getNewPlayerLocation()); 
			} while (f == 0);
			if (f < 0)
				ui.youLose();
			else
				ui.youWin();

			resetItemLocations();
		
			if (useSameSetup()) 
				placeItemsOnMap();
		} while (true);
	}
	private void resetItemLocations() {
		for (int j = 1; j <= 6; ++j) {
			items.mapItemLocations[j] = items.copyOfMapItemlocations[j];
		}
	}
	private boolean useSameSetup() throws IOException {
		ui.print("SAME SETUP (Y-N)");
		i$ = (char) ui.readChar(); ui.readChar(); 
		boolean useSameSetup = i$ != 'Y' && i$ != 'y';
		return useSameSetup;
	}
	public int movePlayerToLocation(int newLocation) {
		items.mapItemLocations[1] = newLocation;
		
		if (newLocation == getWumpus()) {
			ui.println("... OOPS! BUMPED A WUMPUS!");
			int f = moveWumpus();
			if (f != 0) 
				return f;
		}
		
		if (newLocation == items.mapItemLocations[3] || newLocation == items.mapItemLocations[4]) {
			ui.println("YYYYIIIIEEEE . . . FELL IN PIT");
			return -1;
		}
		
		if (newLocation == items.mapItemLocations[5] || newLocation == items.mapItemLocations[6]) {
			ui.println("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!");
			return movePlayerToLocation(selector.pickRoom(this));
		}
		return 0;
	}
	public int getNewPlayerLocation() {
		boolean validMove;
		int newLocation;
		do {
			newLocation = ui.getMoveDirection();
			validMove = isValidPlayerMove(newLocation);
			if (!validMove)
				ui.print("NOT POSSIBLE - ");											// 1030 print "NOT POSSIBLE -";
		} while (!validMove);
		return newLocation;
	}

	private boolean isValidPlayerMove(int newLocation) {
		if (map.isValidMove(playerLocation(), newLocation))
			return true;
		if (newLocation == playerLocation())
			return true;
		return false;
	}
	private int shoot() {
		int j9 = getShotDistanceFromPlayer();
		int[] p = getIntendedFlightPathFromPlayer(j9);
																					// 795 rem *** SHOOT ARROW ***
		int f = shootArrow(j9, p);
		boolean gameEnded = (f != 0);
		if (gameEnded)
			return f;
		ui.println("MISSED");													// 845 print "MISSED"
		f = moveWumpus();																// 860 gosub 935
																					// 865 rem *** AMMO CHECK ***
		availableArrows = availableArrows - 1;																	// 870 a = a-1
		if (availableArrows <= 0)													// 875 if a > 0 then 885
			f = -1;																		// 880 f = -1
		return f;
	}
	public int moveWumpus() {
		int k = selector.pickMove(this);																		// 940 k = fnc(0)
		if (k < 4) {													// 945 if k = 4 then 955
			setWumpus(map.getRoomExits(getWumpus()).room(k));
		}																// 950 l(2) = s(l(2),k)
		if (getWumpus() == playerLocation()) {												// 955 if l(2) <> l then 970
			ui.println("TSK TSK TSK - WUMPUS GOT YOU!");							// 960 print "TSK TSK TSK - WUMPUS GOT YOU!"
			return -1;																		// 965 f = -1
		}
		return 0;
	}
	private int setWumpus(int newLocation) {
		return items.mapItemLocations[2] = newLocation;
	}
	private int getWumpus() {
		return items.mapItemLocations[2];
	}
	public int shootArrow(int shotDistance, int[] arrowPath) {
		int ll = playerLocation();																		// 800 l = l(1)
		for (int k2 = 1; k2 <= shotDistance; ++k2) {
			ll = map.isValidMove(ll, arrowPath[k2]) ? arrowPath[k2] : map.getRoomExits(ll).room(selector.pickPath(this));																// 830 l = s(l,fnb(1))
			if (ll == getWumpus()) {												// 900 if l <> l(2) then 920
				ui.println("AHA! YOU GOT THE WUMPUS!");								// 905 print "AHA! YOU GOT THE WUMPUS!"
				return 1;																			// 910 f = 1
			}
																			// 915 return
			if (ll == playerLocation()) {												// 920 if l <> l(1) then 840
				ui.println("OUCH! ARROW GOT YOU!");									// 925 print "OUCH! ARROW GOT YOU!"
				return -1;
			}
		}
		return 0;
	}
	public int[] getIntendedFlightPathFromPlayer(int numberOfRooms) {
		int[] p = new int[6];
		for (int k = 1; k <= numberOfRooms; ++k) {
			do {
				ui.print("ROOM # ");													// 760 print "ROOM #";
				p[k] = ui.readInt();																// 765 input p(k)
				if (did180(p, k))
					ui.println("ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM");			// 780 print "ARROWS AREN'T THAT CROOKED - TRY ANOTHER ROOM"
			} while(did180(p, k));
		}
		return p;
	}
	public boolean did180(int[] path, int roomCount) {
		return roomCount > 2 && path[roomCount] == path[roomCount-2];
	}
	public int getShotDistanceFromPlayer() {
		int numberOfRoomsToShoot;
		do {
			ui.print("NO. OF ROOMS (1-5) ");										// 735 print "NO. OF ROOMS (1-5)";
			numberOfRoomsToShoot = ui.readInt();																// 740 input j9
		} while (outOfArrowRange(numberOfRoomsToShoot));
		return numberOfRoomsToShoot;
	}
	private boolean outOfArrowRange(int numberOfRoomsToShoot) {
		return numberOfRoomsToShoot < 1 || numberOfRoomsToShoot > 5;
	}

	public void placeItemsOnMap() {
		do {
					randomizeMapItemLocations();
			}  while (crossover());
	}
	public void printPlayerStatus() {
		ui.println("");														// 590 print
		printNearbyItemHints();
		printPlayerLocation();
		printTunnelOptions();
		ui.println("");														// 660 print
	}
	public void printTunnelOptions() {
		Paths room = map.getRoomExits(playerLocation());
		ui.print("TUNNELS LEAD TO "); ui.print(room.room(1));
					ui.print(" "); ui.print(room.room(2)); 
					ui.print(" "); ui.println(room.room(3));
	}
	public void printPlayerLocation() {
		ui.print("YOUR ARE IN ROOM "); ui.println(playerLocation());				// 650 print "YOU ARE IN ROOM ";l(1)
	}

	public void printNearbyItemHints() {
		for (int j = 2; j <= 6; ++j) {
			Paths roomExits = map.getRoomExits(playerLocation());
				if (roomExits.canGetToRoom(items.mapItemLocations[j]))
					printItemNearbyPlayerHint(j - 1);

		}
	}

	public void printItemNearbyPlayerHint(int itemType) {
		switch(itemType) {																			// 610 on j-1 goto 615,625,x,635,635
				case 1: ui.println("I SMELL A WUMPUS!"); break;
				case 2:
				case 3: ui.println("I FEEL A DRAFT"); break;
				case 4:
				case 5: ui.println("BATS NEARBY!"); break;
				};
	}
	public boolean crossover() {
		for (int j = 1; j <= 6; ++j) {
			for (int k = 1; k <= 6; ++k) {
				if (j == k) continue;
				if (items.mapItemLocations[j] == items.mapItemLocations[k])
					return true;
			}
		}
		return false;
	}
	
	private void randomizeMapItemLocations() {
		for (int j = 1; j <= 6; ++j) {
			items.mapItemLocations[j] = selector.pickRoom(this);																	// 175 l(j) = fna(0)
			items.copyOfMapItemlocations[j] = items.mapItemLocations[j];																	// 180 m(j) = l(j)
		}
	}
	private int playerLocation() {
		return items.mapItemLocations[1];
	}
}
