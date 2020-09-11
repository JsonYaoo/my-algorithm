package com.jsonyao.jz.eighteen;

/**
 * Version: Java 1.7
 * Jz18 desc:
 * 操作给定的二叉树，将其变换为源二叉树的镜像。
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

    public void Mirror(TreeNode root) {
        if(root == null){
            return;
        }

        // 交换左右节点
        TreeNode tempNode = root.right;
        root.right = root.left;
        root.left = tempNode;

        // 继续交换左子节点
        Mirror(root.left);

        // 继续交换右子节点
        Mirror(root.right);

        return;
    }


}
