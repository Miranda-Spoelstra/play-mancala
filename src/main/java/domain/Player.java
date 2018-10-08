package domain;

public class Player {
	private boolean hasTurn;
	private Player opponent;

	public Player() {
		this.hasTurn = true;
		this.opponent = new Player(false, this);
	}

	public Player(boolean turn, Player opponent) {
		this.hasTurn = turn;
		this.opponent = opponent;
	}

	public Player getOpponent() {
		return this.opponent;
	}

	public boolean hasTurn() {
		return this.hasTurn;
	}

	public void switchTurn() {
		hasTurn = !hasTurn;
		getOpponent().switchTurn("");
	}

	private void switchTurn(String input) {
		hasTurn = !hasTurn;
	}
}
