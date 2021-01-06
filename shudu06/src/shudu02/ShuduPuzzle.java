package shudu02;

import java.util.Random;

public class ShuduPuzzle {


	private Random random = new Random();

	//调用随机生成数组buildRandomArray()的次数
	int currentTimes;

	/**运行此程序300次，最大值是217，最小值11，平均约等于50
	 * 阈值设置为220， 能满足大部分程序，二维矩阵不会置为0，重新再产生值。
	 */
	private static final int MAX_CALL_RANDOM_ARRAY_TIMES = 220;

	int[][] Matrix = new int[9][9];

	public int[][] getPuzzleMatrix() {

		currentTimes = 0;
		for(int i = 0;i < 9;i++){
			for(int j = 0;j < 9;j++){
				Matrix[i][j]=0;
			}
		}

		for(int row = 0;row < 9;){
			if(row == 0){
				Matrix[row] = buildRandomArray();
			}else{
				int[] tempMatrix = buildRandomArray();

				if(currentTimes > MAX_CALL_RANDOM_ARRAY_TIMES){
					row -= 1;
					for(int i = 0;i < 9;i++){
						for(int j = 0;j < 9;j++){
							Matrix[i][j]=0;
						}
					}
					currentTimes = 0;
				}
				else{

					if(check(tempMatrix,row)==true){
						Matrix[row] = tempMatrix;
						row++;
					}
				}
				//getMatrixRow(row);
			}
		}

		return Matrix;
	}


	private boolean check(int[] tempMatrix, int row) {

		//判断每一列不能相同
		for(int i = 0;i < row;i++){
			for(int col = 0;col < 9;col++){
				if(Matrix[i][col] == tempMatrix[col]){
					return false;
				}
			}
		}
		//判断每个小九宫格不可有相同
		Matrix[row]=tempMatrix;

		int[] num = {0,0,0,0,0,0,0,0,0,0};
		for(int i = 0,col = 0;i < 9;col+=3,i+=3){
			num[Matrix[i][col]]++;
			num[Matrix[i][col+1]]++;
			num[Matrix[i][col+2]]++;
			num[Matrix[i+1][col]]++;
			num[Matrix[i+1][col+1]]++;
			num[Matrix[i+1][col+2]]++;
			num[Matrix[i+2][col]]++;
			num[Matrix[i+2][col+1]]++;
			num[Matrix[i+2][col+2]]++;

			for(int j=1;j<=9;j++){
				if(num[j]>1)return false;
			}
		}
		int[] num1 = {0,0,0,0,0,0,0,0,0};
		Matrix[row]=num1;

		return true;

	}


	/**
	 * 返回一个有1到9九个数随机排列的一维数组,
	 */
	private int[] buildRandomArray() {

		currentTimes++;

		int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int randomInt = 0;
		/*
		 * 随机产生一个1到8的随机数，使得该下标的数值与下标为0的数值交换，
		 * 处理多次，能够获取一个有1到9九个数随机排列的一维数组,
		 */
		for (int i = 0; i < 20; i++) {
			randomInt = random.nextInt(8) + 1;
			int temp = array[0];
			array[0] = array[randomInt];
			array[randomInt] = temp;
		}
		//for(int i=0;i<9;i++)System.out.print(array[i]+" ");
		//System.out.println("");
		return array;


	}


}
