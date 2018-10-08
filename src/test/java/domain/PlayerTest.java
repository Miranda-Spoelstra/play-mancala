package domain;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import domain.Player;

class PlayerTest {

	@Test
	void testFirstPlayerTurn() {
		Player p1 = new Player();
		assertTrue(p1.hasTurn(), "It should not be player two's turn.");
	}

	@Test
	void testSecondPlayerTurn() {
		Player p1 = new Player();
		assertFalse(p1.getOpponent().hasTurn(), "It should not be opponents turn.");
	}

	@Test
	void testSwitchTurn() {
		Player p1 = new Player();
		p1.switchTurn();
		assertFalse(p1.hasTurn(), "It should not be player one's turn.");
	}

	@Test
	void testSecondPlayerSwitchTurn() {
		Player p1 = new Player();
		p1.switchTurn();
		p1.switchTurn();
		assertFalse(p1.getOpponent().hasTurn(), "It should not be opponents turn.");
	}

}
