package com.jsonyao.jz.twentyNine;

/**
 * Version: Java 1.7
 * Jz29
 *  keywords:
 *      a. 数组 b. 高级算法
 *  desc:
 *      输入n个整数，找出其中最小的K个数。例如输入4,5,1,6,2,7,3,8这8个数字，则最小的4个数字是1,2,3,4。
 */
import java.util.ArrayList;

public class Solution {
    public ArrayList<Integer> GetLeastNumbers_Solution(int[] input, int k) {
        /**
         * 思路一:
         *      a. 冒泡排序, 取前K个数则为最小的K个数
         *      b. 时间复杂度O(n^2)
         */
//        for(int i = 0; i < input.length; i++){
//            for(int j = i + 1; j < input.length; j++){
//                if(input[j] < input[i]){
//                    int temp = input[i];
//                    input[i] = input[j];
//                    input[j] = temp;
//                }
//            }
//        }
//
//        ArrayList<Integer> res = new ArrayList<>();
//        if(k > input.length){
//            return res;
//        }
//        for(int i = 0; i < k; i++){
//            res.add(input[i]);
//        }
//        return res;

//        /**
//         * 思路二:
//         *      a. 冒泡排序优化
//         *      b. 时间复杂度O(n^2)
//         */
//        ArrayList<Integer> res = new ArrayList<>();
//        if(k > input.length){
//            return res;
//        }
//
//        // 全数组比较
//        for(int i = 0; i < k; i++){
//            for(int j = input.length - 1; j > i; j--){
//                if(input[j] < input[j - 1]){
//                    int temp = input[j - 1];
//                    input[j - 1] = input[j];
//                    input[j] = temp;
//                }
//            }
//            res.add(input[i]);
//        }
//        return res;

        /**
         * 思路三：
         *      a. 利用对堆排序减少时间复杂度成O(n)
         */
        return null;
    }

    public static void main(String[] args) {
        // [4,5,1,6,2,7,3,8],10
        Solution solution = new Solution();
        int[] input = new int[]{
                4,5,1,6,2,7,3,8
        };
        ArrayList<Integer> res = solution.GetLeastNumbers_Solution(input, 4);
        System.out.println(res);
    }
}