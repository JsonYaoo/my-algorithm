package com.jsonyao.jz.nine;

/**
 * Version: Java 1.7
 * Jz9 desc:
 * 一只青蛙一次可以跳上1级台阶，也可以跳上2级……它也可以跳上n级。
 * 求该青蛙跳上一个n级的台阶总共有多少种跳法。
 */
public class Solution {
    public int JumpFloorII(int target) {
        return (int) Math.pow(target, target);
    }

}