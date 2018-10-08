package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.Cup;
import domain.Hole;
import domain.Kalaha;

class CupTest {

	@Test
	void testCupSetup() {
		Cup cup = new Cup();
		assertEquals(4, cup.getStones(), "The cup should have the starting amount of stones");
		assertEquals(4, cup.getNextHole().getStones(), "The next cup should have the starting amount of stones");
	}

	@Test
	void testPlayerTurn() {
		Cup cup = new Cup();
		assertTrue(cup.getOwner().hasTurn(), "The owner of the first cup should be the current player.");
		assertFalse(cup.getOwner().getOpponent().hasTurn(), "The opponent should not be the current player.");
	}

	@Test
	void testGiveAwayStones() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertEquals(0, cup.getStones(), "The cup should be empty");
		assertEquals(5, cup.getNextHole().getStones(), "After give away, the next cup should have an extra stone");
		assertEquals(4, cup.findHole(6).getStones(), "The give away should stop after the stones are up");
	}

	@Test
	void testImpossibleMove() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		cup.giveAwayStones();
		assertEquals(5, cup.getNextHole().getStones(), "The next cup should only have 1 extra stone");
	}
	
	@Test
	void testMoveOnOpponentCup() {
		Cup cup = new Cup();
		Cup currentCup = (Cup) cup.findHole(8);
		currentCup.giveAwayStones();
		assertEquals(4, currentCup.getStones(), "Should still have stones, because not a cup of the current player");
		assertTrue(cup.getOwner().hasTurn(), "The turn should not have switched");
	}

	@Test
	void testFindHole() {
		Cup cup = new Cup();
		assertEquals(4, cup.findHole(5).getStones(), "This hole should exist and have the starting amount of stones");
	}

	@Test
	void testFullCircle() {
		Cup cup = new Cup();
		assertEquals(cup, cup.findHole(15), "The 15th hole should be the first cup");
	}

	@Test
	void testKalahaExists() {
		Cup cup = new Cup();
		Hole kalaha = cup.findHole(7);
		assertTrue(kalaha instanceof Kalaha, "This hole should be a kalaha");
	}

	@Test
	void testSwitchTurnAfterMove() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertFalse(cup.getOwner().hasTurn(), "The turn should have passed to the opponent");
	}

	@Test
	void testTurnDoesnotSwitch() {
		Cup cup = new Cup();
		Cup currentCup = (Cup) cup.findHole(3);
		currentCup.giveAwayStones();
		assertTrue(currentCup.getOwner().hasTurn(), "The player should have another turn, because ended in kalaha");
	}

	@Test
	void testFindOpposingCup() {
		Cup cup = new Cup();
		Cup currentCup = (Cup) cup.findHole(3);
		Cup opposite = currentCup.findOppositeCup();
		assertSame(opposite, cup.findHole(11), "The wrong cup is found as the opposite");
	}

	@Test
	void testHitOpponent() {
		Cup cup = new Cup();
		cup.getNextHole().setStones(0);
		cup.setStones(1);
		cup.giveAwayStones();
		assertEquals(0, cup.findHole(12).getStones(), "The opposite cup should be empty");
		assertEquals(5, cup.findHole(7).getStones(), "The current player's kalaha should have 5 stones");
		assertEquals(0, cup.findHole(14).getStones(), "The opponents kalaha should still be empty");
	}

	@Test
	void testGiveStonesPastKalaha() {
		Cup cup = new Cup();
		Cup currentCup = (Cup) cup.findHole(4);
		currentCup.giveAwayStones();
		assertEquals(1, cup.findHole(7).getStones(), "The kalaha should have 1 stone");
		assertEquals(5, cup.findHole(8).getStones(), "The hole after the kalaha should have an extra stone");
	}

	@Test
	void testKalahaDifferentOwner() {
		Cup cup = new Cup();
		assertNotSame(cup.findHole(7).getOwner(), cup.findHole(14).getOwner(), "The kalaha's should have different owners");
	}

	@Test
	void testStonesFullCircle() {
		Cup cup = new Cup();
		Cup currentCup = (Cup) cup.findHole(6);
		currentCup.setStones(8);
		currentCup.giveAwayStones();
		assertEquals(0, cup.findHole(14).getStones(), "The opposing kalaha should still be empty");
		assertEquals(5, cup.findHole(15).getStones(), "The cup after the opposing kalaha should have the last stone");
		assertEquals(4, cup.findHole(16).getStones(), "This cup should still have the starting amount of stones");
	}

	@Test
	void testGameNotOver() {
		Cup cup = new Cup();
		cup.giveAwayStones();
		assertFalse(cup.gameEnded(), "Game should not be over");
	}

	@Test
	void testGameEnded() {
		Cup cup = new Cup();
		cup.setStones(0);
		Cup currentCup = cup;
		for (int i = 1; i < 6; i++) {
			currentCup = (Cup) currentCup.getNextHole();
			currentCup.setStones(0);
		}
		assertTrue(cup.gameEnded(), "Game should be over");
	}
	
	@Test
	void rightAmountOfCups() {
		Cup cup = new Cup();
		
		int stoneCount = cup.getStones();
		Hole currentCup = cup.getNextHole();
		while (!currentCup.equals(cup)) {
			stoneCount += currentCup.getStones();
			currentCup = currentCup.getNextHole();
		}
		
		assertEquals(48, stoneCount);
	}
}
