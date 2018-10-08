package game;

import java.util.Scanner;

import domain.*;

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
		game.playerTurn();
	}

	public Player getFirstPlayer() {
		return this.firstPlayer;
	}

	protected Cup getStartingCup() {
		return this.startingCup;
	}

	private void setGameOver(boolean gameOver) {
		this.gameOver = gameOver;
	}

	private boolean gameOver() {
		return this.gameOver;
	}

	private void playerTurn() {
		try (Scanner userInput = new Scanner(System.in)) {
			while (!gameOver()) {
				gameOverCheck();
				displayGame();

				String player = "one";
				if (!getFirstPlayer().hasTurn()) {
					player = "two";
				}
				System.out.println("Player " + player + " can make a move now");
				makeMove(userInput);
			}
		}
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

	private void displayWinner() {
		Kalaha winner = getStartingCup().getWinner();
		String winningPlayer = "Player 2";
		int score = winner.getStones();
		
		if (winner.getOwner().equals(getFirstPlayer())) {
			winningPlayer = "Player 1";
		}
		System.out.println("Game over!");
		
		if (score == 24) {
			System.out.println("Gelijkspel");
		} else {
			System.out.println(winningPlayer + " is the winner with a score of " + score);

		}
	}

	public void makeMove(Scanner userInput) {
		while (!userInput.hasNextInt()) {
			System.out.println("Make your move by choosing from cup 1 - 6.");
			userInput.next();
		}
		int selectedCup = userInput.nextInt();
		if (selectedCup > 0 && selectedCup < 7) {
			if (!getFirstPlayer().hasTurn()) {
				selectedCup += 7;
			}
			Cup currentCup = (Cup) getStartingCup().findHole(selectedCup);
			currentCup.giveAwayStones();
		} else {
			System.out.println("Invalid input, try again.");
			makeMove(userInput);
		}
	}

	private void displayGame() {
		System.out.println();
		System.out.println("           Player 2");
		System.out.println();
		System.out.println(" <-  6   5   4   3   2   1  <-");
		System.out.println("-------------------------------");
		displayCups(-13, -8);
		System.out.println("   -------------------------");
		displayCups(1, 6);
		System.out.println("-------------------------------");
		System.out.println(" ->  1   2   3   4   5   6  ->");
		System.out.println();
		System.out.println("           Player 1");
		System.out.println();
	}

	private void displayCups(int start, int finish) {
		if (finish < 0) {
			System.out.print(" " + getStartingCup().findHole(Math.abs(start) + 1).getStones() + " | ");
		} else {
			System.out.print("   | ");
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
