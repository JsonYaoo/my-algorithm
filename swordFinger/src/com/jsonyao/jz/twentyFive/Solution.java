package com.jsonyao.jz.twentyFive;

/**
 * Version: Java 1.7
 * Jz25 desc:
 * 输入一个复杂链表（每个节点中有节点值，以及两个指针，一个指向下一个节点，另一个特殊指针random指向一个随机节点），
 * 请对此链表进行深拷贝，并返回拷贝后的头结点。（注意，输出结果中请不要返回参数中的节点引用，否则判题程序会直接返回空）
 */
/*
public class RandomListNode {
    int label;
    RandomListNode next = null;
    RandomListNode random = null;

    RandomListNode(int label) {
        this.label = label;
    }
}
*/
public class Solution {

    static class RandomListNode {
        int label;
        RandomListNode next = null;
        RandomListNode random = null;

        RandomListNode(int label) {
            this.label = label;
        }
    }

    public RandomListNode Clone(RandomListNode pHead) {
        /**
         * 复制复杂链表思路：
         *      a. 在原有链表基础上复制兄弟节点产生新的链表
         *      b. 新的兄弟节点绑定对应的原有链表属性
         *      c. 把新链表一份为二, 得到的新链表即为克隆链表
         */
        if(pHead == null){
            return null;
        }

        // 1 复制兄弟节点产生组合链表
        RandomListNode currentNode = pHead;// 给予初始指针
        while (currentNode != null){
            // 遍历初始列表, 在兄弟节点出产生新的Clone
            RandomListNode cloneNode = new RandomListNode(currentNode.label);

            // 设置新链表指针
            cloneNode.next = currentNode.next;
            currentNode.next = cloneNode;

            // 移动遍历指针
            currentNode = currentNode.next.next;
        }

        // 2 为新的兄弟节点设置原有链表的指针
        currentNode = pHead;// 重新给予初始指针
        while (currentNode != null){
            RandomListNode cloneNode = currentNode.next;// 获取兄弟节点(新链表)

            // 设置新链表属性
            cloneNode.random = currentNode.random == null? null : currentNode.random.next;

            // 移动遍历指针
            currentNode = currentNode.next.next;
        }

        // 3 拆除链表为新旧链表 新链表则为克隆链表
        currentNode = pHead;// 重新给予初始指针
        RandomListNode clonePHead = pHead.next;// 给予克隆链表的初始指针
        while (currentNode != null) {
            RandomListNode cloneNode = currentNode.next;// 获取兄弟节点(新链表)

            // 拆分链表, 更改链表指针
            if(cloneNode.next != null){// 如果克隆节点还有兄弟节点, 那么必然还有兄弟节点的兄弟节点
                RandomListNode nextCloneNode = cloneNode.next.next;
                currentNode.next = cloneNode.next;
                cloneNode.next = nextCloneNode;
            }else {// 否则, 代表到了最后一个克隆节点
                currentNode.next = cloneNode.next;
            }

            // 移动遍历指针
            currentNode = currentNode.next;
        }

        return clonePHead;
    }

    public static void main(String[] args) {
        // {1,2,3,4,5,3,5,#,2,#}
        RandomListNode head = new RandomListNode(1);
        head.next = new RandomListNode(2);
        head.next.next = new RandomListNode(3);
        head.next.next.next = new RandomListNode(4);
        head.next.next.next.next = new RandomListNode(5);
        head.next.next.next.next.next = new RandomListNode(3);
        head.next.next.next.next.next.next = new RandomListNode(5);
        head.next.next.next.next.next.next.next = new RandomListNode('#');
        head.next.next.next.next.next.next.next.next = new RandomListNode(2);
        head.next.next.next.next.next.next.next.next.next = new RandomListNode('#');

        Solution solution = new Solution();
        solution.Clone(head);
    }
}