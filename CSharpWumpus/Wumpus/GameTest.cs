using System;
using NUnit.Framework;

namespace Wumpus
{
    [TestFixture]
    public class GameTest
    {
        public class FakeIO : IO
        {
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
    }
}
