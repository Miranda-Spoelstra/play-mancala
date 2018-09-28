package components;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CupTest {

	@Test
	void testCupStones() {
		Cup cup = new Cup();
		assertEquals(4, cup.getStones());
	}

	@Test
	void getPlayer() {
		Cup cup = new Cup();
		assertTrue(cup.getOwner().hasTurn(), "The owner of the first cup should be the current player.");
	}

	@Test
	void getOpponentTurn() {
		Cup cup = new Cup();
		assertFalse(cup.getOwner().getOpponent().hasTurn(), "The opponent should not have the turn.");
	}

	@Test
	void nextHoleStones() {
		Cup cup = new Cup();
		assertEquals(4, cup.getNextHole().getStones());
	}

	@Test
	void emptyAfterGiveAwayStones() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertEquals(0, cup.getStones());
	}

	@Test
	void unableToMakeMove() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		cup.giveAwayStones();
	}

	@Test
	void nextCupAddStone() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertEquals(5, cup.getNextHole().getStones());
	}

	@Test
	void findHole() {
		Cup cup = new Cup();
		assertEquals(4, cup.findHole(5).getStones());
	}

	@Test
	void findFirst() {
		Cup cup = new Cup();
		assertEquals(cup, cup.findHole(15));
	}
	
	@Test
	void kalahaExists() {
		Cup cup = new Cup();
		assertTrue(cup.findHole(7) instanceof Kalaha);
	}

	@Test
	void giveAwayNotToFar() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertEquals(4, cup.findHole(8).getStones()); // 6?
	}

	@Test
	void opponentHasCupsToo() {
		Cup cup = new Cup();
		assertFalse(cup.findHole(8).getOwner().hasTurn());
	}

	@Test
	void switchTurnAfterGiveAway() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertFalse(cup.getOwner().hasTurn(), "The turn should have passed to the opponent");
	}

	@Test
	void findOpposingCup() {
		Cup cup = new Cup();
		Cup current = (Cup) cup.findHole(3);
		Cup opposite = current.findOppositeCup();
		assertFalse(opposite.getOwner().hasTurn(), "The owner of this cup should not have the turn.");
	}

	@Test
	void hitOpponent() {
		Cup cup = new Cup();
		cup.getNextHole().setStones(0);
		cup.setStones(1);
		cup.giveAwayStones();
		assertEquals(0, cup.findHole(12).getStones());
	}

	@Test
	void giveStonesPastKalaha() {
		Cup cup = new Cup();
		Cup current = (Cup) cup.findHole(4);
		current.giveAwayStones();
		assertEquals(5, current.findHole(4).getStones());
	}
	
	@Test
	void kalahaDifferentOwner() {
		Cup cup = new Cup();
		assertNotEquals(cup.findHole(7).getOwner(), cup.findHole(14).getOwner());
	}

	@Test
	void gameNotOver() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertFalse(cup.gameEnded(), "Game should not be over");
	}

	@Test
	void gameEnded() {
		Cup cup = new Cup();
		Cup current = cup;
		for (int i = 0; i < 6; i++) {
			current = (Cup) current.getNextHole();
			current.giveAwayStones();
		}
		assertTrue(cup.gameEnded(), "Game should be over");
	}
}
