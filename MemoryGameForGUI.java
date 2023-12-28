package edu.wm.cs.cs301.guimemorygame.model;

public class MemoryGameForGUI {
    private GameBoard board;
    private int cols, rows;
    
    public MemoryGameForGUI(int rows, int cols,int alphabetTypeSelected) {
    	this.rows = rows;
    	this.cols = cols;
        Alphabet greekAlphabet = new GreekAlphabet();
        
        board = new GameBoard(rows, cols, greekAlphabet);
    }

	public int getRows() {
		return rows;
	}
	public int getCols() {
		return cols;
	}
	public char getSymbol(int row, int col) {
		return board.returnSymbol(row, col);
	}
	public boolean getSymbolFlip(int row, int col) {
		return board.returnSymbolFlip(row, col);
	}
}