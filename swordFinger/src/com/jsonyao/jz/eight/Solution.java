package com.jsonyao.jz.eight;

/**
 * Version: Java 1.7
 * Jz8 desc:
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法（先后次序不同算不同的结果）。
 */
public class Solution {

    public int JumpFloor(int target) {
        if(target < 3){
            return target;
        }
        target += 1;
        return f(target - 1) + f(target - 2);
    }

    public int f(int n){
        if(n == 0){
            return 0;
        }
        if(n == 1){
            return 1;
        }

        int pre = 0;
        int post = 1;
        int result = 0;
        while ((n - 1) > 0){
            result = pre + post;
            pre = post;
            post = result;
            n--;
        }

        return result;
    }

}