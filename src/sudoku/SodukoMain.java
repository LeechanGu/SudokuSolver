package sudoku;

import java.util.ArrayList;
import java.util.List;

public class SodukoMain {

	
	public static void main(String[] args) throws Exception {
		// TODO Auto-generated method stub
		String folderURL = "C:\\Users\\Administrator\\Google ‘∆∂À”≤≈Ã\\Slides\\CS686\\A2\\SodukoProblem\\problems\\";
		List<String> nameList = Loader.getAllSdURLFromFolder(folderURL);
		List<Solution> record = new ArrayList<Solution>();
		for (String name:nameList)
		{
			//System.out.println(name);
			Board board = Loader.loadInitialBoard(name);
			SudokuSolver solver = SudokuSolver.getInstance();
			Solution solution = solver.solve(board);
			record.add(solution);
			System.out.print(solution.initNum+" ");
			System.out.print(solution.cntState+" ");
			//System.out.println(solution.board);
			System.out.println(solution.isFinished?"Finished":"Failed");
			if (solution.cntState>0)
			{
				System.out.println(board);
				System.out.println(solution.board);
			}
			//if (!solution.isFinished) break;
		}
		//printStatistics(record);
		
	}
	
	private static void printStatistics(List<Solution> record)
	{
		int[] sumArr = new int[72];
		int[] cntArr = new int[72];
		for (int i=0;i<record.size();i++)
		{
			if (record.get(i).isFinished)
			{
				sumArr[record.get(i).initNum]+=record.get(i).cntState;
				cntArr[record.get(i).initNum]++;
			}
		}
		for (int i=1;i<72;i++)
		{
			System.out.println(i+" "+ sumArr[i]/cntArr[i]);
		}
	}
}
