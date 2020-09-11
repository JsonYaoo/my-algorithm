package com.jsonyao.jz.twenty;

/**
 * Version: Java 1.7
 * Jz20 desc:
 * 定义栈的数据结构，请在该类型中实现一个能够得到栈中所含最小元素的min函数（时间复杂度应为O（1））。
 */
import java.util.Stack;

public class Solution {

//    private Stack<Integer> stack = new Stack<>();
//    private Integer minValue = null;
//    private Integer lastMinValue = null;
//
//    public void push(int node) {
//        if(minValue == null){
//            lastMinValue = minValue;
//            minValue = node;
//        }else if(node <= minValue){
//            lastMinValue = minValue;
//            minValue = node;
//        }
//        stack.push(node);
//    }
//
//    public void pop() {
//        int node = stack.pop();
//        if(node <= minValue){
//            minValue = lastMinValue;
//        }
//    }
//
//    public int top() {
//        return stack.firstElement();
//    }
//
//    public int min() {
//        return minValue;
//    }

    private Stack<Integer> stack1 = new Stack<>();
    private Stack<Integer> stack2 = new Stack<>();// 辅助栈

    public void push(int node) {
        if(stack2.isEmpty()){
            stack2.push(node);
        }else {
            if(node <= stack2.peek()){
                stack2.push(node);
            }
        }
        stack1.push(node);
    }

    public void pop() {
       int popNode = stack1.pop();
       if(popNode == stack2.peek()){
           stack2.pop();
       }
    }

    public int top() {
        return stack1.peek();
    }

    public int min() {
        return stack2.peek();
    }

    public static void main(String[] args) {
        // ["PSH3","MIN","PSH4","MIN","PSH2","MIN","PSH3","MIN","POP","MIN","POP","MIN","POP","MIN","PSH0","MIN"]
        // 3,3,2,2,2,3,3,0
        Solution solution = new Solution();
        solution.push(3);
        System.out.print(solution.min() + ",");
        solution.push(4);
        System.out.print(solution.min() + ",");
        solution.push(2);
        System.out.print(solution.min() + ",");
        solution.push(3);
        System.out.print(solution.min() + ",");
        solution.pop();
        System.out.print(solution.min() + ",");
        solution.pop();
        System.out.print(solution.min() + ",");
        solution.pop();
        System.out.print(solution.min() + ",");
        solution.push(0);
        System.out.print(solution.min());
    }
}