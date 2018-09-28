package components;

public class Mancala {

	public static void main(String[] args) {

		
		Cup cup = new Cup();
		Cup current = cup;
		for (int i = 0; i < 6; i++) {
			current = (Cup) current.getNextHole();
			current.giveAwayStones();
			System.out.println(current.getStones());
		}
	}
}
