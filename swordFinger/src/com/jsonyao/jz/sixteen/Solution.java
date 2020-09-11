package com.jsonyao.jz.sixteen;

/**
 * Version: Java 1.7
 * Jz16 desc:
 * 输入两个单调递增的链表，输出两个链表合成后的链表，当然我们需要合成后的链表满足单调不减规则。
 */
/*
public class ListNode {
    int val;
    ListNode next = null;

    ListNode(int val) {
        this.val = val;
    }
}*/
public class Solution {

    static class ListNode {
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }
    }

    public ListNode Merge(ListNode list1, ListNode list2) {
//        /**
//         * 非递归实现思路:
//         *  a. 循环判断list1、list2, 为每一步的head赋值, 直到完成
//         */
//        ListNode head = new ListNode(-1);// 先初始化
//        head.next = null;
//
//        ListNode rst = head;// 记录初始头结点
//        while (list1 != null && list2 != null){
//            if(list1.val < list2.val){// list1的小
//                head.next = list1;// 下一个结点取list1
//                head = head.next;// 移动head头结点
//                list1 = list1.next;// 移动list1头结点
//            }else {// list2的小
//                head.next = list2;// 下一个结点取list2
//                head = head.next;// 移动head头结点
//                list2 = list2.next;// 移动list2头结点
//            }
//        }
//        // list1 或者 list2 任意一个链表到头了, 则合并另外的一个到末尾
//        if(list1 != null){
//            head.next = list1;
//        }
//        if(list2 != null){
//            head.next = list2;
//        }
//        return rst.next;// 返回-1结点后面的那个链表

        /**
         * 递归实现思路:
         *  a. 判断list1、list2头结点的大小, 分别对大的或者小的进行分治处理
         */
        if(list1 == null){
            return list2;
        }
        if(list2 == null){
            return list1;
        }
        ListNode head;
        if(list1.val < list2.val){
            head = list1;
            head.next = this.Merge(list1.next, list2);
        }else {
            head = list2;
            head.next = this.Merge(list1, list2.next);
        }

        return head;
    }
}