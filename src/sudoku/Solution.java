package sudoku;

public class Solution {
	public int cntState;
	public int initNum;
	public Board board;
	public boolean isFinished;
	
	public Solution(Board board, int initNum, int cnt) throws Exception
	{
		this.board = board;
		this.cntState = cnt;
		this.initNum = initNum;
		this.isFinished = board.sumUpDomainSizeAndForwardChecking()==0;
	}
}
