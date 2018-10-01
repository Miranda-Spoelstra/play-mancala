package components;

public class Cup extends Hole {

	public Cup() {
		Player p1 = new Player();
		this.stones = 4;
		this.owner = p1;
		this.nextHole = new Cup(p1, 0, this);
	}
	
	public Cup(Player owner, int index, Cup original) {
		this.stones = 4;
		index++;
		
		if (index == 8) {
			this.owner = owner.getOpponent();
		} else {
			this.owner = owner;
		}
		
		if (index == 6 || index == 13) {
			this.nextHole = new Kalaha(getOwner(), index, original);
		} else {
			this.nextHole = new Cup(getOwner(), index, original);
		}
	}
	
	public void giveAwayStones() {
		int nrStones = getStones();
		
		if (nrStones != 0) {
			setStones(0);
			getNextHole().passStones(nrStones);
		} else {
			System.out.println("This cup is empty, you cannot make this move.");
		}
	}
	
	public void passStones(int stones) {
		addStone();
		if(stones == 1) {
			checkOpposingCup();
			getOwner().switchTurn();
		} else {
			stones--;
			getNextHole().passStones(stones);
		}
	}

	private void checkOpposingCup() {
		if (getStones() == 1 && getOwner().hasTurn()) {
			Cup opposite = findOppositeCup();
			stonesToKahala(opposite);
		}
	}

	private void stonesToKahala(Cup opposite) {	
		if(opposite.getStones() != 0) {
			opposite.giveToKalaha(opposite.getStones(), getOwner());
			opposite.setStones(0);
			giveToKalaha(getStones(), getOwner());
			setStones(0);
		}
	}

	private void addStone() {
		int newStones = getStones() + 1;
		setStones(newStones);
	}
	
	public Cup findOppositeCup() { 
		Hole nextCup = this;
		int nrCups = -1;

		while(nextCup instanceof Cup) {
			nrCups++;
			nextCup = nextCup.getNextHole();
		}
		while(nrCups > 0) {
			nrCups--;
			nextCup = nextCup.getNextHole();
		}
		return (Cup) nextCup;
	}
	
	public void giveToKalaha(int stones, Player target) {
		getNextHole().giveToKalaha(stones, target);
	}
	
	public boolean gameEnded() {
		if(getStones() == 0) {
			if (getNextHole() instanceof Kalaha) {
				return true;
			}
			Cup next = (Cup) getNextHole();
			return next.gameEnded();
		}
		return false;
	}

	public Kalaha getWinner() {
		return getNextHole().getWinner();
	}
}
