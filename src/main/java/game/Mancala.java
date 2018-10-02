package game;

import java.util.Scanner;

import components.Cup;
import components.Kalaha;
import components.Player;

public class Mancala {
	private Player firstPlayer;
	private Cup startingCup;
	boolean gameOver;

	public Mancala() {
		this.startingCup = new Cup();
		this.firstPlayer = getStartingCup().getOwner();
		this.gameOver = false;
	}

	public static void main(String[] args) {
		Mancala game = new Mancala();
		// MancalaGUI gui = new MancalaGUI(game);
		// gui.display();
		game.playerTurn();
	}

	private void playerTurn() {

		try (Scanner userInput = new Scanner(System.in)) {
			while (!gameOver()) {
				displayGame();
				gameOverCheck();

				String player = "one";
				if (!getFirstPlayer().hasTurn()) {
					player = "two";
				}
				System.out.println("Player " + player + " can make a move now");
				makeMove(userInput);
			}
		}		
	}

	protected boolean gameOver() {
		return this.gameOver;
	}

	private void gameOverCheck() {
		Cup currentCup = getStartingCup();

		if (!getFirstPlayer().hasTurn()) {
			currentCup = (Cup) currentCup.findHole(8);
		}

		if (currentCup.gameEnded()) {
			setGameOver(true);
			displayWinner();
		}
	}

	private void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	private void displayWinner() {
		Kalaha winner = getStartingCup().getWinner();
		String winningPlayer = "Player 2";
		int score = winner.getStones();

		if (winner.getOwner().equals(getFirstPlayer())) {
			winningPlayer = "Player 1";
		}
		System.out.println("Game over!");
		System.out.println(winningPlayer + " is the winner with a score of " + score);
	}

	public Player getFirstPlayer() {
		return firstPlayer;
	}

	protected Cup getStartingCup() {
		return startingCup;
	}

	public void makeMove(Scanner userInput) {
		System.out.println("Make your move by choosing from cup 1 - 6.");
		int cupNr = userInput.nextInt();

		if(cupNr > 6) {
			cupNr = cupNr % 6;
		}
		
		if (!getFirstPlayer().hasTurn()) {
			cupNr += 7;
		}
		Cup currentCup = (Cup) getStartingCup().findHole(cupNr);
		currentCup.giveAwayStones();
		System.out.println("Your move was done succesfully.");
	}

	private void displayGame() {
		System.out.println();
		displayCups(-13, -8);
		displayCups(1, 6);
		System.out.println();
	}

	private void displayCups(int start, int finish) {
		if (finish < 0) {
			System.out.print(getStartingCup().findHole(Math.abs(start) + 1).getStones() + " | ");
		} else {
			System.out.print("    ");
		}
		for (int i = start; i <= finish; i++) {
			System.out.print(getStartingCup().findHole(Math.abs(i)).getStones() + " | ");
		}
		if (finish > 0) {
			System.out.print(getStartingCup().findHole(finish + 1).getStones());
		}
		System.out.println();
	}
}
