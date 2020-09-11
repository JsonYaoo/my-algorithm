package com.jsonyao.jz.fourteen;

/**
 * Version: Java 1.7
 * Jz14 desc:
 * 输入一个链表，输出该链表中倒数第k个结点。
 */
public class Solution {

    static class ListNode{
        int val;
        ListNode next = null;

        ListNode(int val) {
            this.val = val;
        }

        public ListNode(int val, ListNode next) {
            this.val = val;
            this.next = next;
        }
    }

    public ListNode FindKthToTail(ListNode head, int k) {
//        ArrayList<ListNode> nodeList = new ArrayList<>();
//
//        while (head != null){
//            nodeList.add(head);
//            head = head.next;
//        }
//
//        for(int i = 0; i < nodeList.size(); i++){
//            if(i == k - 1){
//                return nodeList.get(nodeList.size() - 1 - i);
//            }
//        }
//        return null;

        ListNode fast, slow;
        fast = slow = head;
        int i = 0;
        for(; fast != null; i++){
            if(i >= k){
                slow = slow.next;
            }
            fast = fast.next;
        }

        return i < k? null : slow;
    }

    public static void main(String[] args) {
        ListNode listNode =
                new ListNode(1,
                    new ListNode(2,
                        new ListNode(3,
                            new ListNode(4,
                                new ListNode(5, null)))));

        Solution solution = new Solution();
        ListNode rst = solution.FindKthToTail(listNode, 5);
        System.out.println(rst);
    }

}