package components;

public abstract class Hole {
	protected int stones;
	protected Hole nextHole;
	protected Player owner;

	protected int getStones() {
		return this.stones;
	}

	protected void setStones(int stones) {
		this.stones = stones;
	}

	protected Player getOwner() {
		return this.owner;
	}
	
	protected void setNextHole(Hole next) {
		this.nextHole = next;
	}

	protected Hole getNextHole() {
		return this.nextHole;
	}

	public Hole findHole(int distance) {
		Hole result = this;
		for (int i = 0; i < distance; i++) {
			result = result.getNextHole();
		}

		if (result instanceof Kalaha) {
			return (Kalaha) result;
		}
		return (Cup) result;
	}

	public abstract void giveToKalaha(int stones, Player target);

	public abstract void passStones(int s);
}
