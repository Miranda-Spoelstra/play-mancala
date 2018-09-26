package components;

public abstract class Hole {
	protected int stones;
	protected Hole nextHole;
	protected Player owner;
	
	protected int getStones() {
		return this.stones;
	}
	
	protected Player getPlayer() {
		return this.owner;
	}
	
	protected Hole getNextHole() {
		return this.nextHole;
	}
	
	public abstract void passStones(int s);
}
