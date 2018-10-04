package components;

public class Cup extends Hole {

	public Cup() {
		Player firstPlayer = new Player();
		this.stones = STARTING_NUMBER_OF_STONES;
		this.owner = firstPlayer;
		this.nextHole = new Cup(firstPlayer, 0, this);
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
		} else {
			this.nextHole = new Cup(getOwner(), holeNumber, startCup);
		}
	}
	
	private void addStone() {
		int newStones = getStones() + 1;
		setStones(newStones);
	}

	public void giveAwayStones() {
		int stoneAmount = getStones();

		if (stoneAmount != 0 && getOwner().hasTurn()) {
			setStones(0);
			getNextHole().passStones(stoneAmount);
		} else {
			System.out.println("You cannot make this move.");
		}
	}

	public void passStones(int stones) {
		addStone();
		if (stones == 1) {
			checkOppositeCup();
			getOwner().switchTurn();
		} else {
			stones--;
			getNextHole().passStones(stones);
		}
	}

	private void checkOppositeCup() {
		if (getStones() == 1 && getOwner().hasTurn()) {
			Cup oppositeCup = findOppositeCup();
			stonesToKahala(oppositeCup);
		}
	}
	
	public Cup findOppositeCup() {
		Hole currentCup = this;
		int cupCounter = -1;

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
			oppositeCup.giveToKalaha(oppositeCup.getStones(), getOwner());
			oppositeCup.setStones(0);
			giveToKalaha(getStones(), getOwner());
			setStones(0);
		}
	}

	public void giveToKalaha(int stones, Player currentPlayer) {
		getNextHole().giveToKalaha(stones, currentPlayer);
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
}
