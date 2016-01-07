using System;
using System.Collections.Generic;
using System.Linq;
using System.Text;
using System.Threading.Tasks;

namespace Wumpus
{
    public abstract class IO
    {
        public abstract void WriteLine(string data);
        public abstract void Prompt(string data);
        public abstract char ReadChar();
        public abstract int readInt();
        public abstract void Continue();

        public void GiveInstructions()
        {
            WriteLine("WELCOME TO 'HUNT THE WUMPUS'");
            WriteLine("  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM");
            WriteLine("HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A");
            WriteLine("DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW");
            WriteLine("WHAT A DODECAHEDRON IS, ASK SOMEONE)");
            WriteLine("");
            WriteLine("     HAZARDS:");
            WriteLine(" BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM");
            WriteLine("     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)");
            WriteLine(" SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU");
            WriteLine("     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER");
            WriteLine("     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)");
            WriteLine("HIT RETURN TO CONTINUE");
            Continue();
            WriteLine("     WUMPUS:");
            WriteLine(" THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER");
            WriteLine(" FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY");
            WriteLine(" HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN");
            WriteLine("ARROW OR YOU ENTERING HIS ROOM.");
            WriteLine("     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM");
            WriteLine(" OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU");
            WriteLine(" ARE, HE EATS YOU UP AND YOU LOSE!");
            WriteLine("");
            WriteLine("     YOU:");
            WriteLine(" EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW");
            WriteLine("   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)");
            WriteLine("   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT");
            WriteLine("   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING");
            WriteLine("   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO.");
            WriteLine("   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES");
            WriteLine("   AT RANDOM TO THE NEXT ROOM.");
            WriteLine("     IF THE ARROW HITS THE WUMPUS, YOU WIN.");
            WriteLine("     IF THE ARROW HITS YOU, YOU LOSE.");
            WriteLine("HIT RETURN TO CONTINUE");
            Continue();
            WriteLine("    WARNINGS:");
            WriteLine("     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,");
            WriteLine("     THE COMPUTER SAYS:");
            WriteLine(" WUMPUS:  'I SMELL A WUMPUS'");
            WriteLine(" BAT   :  'BATS NEARBY'");
            WriteLine(" PIT   :  'I FEEL A DRAFT'");
            WriteLine("");
        }
    }
}
