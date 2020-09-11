package com.jsonyao.jz.thirteen;

/**
 * Version: Java 1.7
 * Jz13 desc:
 * 输入一个整数数组，实现一个函数来调整该数组中数字的顺序，
 * 使得所有的奇数位于数组的前半部分，所有的偶数位于数组的后半部分，并保证奇数和奇数，偶数和偶数之间的相对位置不变。
 */
public class Solution {
    public void reOrderArray(int [] array) {
        int[] oddArr = new int[array.length];// 奇数数组
        int[] evenArr = new int[array.length];// 偶数数组

        int oddIndex = 0;
        int evenIndex = 0;
        for(int i = 0; i < array.length; i++){
            if((array[i] & 1) == 1){// 奇数
                oddArr[oddIndex] = array[i];
                oddIndex++;
            }else {
                evenArr[evenIndex] = array[i];
                evenIndex++;
            }
        }

        for(int i = 0; i < oddIndex; i++){
            array[i] = oddArr[i];
        }
        for(int i = 0; i < evenIndex; i++){
            array[i+oddIndex] = evenArr[i];
        }
    }
}