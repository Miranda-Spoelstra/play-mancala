package components;

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
	
	private void setTurn(boolean turn) {
		this.hasTurn = turn;
	}
	
	public Player getOpponent() {
		return this.opponent;
	}	

	public boolean hasTurn() {
		return this.hasTurn;
	}

	public void switchTurn() {
		if (hasTurn()) {
			setTurn(false);
			getOpponent().setTurn(true);
		} else {
			setTurn(true);
			getOpponent().setTurn(false);
		}
	}
}
