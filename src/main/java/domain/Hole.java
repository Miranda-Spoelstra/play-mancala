package domain;

public abstract class Hole {
	protected int stones;
	protected Hole nextHole;
	protected Player owner;

	public int getStones() {
		return this.stones;
	}

	protected void setStones(int stones) {
		this.stones = Math.abs(stones);
	}

	public Player getOwner() {
		return this.owner;
	}

	protected Hole getNextHole() {
		return this.nextHole;
	}

	public Hole findHole(int distance) {
		Hole result = this;
		for (int i = 1; i < distance; i++) {
			result = result.getNextHole();
		}
		return result;
	}

	protected abstract void giveToKalaha(int stones, Player target, int counter);

	protected abstract void passStones(int s);

	public abstract Kalaha getWinner();
}
