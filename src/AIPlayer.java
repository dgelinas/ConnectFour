public class AIPlayer {
	
	private int difficulty;
	private static int depth;
	private static Board board;
	
	public AIPlayer(Board board, int difficulty) {
		if(difficulty != 0) {
			this.board = board;
			this.difficulty = difficulty;
			if(difficulty == 1) depth = 2;
			if(difficulty == 2) depth = 4;
			if(difficulty == 3) depth = 6;
		}
	}
	
	private static int valueOfMove(int col) {
		board.makeMovePlayerTwo(col);
		int v = alphabeta(depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
		board.undoMove(col);
		return v;
	}
	
	public int makeMove() {
		int move = 0;
		int max = Integer.MIN_VALUE;
		
		for(int j = 0 ; j < board.width() ; j++) {
			if(board.isLegalMove(j)) {
				int v = valueOfMove(j);
				if(v > max) {
					max = v;
					move = j;
					if(v == -1) break;
				}
			}
		}
		
		board.makeMovePlayerTwo(move);
		return move;
	}
	
	private static int findScore(int aiValue) {
		switch(aiValue) {
			case 0: return 0;
			case 1: return 1;
			case 2: return 20;
			case 3: return 300;
			default: return Integer.MAX_VALUE;
		}
	}
	
	private static int processBoard(Board b) {
		int aiValue = 1;
		int score = 0;
		int emptySpaces = 0;
		
		for(int i = 0 ; i < board.height() ; i++) {
			for(int j = 0 ; j < board.width() ; j++) {
				if(board.spaceValue(i, j) == 0 || board.spaceValue(i, j) == 1)
					continue;
				
				//checks horizontal
				if(j <= 3) {
					for(int k = 1 ; k < 4 ; k++) {
						if(board.spaceValue(i, j + k) == 2)
							aiValue++;
						if(board.spaceValue(i, j + k) == 1) {
							aiValue = 0;
							emptySpaces = 0;
							break;
						}
						else
							emptySpaces++;
						
					}
					
					if(emptySpaces > 0)
						score = score + findScore(aiValue);
					aiValue = 1;
					emptySpaces = 0;
				}
				
				//checks vertical
				if(i <= 2) {
					for(int k = 1 ; k < 4 ; k++) {
						if(board.spaceValue(i + k, j) == 2)
							aiValue++;
						if(board.spaceValue(i + k, j) == 1) {
							aiValue = 0;
							emptySpaces = 0;
							break;
						}
						else
							emptySpaces++;
					}
					
					if(emptySpaces > 0)
						score = score + findScore(aiValue);
					aiValue = 1;
					emptySpaces = 0;
				}
				
				//checks downward diagonal
				if(i <= 2 && j <= 3) {
					for(int k = 1 ; k < 4 ; k++) {
						if(board.spaceValue(i + k, j + k) == 2)
							aiValue++;
						if(board.spaceValue(i + k, j + k) == 1) {
							aiValue = 0;
							emptySpaces = 0;
							break;
						}
						else
							emptySpaces++;
					}
					
					if(emptySpaces > 0)
						score = score + findScore(aiValue);
					aiValue = 1;
					emptySpaces = 0;
				}
				
				//checks upward diagonal
				if(i >= 3 && j <= 3) {
					for(int k = 1 ; k < 4 ; k++) {
						if(board.spaceValue(i - k, j + k) == 2)
							aiValue++;
						if(board.spaceValue(i - k, j + k) == 1) {
							aiValue = 0;
							emptySpaces = 0;
							break;
						}
						else
							emptySpaces++;
					}
					
					if(emptySpaces > 0)
						score = score + findScore(aiValue);
					aiValue = 1;
					emptySpaces = 0;
				}
			}
		}
		
		return score;
	}
	
	private static int alphabeta(int level, int alpha, int beta, boolean maximize) {
		boolean hasWinner = board.hasWinner();
		
		//base case
		if(level == 0 || hasWinner) {
			int score;
			if(hasWinner) {
				if(board.findWinner() == 1)
					score = Integer.MIN_VALUE;
				else
					score = Integer.MAX_VALUE;
			}
			else {
				score = processBoard(board);
			}
			
			return score / (depth - level + 1);     //weights the score based on how far in the future the turn occurs
		}
		
		//if it is the ai's turn
		if(maximize) {
			for(int j = 0 ; j < board.width() ; j++) {
				if(board.isLegalMove(j)) {
					board.makeMovePlayerTwo(j);
					alpha = Math.max(alpha, alphabeta(level - 1, alpha, beta, false));
					board.undoMove(j);
					if(beta <= alpha) break;
				}
			}
			return alpha;
		}
		
		//if it is the player's turn
		else {
			for(int j = 0 ; j < board.width() ; j++) {
				if(board.isLegalMove(j)) {
					board.makeMovePlayerOne(j);
					beta = Math.min(beta, alphabeta(level - 1, alpha, beta, true));
					board.undoMove(j);
					if(beta <= alpha) break;
				}
			}
			return beta;
		}
	}
}











