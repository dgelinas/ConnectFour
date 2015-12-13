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
	
	private static double valueOfMove(int col) {
		board.makeMovePlayerTwo(col);
		double v = alphabeta(depth, Integer.MIN_VALUE, Integer.MAX_VALUE, false);
		board.undoMove(col);
		return v;
	}
	
	public int makeMove() {
		int move = 0;
		double max = (double) Integer.MIN_VALUE;
		
		for(int j = 0 ; j < board.width() ; j++) {
			if(board.isLegalMove(j)) {
				double v = valueOfMove(j);
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
	
	private static double alphabeta(int level, double alpha, double beta, boolean maximize) {
		boolean hasWinner = board.hasWinner();
		
		//base case
		if(level == 0 || hasWinner) {
			//System.out.println("check");
			double score = 0.0;
			if(hasWinner) {
				if(board.findWinner() == 1)
					score = -1.0;
				else
					score = 1.0;
			}
			else {
				//other heuristic
				score = 0.0;
			}
			
			return score / (depth - level + 1);     //weights the score
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











