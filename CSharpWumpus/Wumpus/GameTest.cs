using System;
using System.Collections;
using System.Collections.Generic;
using NUnit.Framework;

namespace Wumpus
{
    [TestFixture]
    public class GameTest
    {
        public class FakeIO : IO
        {
            public FakeIO()
            {
                CollectedPrompts = new List<string>();
                CollectedWrites = new List<string>();
                CharInput = new Queue<char>();
            }

            public void WriteLine(string data)
            {
                CollectedWrites.Add(data);
            }

            public void Prompt(string data)
            {
                CollectedPrompts.Add(data);
            }

            public char ReadChar()
            {
                return '0';
            }

            public int readInt()
            {
                return 0;
            }

            public void Continue()
            {
                ContinueCount++;
            }

            public List<string> CollectedPrompts { get; set; }
            public List<string> CollectedWrites { get; set; }
            public Queue<char> CharInput { get; set; }
            public int ContinueCount { get; set; }
        }

        private Game testObj;
        private FakeIO io;
        [SetUp]
        public void SetUp()
        {
            io = new FakeIO();
            testObj = new Game(io);
            testObj.random = new Random(0); //seed random for expected results
        }

        [TestCase]
        public void fnA()
        {
            Assert.AreEqual(15, testObj.fnA());
            Assert.AreEqual(17, testObj.fnA());
            Assert.AreEqual(16, testObj.fnA());
            Assert.AreEqual(12, testObj.fnA());
            Assert.AreEqual(5, testObj.fnA());
            Assert.AreEqual(12, testObj.fnA());
            Assert.AreEqual(19, testObj.fnA());
            Assert.AreEqual(9, testObj.fnA());
            Assert.AreEqual(20, testObj.fnA());
            Assert.AreEqual(6, testObj.fnA());
        }

        [TestCase]
        public void fnB()
        {
            Assert.AreEqual(3, testObj.fnB());
            Assert.AreEqual(3, testObj.fnB());
            Assert.AreEqual(3, testObj.fnB());
            Assert.AreEqual(2, testObj.fnB());
            Assert.AreEqual(1, testObj.fnB());
            Assert.AreEqual(2, testObj.fnB());
            Assert.AreEqual(3, testObj.fnB());
            Assert.AreEqual(2, testObj.fnB());
            Assert.AreEqual(3, testObj.fnB());
            Assert.AreEqual(1, testObj.fnB());
        }

        [TestCase]
        public void fnC()
        {
            Assert.AreEqual(3, testObj.fnC());
            Assert.AreEqual(4, testObj.fnC());
            Assert.AreEqual(4, testObj.fnC());
            Assert.AreEqual(3, testObj.fnC());
            Assert.AreEqual(1, testObj.fnC());
            Assert.AreEqual(3, testObj.fnC());
            Assert.AreEqual(4, testObj.fnC());
            Assert.AreEqual(2, testObj.fnC());
            Assert.AreEqual(4, testObj.fnC());
            Assert.AreEqual(2, testObj.fnC());
        }

        [TestCase]
        public void testEarlyExit()
        {
            testObj.EarlyExit = 20;
            testObj.Play();
            CollectionAssert.AreEqual(new string[]{ "INSTRUCTIONS (Y-N) "}, io.CollectedPrompts);
        }

        [TestCase]
        public void testInstructions()
        {
            testObj.EarlyExit = 35;
            io.CharInput.Enqueue('Y');
            testObj.Play();
            Assert.AreEqual(2, io.ContinueCount);
            CollectionAssert.AreEqual(new string[]
            {
            "WELCOME TO 'HUNT THE WUMPUS'",
            "  THE WUMPUS LIVES IN A CAVE OF 20 ROOMS. EACH ROOM",
            "HAS 3 TUNNELS LEADING TO OTHER ROOMS. (LOOK AT A",
            "DODECAHEDRON TO SEE HOW THIS WORKS-IF YOU DON'T KNOW",
            "WHAT A DODECAHEDRON IS, ASK SOMEONE)",
            "",
            "     HAZARDS:",
            " BOTTOMLESS PITS - TWO ROOMS HAVE BOTTOMLESS PITS IN THEM",
            "     IF YOU GO THERE, YOU FALL INTO THE PIT (& LOSE!)",
            " SUPER BATS - TWO OTHER ROOMS HAVE SUPER BATS. IF YOU",
            "     GO THERE, A BAT GRABS YOU AND TAKES YOU TO SOME OTHER",
            "     ROOM AT RANDOM. (WHICH MAY BE TROUBLESOME)",
            "HIT RETURN TO CONTINUE",
            "     WUMPUS:",
            " THE WUMPUS IS NOT BOTHERED BY HAZARDS (HE HAS SUCKER",
            " FEET AND IS TOO BIG FOR A BAT TO LIFT).  USUALLY",
            " HE IS ASLEEP.  TWO THINGS WAKE HIM UP: YOU SHOOTING AN",
            "ARROW OR YOU ENTERING HIS ROOM.",
            "     IF THE WUMPUS WAKES HE MOVES (P=.75) ONE ROOM",
            " OR STAYS STILL (P=.25).  AFTER THAT, IF HE IS WHERE YOU",
            " ARE, HE EATS YOU UP AND YOU LOSE!",
            "",
            "     YOU:",
            " EACH TURN YOU MAY MOVE OR SHOOT A CROOKED ARROW",
            "   MOVING:  YOU CAN MOVE ONE ROOM (THRU ONE TUNNEL)",
            "   ARROWS:  YOU HAVE 5 ARROWS.  YOU LOSE WHEN YOU RUN OUT",
            "   EACH ARROW CAN GO FROM 1 TO 5 ROOMS. YOU AIM BY TELLING",
            "   THE COMPUTER THE ROOM#S YOU WANT THE ARROW TO GO TO.",
            "   IF THE ARROW CAN'T GO THAT WAY (IF NO TUNNEL) IT MOVES",
            "   AT RANDOM TO THE NEXT ROOM.",
            "     IF THE ARROW HITS THE WUMPUS, YOU WIN.",
            "     IF THE ARROW HITS YOU, YOU LOSE.",
            "HIT RETURN TO CONTINUE",
            "    WARNINGS:",
            "     WHEN YOU ARE ONE ROOM AWAY FROM A WUMPUS OR HAZARD,",
            "     THE COMPUTER SAYS:",
            " WUMPUS:  'I SMELL A WUMPUS'",
            " BAT   :  'BATS NEARBY'",
            " PIT   :  'I FEEL A DRAFT'",
            ""
            }, io.CollectedWrites);
        }
    }
}
