package com.jsonyao.jz.twentyEight;

/**
 * Version: Java 1.7
 * Jz28
 *  keywords:
 *      a. 位运算 b. 分治
 *  desc:
 *      数组中有一个数字出现的次数超过数组长度的一半，请找出这个数字。
 *      例如输入一个长度为9的数组{1,2,3,2,2,2,5,4,2}。由于数字2在数组中出现了5次，超过数组长度的一半，因此输出2。如果不存在则输出0。
 */

public class Solution {

//    ArrayList<Integer> used = new ArrayList<>();

    public int MoreThanHalfNum_Solution(int[] array) {
        /**
         * 思路一:
         *      a. 利用冒泡思想, 统计过的起泡, 依次遍历完数组
         *      b. 时间复杂度: O(n^2)
         */
//        if(array.length == 0){
//            return 0;
//        }
//
//        for(int i = 0; i < array.length; i++){
//            if(this.quickSearch(array, array[i], i, array.length) > array.length / 2){
//                return array[i];
//            }
//        }
//
//        return 0;

        /**
         * 思路二:
         *      a. 采用阵地攻守思想:
         *          1) 初始士兵是1, 碰到相同数字士兵加1，, 碰到不同数字士兵减1
         *          2) 当士兵数量为0时, 补充新值作为新的士兵, 依次遍历, 最后留存在场上的很有可能就是主元素了
         *          3) 这时候知道目标元素后, 在对目标元素进行统计判断是否大于数组的一半即可
         *      b. 时间复杂度: O(n)
         */
        if(array.length == 0){
            return 0;
        }
        if(array.length == 1){
            return array[0];
        }

        int soldier = array[0];
        int count = 1;

        // 遍历主数组
        for(int i = 1; i < array.length; i++){
            if(count == 0){
                soldier = array[i];
                count = 1;
            }else {
                if(soldier == array[i]){
                    count++;
                }else {
                    count--;
                }
            }
        }

        // 对存留下来的士兵进行统计, 符合条件的返回, 不符合条件的返回0
        count = 0;
        for(int i = 0; i < array.length; i++){
            if(soldier == array[i]){
                count++;
            }
        }

        if(count > array.length / 2){
            return soldier;
        }else {
            return 0;
        }
    }

//    private int quickSearch(int[] arr, int target, int start, int end){
//        int count = 0;
//        for(int i = start; i < end; i++){
//            if(target == arr[i]){
//                count++;
//                if(count > arr.length / 2){
//                    return count;
//                }
//                if(!used.contains(target)){
//                    used.add(target);
//                }
//            }
//        }
//        return count;
//    }

    public static void main(String[] args) {
        // [1,2,3,2,2,2,5,4,2]
        Solution solution = new Solution();
        int[] array = new int[]{
            1,2,3,2,2,2,5,4,2
        };
        int target = solution.MoreThanHalfNum_Solution(array);
        System.out.println(target);
    }
}