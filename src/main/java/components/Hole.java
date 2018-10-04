package components;

public abstract class Hole {
	protected static final int STARTING_NUMBER_OF_STONES = 4;
	protected int stones;
	protected Hole nextHole;
	protected Player owner;

	public int getStones() {
		return this.stones;
	}

	protected void setStones(int stones) {
		this.stones = stones;
	}

	public Player getOwner() {
		return this.owner;
	}

	protected Hole getNextHole() {
		return this.nextHole;
	}

	public Hole findHole(int distance) {
		Hole result = this;
		for (int i = 0; i < distance; i++) {
			result = result.getNextHole();
		}
		return result;
	}

	public abstract void giveToKalaha(int stones, Player target);

	public abstract void passStones(int s);

	public abstract Kalaha getWinner();
}
