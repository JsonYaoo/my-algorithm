package com.jsonyao.jz.fifteen;

/**
 * Version: Java 1.7
 * Jz15 desc:
 * 输入一个链表，反转链表后，输出新链表的表头。
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

    public ListNode ReverseList(ListNode head) {
        if(head == null){
            return null;
        }

        ListNode rst = null;// 构建新的反转链表
        ListNode next = null;// 表示下一个结点
        while (head != null){
            next = head.next;// 备份下一节点
            head.next = rst;// 更改下一节点
            rst = head;// 给反转链表设置节点
            head = next;// 移动到下一个节点
        }

        return rst;
    }
}