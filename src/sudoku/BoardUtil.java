package sudoku;

public class BoardUtil {
	public static Point getMCV(Board board) throws Exception
	{
		int[][] numInfeasibleDigit = new int[9][9];
		int[][] mat = board.mat;
		
		// calculate the matrix of number of infeasible digits
		for (int j=0;j<9;j++)
			for (int i=0;i<9;i++)
				if (mat[j][i]==0)				
					numInfeasibleDigit[j][i] = board.calNumOfInfeasibleDigits(i,j);				
		// find the max number of infeasible digits (0-9)
		int maxNumOfInfeasible=-1;
		for (int j=0;j<9;j++)
			for (int i=0;i<9;i++)			
				if (mat[j][i]==0 && maxNumOfInfeasible<numInfeasibleDigit[j][i])				
					maxNumOfInfeasible =numInfeasibleDigit[j][i];
		
		// among those with the max number of infeasible digits, find the one with max degree
		int maxDegree = -1;
		int tj=-1;
		int ti=-1;
		for (int j=0;j<9;j++)
			for (int i=0;i<9;i++)
			{
				if (numInfeasibleDigit[j][i]==maxNumOfInfeasible)
				{
					int degree = getDegree(board, i,j);
					if (maxDegree<degree)
					{
						maxDegree = degree;
						tj = j;
						ti = i;
					}
				}
			}
		if (maxNumOfInfeasible==-1 || maxDegree==-1)
			throw new Exception();
		return new Point(ti,tj);
	}
	
	private static int getDegree(Board board,int x, int y)
	{
		int[][] mat = board.mat;
		int cntDegree=0;
		int xLeft = x-(x%3);
		int xRight = xLeft+2;
		int yUp = y-(y%3);
		int yDown = yUp+2;
		for (int i=0;i<xLeft;i++)
			if (mat[y][i]==0) cntDegree++;
		for (int i=xLeft;i<=xRight;i++)
			if (mat[y][i]==0) cntDegree++;
		for (int i=xRight+1;i<9;i++)
			if (mat[y][i]==0) cntDegree++;
		
		for (int j=0;j<yUp;j++)
			if (mat[j][x]==0) cntDegree++;
		for (int j=yUp;j<=yDown;j++)
			if (mat[j][x]==0) cntDegree++;
		for (int j=yDown+1;j<9;j++)
			if (mat[j][x]==0) cntDegree++;
		
		for (int i=xLeft;i<=xRight;i++)
			for (int j=yUp;j<=yDown;j++)
				cntDegree++;
		if (mat[y][x]==0) cntDegree--;
		return cntDegree;
	}
	
	

	public static boolean[] calFeasibleDigit(Board board,int x,int y)
	{
		int[][] mat = board.mat;
		boolean[] feasible = new boolean[10];
		for (int i=0;i<=9;i++) feasible[i] = true;
		int xLeft = x-(x%3);
		int xRight = xLeft+2;
		int yUp = y-(y%3);
		int yDown = yUp+2;
		for (int i=0;i<9;i++)
			if (mat[y][i]!=0) feasible[mat[y][i]] = false;
		
		for (int j=0;j<9;j++)
			if (mat[j][x]!=0) feasible[mat[j][x]] = false;
		
		for (int i=xLeft;i<=xRight;i++)
			for (int j=yUp;j<=yDown;j++)
				feasible[mat[j][i]] = false;
		return feasible;
	}
	
	public static String printFeasibleSet(boolean[] available)
	{
		StringBuilder sb = new StringBuilder();
		sb.append("{");
		for (int i=1;i<=9;i++)
		{
			if (available[i])
				sb.append(i+",");
		}
		sb.append("}");
		return sb.toString();
	}
	
	public static int countUnfilled(Board board)
	{
		int[][] mat = board.mat;
		int cnt=0;
		for (int j=0;j<9;j++)
			for (int i=0;i<9;i++)
			{
				if (mat[j][i]==0)
					cnt++;
			}
		return cnt;
	}
}
