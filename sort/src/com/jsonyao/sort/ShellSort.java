package com.jsonyao.sort;

/**
 * On: Java Version 1.7
 * 希尔排序概念:
 *      a. 希尔排序是插入排序的一种, 又称缩小增量排序, 是直接插入排序算法的一种更高效的改进版本
 *      b. 希尔排序与直接插入排序不同之处在于, 它会优先比较距离较远的元素, 是第一个突破O(n^2)的算法
 *      c. 希尔排序是把记录按照下标的一定增量分组, 对每组使用直接插入排序算法排序, 而随着增量逐渐减少, 每组包含的关键词越来越多,
 *         当增量减至1时,  整个数组恰好被分成1组, 而这时大部分数据已经是排好序的, 经过这一轮排序后, 算法便终止
 * Relation：
 *      a. https://www.cnblogs.com/onepixel/articles/7674659.html
 *      b. https://baike.baidu.com/item/%E5%B8%8C%E5%B0%94%E6%8E%92%E5%BA%8F/3229428?fr=aladdin
 *      c. https://zhuanlan.zhihu.com/p/31173825
 */
public class ShellSort {

    public void shellSort(int[] arr){
        /**
         * A. 核心思想:
         *      a. 选择一个增量t1, t2, ..., tk, 其中ti > tj, tk = 1, 一般初次增量取序列的一半, 以后每次减半, 直到为1
         *      b. 设置增量后, 根据对应的增量ti, 将待排序序列分割成若干长度为m的子序列, 分别对各子序列进行直接插入排序
         *      c. 进行k趟增量分组直接插入后, 当增量为1时, 整个序列刚好作为子序列进行排序, 所以这趟排序过后就得到了最终的排序序列了
         * B. 时间复杂度:
         *      a. 该程序的平均时间复杂度为:
         *         a.1. 第一次gap取n/2, 第二次gap取n/4, 第三次gap取n/8,..., 直到第k次gap为1, 建立公式, n*(1/2)^k=1 => 所以该程序的增量公式为 k=logn
         *         a.2. 对于增量k, n长的数组会平均分割为n/k组子序列
         *         a.3. 对于每个子序列, 每个子序列的元素就有 n/(n/k)=k个元素
         *         a.4. 对k长的子序列进行平均时间复杂度为O(n^2)的直接插入排序, 就会有k^2的时间复杂度
         *         a.5. 对增量k, 一共有n/k组, 每组k^2时间复杂度, 所以其平均时间复杂度为 nk=nlogn, 即O(nlogn)
         *      b. 可以看出, 希尔排序的时间复杂度取决于增量公式k的取值, 然而k究竟应该选取怎么样的增量才是最好, 目前还是个数学难题, 迄今为止, 还没有人找到一种最好的增量序列
         *      c. 不过经过大量的研究表明, 当增量序列为k=2^(t-k+1)-1(0 <= k <= t <= [log(n+1)])时, 可以获得不错的效率其时间复杂度为O(n^[3/2]), 要好于直接排序的O(n^2)
         * C. 稳定性:
         *      a. 一次的插入排序时稳定的, 因为不会改变相同元素的相对顺序
         *      b. 但对于希尔排序来说, 由于是分组进行插入排序, 就会在不同的插入排序过程中, 相同的元素可能在各自的插入排序中移动, 有可能会打乱相同元素的相对顺序, 因为希尔排序是不稳定的
         * D. 算法分析:
         *      a. 希尔排序的时间复杂度下限为O(n*logn*logn), 没有快速排序的O(nlogn)快, 因此中等大小规模表现良好, 对规模非常大的数据排序不是最优选择, 但仍然比O(n^2)快
         *      b. 专家们提倡, 几乎任何排序工作在开始时都可以用希尔排序, 若在实际使用中证明它不够快, 再改成快速排序这样更高级的排序算法
         */
        int preIndex, compare;

        for(int gap = arr.length / 2; gap >= 1; gap = gap / 2){// 2. 擦除增量1, 增量根据该序列长度作动态获取; 初始增量默认取序列的一半(向下取整)
            for(int i = gap; i < arr.length; i++){// 1. 先写增量为1的简单插入排序; 2.1 i++代表分组序列交错执行
                preIndex = i - gap;// 前移一个单元, 取已排序序列的最后一个元素作为起始比较元素
                compare = arr[i];// 当前比较元素

                while (preIndex >= 0 && compare < arr[preIndex]){// 比较元素还小时, 则继续比较
                    arr[preIndex+gap] = arr[preIndex];// 覆盖式插入到已排序元素后面一个单元
                    preIndex = preIndex - gap;// 简单插入指针前移
                }

                // 当前移都到分组的1位 或者 找到比较元元素 大于等于 已排序元素后, 需要插入到该已排序元素后面一个单元
                arr[preIndex+gap] = compare;
            }
        }

    }

    public static void main(String[] args) {
        ShellSort shellSort = new ShellSort();

        int[] arr = new int[]{
            3, 5, 1, 2, 1, 4
        };

        shellSort.shellSort(arr);
        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}
