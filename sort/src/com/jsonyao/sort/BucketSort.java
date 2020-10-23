package com.jsonyao.sort;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 桶排序概念:
 *      a. 桶排序工作原理是, 将数组分到有限数量的桶子里, 然后每个桶子再分别进行排序(期间有可能再使用别的排序算法, 或者以递归的方式继续使用桶排序进行排序)
 *      b. 桶排序不是比较排序, 时间复杂度能够突破O(nlogn)下限, 当要被排序的数组内的数组是均匀分布的, 桶排序时间复杂度是线性级别的O(n)
 *      c. 计数排序是桶排序的一种特殊情况, 可以把计数排序当成每个桶里只有一个元素的情况(而桶排序是多个值经过哈希函数可能会产生同一个桶索引)
 * Relation:
 *      a. https://baike.baidu.com/item/%E6%A1%B6%E6%8E%92%E5%BA%8F/4973777?fr=aladdin
 *      b. https://www.cnblogs.com/zer0Black/p/6169858.html
 *      c. https://blog.csdn.net/qq_39445165/article/details/84627977
 */
public class BucketSort {

    public class LinkedNode {
        private int var;
        private LinkedNode pre;
        private LinkedNode next;

        public LinkedNode() {

        }

        public LinkedNode(int var) {
            this.var = var;
            this.pre = pre;
            this.next = next;
        }
    }

    public void bucketSort(int[] arr) {//data为待排序数组
        /**
         * 基于数组集合实现:
         * A. 核心思想:
         *      a. 把数组arr, 划分为n个大小相同的子区间作为桶
         *      b. 然后每个子区间各自排序
         *      c. 最后按顺序输出桶与各桶中的内容, 即得到有序数组
         * B. 时间复杂度:
         *      a. 该算法, 遍历数组创建桶花费O(n), 假设n长数组, m个桶, 平均每个桶n/m个数据, 顺序遍历桶然后插入排序花费:
         *         => O(m * [n/m]^2) = O(n^2/m), 因此该算法平均时间复杂度为O(n + n^2/m), m越大, 时间复杂度越小
         *      b. 最坏情况, m=1时, 时间复杂度为O(n + n^2), 算法退化成O(n^2)级别的时间复杂度
         *      c. 最好情况, m=无穷大, 时间复杂度无线接近O(n), 算法接近O(n)线性级别时间复杂度
         * C. 空间复杂度:
         *      a. 开辟了n长的数组, k长的桶, 因此, 空间复杂度为O(n+k), k为二维数组O(k^2)
         * D. 稳定性:
         *      a. 由于相等元素计算出来的哈希索引相等, 故会放同一个桶中, 而桶内排序用的是稳定的插入排序, 因此该算法也是稳定的
         * E. 算法分析:
         *      a. 对N个关键字进行桶排序的时间复杂度分为两部分:
         *          a.1. 循环计算每个关键字的桶映射函数, 时间复杂度为O(n)
         *          a.2. 利用先进的比较排序算法对每个桶内的所有数据进行排序, 其时间复杂度为O(ni * logni), 其中ni为第i个桶数据量
         *      b. 显然, a.2.部分是桶排序性能好坏的决定因素. 由于基于比较排序算法时间复杂度下限是O(nlogn), 因此尽量减少桶内数据的数量是
         *         提高效率的唯一办法. 这时, 需要尽量做到下面两点:
         *          b.1. 映射函数f(k)能够将N个数据平均分配到M个桶中, 这样每个桶就有[N/M]个数据量
         *          b.2. 尽量增大桶的数量. 极限情况下每个桶只能得到一个数据, 这样就完全避开了桶内数据的比较排序操作. 不过当数据量巨大的情况下,
         *               这样的f(k)函数会使得桶集合数量巨大, 造成严重的空间浪费, 是一个时间代价和空间代价权衡的问题
         *      c. 对N个待排数据, M个桶, 平均每个桶[N/M]个数据的桶排序的平均时间复杂度为:
         *          c.1. O(N) + O(M * N/M * logN/M) = O(N + N * [logN - log M]) = O(N + NlogN - NlogM)
         *          c.2. 当N=M时, 即极限情况下, 每个桶只有一个数据时, 桶排序最优时间复杂度能够达到O(n)
         *          c.3. 当M=1时, 最坏情况下, O(N + NlogN), 算法退化成比较算法O(nlogn)级别的时间复杂度
         *          c.4. 因此, 桶排序的平均时间复杂度为线性的O(N+K), K = NlogN - NlogM, 其中N为数组长度, M为桶个数. 对于相同的N,
         *               M越大, 效率越高, 最高的效率为O(N)
         */
//        int min = 0, max = 0;
//        for(int i = 1; i < arr.length; i++){
//            min = arr[i] < min ? arr[i] : min;
//            max = arr[i] > max ? arr[i] : max;
//        }
//
//        // 创建桶
//        int bucketSize = arr.length;
//        ArrayList<ArrayList<Integer>> bucketList = new ArrayList<>(bucketSize);
//        for(int i = 0; i < bucketSize; i++){
//            bucketList.add(new ArrayList<Integer>());
//        }
//
//        // 遍历数组
//        for(int i = 0; i < arr.length; i++){
//            int hashIndex = (arr[i] - min) * arr.length / (max - min + 1);
//            bucketList.get(hashIndex).add(arr[i]);
//        }
//
//        // 顺序遍历桶
//        int pos = 0;
//        for(ArrayList bucket : bucketList){
//            if(bucket == null || bucket.isEmpty() || bucket.size() == 0){
//                continue;
//            }
//
//            // 对有值的桶进行排序
//            Integer[] sortedArr = insertionSort((Integer[]) bucket.toArray(new Integer[0]));
//
//            // 顺序遍历每个桶中的内容
//            for(int i = 0; i < sortedArr.length; i++){
//                arr[pos++] = sortedArr[i];
//            }
//        }

        /**
         * 基于链表实现:
         * A. 核心思想:
         *      a. 桶数量根据输入元素个数来确定 => 桶数量等于数组长度,
         *      b. 哈希函数为 (e - min) * n / (max - min + 1), 保证均匀性以及负数排序, 确定桶索引后, 若桶内没有
         *         元素或者当前插入元素小于head指针元素, 则把当前插入元素插入到桶中, 并作为head指针, 原head指针作为
         *         下一个结点
         *      c. 由于输入的数据是随机的, 对于桶中的元素使用动态的数据结构来存储, 考虑到插入的操作比较频繁, 所以这里
         *         使用链表来进行插入元素, 在找到大于当前插入元素时, 把当前插入元素插入到该元素前, 以维持桶内
         *         链表元素从head指针到最后从小到大排序
         * B. 时间复杂度:
         *      a. 最好情况下, n长数组, n个桶, 每个桶1个元素, 在创建桶时花费O(n), 顺序遍历桶时花费O(n), 时间复杂度为O(n+n)
         *      b. 最坏情况下, n长数组, n个桶, 只有一个桶有元素, 且数量为n, 在创建桶时插入链表相当于遍历n长数组, 花费O(n^2), 顺序遍历
         *         桶碰到链表时相当于遍历n长数组, 花费O(n^2), 时间复杂度为O(n^2)
         *      c. 平均情况下, n长数组, n个桶, 每桶一个元素, 则刚好是最优情况
         * C. 空间复杂度:
         *      a. 在最好情况下, 需要n长的桶, 1长的链表, O(n+1)
         *      b. 在最坏情况下, 需要n长的桶, n长的链表, O(n+k)
         * D. 稳定性:
         *      a. 在相等元素插入时, 只有碰到比该元素大的才插入到其前面, 因此, 总能保证相等元素能插入到上一个相等元素之后, 保证了稳定性,
         *         因此, 该算法是稳定的
         * E. 算法分析:
         *      a. 可以看到, 无论是链表实现还是数组实现, 能够保证平均分配到每个桶的哈希函数至关重要, 是降低时间复杂度的关键
         *      b. 桶排序能够实现海量数据处理, 比如500w考生按分数段排序, 并且分数段内也要是有序的, 再如10G文件整数, 2G内存实现找整数中位数,
         *         这时可以利用桶排序对最高位做桶索引, 将10G整数按照最高位桶排序到磁盘中, 然后确认中间位数地桶位置, 对该桶load进内存, 排序取中位数即可
         */
        // 求max和min
        int max = 0, min = 0;
        for(int i = 0; i < arr.length; i++){
            max = arr[i] > max? arr[i] : max;
            min = arr[i] < min? arr[i] : min;
        }

        // 创建桶
        int bucketSize = arr.length;
        LinkedNode[] buckets = new LinkedNode[bucketSize];

        // 遍历数组
        for(int i = 0; i < arr.length; i++){
            int hashIndex = (arr[i] - min) * arr.length / (max - min + 1);

            // 根据索引获取桶
            LinkedNode head = buckets[hashIndex];
            if(head == null){// 如果为空, 则创建head指针
                buckets[hashIndex] = new LinkedNode(arr[i]);
            }else{// 否则插入到桶中
                insertNode2Bucket(buckets, hashIndex, arr[i]);
            }
        }

        // 顺序遍历桶
        int pos = 0;
        for(int i = 0; i < bucketSize; i++){
            LinkedNode head = buckets[i];
            while (head != null){
                arr[pos++] = head.var;
                head = head.next;
            }
        }
    }

    private void insertNode2Bucket(LinkedNode[] buckets, int hashIndex, int value){
        // 获取head指针
        LinkedNode head = buckets[hashIndex];

        // 创建当前结点
        LinkedNode node = new LinkedNode(value);

        // 比较当前元素与head指针元素的值
        if(value < head.var){// 若小于head指针元素, 则插入到head指针位置
            node.next = head;
            buckets[hashIndex] = node;
        }else {// 否则, 遍历链表找到合适的位置
            while (head.next != null){
                LinkedNode next = head.next;
                if(value < next.var){
                    head.next = node;
                    node.pre = head;

                    node.next = next;
                    next.pre = node;
                    return;
                }

                head = next;
            }

            // 如果遍历指针都找不到, 那么把当前结点插入到最后一个结点
            head.next = node;
            node.pre = head;
        }
    }

    private Integer[] insertionSort(Integer[] arr){
        Integer preIndex, compare;
        for(int i = 1; i < arr.length; i++){
            preIndex = i - 1;
            compare = arr[i];

            while (preIndex >= 0 && compare < arr[preIndex]){
                arr[preIndex+1] = arr[preIndex];
                preIndex--;
            }

            arr[preIndex+1] = compare;
        }

        return arr;
    }

    public static void main(String[] args) {
        int[] arr = {
                9, 8, 10, 4, 7, 2, 5, 5, -1, 105, 999
        };

        BucketSort bucketSort = new BucketSort();
        bucketSort.bucketSort(arr);
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}
