<?php
require_once 'PHPUnit/Autoload.php';
require_once 'Wumpus.php';

class WumpusTest extends PHPUnit_Framework_TestCase
{
	function setUp() {
		srand(0);
	}
	
	function testFnA() {
		$this->assertEquals(6, Wumpus::fnA());
		$this->assertEquals(12, Wumpus::fnA());
		$this->assertEquals(2, Wumpus::fnA());
		$this->assertEquals(2, Wumpus::fnA());
		$this->assertEquals(6, Wumpus::fnA());
		$this->assertEquals(18, Wumpus::fnA());
		$this->assertEquals(7, Wumpus::fnA());
		$this->assertEquals(2, Wumpus::fnA());
		$this->assertEquals(13, Wumpus::fnA());
		$this->assertEquals(2, Wumpus::fnA());
	}

	function testFnB() {
		$this->assertEquals(1, Wumpus::fnB());
		$this->assertEquals(2, Wumpus::fnB());
		$this->assertEquals(1, Wumpus::fnB());
		$this->assertEquals(1, Wumpus::fnB());
		$this->assertEquals(1, Wumpus::fnB());
		$this->assertEquals(3, Wumpus::fnB());
		$this->assertEquals(2, Wumpus::fnB());
		$this->assertEquals(1, Wumpus::fnB());
		$this->assertEquals(2, Wumpus::fnB());
		$this->assertEquals(1, Wumpus::fnB());
	}

	function testFnC() {
		$this->assertEquals(2, Wumpus::fnC());
		$this->assertEquals(3, Wumpus::fnC());
		$this->assertEquals(1, Wumpus::fnC());
		$this->assertEquals(1, Wumpus::fnC());
		$this->assertEquals(2, Wumpus::fnC());
		$this->assertEquals(4, Wumpus::fnC());
		$this->assertEquals(2, Wumpus::fnC());
		$this->assertEquals(1, Wumpus::fnC());
		$this->assertEquals(3, Wumpus::fnC());
		$this->assertEquals(1, Wumpus::fnC());
	}
}