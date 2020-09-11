package com.jsonyao.jz.seventeen;

/**
 * Version: Java 1.7
 * Jz17 desc:
 * 输入两棵二叉树A，B，判断B是不是A的子结构。（ps：我们约定空树不是任意一个树的子结构）
 */

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

    public boolean HasSubtree(TreeNode root1, TreeNode root2) {
        boolean result = false;
        if(root1 == null || root2 == null){
            return result;
        }

        // 节点值相同时才进行子节点比较
        if(root1.val == root2.val){
            // 先比较当前节点是否有Root2树
           result = this.doesTree1HasTree2(root1, root2);
        }

        // 如果当前节点不相等, 则继续判断左子节点是不是包含
        if(!result){
            result = this.HasSubtree(root1.left, root2);
        }

        // 如果左节点还不包含, 则继续判断右子节点是不是包含
        if(!result){
            result = this.HasSubtree(root1.right, root2);
        }

        return result;// 如果都不包含, 则不为他的子结构
    }

    public boolean doesTree1HasTree2(TreeNode node1, TreeNode node2){
        // 树2遍历完则为子结构
        if(node2 == null){
            return true;
        }

        // 树2还没遍历完但树一已经遍历完了, 则肯定不为子结构
        if(node1 == null){
            return false;
        }

        // 如果其中有一个节点的值没对应上, 则也不是子结构
        if(node1.val != node2.val){
            return false;
        }

        // 如果节点对应上, 则去左右子数分别对应
        return this.doesTree1HasTree2(node1.left, node2.left)
                && this.doesTree1HasTree2(node1.right, node2.right);
    }
}
