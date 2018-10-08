package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.Cup;
import domain.Kalaha;

class KalahaTest {

	@Test
	void kalahaExists() {
		Cup cup = new Cup();
		assertTrue(cup.findHole(7) instanceof Kalaha);
		assertTrue(cup.findHole(14) instanceof Kalaha);
	}

	@Test
	void testKalahaEmpty() {
		Cup cup = new Cup();
		Kalaha kal = (Kalaha) cup.findHole(7);
		assertEquals(0, kal.getStones());
	}

	@Test
	void testAddStones() {
		Cup cup = new Cup();
		Kalaha kal = (Kalaha) cup.findHole(7);
		kal.passStones(2);
		assertEquals(1, kal.getStones());
	}

	@Test
	void testEndInKalaha() {
		Cup cup = new Cup();
		Kalaha kal = (Kalaha) cup.findHole(7);
		Cup currentCup = (Cup) cup.findHole(2);
		currentCup.giveAwayStones();
		assertFalse(kal.getOwner().hasTurn(), "The turn should have switched to the opponent.");
	}

	@Test
	void testGetWinner() {
		Cup cup = new Cup();
		cup.findHole(14).setStones(5);
		assertEquals(cup.findHole(14), cup.getWinner());
	}

	@Test
	void testEmptyAllCups() {
		Cup cup = new Cup();
		Cup currentCup = (Cup) cup.findHole(6);
		currentCup.giveAwayStones();
		cup.emptyAllCups();

		assertEquals(48, (cup.findHole(7).getStones() + cup.findHole(14).getStones()));
		assertEquals(cup.findHole(14), cup.getWinner());
	}

}
