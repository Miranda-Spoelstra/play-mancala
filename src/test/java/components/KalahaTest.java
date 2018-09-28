package components;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class KalahaTest {

	@Test
	void kalahaExists() {
		Cup cup = new Cup();
		cup.findHole(14).setNextHole(cup);
		assertTrue(cup.findHole(7) instanceof Kalaha);
	}
	
	@Test
	void setUp() {
		Cup cup = new Cup();
		cup.findHole(14).setNextHole(cup);
		Kalaha kal = (Kalaha) cup.findHole(7);
		assertEquals(0, kal.getStones());
	}
	
	@Test
	void addStones() {
		Cup cup = new Cup();
		cup.findHole(14).setNextHole(cup);
		Kalaha kal = (Kalaha) cup.findHole(7);
		kal.passStones(2);
		assertEquals(1, kal.getStones());
	}
	
	@Test
	void endInKalaha() {
		Cup cup = new Cup();
		cup.findHole(14).setNextHole(cup);
		Kalaha kal = (Kalaha) cup.findHole(7);
		Cup current = (Cup) cup.findHole(2);
		current.giveAwayStones();
		assertFalse(kal.getOwner().hasTurn(), "The turn should have switched to the opponent.");
	}

}
