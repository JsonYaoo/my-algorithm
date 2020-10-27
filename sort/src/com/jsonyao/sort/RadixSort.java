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
 *      c. https://www.cnblogs.com/Braveliu/archive/2013/01/21/2870201.html
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

    public void radixSort2(int[] arr, int left, int right, double digital){
        /**
         * 基于MSD & 计数排序实现:
         * A. 核心思想:
         *      a. 最高位优先法通常是一个递归的过程
         *      b. 先根据最高位关键码K1排序, 得到若干对象组, 对象组(每个桶)每个对象都有相同关键码K1
         *      c. 再分别对每组(每个桶)中对象根据关键码K2进行排序, 按K2值的不同, 再分成若干个更小的子组, 每个子组的对象具有相同的K1和K2值
         *      d. 依此反复递归...直到对关键码Kd完成排序为止, 由于这时桶间有序, 桶内又有序, 代表整个序列是有序的, 因此就得到一个有序的对象序列
         * B. 时间复杂度:
         *      a. 最好情况, 最高位位数为1, 则遍历数组O(n), 遍历counts数组O(10), 遍历中间数组O(n), 最后再遍历counts数组O(10), 故时间复杂度为O(2n + 20)
         *      b. 最坏情况, 最高位位数趋向于n, 且集中在一个桶中, 这时递归n-1次, 该桶的时间复杂度为O([1 + 10] + [2 + 10] + ... + [n + 10]
         *         = O([n + 1] * [n - 1] / 2 + 10 * [n - 1]) = O(n^2/2 + 10n - 11/2),
         *         之前又有遍历数组O(n), 遍历counts数组O(10), 遍历中间数组O(n), 故总时间复杂度为O(n^2/2 + 12n - 1/2)
         *      c. 平均情况下, 设待排序数组长n, 最高位位数为d, counts数组长radix, 则有遍历数组O(n), 遍历counts数组O(radix), 遍历中间数组O(n),
         *         最后遍历counts数组进行递归则有, 平均每个桶元素为 n/radix个, 递归d-1次, 每轮递归的时间复杂度为O([1 + radix] + ... + [n/radix + radix])
         *         = O([n/radix + 1] * [d - 1] / 2 + radix * [d - 1]) = O([radix^2 + (n + radix) * (d - 1)] / radix), 一共radix轮, 则最后一次
         *         遍历counts数组的时间复杂度为O([radix^2 + (n + radix) * (d - 1)]), 故总时间复杂度为O(2n + radix + [radix^2 + (n + radix) * (d - 1)]),
         *         对于十进制来说, radix = 10, 则O(2n + 10 + [100 + (n + 10) * (d - 1)]) = O([d - 1] * (n + 10) + 2n + 110)
         *         = O(d * n + 10d - n - 10 + 2n + 110) = O(d * n - n + 10d + 100) = O(d * [n + k]), k为 (100-n)/d + 10
         * C. 空间复杂度:
         *      a. 开辟了arr数组为O(n), tempArr为O(n), counts为O(10), 总空间复杂度为O(2n + 10)
         * D. 稳定性:
         *      a. 由于该MSD基于计数排序, 而计数排序采用反向遍历数组arr, 后进先出, 保证了想等元素的前后顺序没有被改变, 故每次递归都是稳定的, 因此MSD是稳定的
         * E. 算法分析:
         *      a. 可见对于十进制数的MSD平均时间复杂度为O(d * [n + k]), k为(100-n)/d + 10, 空间复杂度为O(n + k)
         *      b. 综上, 由于基于MSD的基数排序需要递归计数排序, 计算过程复杂, 可以简化过程为递归比较排序或者桶排序, 而LSD则不用, 循环d趟计数排序即可,
         *         因此, 实现基数排序还是LSD方便点
         */
        int[] tempArr = new int[right-left+1];// 用于存储排好序的数组 -> 注意这里arr是长度不变的, 取值发生位移, 而tempArr长度是根据位移间的长度决定的
        int[] counts = new int[10];// 基数桶

        // 遍历数组设置桶内容
        for(int i = left; i <= right; i++){
            int countIndex = (arr[i] / (int) Math.pow(10d, digital-1)) % 10;
            counts[countIndex]++;
        }

        // 遍历桶, 前者加后者得到正确排好序的数组索引
        for(int i = 1; i < counts.length; i++){
            counts[i] = counts[i-1] + counts[i];
        }

        // 后进先出, 反向遍历数组
        for(int i = right; i >= left; i--){
            int countIndex = (arr[i] / (int) Math.pow(10d, digital-1)) % 10;
            tempArr[counts[countIndex]-1] = arr[i];
            counts[countIndex]--;
        }

        // 深拷贝tempArr到arr
        for(int i = 0; i < tempArr.length; i++){
            arr[left+i] = tempArr[i];// arr设置值也应该用left加上位移偏量i, 实质上tempArr数组是arr的偏移数组
        }

        // 对每个桶内部进行递归计数排序 <= 其中经计算可知, 反向遍历后的counts数组每个值, 代表下一桶索引在正确开始数组中的开始下标
        for(int i = 0; i < counts.length; i++){
            /**
             * 在digital=3的桶0即对十位数基数进行递归排序, left = 0, right = 7 => 遍历digital=2, 即判断10位基数的桶哪个需要递归进行个位基数的计数排序, 判断情况如下:
             *      a. arr索引: 0, 1, 2, 3,  4,  5,  6,  7,  8,   9
             *      b. arr数组：1, 5, 8, 19, 34, 35, 34, 72, 102, 2011
             *      c. counts: 0, 1, 2, 3, 4, 5, 6, 7, 8, 9
             *      d. counts: 0, 3, 4, 4, 7, 7, 7, 7, 8, 8
             * 1、i = 0, 判断第0桶是否需要递归:
             *      a. 新左边界l = 左边界left + 下一桶索引 => 0 + 0 = 0
             *      b. 新右边界r = 左边界left + 下下一桶索引 => 0 + 3 - 1 = 2
             *      c. l < r, 说明十位为0的桶中元素为arr[0]~arr[2], 这时由于数据超过1种元素, 所以需要进行递归计数排序
             * 2、i = 1, 判断第1桶是否需要递归:
             *      a. 新左边界l = 左边界left + 下一桶索引 => 0 + 3 = 3
             *      b. 新右边界r = 左边界left + 下下一桶索引 => 0 + 4 - 1 = 3
             *      c. l = r, 说明十位为1的桶只有arr[3]一个元素, 这时也不需进行递归计数排序
             * 3、i = 2, 判断第2桶是否需要递归:
             *      a. 新左边界l = 左边界left + 下一桶索引 => 0 + 4 = 4
             *      b. 新右边界r = 左边界left + 下下一桶索引 => 0 + 4 - 1 = 3
             *      c. l > r, 说明十位为2的桶不存在元素, 因此也不用进行递归计数排序
             * 4、i = 3, 判断第3桶是否需要递归:
             *      a. 新左边界l = 左边界left + 下一桶索引 => 0 + 4 = 4
             *      b. 新右边界r = 左边界left + 下下一桶索引 => 0 + 7 - 1 = 6
             *      c. l < r, 说明十位为3的桶存在arr[4]~arr[6]的元素, 因此需要进行递归计数排序
             * 5、i = 4, 5, 6, l > r, 说明十位为4, 5, 6的桶中不存在元素
             * 6、i = 7, 判断第7个桶是否需要递归:
             *      a. 新左边界l = 左边界left + 下一桶索引 => 0 + 7 = 7
             *      b. 新右边界r = 左边界left + 下下一桶索引 => 0 + 8 - 1 = 7
             *      c. l = r, 说明十位为1的桶只有arr[7]一个元素, 这时也不需进行递归计数排序
             * 7、i = 8, l > r, 说明十位为8的桶中不存在元素
             * 8、i= 9, l = 8 > r = end = 7, 说明十位为9的桶中不存在元素
             *
             * => 因此, 可以看出, 通过判断 left + count[i] 和 left + count[i+1] - 1的大小便可知到第i桶是否存在元素
             *      a. 当 left + count[i] < left + count[i+1] - 1, 即 count[i+1] - count[i] > 1, 桶i中元素个数肯定大于1
             *      b. 当 left + count[i] = left + count[i+1] - 1, 即 count[i+1] - count[i] = 1, 桶i中元素个数为1
             *      c. 当 left + count[i] > left + count[i+1] - 1, 即 count[i+1] - count[i] < 1, 桶i中元素个数肯定小于1
             *      d. 这里count[i]相当于第i桶的起始索引, count[i+1]相当于第i+1桶的起始索引, count[i+1]-count[i]相当于当前桶到下一桶间的元素个数
             *      e. 故 left + count[i], 可得到当前桶在arr中的起始索引(桶顶索引), 用来找出真正的数值可做一轮的计数排序
             *      f. 故 left + count[i] + 1, 可得到下一桶在arr中的起始索引-1, 即当前桶在arr中的末尾索引(桶底索引), 用来找出真正的数值可做一轮的计数排序
             */
            int newLeft = left + counts[i];// 当前digital基数的第i桶的桶顶索引(桶中开头的元素)
            int newRight;// 当前digital基数的第i桶的桶底索引(桶中最后一个元素)

            if(i < counts.length-1){
                newRight = left + counts[i+1] - 1;// 如果不是当前桶不是最后一桶, 则桶底索引通过下一桶的起始索引计算得出
            }else {
                newRight = right;// 如果当前桶时最后一桶, 则桶底索引等于arr数组最后一个元素的位置
            }

            // 如果当前桶i中元素大于1, 且当前桶的基数还没到个位, 那么需要对基数往低位退一位然后进行计数排序递归
            if(newLeft < newRight && digital > 1){
                radixSort2(arr, newLeft, newRight, digital-1);
            }
        }
    }

    public static void main(String[] args) {
        int[] arr = {
                19, 8, 102, 34, 72, 2011, 5, 35, 1, 34
        };

        RadixSort radixSort = new RadixSort();
        // LSD
//        radixSort.radixSort(arr);

        // 求最大值
        int max = 0;
        for(int i = 0; i < arr.length; i++){
            max = arr[i] > max? arr[i] : max;
        }

        // 求最大位数
        int digitalMax = 1;
        while (max >= 10){
            max /= 10;
            digitalMax++;
        }

        // MSD
        radixSort.radixSort2(arr, 0, arr.length-1, digitalMax);

        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}
