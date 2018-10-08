package domain;

public class Cup extends Hole {
	protected static final int STARTING_NUMBER_OF_STONES = 4;

	public Cup() {
		Player firstPlayer = new Player();
		this.stones = STARTING_NUMBER_OF_STONES;
		this.owner = firstPlayer;
		this.nextHole = new Cup(firstPlayer, 1, this);
	}

	public Cup(Player owner, int holeNumber, Cup startCup) {
		this.stones = STARTING_NUMBER_OF_STONES;
		holeNumber++;
		
		if (holeNumber == 8) {
			this.owner = owner.getOpponent();
		} else {
			this.owner = owner;
		}

		if (holeNumber == 6 || holeNumber == 13) {
			this.nextHole = new Kalaha(getOwner(), holeNumber, startCup);
		} else if (holeNumber < 14){
			this.nextHole = new Cup(getOwner(), holeNumber, startCup);
		}
	}
	
	private void addStone() {
		setStones(getStones() + 1);
	}

	public void giveAwayStones() {
		int stoneAmount = getStones();
		if (stoneAmount != 0 && getOwner().hasTurn()) {
			setStones(0);
			getNextHole().passStones(stoneAmount);
		} else {
			System.out.println("Invalid move."); // make custom exception?
		}
	}
	
	protected void passStones(int stones) {
		addStone();
		if (stones == 1) {
			emptyOppositeCup();
			getOwner().switchTurn();
		} else {
			stones--;
			getNextHole().passStones(stones);
		}	
	}

	private void emptyOppositeCup() {
		if (getStones() == 1 && getOwner().hasTurn()) {
			Cup oppositeCup = findOppositeCup();
			stonesToKahala(oppositeCup);
		}
	}
	
	protected Cup findOppositeCup() {
		Hole currentCup = this;
		int cupCounter = 0;

		while (currentCup instanceof Cup) {
			cupCounter++;
			currentCup = currentCup.getNextHole();
		}
		while (cupCounter > 0) {
			cupCounter--;
			currentCup = currentCup.getNextHole();
		}
		return (Cup) currentCup;
	}

	private void stonesToKahala(Cup oppositeCup) {
		if (oppositeCup.getStones() != 0) {
			oppositeCup.giveToKalaha(oppositeCup.getStones(), getOwner(), 0);
			giveToKalaha(getStones(), getOwner(), 0);
		}
	}

	protected void giveToKalaha(int stones, Player currentPlayer, int counter) {
		counter++;
		getNextHole().giveToKalaha(stones, currentPlayer, counter);
		if (counter == 1) {
			setStones(0);
		}
	}

	public boolean gameEnded() {
		if (getStones() == 0) {
			if (getNextHole() instanceof Kalaha) {
				return true;
			}
			Cup nextCup = (Cup) getNextHole();
			return nextCup.gameEnded();
		}
		return false;
	}

	public Kalaha getWinner() {
		return getNextHole().getWinner();
	}

	protected void emptyAllCups() {
		giveToKalaha(getStones(), getOwner(), 0);
		Hole currentCup = getNextHole();
		
		while (!currentCup.equals(this)) {
			if (currentCup instanceof Cup) {
				((Cup) currentCup).giveToKalaha(currentCup.getStones(), currentCup.getOwner(), 0);
			}
			currentCup = currentCup.getNextHole();
		}
	}
}
