package javaFXproject;

public class Board {

	private int[][] board;

	public Board() {
		this.board = new int[9][9];
	}

	public Board(int[][] arr) {
		this.board=arr;
	}

	//print board
	public void printBoard(Board board) {
		for (int i = 0; i < board.length(); i++) {
			for (int j = 0; j < board.length(); j++) {
				System.out.print("["+board.getBaord(i, j)+"]");
			}
			System.out.println();
		}
		System.out.println();
	}

	//print board
	public void printBoard(Board board,String str) {
		System.out.println(str);
		for (int i = 0; i < board.length(); i++) {
			for (int j = 0; j < board.length(); j++) {
				System.out.print("["+board.getBaord(i, j)+"]");
			}
			System.out.println();
		}
		System.out.println();
	}

	//set 1 digit in table
	public void setBaord(int i,int j,int k) {
		this.board[i][j]=k;
	}

	//get 1 digit in table
	public int getBaord(int i,int j) {
		return this.board[i][j];
	}

	public int length() {
		return this.board.length;
	}


	//check if Sudoku solved properly
	public boolean solved(Board board) {
		if(BoardHasAllNumbers(board)) {
			for (int i = 0; i < this.board.length; i++) {
				for (int j = 0; j < this.board[i].length; j++) {
					if(!unickNumberInRowCol(board,i,j)) {
						return false;
					}
				}
			}
		}
		else {
			return false;
		}
		return true;
	}

	//check if each square has all the number 1-9
	private boolean BoardHasAllNumbers(Board board) {
		int[] squer1 = new int[9];
		int[] squer2 = new int[9];
		int[] squer3 = new int[9];
		int[] squer4 = new int[9];
		int[] squer5 = new int[9];
		int[] squer6 = new int[9];
		int[] squer7 = new int[9];
		int[] squer8 = new int[9];
		int[] squer9 = new int[9];
		int number=0;

		for (int i=0;i<3;i++) {
			for (int j = 0; j < 3; j++) {
				number=board.getBaord(i, j);
				if (number>0)
					squer1[number-1]++;
			}
		}
		for (int i=0;i<3;i++) {
			for (int j = 3; j < 6; j++) {
				number=board.getBaord(i, j);
				if (number>0)
					squer2[number-1]++;
			}
		}
		for (int i=0;i<3;i++) {
			for (int j = 6; j < 9; j++) {
				number=board.getBaord(i, j);
				if (number>0)
					squer3[number-1]++;
			}
		}
		for (int i=3;i<6;i++) {
			for (int j = 0; j < 3; j++) {
				number=board.getBaord(i, j);
				if (number>0)
					squer4[number-1]++;			}
		}
		for (int i=3;i<6;i++) {
			for (int j = 3; j < 6; j++) {
				number=board.getBaord(i, j);
				if (number>0)
					squer5[number-1]++;			}
		}
		for (int i=3;i<6;i++) {
			for (int j = 6; j < 9; j++) {
				number=board.getBaord(i, j);
				if (number>0)
					squer6[number-1]++;			}
		}
		for (int i=6;i<9;i++) {
			for (int j = 0; j < 3; j++) {
				number=board.getBaord(i, j);
				if (number>0)
					squer7[number-1]++;			}
		}
		for (int i=6;i<9;i++) {
			for (int j = 3; j < 6; j++) {
				number=board.getBaord(i, j);
				if (number>0)
					squer8[number-1]++;			}
		}
		for (int i=6;i<9;i++) {
			for (int j = 6; j < 9; j++) {
				number=board.getBaord(i, j);
				if (number>0)
					squer9[number-1]++;			}
		}

		for(int k=0; k<squer1.length;k++) {
			if (squer1[k]!=1 || squer2[k]!=1 || squer3[k]!=1 || squer4[k]!=1 || squer5[k]!=1 || squer6[k]!=1 || squer7[k]!=1 || squer8[k]!=1 || squer9[k]!=1)
				return false;
		}

		return true;
	}

	//check if there is 2 identical numbers in the same row or col
	private boolean unickNumberInRowCol(Board board,int i, int j) {
		int number = board.getBaord(i, j);
		if (number == 0)
			return true;
		for (int m=0; m <i; m++) {
			if (board.getBaord(m, j)==number)
				return false;
		}
		for (int m=i+1; m <this.board.length; m++) {
			if (board.getBaord(m, j)==number)
				return false;
		}
		for (int l=0; l <j; l++) {
			if (board.getBaord(i, l)==number)
				return false;
		}
		for (int l=j+1; l <this.board.length; l++) {
			if (board.getBaord(i, l)==number)
				return false;
		}
		return true;
	}

	//return 2 digit, first digit 1:row;2:column, second digit  number of row/col, and 0 if cant find anything
	public int findIfMissingOneDigitInRowCol (Board board) {
		int[] digitInRow = new int[9];
		int[] digitInCol = new int[9];

		for (int i=0;i<9;i++) {
			if (board.getBaord(i, 0) != 0)
				digitInCol[0]++;
			if (board.getBaord(i, 1) != 0)
				digitInCol[1]++;
			if (board.getBaord(i, 2) != 0)
				digitInCol[2]++;
			if (board.getBaord(i, 3) != 0)
				digitInCol[3]++;
			if (board.getBaord(i, 4) != 0)
				digitInCol[4]++;
			if (board.getBaord(i, 5) != 0)
				digitInCol[5]++;
			if (board.getBaord(i, 6) != 0)
				digitInCol[6]++;
			if (board.getBaord(i, 7) != 0)
				digitInCol[7]++;
			if (board.getBaord(i, 8) != 0)
				digitInCol[8]++;
		}
		for (int k=0;k<9;k++)
			if (digitInCol[k]==8)
				return 2*10+k;

		for (int j=0;j<9;j++) {
			if (board.getBaord(0, j) != 0)
				digitInRow[0]++;
			if (board.getBaord(1, j) != 0)
				digitInRow[1]++;
			if (board.getBaord(2, j) != 0)
				digitInRow[2]++;
			if (board.getBaord(3, j) != 0)
				digitInRow[3]++;
			if (board.getBaord(4, j) != 0)
				digitInRow[4]++;
			if (board.getBaord(5, j) != 0)
				digitInRow[5]++;
			if (board.getBaord(6, j) != 0)
				digitInRow[6]++;
			if (board.getBaord(7, j) != 0)
				digitInRow[7]++;
			if (board.getBaord(8, j) != 0)
				digitInRow[8]++;
		}
		for (int k=0;k<9;k++)
			if (digitInRow[k]==8)
				return 1*10+k;
		return 0;
	}

	//fill the missing number in a row/column
	public Board fillMisingDigit (Board board, int i) {
		int[] arrayCounter = new int[9];
		int numberMissing=0;
		int row=0,col=0;
		if (i/10==1) {
			row=i%10;
			for (int j = 0; j < 9; j++) {
				int number = board.getBaord(row, j);
				if (number>0)
					arrayCounter[number-1]++;
			}
			for (int j = 0; j < arrayCounter.length; j++) {
				if (arrayCounter[j]==0)
					numberMissing = j+1;
			}
			for (int j = 0; j < 9; j++) {
				if (board.getBaord(row, j)==0) {
					board.setBaord(row, j, numberMissing);
					break;
				}
			}
		}
		else {
			col=i%10;
			for (int j = 0; j < 9; j++) {
				int number = board.getBaord(j, col);
				if (number>0)
					arrayCounter[number-1]++;
			}
			for (int j = 0; j < arrayCounter.length; j++) {
				if (arrayCounter[j]==0)
					numberMissing = j+1;
			}
			for (int j = 0; j < this.board[row].length; j++) {
				if (board.getBaord(j, col)==0) {
					board.setBaord(j, col, numberMissing);
					break;
				}
			}

		}

		return board;
	}

	//if not missing return 0, if missing digit: return number of square [1-9]
	public int findIfMissingOneDigitInsquare (Board board) {
		int[] squareCountr = new int[9];
		for (int i =0; i<9;i++) {
			for (int j =0; j <9; j++) {
				if(this.board[i][j]!=0) {
					if (i<3 && j<3)
						squareCountr[0]++;
					if (i<3 && j>=3 && j<6)
						squareCountr[1]++;
					if (i<3 && j>=6 && j<9)
						squareCountr[2]++;
					if (i>=3 && i<6 && j<3)
						squareCountr[3]++;
					if (i>=3 && i<6 && j>=3 && j<6)
						squareCountr[4]++;
					if (i>=3 && i<6 && j>=6 && j<9)
						squareCountr[5]++;
					if (i>=6 && i<9 && j<3)
						squareCountr[6]++;
					if (i>=6 && i<9 && j>=3 && j<6)
						squareCountr[7]++;
					if (i>=6 && i<9 && j>=6 && j<9)
						squareCountr[8]++;
				}

			}
			for (int j = 0; j < squareCountr.length; j++) {
				if (squareCountr[j]==8)
					return j+1;
			}
		}
		return 0;
	}

	// fill the missing number in a square
	public Board fillMisingDigitSquare (Board board, int number) {
		int row=0,col=0;
		switch (number) {
		case 2:
			col=3;
			break;
		case 3:
			col=6;
			break;
		case 4:
			row=3;
			break;
		case 5:
			row=3;
			col=3;
			break;
		case 6:
			row=3;
			col=6;
			break;
		case 7:
			row=6;
			break;
		case 8:
			row=6;
			col=3;
			break;
		case 9:
			row=6;
			col=6;
			break;
		}
		int[] arrCounter = new int[9];
		int m=0,l=0;//index of empty number
		for (int i =row; i<row+3;i++) {
			for(int j=col;j<col+3;j++) {
				if (board.getBaord(i, j)==0) {
					m=i;
					l=j;
				}
				int temp=board.getBaord(i, j);
				if (temp>0)
					arrCounter[temp-1]++;
			}
		}
		for (int i = 0; i < arrCounter.length; i++) {
			if (arrCounter[i]==0) {
				board.setBaord(m, l, i+1);
				break;
			}
		}
		return board;
	}

	//Try to place numbers in the correct position
	public Board tryPutNewNumberInside (Board board, int i, int j) {
		int[]  row = new int[9];
		int[]  col = new int[9];
		int[]  square = new int[9];
		int temp=0;
		for (int k = 0; k < 9; k++) {
			temp = board.getBaord(i, k);
			if (temp>0)
				row[temp-1]++;
		}
		for (int k = 0; k < 9; k++) {
			temp = board.getBaord(k, j);
			if (temp>0)
				col[temp-1]++;
		}
		for (int m=(i/3)*3; m<(i/3)*3+3;m++) {
			for (int l=(j/3)*3; l<(j/3)*3+3;l++) {
				temp = board.getBaord(m, l);
				if (temp>0)
					square[temp-1]++;			}
		}
		int count=0,number=0;
		for (int k=0;k<9;k++) {
			if (row[k]==1 || col[k]==1 || square[k]==1)
				count++;
			if (row[k]==0 && col[k]==0 && square[k]==0)
				number = k+1;
		}
		if (count==8)
			board.setBaord(i, j, number);

		return board;
	}

	public boolean hasProblem(Board board) {
		//find 2 identical numbers in rows/columns
		for (int i = 0; i < 9; i++) {
			for (int j = 0; j < 9;j++) {
				if(!unickNumberInRowCol(board,i,j)) {
					board.printBoard(board);
					System.out.println("problem 1 accured, in pisition: "+i+", "+j);
					return true;
				}
			}
		}

		//find 2 identical numbers in square

		for (int i = 1; i<9; i+=3) {
			for(int j =1; j <9; j+=3) {
				int[] numbers = {0,0,0,0,0,0,0,0,0,0};

				numbers[board.getBaord(i-1, j-1)]++;
				numbers[board.getBaord(i-1, j)]++;
				numbers[board.getBaord(i-1, j+1)]++;
				numbers[board.getBaord(i, j-1)]++;
				numbers[board.getBaord(i, j)]++;
				numbers[board.getBaord(i, j+1)]++;
				numbers[board.getBaord(i+1, j-1)]++;
				numbers[board.getBaord(i+1, j)]++;
				numbers[board.getBaord(i+1, j+1)]++;

				for (int k = 1; k < numbers.length; k++) {
					if (numbers[k]>1) {
						System.out.println("hsd problem 2");
						return true;}
				}
			}
		}	

		return false;
	}


}
