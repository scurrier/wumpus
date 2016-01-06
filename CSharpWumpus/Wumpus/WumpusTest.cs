using System;
using NUnit.Framework;

namespace Wumpus
{
    [TestFixture]
    public class ProgramTest
    {
        [SetUp]
        public void SetUp()
        {
            Game.random = new Random(0); //seed random for expected results
        }

        [TestCase]
        public void fnA()
        {
            Assert.AreEqual(15, Game.fnA());
            Assert.AreEqual(17, Game.fnA());
            Assert.AreEqual(16, Game.fnA());
            Assert.AreEqual(12, Game.fnA());
            Assert.AreEqual(5, Game.fnA());
            Assert.AreEqual(12, Game.fnA());
            Assert.AreEqual(19, Game.fnA());
            Assert.AreEqual(9, Game.fnA());
            Assert.AreEqual(20, Game.fnA());
            Assert.AreEqual(6, Game.fnA());
        }

        [TestCase]
        public void fnB()
        {
            Assert.AreEqual(3, Game.fnB());
            Assert.AreEqual(3, Game.fnB());
            Assert.AreEqual(3, Game.fnB());
            Assert.AreEqual(2, Game.fnB());
            Assert.AreEqual(1, Game.fnB());
            Assert.AreEqual(2, Game.fnB());
            Assert.AreEqual(3, Game.fnB());
            Assert.AreEqual(2, Game.fnB());
            Assert.AreEqual(3, Game.fnB());
            Assert.AreEqual(1, Game.fnB());
        }

        [TestCase]
        public void fnC()
        {
            Assert.AreEqual(3, Game.fnC());
            Assert.AreEqual(4, Game.fnC());
            Assert.AreEqual(4, Game.fnC());
            Assert.AreEqual(3, Game.fnC());
            Assert.AreEqual(1, Game.fnC());
            Assert.AreEqual(3, Game.fnC());
            Assert.AreEqual(4, Game.fnC());
            Assert.AreEqual(2, Game.fnC());
            Assert.AreEqual(4, Game.fnC());
            Assert.AreEqual(2, Game.fnC());
        }
    }
}
