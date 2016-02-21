package sudoku;

public class Board {
	int[][] mat;
	public Board(int[][] mat) throws Exception
	{
		if (mat==null || mat.length!=9) throw new Exception();
		for (int i=0;i<9;i++)
			if (mat[i].length!=9) throw new Exception();
		this.mat = mat;
	}
	
	public Board cloneIt() throws Exception
	{
		int[][] nmat = new int[9][9];
		for (int i=0;i<9;i++)
			nmat[i] = mat[i].clone();
		return new Board(nmat);
	}
	
	public String toString()
	{
		StringBuilder sb = new StringBuilder();
		for (int j=0;j<9;j++)
		{
			for (int i=0;i<9;i++)
			{
				sb.append(mat[j][i]+" ");
			}
			sb.append('\n');
		}
		return sb.toString();
	}
	
	public void setPoint(int x,int y,int digit)
	{
		mat[y][x] = digit;
	}
	
	public void resetPoint(int x,int y)
	{
		mat[y][x] = 0;
	}
	
	public Point getMaximunConstrainedAndConstrainingVariable() throws Exception
	{
		return BoardUtil.getMCV(this);
	}
	
	public int calNumOfInfeasibleDigits(int x,int y)
	{
		boolean[] available = getDomain(x,y);
		int cnt=0;
		for (int i=1;i<=9;i++)
			if (!available[i])
				cnt++;
		return cnt;
	}
	
	public int getDomainSize(int x,int y)
	{
		boolean[] available = getDomain(x,y);
		int cnt=0;
		for (int i=1;i<=9;i++)
			if (available[i])
				cnt++;
		return cnt;
	}
	
	public Integer sumUpDomainSizeAndForwardChecking() throws Exception
	{
		int total = 0;
		for (int j=0;j<9;j++)
			for (int i=0;i<9;i++)
			{
				if (mat[j][i]==0)
				{
					int constraint = getDomainSize(i,j);
					if (constraint>0)
						total+=constraint;
					else
						return null;
				}
			}
		return total;
	}
	
	/**
	 * Get the domain for a particular variable
	 * @param x
	 * @param y
	 * @return
	 */
	public boolean[] getDomain(int x,int y)
	{
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
	
	/**
	 * check whether the board is a valid solution
	 * @return
	 */
	public boolean isValid()
	{
		
		for (int j=0;j<9;j++)
		{
			boolean[] all = new boolean[10];
			for (int i=0;i<9;i++)
			{
				all[mat[j][i]] = true;
			}
			for (int i=1;i<10;i++)
			{
				if (all[i]==false) return false;
			}
		}
		
		for (int i=0;i<9;i++)
		{
			boolean[] all = new boolean[10];
			for (int j=0;j<9;j++)
			{
				all[mat[j][i]] = true;
			}
			for (int j=1;j<10;j++)
			{
				if (all[j]==false) return false;
			}
		}
		
		for (int tj=0;tj<3;tj++)
			for (int ti=0;ti<3;ti++)
			{
				boolean[] all = new boolean[10];
				for (int j=tj*3;j<(tj+1)*3;j++)
					for (int i=ti*3;i<(ti+1)*3;i++)
						all[mat[j][i]] = true;
				for (int j=1;j<10;j++)
				{
					if (all[j]==false) return false;
				}
			}
		return true;
	}
	
	public int countUnfilled()
	{
		return BoardUtil.countUnfilled(this);
	}
}
