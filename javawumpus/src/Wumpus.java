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
				ui.printPlayerStatus(map, items);
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
		int j9 = ui.getShotDistance();
		int[] p = ui.getIntendedFlightPath(j9);
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
			ui.println("TSK TSK TSK - WUMPUS GOT YOU!");
			return LOST;
		}
		return PLAYING;
	}
	public int shootArrow(int shotDistance, int[] arrowPath) {
		int ll = items.getPlayer();
		for (int k2 = 1; k2 <= shotDistance; ++k2) {
			ll = map.isValidMove(ll, arrowPath[k2]) ? arrowPath[k2] : map.getRoomExits(ll).room(selector.pickPath());
			if (items.isWumpus(ll)) {
				ui.shotTheWumpus();
				return WON;
			}

			if (ll == items.getPlayer()) {
				ui.shotSelf();
				return LOST;
			}
		}
		return PLAYING;
	}
}
