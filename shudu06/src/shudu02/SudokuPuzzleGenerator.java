package shudu02;

import java.util.Random;

public class SudokuPuzzleGenerator {

	private Random random = new Random();
	
	/**���д˳���300�Σ����ֵ��217����Сֵ11��ƽ��Լ����50
	 * ��ֵ����Ϊ220�� ������󲿷ֳ��򣬶�ά���󲻻���Ϊ0�������ٲ���ֵ��
	 */
	private static final int MAX_CALL_RANDOM_ARRAY_TIMES = 220;

	/**��¼��ǰbuildRandomArray()�������õĴ���*/
	private int currentTimes = 0;

	public int[][] generatePuzzleMatrix(int hole) {

		int[][] randomMatrix = new int[9][9];
		
		if(hole == 81){
			for(int i = 0;i < 9; i++){
				for(int j = 0;j < 9; j++){
					randomMatrix[i][j] = 0;
				}
			}
		}
		else{
			
			for (int row = 0; row < 9; row++) {
				if (row == 0) {
					currentTimes = 0;
					randomMatrix[row] = buildRandomArray();

				}else {
					int[] tempRandomArray = buildRandomArray();

					for (int col = 0; col < 9; col++) {
						if (currentTimes < MAX_CALL_RANDOM_ARRAY_TIMES) {
							if (!isCandidateNmbFound(randomMatrix, tempRandomArray,
								row, col)) {
								/*
							 	* �����е�������Ϊ0��������Ϊ��׼��һά���������
							 	*/
								resetValuesInRowToZero(randomMatrix,row);
								row -= 1;
								col = 8;
								tempRandomArray = buildRandomArray();
							}
						} else {
							/*
						 	* ����ά�����е���ֵ��Ϊ0��
						 	* row��ֵΪ-1 col��ֵΪ8�� ��һ��ִ�еľ���row =0 col=0��
						 	* ��ͷ��ʼ
 						 	*/
							row = -1;
							col = 8;
							resetValuesToZeros(randomMatrix);
							currentTimes = 0;
						}
					}
				}
			}
		
			//�ڶ�
			for(int i=0;i<hole;){
				int col = random.nextInt(9);
				int row = random.nextInt(9);
				if(randomMatrix[row][col] != 0){
					randomMatrix[row][col] = 0;
					i++;
				}
			}
		
		}
		
		return randomMatrix;
	}
	
	private void resetValuesInRowToZero(int[][] matrix, int row)
	{
		for (int j = 0; j < 9; j++) {
			matrix[row][j] = 0;
		}
		
	}

	private void resetValuesToZeros(int[][] matrix) {
		for (int row = 0; row < 9; row++) {
			for (int col = 0; col < 9; col++) {
				matrix[row][col] = 0;
			}
		}
	}

	private boolean isCandidateNmbFound(int[][] randomMatrix,
			int[] randomArray, int row, int col) {
		for (int i = 0; i < randomArray.length; i++) {
			/**
			 * ���Ÿ�randomMatrix[row][col] ��ֵ,���ж��Ƿ����
			 */

			randomMatrix[row][col] = randomArray[i];
			if (noConflict(randomMatrix, row, col)) {
				return true;
			}
		}
		return false;
	}

	private boolean noConflict(int[][] candidateMatrix, int row, int col) {
		return noConflictInRow(candidateMatrix, row, col)
				&& noConflictInColumn(candidateMatrix, row, col)
				&& noConflictInBlock(candidateMatrix, row, col);
	}

	private boolean noConflictInRow(int[][] candidateMatrix, int row, int col) {
		/**
		 * ��Ϊ��������������ǰ������к��У������Ҳ����� �����е�ǰ�к���������е�ֵ������0�� �������бȽϵ�ʱ��
		 * ֻҪ�жϸ��е�ǰ����֮ǰ����������ͬ�����ּ��ɡ�
		 * 
		 */
		int currentValue = candidateMatrix[row][col];

		for (int colNum = 0; colNum < col; colNum++) {
			if (currentValue == candidateMatrix[row][colNum]) {
				return false;
			}
		}

		return true;
	}

	private boolean noConflictInColumn(int[][] candidateMatrix, int row, int col) {

		/**
		 * ��noConflictInRow(...)�������ƣ�
		 * 
		 * 
		 * ��Ϊ��������������ǰ������к��У������Ҳ����ģ����е�ǰ�к���������е�ֵ������0��
		 * 
		 * �������бȽϵ�ʱ�� ֻҪ�жϸ��е�ǰ����֮ǰ����������ͬ�����ּ��ɡ�
		 * 
		 */

		int currentValue = candidateMatrix[row][col];

		for (int rowNum = 0; rowNum < row; rowNum++) {
			if (currentValue == candidateMatrix[rowNum][col]) {
				return false;
			}
		}

		return true;
	}

	private boolean noConflictInBlock(int[][] candidateMatrix, int row, int col) {

		/**
		 * Ϊ�˱Ƚ�3 x 3 ����������Ƿ���� ��Ҫȷ������һ��Block��������Ҫ���3 x 3����ʼ�㡣 ���磺 Block 1
		 * ����ʼ����[0][0] Block 2 ����ʼ����[3]][0]
		 * 
		 * ... Block 9 ����ʼ����[6][6]
		 */

		int baseRow = row / 3 * 3;
		int baseCol = col / 3 * 3;

		for (int rowNum = 0; rowNum < 8; rowNum++) {
			if (candidateMatrix[baseRow + rowNum / 3][baseCol + rowNum % 3] == 0) {
				continue;
			}
			for (int colNum = rowNum + 1; colNum < 9; colNum++) {
				if (candidateMatrix[baseRow + rowNum / 3][baseCol + rowNum % 3] == candidateMatrix[baseRow
						+ colNum / 3][baseCol + colNum % 3]) {
					return false;
				}
			}
		}
		return true;

	}

	/**
	 * ����һ����1��9�Ÿ���������е�һά����,
	 */
	private int[] buildRandomArray() {
		
		currentTimes++;
		
		int[] array = new int[] { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int randomInt = 0;
		/*
		 * �������һ��1��8���������ʹ�ø��±����ֵ���±�Ϊ0����ֵ������
		 *  �����Σ��ܹ���ȡһ����1��9�Ÿ���������е�һά����,
		 */
		for (int i = 0; i < 20; i++) {
			randomInt = random.nextInt(8) + 1;
			int temp = array[0];
			array[0] = array[randomInt];
			array[randomInt] = temp;
		}

		return array;
	}

	/**
	 * @return the currentTimes
	 */
	public int getCurrentTimes() {
		return currentTimes;
	}

	/**
	 * @param currentTimes the currentTimes to set
	 */
	public void setCurrentTimes(int currentTimes) {
		this.currentTimes = currentTimes;
	}
	
}

