package com.jsonyao.jz.eleven;

/**
 * Version: Java 1.7
 * Jz11 desc:
 * 输入一个整数，输出该数32位二进制表示中1的个数。其中负数用补码表示。
 */
public class Solution {
    public int NumberOf1(int n) {
        char[] chars = new char[Integer.SIZE];
        for(int i = 0; i < chars.length; i++){
            chars[chars.length - 1 - i] = (char) (((n>>i) & 1) + '0');
        }
        int count = 0;
        for(int i = chars.length - 1; i > -1; i--){
            if(i == 1){
                System.out.println(i);
            }
            if(chars[i] == '1'){
                count++;
            }
        }
        if(n < 0){
            count++;
        }
        return count;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();
        System.out.println(solution.NumberOf1(-2147483648));
//        solution.NumberOf2(-2147483648);
    }

    public void NumberOf2(int n) {
        char s = (char) (((n>>1) & 1) + '0');
        System.out.println(s);
        s = (char) (((n>>1) & 1) + '0');
        System.out.println(s);
        s = (char) (((n>>1) & 1) + '0');
        System.out.println(s);
        s = (char) (((n>>1) & 1) + '0');
        System.out.println(s);
        s = (char) (((n>>1) & 1) + '0');
        System.out.println(s);
        s = (char) (((n>>1) & 1) + '0');
        System.out.println(s);
        s = (char) (((n>>1) & 1) + '0');
        System.out.println(s);
        s = (char) (((n>>1) & 1) + '0');
        System.out.println(s);
        s = (char) (((n>>1) & 1) + '0');
        System.out.println(s);
    }
}