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
            Program.random = new Random(0); //seed random for expected results
        }

        [TestCase]
        public void fnA()
        {
            Assert.AreEqual(15, Program.fnA());
            Assert.AreEqual(17, Program.fnA());
            Assert.AreEqual(16, Program.fnA());
            Assert.AreEqual(12, Program.fnA());
            Assert.AreEqual(5, Program.fnA());
            Assert.AreEqual(12, Program.fnA());
            Assert.AreEqual(19, Program.fnA());
            Assert.AreEqual(9, Program.fnA());
            Assert.AreEqual(20, Program.fnA());
            Assert.AreEqual(6, Program.fnA());
        }

        [TestCase]
        public void fnB()
        {
            Assert.AreEqual(3, Program.fnB());
            Assert.AreEqual(3, Program.fnB());
            Assert.AreEqual(3, Program.fnB());
            Assert.AreEqual(2, Program.fnB());
            Assert.AreEqual(1, Program.fnB());
            Assert.AreEqual(2, Program.fnB());
            Assert.AreEqual(3, Program.fnB());
            Assert.AreEqual(2, Program.fnB());
            Assert.AreEqual(3, Program.fnB());
            Assert.AreEqual(1, Program.fnB());
        }

        [TestCase]
        public void fnC()
        {
            Assert.AreEqual(3, Program.fnC());
            Assert.AreEqual(4, Program.fnC());
            Assert.AreEqual(4, Program.fnC());
            Assert.AreEqual(3, Program.fnC());
            Assert.AreEqual(1, Program.fnC());
            Assert.AreEqual(3, Program.fnC());
            Assert.AreEqual(4, Program.fnC());
            Assert.AreEqual(2, Program.fnC());
            Assert.AreEqual(4, Program.fnC());
            Assert.AreEqual(2, Program.fnC());
        }
    }
}
