package shudu02;

import java.util.Random;

public class ShuduPuzzle {


	private Random random = new Random();

	//���������������buildRandomArray()�Ĵ���
	int currentTimes;

	/**���д˳���300�Σ����ֵ��217����Сֵ11��ƽ��Լ����50
	 * ��ֵ����Ϊ220�� ������󲿷ֳ��򣬶�ά���󲻻���Ϊ0�������ٲ���ֵ��
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

		//�ж�ÿһ�в�����ͬ
		for(int i = 0;i < row;i++){
			for(int col = 0;col < 9;col++){
				if(Matrix[i][col] == tempMatrix[col]){
					return false;
				}
			}
		}
		//�ж�ÿ��С�Ź��񲻿�����ͬ
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
	 * ����һ����1��9�Ÿ���������е�һά����,
	 */
	private int[] buildRandomArray() {

		currentTimes++;

		int[] array = { 1, 2, 3, 4, 5, 6, 7, 8, 9 };
		int randomInt = 0;
		/*
		 * �������һ��1��8���������ʹ�ø��±����ֵ���±�Ϊ0����ֵ������
		 * �����Σ��ܹ���ȡһ����1��9�Ÿ���������е�һά����,
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
