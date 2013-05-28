var wumpus = require("./wumpus");

beforeEach(function() {
	this.addMatchers({
		toBeAnInteger: function() {
			return this.actual == Math.floor(this.actual);
		}
	});
});

describe("fnA returns random between 1 and 20", function() {
	it("is bigger than zero", function() {
		expect(wumpus.fnA()).toBeGreaterThan(0);
	});
	it("and is smaller than 21", function() {
		expect(wumpus.fnA()).toBeLessThan(21);
	});
	it("and is a whole number", function() {
		expect(wumpus.fnA()).toBeAnInteger();
	});
});

describe("fnB returns random between 1 and 3", function() {
	it("is bigger than zero", function() {
		expect(wumpus.fnB()).toBeGreaterThan(0);
	});
	it("and is smaller than 21", function() {
		expect(wumpus.fnB()).toBeLessThan(4);
	});
	it("and is a whole number", function() {
		expect(wumpus.fnB()).toBeAnInteger();
	});
});

describe("fnC returns random between 1 and 4", function() {
	it("is bigger than zero", function() {
		expect(wumpus.fnC()).toBeGreaterThan(0);
	});
	it("and is smaller than 5", function() {
		expect(wumpus.fnC()).toBeLessThan(4);
	});
	it("and is a whole number", function() {
		expect(wumpus.fnC()).toBeAnInteger();
	});
});