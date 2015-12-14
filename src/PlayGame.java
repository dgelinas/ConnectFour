import java.util.Scanner;

public class PlayGame {
	public static void main(String[] args) {
		Board board = new Board();
		AIPlayer ai;
		boolean playerOnesTurn = true;
		int gameMode = 0;
		int difficulty = 0;
		int cheat = 0;
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Please Choose Game Mode:");
		System.out.println("1: Against AI");
		System.out.println("2: Two Player Game");
		while(gameMode != 1 && gameMode != 2) gameMode = scan.nextInt();
		
		if(gameMode == 1) {
			System.out.println("Please Choose A Difficulty:");
			System.out.println("1: Easy");
			System.out.println("2: Medium");
			System.out.println("3: Hard");
			while(difficulty < 1 || difficulty > 3) difficulty = scan.nextInt();
			
			System.out.println("Do you want the AI to be able to cheat?");
			System.out.println("1: Yes");
			System.out.println("2: No");
			while(cheat < 1 || cheat > 2) cheat = scan.nextInt();
		}
		
		ai = new AIPlayer(board, difficulty);
		
		int winner = board.findWinner();
		while(winner == 0) {
			
			int move = 0;
			if(playerOnesTurn) {
				board.print();
				while(move < 1 || move > 7) {
					System.out.println("Player One: Choose a column (1 - 7)");
					move = scan.nextInt();
					if(!board.isLegalMove(move - 1)) {
						System.out.println("Column " + move + " is already full.");
						move = 0;
					}
				}
				board.makeMovePlayerOne(move - 1);
			}
			else if(gameMode == 2) {
				board.print();
				while(move < 1 || move > 7) {
					System.out.println("Player Two: Choose a column (1 - 7)");
					move = scan.nextInt();
					if(!board.isLegalMove(move - 1)) {
						System.out.println("Column " + move + " is already full.");
						move = 0;
					}
				}
				board.makeMovePlayerTwo(move - 1);
			}
			else {
				int aiMove = ai.makeMove();
				if(cheat == 1){
					double ran = Math.random();
					if(ran < .2) aiMove = ai.makeMove();
				}
				System.out.println("\n" + "Your opponent chose column " + (aiMove + 1));
			}
			
			playerOnesTurn = !playerOnesTurn;
			winner = board.findWinner();
		}
		
		scan.close();
		board.print();
		
		if(winner == -1) System.out.println("Tie!");
		if(winner ==  1) System.out.println("Player One Wins!");
		if(winner ==  2) System.out.println("Player Two Wins!");
	}
}
