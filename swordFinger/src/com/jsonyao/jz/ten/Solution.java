package com.jsonyao.jz.ten;

/**
 * Version: Java 1.7
 * Jz10 desc:
 * 我们可以用2*1的小矩形横着或者竖着去覆盖更大的矩形。
 * 请问用n个2*1的小矩形无重叠地覆盖一个2*n的大矩形，总共有多少种方法？
 */
public class Solution {
    public int RectCover(int target) {
        int pre = 0;
        int post = 1;
        int result = 0;

        while (target > 0){
            result = pre + post;
            pre = post;
            post = result;
            target--;
        }
        return result;
    }
}