public class Board {
	public final int EMPTY = 0;
	public final int PLAYER_ONE = 1;
	public final int PLAYER_TWO = 2;
	private static int[][] board;
	private static int rows, cols;
	
	public Board() {
		rows = 6;
		cols = 7;
		board = new int[rows][cols];
		
		for(int i = 0 ; i < rows ; i++)
			for(int j = 0 ; j < cols ; j++)
				board[i][j] = 0;
	}
	
	public boolean isLegalMove(int col) {
		if(col > cols - 1)
			return false;
		if(board[0][col] != 0)
			return false;
		
		return true;
	}
	
	public void makeMovePlayerOne(int col) {
		for(int i = rows - 1 ; i >= 0 ; i--) {
			if(board[i][col] == 0) {
				board[i][col] = PLAYER_ONE;
				break;
			}
		}
	}
	
	public void undoMove(int col) {
		for(int i = rows - 1 ; i >= 0 ; i--) {
			if(board[i][col] == 0) {
				board[i + 1][col] = 0;
				break;
			}
			if(i == 0) board[i][col] = 0;
		}
	}
	
	public void makeMovePlayerTwo(int col) {
		for(int i = rows - 1 ; i >= 0 ; i--) {
			if(board[i][col] == 0) {
				board[i][col] = PLAYER_TWO;
				break;
			}
		}
	}
	
	public int findRowWinner() {
		for(int i = 0 ; i < rows ; i++) {
			int counter = 1;
			for(int j = 1 ; j < cols ; j++) {
				if(board[i][j] != 0 && board[i][j] == board[i][j - 1]) {
					counter++;
					if(counter == 4)
						return board[i][j];
				}
				else
					counter = 1;
			}
		}
		
		return 0;
	}
	
	public int findColWinner() {
		for(int j = 0 ; j < cols ; j++) {
			int counter = 1;
			for(int i = 1 ; i < rows ; i++) {
				if(board[i][j] != 0 && board[i][j] == board[i - 1][j]) {
					counter++;
					if(counter == 4)
						return board[i][j];
				}
				else
					counter = 1;
			}
		}
		
		return 0;
	}
	
	public int findDiagWinner() {
		//checks right-diagonals along the top row
		for(int j = 0 ; j < cols ; j++) {
			int counter = 1;
			for(int i = 1 ; i < rows ; i++) {
				if(i + j >= cols) break;
				if(board[i][i + j] != 0 && board[i][i + j] == board[i - 1][i + j - 1]) {
					counter++;
					if(counter == 4)
						return board[i][i + j];
				}
				else
					counter = 1;
			}
		}
		
		//checks right-diagonals along the first column
		for(int i = 1 ; i < rows ; i++) {
			int counter = 1;
			for(int j = 1 ; j < cols ; j++) {
				if(i + j >= rows) break;
				if(board[i + j][j] != 0 && board[i + j][j] == board[i + j - 1][j - 1]) {
					counter++;
					if(counter == 4)
						return board[i + j][j];
				}
				else
					counter = 1;
			}
		}
		
		//checks left-diagonals along the top row
		for(int j = 0 ; j < cols ; j++) {
			int counter = 1;
			for(int i = 1 ; i < rows ; i++) {
				if(j - i < 0) break;
				if(board[i][j - i] != 0 && board[i][j - i] == board[i - 1][j - i + 1]) {
					counter++;
					if(counter == 4)
						return board[i][j - i];
				}
				else
					counter = 1;
			}
		}
		
		//checks left-diagonals along the last column
		for(int i = 1 ; i < rows ; i++) {
			int counter = 1;
			for(int j = cols - 2 ; j >= 0 ; j--) {
				if(i + j >= rows) break;
				if(board[j - i][j] != 0 && board[j - i][j] == board[j - i - 1][j + 1]) {
					counter++;
					if(counter == 4)
						return board[i + j][j];
				}
				else
					counter = 1;
			}
		}
		
		return 0;
	}
	
	public boolean hasWinner() {
		return (this.findRowWinner() + this.findColWinner() 
			     + this.findDiagWinner() != 0);
	}
	
	public int findWinner() {
		int winner = this.findRowWinner();
		if(winner != 0) return winner;
		winner = this.findColWinner();
		if(winner != 0) return winner;
		winner = this.findDiagWinner();
		if(winner != 0) return winner;
		
		for(int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < cols ; j++) {
				if(board[i][j] == 0) return 0;
			}
		}
		
		return -1;
	}
	
	public void print() {
		System.out.println("1 2 3 4 5 6 7");
		System.out.println("-------------");
		for(int i = 0 ; i < rows ; i++) {
			for(int j = 0 ; j < cols ; j++) {
				int x = board[i][j];
				if(x == 0)
					System.out.print(". ");
				else if(x == 1)
					System.out.print("x ");
				else
					System.out.print("o ");
			}
			System.out.println("");
		}
		System.out.println("");
	}
	
	public int width() {
		return cols;
	}
	
	public int height() {
		return rows;
	}
}
