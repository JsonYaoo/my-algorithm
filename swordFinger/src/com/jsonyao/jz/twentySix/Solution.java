package com.jsonyao.jz.twentySix;

/**
 * Version: Java 1.7
 * Jz26
 *  keywords:
 *      a. 分治
 *  desc:
 *      输入一棵二叉搜索树，将该二叉搜索树转换成一个排序的双向链表。要求不能创建任何新的结点，只能调整树中结点指针的指向。
 */

import java.util.Stack;

/**
 public class TreeNode {
 int val = 0;
 TreeNode left = null;
 TreeNode right = null;

 public TreeNode(int val) {
 this.val = val;

 }

 }
 */
public class Solution {

    static class TreeNode {
        int val = 0;
        TreeNode left = null;
        TreeNode right = null;

        public TreeNode(int val) {
            this.val = val;
        }
    }

    TreeNode linkedList = null;// 组装中的链表
    TreeNode head = null;// 链表的头指针

    public TreeNode Convert(TreeNode pRootOfTree) {
        /**
         * 思路:
         *  1、由于是二叉搜索树: 任意一个左节点都比根节点小, 任意一个右节点都比根节点大
         *  2、所以, 中序遍历 左 中 右, 遍历顺序刚好满足此顺序
         *  3、所以, 得出中序遍历转换成列表即可
         *  4、中序遍历两种获取方法:
         *      1) 递归: 利用递归先左 然后中 然后递归右的方法, 得到中序遍历, 再此过程中, 逐步更改二叉搜索树的指针, 重构出链表
         *      2) 非递归：利用入栈后进先出的特点, 先根左入栈, 那么栈顶就是中序遍历的开头, 实现思路同递归的思路
         */
//        // 1 递归方法
//        if(pRootOfTree == null){
//            return null;
//        }
//
//        // 整型左子树
//        Convert(pRootOfTree.left);
//
//        // 第一个左叶子节点作为双向链表的起始节点
//        if(linkedList == null){
//            linkedList = pRootOfTree;// 设置链表头指针
//            head = linkedList;// 赋予链表头指针, 用于定位返回
//        }else {
//            linkedList.right = pRootOfTree;// 整型链表节点
//            pRootOfTree.left = linkedList;// 把当前节点套进新的链表节点中
//            linkedList = pRootOfTree;// 链表向前进1
//        }
//
//        // 整型右子树
//        Convert(pRootOfTree.right);
//
//        return head;
        if(pRootOfTree == null){
            return null;
        }

        Stack<TreeNode> stack = new Stack<>();
        while (pRootOfTree != null || !stack.isEmpty()) {
            if(pRootOfTree != null){// 遍历完左子树
                // 左 中
                stack.push(pRootOfTree);
                pRootOfTree = pRootOfTree.left;
            }else {// 左子树已经遍历完
                // 中 右
                pRootOfTree = stack.pop();
                if(linkedList == null){// 初始头结点
                    linkedList = pRootOfTree;
                    head = linkedList;// 给予初始头指针的位置
                }else {// 非头结点
                    // 更改链表指针
                    linkedList.right = pRootOfTree;
                    pRootOfTree.left = linkedList;
                    linkedList = pRootOfTree;// 链表指针进1
                }
                // 把右结点压进栈中
                pRootOfTree = pRootOfTree.right;
            }
        }

        return head;
    }

    public static void main(String[] args) {
        // {10,6,14,4,8,12,16}
        TreeNode root = new TreeNode(10);

        TreeNode root_level_1l = new TreeNode(6);
        root_level_1l.left = new TreeNode(4);
        root_level_1l.right = new TreeNode(8);

        TreeNode root_level_1r = new TreeNode(14);
        root_level_1r.left = new TreeNode(12);
        root_level_1r.right = new TreeNode(16);

        root.left = root_level_1l;
        root.right = root_level_1r;

        Solution solution = new Solution();
        TreeNode rst = solution.Convert(root);
        System.out.println(1);

    }
}