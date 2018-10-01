package components;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class CupTest {

	@Test
	void testCupStones() {
		Cup cup = new Cup();
		assertEquals(4, cup.getStones(), "The cup should have the starting amount of stones");
	}

	@Test
	void getPlayer() {
		Cup cup = new Cup();
		assertTrue(cup.getOwner().hasTurn(), "The owner of the first cup should be the current player.");
	}

	@Test
	void getOpponentTurn() {
		Cup cup = new Cup();
		assertFalse(cup.getOwner().getOpponent().hasTurn(), "The opponent should not be the current player.");
	}

	@Test
	void nextHoleStones() {
		Cup cup = new Cup();
		assertEquals(4, cup.getNextHole().getStones(), "The next cup should have the starting amount of stones");
	}

	@Test
	void emptyAfterGiveAwayStones() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertEquals(0, cup.getStones(), "The cup should be empty");
	}

	@Test
	void unableToMakeMove() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		cup.giveAwayStones();
		assertEquals(5, cup.getNextHole().getStones(), "The next cup should only have 1 extra stone");
	}

	@Test
	void nextCupAddStone() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertEquals(5, cup.getNextHole().getStones(), "After give away, the next cup should have an extra stone");
	}

	@Test
	void findHole() {
		Cup cup = new Cup();
		assertEquals(4, cup.findHole(5).getStones(), "This hole should exist and have the starting amount of stones");
	}

	@Test
	void findFirst() {
		Cup cup = new Cup();
		assertEquals(cup, cup.findHole(15), "The 15th hole should be the first cup");
	}
	
	@Test
	void kalahaExists() {
		Cup cup = new Cup();
		assertTrue(cup.findHole(7) instanceof Kalaha, "This hole should be a kalaha");
	}

	@Test
	void giveAwayNotToFar() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertEquals(4, cup.findHole(6).getStones(), "The give away should stop after the stones are up");
	}

	@Test
	void opponentHasCupsToo() {
		Cup cup = new Cup();
		assertFalse(cup.findHole(8).getOwner().hasTurn(), "The owner of the cup on the other side of the board should not be the current player");
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
		assertFalse(opposite.getOwner().hasTurn(), "The owner of this cup should not be the current player");
	}

	@Test
	void hitOpponent() {
		Cup cup = new Cup();
		cup.getNextHole().setStones(0);
		cup.setStones(1);
		cup.giveAwayStones();
		assertEquals(0, cup.findHole(12).getStones(), "The opposite cup should be empty");
	}

	@Test
	void giveStonesPastKalaha() {
		Cup cup = new Cup();
		Cup current = (Cup) cup.findHole(4);
		current.giveAwayStones();
		assertEquals(5, current.findHole(4).getStones(), "The hole after the kalaha should have an extra stone");
	}
	
	@Test
	void kalahaDifferentOwner() {
		Cup cup = new Cup();
		assertNotEquals(cup.findHole(7).getOwner(), cup.findHole(14).getOwner(), "The kalaha's should have different owners");
	}
	
	@Test
	void giveToKalaha() {
		Cup cup = new Cup();
		cup.getNextHole().setStones(0);
		cup.setStones(1);
		cup.giveAwayStones();
		assertEquals(5, cup.findHole(7).getStones(), "The current player's kalaha should have 5 stones");
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
		cup.setStones(0);
		Cup current = cup;
		for (int i = 0; i < 6; i++) {
			current = (Cup) current.getNextHole();
			current.setStones(0);
		}
		assertTrue(cup.gameEnded(), "Game should be over");
	}
}
