package com.jsonyao.jz.twentyTwo;

/**
 * Version: Java 1.7
 * Jz22 desc:
 * 从上往下打印出二叉树的每个节点，同层节点从左至右打印。
 */
import java.util.ArrayList;

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

    public ArrayList<Integer> PrintFromTopToBottom(TreeNode root) {
        /**
         * 思路是list.remove实现队列效果, 从左到右存方左右子树出队进行打印
         */
        ArrayList<Integer> res = new ArrayList<>();
        ArrayList<TreeNode> queue = new ArrayList<>();

        if(root != null){
            queue.add(root);
        }

        while (queue.size() > 0){
            TreeNode node = queue.remove(0);
            res.add(node.val);

            if(node.left != null){
                queue.add(node.left);
            }

            if(node.right != null){
                queue.add(node.right);
            }
        }

        return res;
    }
}