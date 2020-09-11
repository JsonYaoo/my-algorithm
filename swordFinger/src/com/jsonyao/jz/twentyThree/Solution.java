package com.jsonyao.jz.twentyThree;

import java.util.ArrayList;

/**
 * Version: Java 1.7
 * Jz23 desc:
 * 输入一个整数数组，判断该数组是不是某二叉搜索树的后序遍历的结果。
 * 如果是则返回true,否则返回false。假设输入的数组的任意两个数字都互不相同。
 */
public class Solution {
    public boolean VerifySquenceOfBST(int [] sequence) {
        /**
         *  A. 二叉搜索树的特点:
         *      a. 所有左子树节点的值均小于根节点
         *      b. 所有右子树节点的值均大于根节点
         *  B. 后序遍历的特点:
         *      a. 左右中
         *      b. 后序遍历最后一位为根节点
         *  C. 思路:
         *      a. 利用初始数组最后一位为根节点, 递归判断左右子树的后续遍历是否符合
         *      b. 通过找到左右子树的第一个比根节点大的节点为右节点, 从而判断节点左边是否都小于根节点, 节点右边是否都大于根节点
         */
        if(sequence.length == 0){
            return false;
        }
        
        // 获取最后一位为根节点
        int root = sequence[sequence.length - 1];
        ArrayList<Integer> seqList = new ArrayList<>();
        for(int i = 0; i < sequence.length; i++){
            seqList.add(sequence[i]);
        }

        return isTraverseBack(seqList, root);
    }

    // 判断根节点左右子树是否为后续遍历
    private boolean isTraverseBack(ArrayList<Integer> seq, Integer root){
        boolean result = true;

        ArrayList<Integer> leftTree = new ArrayList<>();

        for (int i = 0; i < seq.size() - 1; i++) {
            Integer c = seq.get(i);

            if(c < root){
               leftTree.add(c);
            }else {
                if(!leftTree.isEmpty()){
                    // 所有左子树应该小于根节点
                    for (Integer num: leftTree) {
                        if(num >= root){
                            result = false;
                            break;
                        }
                    }
                    result &= isTraverseBack(leftTree, leftTree.get(leftTree.size() - 1));
                }

                ArrayList<Integer> rightTree = new ArrayList<>(seq.subList(i, seq.size() - 1));
                if(!rightTree.isEmpty()){
                    // 所有右子树应该大于根节点
                    for (Integer num : rightTree) {
                        if(num <= root){
                            result = false;
                            break;
                        }
                    }
                    result &= isTraverseBack(rightTree, rightTree.get(rightTree.size() - 1));
                }
            }
        }

        return result;
    }

    // 4,8,6,12,16,14,10
    public static void main(String[] args) {
        int[] seq = new int[]{
//            4,8,6,12,16,14,10
//                7,4,6,5
             7,4,9,3,8,11,12,10
        };

        Solution solution = new Solution();
        System.out.println(solution.VerifySquenceOfBST(seq));
    }

}