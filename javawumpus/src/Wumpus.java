import java.io.IOException;
import java.util.Random;


public class Wumpus {

	private static final int PLAYING = 0;
	private static final int WON = 1;
	private static final int LOST = -1;
	Map map = new Map();
	public MapItemLocations items = new MapItemLocations();
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
		int f = PLAYING;
		ui.giveInstructionsIfNeeded();
		items.randomize(selector);
		do { 
			availableArrows = 5;
			ui.gameStartHeader();
			do {
				printPlayerStatus();
				if (1 == ui.getMoveShootChoiceFromPlayer())
					f = shoot();
				else
					f = movePlayerToLocation(getNewPlayerLocation()); 
			} while (f == PLAYING);
			if (f == LOST)
				ui.youLose();
			else
				ui.youWin();

			items.reset();
		
			if (ui.useSameSetup()) 
				items.randomize(selector);
		} while (true);
	}
	public int movePlayerToLocation(int newLocation) {
		items.setPlayer(newLocation);
		
		if (items.isWumpus(newLocation)) {
			int f = bumpedIntoWumpus();
			if (f != PLAYING) 
				return f;
		}
		
		if (items.isPit(newLocation)) {
			int f = fellInPit();
			if (f != PLAYING) 
				return f;
		}
		
		if (items.isBat(newLocation)) {
			int f = foundABat();
			if (f != PLAYING) 
				return f;
		}
		return PLAYING;
	}
	private int foundABat() {
		ui.println("ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!");
		int f = movePlayerToLocation(selector.pickRoom());
		return f;
	}
	private int fellInPit() {
		ui.println("YYYYIIIIEEEE . . . FELL IN PIT");
		int f = LOST;
		return f;
	}
	private int bumpedIntoWumpus() {
		ui.println("... OOPS! BUMPED A WUMPUS!");
		int f = moveWumpus();
		return f;
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
		if (map.isValidMove(items.getPlayer(), newLocation))
			return true;
		if (newLocation == items.getPlayer())
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
			f = LOST;																		// 880 f = -1
		return f;
	}
	public int moveWumpus() {
		int k = selector.pickMove();																		// 940 k = fnc(0)
		if (k < 4) {													// 945 if k = 4 then 955
			int newLocation = map.getRoomExits(items.getWumpus()).room(k);
			items.setWumpus(newLocation);
		}																// 950 l(2) = s(l(2),k)
		if (items.isWumpus(items.getPlayer())) {												// 955 if l(2) <> l then 970
			ui.println("TSK TSK TSK - WUMPUS GOT YOU!");							// 960 print "TSK TSK TSK - WUMPUS GOT YOU!"
			return LOST;																		// 965 f = -1
		}
		return PLAYING;
	}
	public int shootArrow(int shotDistance, int[] arrowPath) {
		int ll = items.getPlayer();																		// 800 l = l(1)
		for (int k2 = 1; k2 <= shotDistance; ++k2) {
			ll = map.isValidMove(ll, arrowPath[k2]) ? arrowPath[k2] : map.getRoomExits(ll).room(selector.pickPath());																// 830 l = s(l,fnb(1))
			if (items.isWumpus(ll)) {												// 900 if l <> l(2) then 920
				ui.println("AHA! YOU GOT THE WUMPUS!");								// 905 print "AHA! YOU GOT THE WUMPUS!"
				return WON;																			// 910 f = 1
			}
																			// 915 return
			if (ll == items.getPlayer()) {												// 920 if l <> l(1) then 840
				ui.println("OUCH! ARROW GOT YOU!");									// 925 print "OUCH! ARROW GOT YOU!"
				return LOST;
			}
		}
		return PLAYING;
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

	public void printPlayerStatus() {
		ui.println("");														// 590 print
		items.printNearbyItemHints(ui, map.getRoomExits(items.getPlayer()));
		printPlayerLocation();
		printTunnelOptions();
		ui.println("");														// 660 print
	}
	public void printTunnelOptions() {
		Paths room = map.getRoomExits(items.getPlayer());
		ui.print("TUNNELS LEAD TO "); ui.print(room.room(1));
					ui.print(" "); ui.print(room.room(2)); 
					ui.print(" "); ui.println(room.room(3));
	}
	public void printPlayerLocation() {
		ui.print("YOUR ARE IN ROOM "); ui.println(items.getPlayer());				// 650 print "YOU ARE IN ROOM ";l(1)
	}
}
