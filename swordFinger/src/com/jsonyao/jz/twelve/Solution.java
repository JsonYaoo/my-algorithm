package com.jsonyao.jz.twelve;

/**
 * Version: Java 1.7
 * Jz12 desc:
 * 给定一个double类型的浮点数base和int类型的整数exponent。求base的exponent次方。
 * 保证base和exponent不同时为0
 */
public class Solution {
    public double Power(double base, int exponent) {
        boolean negetiveE = false;
        if(exponent < 0){
            if(base == 0){
                new RuntimeException("Param is invalid.");
            }
            negetiveE = true;
            exponent = -exponent;
        }
        /**
         * 快速幂算法核心概念
         * (n & 1 == 1) == (n % 2 == 1)
         * n >>= 1 == n /= 2
         */
        double rst = 1d;
        while (exponent > 0){
            if((exponent & 1) == 1){
                rst *= base;// 2^(101) => 2^4 * 2^1
            }
            base *= base;// 2^4 | 2^1
            exponent >>= 1;// 4 | 1
        }
        return !negetiveE? rst : 1 / rst;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        solution.Power(0, 3);
    }
}