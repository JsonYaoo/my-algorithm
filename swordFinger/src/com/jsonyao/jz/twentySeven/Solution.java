package com.jsonyao.jz.twentySeven;

/**
 * Version: Java 1.7
 * Jz27
 *  keywords:
 *      a. 字符串 b. 动态规划 c. 递归
 *  desc:
 *      输入一个字符串,按字典序打印出该字符串中字符的所有排列。
 *      例如输入字符串abc,则按字典序打印出由字符a,b,c所能排列出来的所有字符串abc,acb,bac,bca,cab和cba。
 *      tips : 输入一个字符串,长度不超过9(可能有字符重复),字符只包括大小写字母。
 */
import java.util.ArrayList;

public class Solution {

    ArrayList<String> res = new ArrayList<>();

    public ArrayList<String> Permutation(String str) {
        if(str == null || str.equals("")){
            return res;
        }
        dfs(str.toCharArray(), new ArrayList<Integer>(), 0, new StringBuilder());
        return res;
    }

    private void dfs(char[] chars, ArrayList<Integer> usedIndex, int level, StringBuilder str){
        // 1 筛选条件
        if(level == chars.length){// 层数到达字符串长度时返回
            if(!res.contains(str.toString())){
                res.add(str.toString());
            }
            return;
        }

        // 2 候选人
        for(int i = 0; i < chars.length; i++){
            char c = chars[i];

            // 2 - 1 筛选条件
            if(!usedIndex.contains(i)){
                usedIndex.add(i);
                str.append(c);
                level++;

                dfs(chars, usedIndex, level, str);

                str.deleteCharAt(str.length() - 1);
                level--;
                usedIndex.remove(usedIndex.size() - 1);
            }else {
                continue;
            }
        }

        return;
    }

    public static void main(String[] args) {
        Solution solution = new Solution();

        String str = "aab";
        ArrayList<String> res = solution.Permutation(str);
        System.out.println(res);
    }
}