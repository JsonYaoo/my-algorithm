package com.jsonyao.jz.nineteen;

/**
 * Version: Java 1.7
 * Jz19 desc:
 * 输入一个矩阵，按照从外向里以顺时针的顺序依次打印出每一个数字，
 * 例如，如果输入如下4 X 4矩阵： 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
 * 则依次打印出数字1,2,3,4,8,12,16,15,14,13,9,5,6,7,11,10.
 */
import java.util.ArrayList;

public class Solution {

    public ArrayList<Integer> printMatrix(int[][] matrix) {
        ArrayList<Integer> res = new ArrayList<>();

        /**
         * 思路是：控制四个角的遍历初始位置依次遍历即可
         */
        int count = matrix.length * matrix[0].length;
        int colStart = 0;// 起始列
        int colEnd = matrix[0].length;// 结束列
        int rowStart = 0;// 起始行
        int rowEnd = matrix.length;// 结束行

        while (count > 0){
            // 从左到右遍历
            for(int i = colStart; i < colEnd; i++){
                res.add(matrix[rowStart][i]);// 行号不变 列号改变
                count--;
            }
            if(count <= 0) break;
            rowStart++;

            // 从上到下遍历
            for(int i = rowStart; i < rowEnd; i++){
                res.add(matrix[i][colEnd - 1]);// 列号不变 行号改变
                count--;
            }
            if(count <= 0) break;
            colEnd--;


            // 从右到左遍历
            for(int i = colEnd - 1; i >= colStart; i--){
                res.add(matrix[rowEnd - 1][i]);// 行号不变 列号改变
                count--;
            }
            if(count <= 0) break;
            rowEnd--;

            // 从下到上遍历
            for(int i = rowEnd - 1; i >= rowStart; i--){
                res.add(matrix[i][colStart]);// 列号不变 行号改变
                count--;
            }
            if(count <= 0) break;
            colStart++;
        }

        return res;
    }


    public static void main(String[] args) {
        Solution solution = new Solution();
        // 1 2 3 4 5 6 7 8 9 10 11 12 13 14 15 16
//        int[][] m = new int[][]{
//            {1, 2, 3, 4},
//            {5, 6, 7, 8},
//            {9, 10, 11, 12},
//            {13, 14, 15, 16}
//        };

        int[][] m = new int[][]{
                {1},
                {2},
                {3},
                {4},
                {5},
        };

        solution.printMatrix(m);
    }
}