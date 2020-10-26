package com.jsonyao.sort;

/**
 * On: Java Version 1.7
 * 基数排序概念:
 *      a. 基数排序属于分配式排序, 是透过键值的部分信息, 将要排序的元素分配至某些桶中, 以达到排序的作用
 *      b. 原理是将整数按位数切割成不同的数字, 然后按每个位数分别比较.
 *      c. 排序方式可以采用LSD(Least significant digital)或MSD(Most significant digital), LSD是由键值的最右边开始, 而MSD则相反, 由键值的最左边开始
 * Relation:
 *      a. https://baike.baidu.com/item/%E5%9F%BA%E6%95%B0%E6%8E%92%E5%BA%8F/7875498?fr=aladdin
 *      b. https://zhuanlan.zhihu.com/p/126116878
 */
public class RadixSort {

    public void radixSort(int[] arr){
        /**
         * 基于LSD & 计数排序实现:
         * A. 核心思想:
         *      a. 先求出数组中的最大值, 最大位数
         *      b. 基于计数排序的话, 则创建基数10的桶, 求利用 / 与 % 对元素求索引, 比如先求1的商再取余可以得到最低位, 其中：
         *          b.1. a / 10^m 代表的是取a中前 d-m 位, d代表a的最大位数, m代表幂次数, 也就是取小数点前的位数
         *          b.2. a % 10^m 则代表取a中的后m位, m代表幂次数
         *          b.3. 可以看到, 做 / 可以取高位, 做 % 可以取低位
         *          b.4. 因此, (a / 1) % 10可以得到个位, (a / 10) % 10可以得到十位...而 (a % 10) / 1也可以得到个位, (a % 100) / 10可以得到十位,
         *               (a % 1000) / 100可以得到百位. 对比起来, 还是 (a / 10) % 10更加方便点, 这时余数模不用变, 被除数所求位数+1的10次方
         *      c. 再走计数排序的套路, 根据索引计数, 前后计数相加的和得到正确数组索引, 反向遍历数组, 让后进的元素先出, 保持稳定性
         *      d. 由于负数无法取基数, 且桶被限定位10, 因此, 基于的计数排序无需取最小值来确定索引
         *      e. 对比计数排序, 基数排序相当于用10基数确定了计数排序的桶, 然后循环基数的位数
         * B. 时间复杂度:
         *      a. 该算法遍历最大值的每位数, 循环里再遍历数组 + 遍历Counts数组, 一共时间复杂度位为O(d * [n + 10]) = O(dn + 10d), O(n)线性级别
         *      b. 最好情况, 都是一位数, d = 1, 时间复杂度为O(n+10), O(n)线性级别
         *      c. 最坏情况, d趋向于n, 时间复杂度接近O(n^2), O(n^2)平方级别
         * C. 空间复杂度:
         *      a. 开辟了2个n长的数组, 10单元的radix桶, 空间复杂度为O(2n + 10)
         * D. 稳定性:
         *      a. 由于基于计数排序, 在遇到相同元素总能通过反向遍历数组, 后进先出, 保证稳定性, 因此, 该基数排序是稳定的
         * E. 算法分析:
         *      a. 对于基数排序十进制数, 设待排序序列n个记录, d个关键码(最大值的位数), 关键码取值范围radix(基数), 则需要遍历d次n长数组和radix基数桶,
         *         故基数排序的平均时间复杂度为O(d * [n + radix]) = O(dn + d * radix) = O(n + [(d - 1) * n + dr]) = O(n + k), k为(d-1)*n+dr
         *      b. 可见时间复杂度为最大值位数有关, 位数越少效率越高, 极限情况下d=1, O(n), 最坏情况下d≈n, O(n^2)
         *      c. 基数排序与计数排序、桶排序三种算法都利用了桶的概念, 但对桶的使用方法上有明显的差异:
         *          c.1. 计数排序: 由于使用数值作为索引, 相当于每个索引作为了桶, 故每个桶只存储单一键值
         *          c.2. 桶排序: 由于桶索引由hash函数决定, 对于一定范围数据会落到同一索引上, 故每个桶只存储一定范围的数值
         *          c.3. 基数排序: 由于拆分数值的每位数, 按照数字来分配桶, 故根据键值的每位数字来分配桶
         *      d. 基数排序不是直接根据元素整体的大小来进行元素比较的, 而是将原始列表元素分成多个部分, 对每一部分按照规则排序(按位排序), 进而得到有序序列
         */
        // 求最大值
        int max = 0;
        for(int i = 0; i < arr.length; i++){
            max = arr[i] > max? arr[i] : max;
        }

        // 求最大值的位数
        int digitalMax = 1;
        while (max >= 10){
            max /= 10;
            digitalMax++;
        }

        // 基于计数排序进行基数排序
        int radix = 1;
        int[] tempArr = new int[arr.length];// 创建空数组, 不能直接赋值, 因为赋值只会赋值引用, 导致tempArr和arr变化一致, 需要深拷贝
        int[] counts = new int[10];// 创建10个计数桶
        for(int i = 0; i < digitalMax; i++){
            // 清空counts数组
            for(int j = 0; j < counts.length; j++){
                counts[j] = 0;
            }

            // 顺序遍历数组, 填充计数桶
            for(int j = 0; j < arr.length; j++){
                int countIndex = (arr[j] / radix) % 10;
                counts[countIndex]++;
            }

            // 顺序遍历计数桶, 前数加后数, 得到计数桶中的每个基数最终索引位置
            for(int j = 1; j < counts.length; j++){
                counts[j] = counts[j-1] + counts[j];
            }

            // 因为需要保证稳定性, 就要后进先出, 所以反向遍历数组
            for(int j = arr.length - 1; j >= 0; j--){
                int countIndex = (arr[j] / radix) % 10;
                tempArr[counts[countIndex]-1] = arr[j];
                counts[countIndex]--;
            }

            // 把基数排好序的tempArr深拷贝到arr中
            for(int j = 0; j < tempArr.length; j++){
                arr[j] = tempArr[j];
            }

            // 继续向前取基数
            radix *= 10;
        }
    }

    public static void main(String[] args) {
        int[] arr = {
                9, 8, 10, 4, 7, 2, 5, 5, 1
        };

        RadixSort radixSort = new RadixSort();
        radixSort.radixSort(arr);

        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}
