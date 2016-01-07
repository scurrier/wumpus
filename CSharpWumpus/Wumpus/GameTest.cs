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
                AllStrings = new List<string>();
                CharInput = new Queue<char>();
                IntInput = new Queue<int>();
            }

            public override void WriteLine(string data)
            {
                AllStrings.Add(data);
                CollectedWrites.Add(data);
            }

            public override void Prompt(string data)
            {
                AllStrings.Add(data);
                CollectedPrompts.Add(data);
            }

            public override char ReadChar()
            {
                return CharInput.Dequeue();
            }

            public override int readInt()
            {
                return IntInput.Dequeue();
            }

            public override void Continue()
            {
                ContinueCount++;
            }

            public List<string> CollectedPrompts { get; set; }
            public List<string> CollectedWrites { get; set; }
            public List<string> AllStrings { get; set; }
            public Queue<char> CharInput { get; set; }
            public Queue<int> IntInput { get; set; }
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

        [TestCase]
        public void testNoInstructions()
        {
            testObj.EarlyExit = 35;
            io.CharInput.Enqueue('N');
            testObj.Play();
            //Assert.AreEqual(2, io.ContinueCount);
            CollectionAssert.AreEqual(new string[]
            {
            }, io.CollectedWrites);
        }

        [TestCase]
        public void pinningFallInPitTest()
        {
            testObj.EarlyExit = 355;
            io.CharInput.Enqueue('N');
            io.CharInput.Enqueue('M');
            io.IntInput.Enqueue(5);
            io.CharInput.Enqueue('M');
            io.IntInput.Enqueue(6);
            io.CharInput.Enqueue('N');
            testObj.Play();
            CollectionAssert.AreEqual(new string[]
            {
                "INSTRUCTIONS (Y-N) ",
                "HUNT THE WUMPUS",
                "",
                "YOUR ARE IN ROOM ", "4",
                "TUNNELS LEAD TO ", "3", " ", "5", " ", "14",
                "",
                "SHOOT OR MOVE (S-M) ",
                "WHERE TO ",
                "",
                "I FEEL A DRAFT",
                "YOUR ARE IN ROOM ", "5",
                "TUNNELS LEAD TO ", "1", " ", "4", " ", "6",
                "",
                "SHOOT OR MOVE (S-M) ",
                "WHERE TO ",
                "YYYYIIIIEEEE . . . FELL IN PIT",
                "HA HA HA - YOU LOSE!"
            }, io.AllStrings);
        }

        [TestCase]
        public void pinningCarryBumpMissEaten()
        {
            testObj.EarlyExit = 355;
            io.CharInput.Enqueue('N');
            io.CharInput.Enqueue('M');
            io.IntInput.Enqueue(14);
            io.CharInput.Enqueue('M');
            io.IntInput.Enqueue(13);
            io.CharInput.Enqueue('M');
            io.IntInput.Enqueue(8);
            io.CharInput.Enqueue('M');
            io.IntInput.Enqueue(9);
            io.CharInput.Enqueue('M');
            io.IntInput.Enqueue(10);
            io.CharInput.Enqueue('S');
            io.IntInput.Enqueue(1);
            io.IntInput.Enqueue(11);
            testObj.Play();
            CollectionAssert.AreEqual(new string[]
            {
                "INSTRUCTIONS (Y-N) ",
                "HUNT THE WUMPUS",
                "",
                "YOUR ARE IN ROOM ", "4",
                "TUNNELS LEAD TO ", "3", " ", "5", " ", "14",
                "",
                "SHOOT OR MOVE (S-M) ",
                "WHERE TO ",
                "",
                "BATS NEARBY!",
                "YOUR ARE IN ROOM ", "14",
                "TUNNELS LEAD TO ", "4", " ", "13", " ", "15",
                "",
                "SHOOT OR MOVE (S-M) ",
                "WHERE TO ",
                "ZAP--SUPER BAT SNATCH! ELSEWHEREVILLE FOR YOU!",
                "",
                "YOUR ARE IN ROOM ", "1",
                "TUNNELS LEAD TO ", "2", " ", "5", " ", "8",
                "",
                "SHOOT OR MOVE (S-M) ",
                "WHERE TO ",
                "",
                "YOUR ARE IN ROOM ", "8",
                "TUNNELS LEAD TO ", "1", " ", "7", " ", "9",
                "",
                "SHOOT OR MOVE (S-M) ",
                "WHERE TO ",
                "",
                "I SMELL A WUMPUS!",
                "YOUR ARE IN ROOM ", "9",
                "TUNNELS LEAD TO ", "8", " ", "10", " ", "18",
                "",
                "SHOOT OR MOVE (S-M) ",
                "WHERE TO ",
                "... OOPS! BUMPED A WUMPUS!",
                "",
                "I SMELL A WUMPUS!",
                "YOUR ARE IN ROOM ", "10",
                "TUNNELS LEAD TO ", "2", " ", "9", " ", "11",
                "",
                "SHOOT OR MOVE (S-M) ",
                "NO. OF ROOMS (1-5) ",
                "ROOM # ",
                "MISSED",
                "TSK TSK TSK - WUMPUS GOT YOU!",
                "HA HA HA - YOU LOSE!"
            }, io.AllStrings);
        }

    }
}
