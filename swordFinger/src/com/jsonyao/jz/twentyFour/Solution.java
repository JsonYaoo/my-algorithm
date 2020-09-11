package com.jsonyao.jz.twentyFour;

import java.util.ArrayList;

/**
 * Version: Java 1.7
 * Jz24 desc:
 * 输入一颗二叉树的根节点和一个整数，按字典序打印出二叉树中结点值的和为输入整数的所有路径。
 * 路径定义为从树的根结点开始往下一直到叶结点所经过的结点形成一条路径。
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

        @Override
        public String toString() {
            return "TreeNode{" +
                    "val=" + val +
                    ", left=" + left +
                    ", right=" + right +
                    '}';
        }
    }

    ArrayList<ArrayList<Integer>> paths = new ArrayList<>();

    public ArrayList<ArrayList<Integer>> FindPath(TreeNode root, int target) {
        /**
         * 思路:
         *      a. 遍历当前结点的左子树、右子树, 得出两种组合
         *      b. 从头到尾遍历左右子树, 并记录路径
         */
        if(root == null){
            return paths;
        }

        ArrayList<Integer> path = new ArrayList<>();
        path.add(root.val);

        dfs(root, target, path);

        return paths;
    }

    private void dfs(TreeNode node, int target, ArrayList<Integer> path){
        // 1 截止条件
        if(node.left == null && node.right == null){// 到了叶子节点
            int sum = 0;
            for(Integer i : path){
                sum += i;
            }

            if(sum == target){
                paths.add(new ArrayList<Integer>(path));
            }
            return;
        }

        // 2 候选人 - 添加当前节点的左子树到路径中
        if(node.left != null){
            path.add(node.left.val);

            // 2-1 筛选条件
            dfs(node.left, target, path);

            path.remove(path.size() - 1);
        }

        // 2 候选人 - 添加当前节点的右子树到路径中
        if(node.right != null){
            path.add(node.right.val);

            // 2-1 筛选条件
            dfs(node.right, target, path);
            path.remove(path.size() - 1);
        }
    }

    public static void main(String[] args) {
        // {10,5,12,4,7},22 => [[10,5,7],[10,12]]
        Solution solution = new Solution();

        TreeNode root = new TreeNode(10);
        root.left = new TreeNode(5);
        root.left.left = new TreeNode(4);
        root.left.right = new TreeNode(7);

        root.right = new TreeNode(12);

        System.out.println(root);
        solution.FindPath(root, 22);
        System.out.println(solution.paths);
    }
}