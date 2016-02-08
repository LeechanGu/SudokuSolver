package sudoku;

import java.util.LinkedList;
import java.util.PriorityQueue;
import java.util.Queue;

public class SudokuSolver {
	private static SudokuSolver instance;
	int numState;
	
	private SudokuSolver(){};
	public static SudokuSolver getInstance()
	{
		if (instance==null)
			instance = new SudokuSolver();
		return instance;			
	}
	
	public Solution solve(Board board) throws Exception
	{
		numState = 0;
		Board endBoard =  helper(board);
		return new Solution(endBoard,81-board.countUnfilled(),numState);
	}
	
	private class DigitConstraint implements Comparable<DigitConstraint>
	{
		int digit;
		int numConstraint;
		
		public DigitConstraint(int digit, int constraint)
		{
			this.digit = digit;
			this.numConstraint = constraint;
		}
		@Override
		public int compareTo(DigitConstraint o) {

			if (numConstraint>o.numConstraint)
				return 1;	// flip
			else if (numConstraint<o.numConstraint)
				return -1;	// flip
			else
				return 0;
		}
	}
	
	private Board helper(Board board) throws Exception
	{
		numState++;
		if (numState>=10000) return board;
		if (board.countUnfilled()==0 )
		{
			return board.cloneIt();
		}
		Point p = board.getMaximunConstrainedAndConstrainingVariable();
		boolean[] feasible = board.calFeasibleDigit(p.x, p.y);
		PriorityQueue<DigitConstraint> heap = new PriorityQueue<>();
		for (int i=1;i<=9;i++)
		{
			if (!feasible[i]) continue;
			board.setPoint(p.x, p.y, i);

			Integer totalConstraint = board.calTotalInfeasibleDigitsAndForwardChecking();
			if (totalConstraint!=null)
				heap.add(new DigitConstraint(i,totalConstraint));
		}
		//heap = printQueue(heap);
		board.resetPoint(p.x, p.y);
		// System.out.println(board);
		
		Board b = null;
		while (!heap.isEmpty())
		{
			DigitConstraint dc = heap.poll();
			// System.out.println(board);
			board.setPoint(p.x, p.y, dc.digit);			
			b = helper(board);
			if (b!=null) break;
		}
		board.resetPoint(p.x, p.y);
		return b;
	}
	
	private PriorityQueue<DigitConstraint> printQueue(Queue<DigitConstraint> queue)
	{
		PriorityQueue<DigitConstraint> q = new PriorityQueue<>();
		System.out.print("{");
		while(!queue.isEmpty())
		{
			DigitConstraint con = queue.poll();
			System.out.print("<"+con.digit+","+con.numConstraint+">");
			q.add(con);
		}
		System.out.println("}");
		return q;
	}
}
