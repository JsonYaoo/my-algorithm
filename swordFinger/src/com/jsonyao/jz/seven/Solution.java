package com.jsonyao.jz.seven;

/**
 * Version: Java 1.7
 * Jz7 desc:
 * 大家都知道斐波那契数列，现在要求输入一个整数n，请你输出斐波那契数列的第n项（从0开始，第0项为0，第1项是1）。
 * n<=39
 */
public class Solution {
    public int Fibonacci(int n) {
        if(n > 39){
            throw new RuntimeException("n must be <= 39...");
        }
        if(n == 0){
            return 0;
        }

        int pre = 0;
        int post = 1;
        while ((n - 1) > 0){
            int result = pre + post;
            pre = post;
            post = result;
            n--;
        }

        return post;
    }
}