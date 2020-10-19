package com.jsonyao.sort;

/**
 * On: Java Version 1.7
 * 堆排序概念:
 *      a. 堆排序是指, 利用堆这种数据结构所设计的一种算法
 *      b. 堆是一个近似完全二叉树的结构, 并同时满足堆积的性质, 即子节点的键值或索引总是小于(或者大于)它的父节点
 *      c. 每个结点的值都小于或等于其左右孩子结点的值, 成为大顶堆. 或者每个结点的值都小于或等于其左右孩子结点的值, 成为小顶堆
 *  Relation:
 *      a. https://baike.baidu.com/item/%E5%A0%86%E6%8E%92%E5%BA%8F/2840151?fr=aladdin
 *      b. https://www.cnblogs.com/chengxiao/p/6129630.html
 *      c. https://www.cnblogs.com/malw/p/10542557.html
 *      d. https://blog.csdn.net/yuzhihui_no1/article/details/44258297
 *      e. https://bbs.csdn.net/topics/393162216
 *      f. https://blog.csdn.net/qq_34228570/article/details/80024306
 */
public class HeapSort {

    public void heapSort(int[] arr){
        /**
         * 基于数组的堆排序:
         * A. 核心思想:
         *      a. 对数组从第一个元素到最后一个元素, 依次从根结点到最后一个叶子结点构建堆. 这样, 该数组从逻辑上就是一个堆结构, 用简单公式
         *         描述以下堆的定义就是:
         *              a.1. 大顶堆: arr[i] >= arr[2i+1] && arr[i] >= arr[2i_2]
         *              a.2. 小顶堆: arr[i] <= arr[2i+1] && arr[i] <= arr[2i+2]
         *              a.3. 基于数组构建的堆, 其最后一个非叶子结点的序号为 n/2-1 (n为数组的长度):
         *                      a.3.1. 堆的最后一个非叶子结点只有左孩子(因为是从左到右分布数组的, 所以不一定有右孩子, 但非叶子结点一定会有左孩子)
         *                          => 证明:
         *                                  1) 对于该非叶子结点, 记序号为i, 由于是最后一个非叶子结点, 那么其左孩子序号为n-1
         *                                  2) 根据公式a.1. 建立等式: 2i+1 = n-1 => i = n/2 - 1
         *                                  3) 原命题得证
         *                      a.3.2. 堆的最后一个非叶子结点同时有左右两个孩子
         *                          => 证明:
         *                                  1) 对于该非叶子结点, 记序号为i, 由于是最后一个非叶子结点, 那么其左孩子序号为n-2, 右孩子序号为n-1
         *                                  2) 根据公式a.1. 建立等式: 2i+1 = n-2 => i = (n-1)/2 - 1
         *                                  3) 根据公式a.2. 建立等式: 2i+2 = n-1 => i = (n-1)/2 - 1
         *                                  4) 根据完全二叉树的结构, 可以明显知道, 当最后一个非叶子结点只有左孩子时, n为偶数, a.3.1. 证明成立,
         *                                     而当最后一个非叶子结点同时有左右孩子时, n为奇数, 对于a.3.2. 由于Java编程里默认是向下取整的,
         *                                     也就是: n为奇数时, n/2=(n-1)/2, 也就是 (n-1)/2 - 1 => n/2 - 1, 原命题得证
         *                                  5) 所以, 无论n时奇数还是偶数, 在Java编程里, 堆中最后一个非叶子结点的序号都为 n/2 - 1
         *              a.4. i结点的父结点为 (i-1)/2, i为当前结点的序号(其他推导):
         *                      a.4.1.
         *                          => 证明:
         *                                  1) 当i为根结点时, (i-1)/2=0, 公式成立
         *                                  2) 当i为左孩子时, 设父节点是 (i-1)/2, 由于左孩子序号肯定为偶数, 所以i=2*(i-1)/2+1, 公式成立
         *                                  3) 当i为右孩子时, 设父节点是 (i-1)/2, 由于右孩子序号肯定为奇数, 而Java默认是向下取证,
         *                                     所以(i-1)/2 = (i-2)/2, 则2*(i-1)/2+2 = 2*(i-2)/2+2 = i, 公式成立
         *      b. 将待排序序列造成一个大顶堆, 此时, 整个序列的最大值就是堆顶的根节点. 将其与序列末尾元素进行交换, 此时该末尾就为最大值.
         *         然后将剩余n-1个元素作为次小值, 重新构造成一个堆, 再经过堆调整, 得到新的最大堆...递归执行这些操作, 最后便能得到一个有序
         *         序列:
         *              b.1. 将给定的无序序列构造成一个大顶堆(一般升序采用大顶堆, 降序采用小顶堆)
         *              b.2. 将堆顶元素与末尾元素进行交换, 使最大元素沉到序列末尾
         *              b.3. 对剩余n-1个元素重新构建大顶堆, 调整堆结构, 使其满足堆定义, 然后继续交换堆顶元素, 反复执行b.1、b.2操作,
         *                   直到整个序列有序
         * B. 时间复杂度:
         *      a. 初始化堆的时间复杂度为 O(n):
         *          a.1. 假设完全二叉树高度为k, 则从倒数第二层右边结点开始. 这一层的结点都要执行子结点比较, 然后交换(当然如果顺序是对的,
         *               就不用交换). 对于倒数第三层, 还是会选择其子结点进行比较和交换, 如果发生交换还需要选择子结点的子树再进行交换...
         *          a.2. 根据a.1. 可以建立公式, 第i层总花费时间为:
         *                  S = 2^(i-1)*(k-i), i表示第几层, 则2^(i-1)表示该层上有多少个元素, k-i表示该层每个元素子树还需要比较的次数
         *          a.3. 由于叶子结点不用交换, 所以i从k-1开始一直到1, 则初始化堆的总花费时间为:
         *                  Sn = 2^(k-2)*1 + 2^(k-3)*2 +...+ 2^(1)*(k-2) + 2^(0)*(k-1)
         *               => 2Sn = 2^(k-1)*1 + 2^(k-2)*2 +...+ 2^(2)*(k-2) + 2^(1)*(k-1)
         *               => 2Sn-Sn = 2^(k-1) + 2^(k-2) + 2^(k-3) +... + 2^(2) + 2^(1) - (k-1)
         *               而2^(k-1) + 2^(k-2) + 2^(k-3) +... + 2^(2) + 2^(1)构成等比序列, 根据等比序列求和公式Sn = a1*(1-q^n)/(1-q)
         *               得: Sn = 2*[1-2^(k-1)]/(1-2) - k + 1
         *               => Sn = 2^(k-1) - k - 1
         *          a.4. 而对于高度为k, 结点数为n的完全二叉树来说, 设有两种极端情况:
         *               1) 当该树为满二叉树附加一个子结点时, 结点数n1 = 2^(k-1) - 1 + 1 = 2^(k-1)
         *               2) 当该树为满二叉树时, 结点数n2 = 2^k - 1
         *               3) 而n1 <= n <= n2, 即2^(k-1) <= n <= 2^k - 1, 得到两公式:
         *                      ① 2^(k-1) <= n
         *                      ② n <= 2^k - 1
         *                  分别求出k的范围为:
         *                      ① k <= logn+1
         *                      ② k >= log(n+1)
         *                  因此, 对于结点数为n的完全二叉树, 高度k的取值范围为 log(n+1) <= k <= logn+1
         *          a.5. 因此, Sn <= 2^(logn+1-1) - (logn+1) - 1 = n - logn <= n, 即初始化堆的时间复杂度为O(n)
         *      b. 更换堆顶元素后重建堆的时间复杂度为O(nlogn):
         *          a.1. 堆调整函数时间复杂度:
         *              1) 第1次比较结束, 错误位置的元素减少2个
         *              2) 第2次比较结束, 错误位置的元素减少4个
         *              ...
         *              3) 第k次比较结束, 错误位置的元素减少n个, 这时, n个元素全部放在了正确的位置上
         *              => 建立公式, 2^k = n, 则 k = log(n), 故时间复杂度为O(logn)
         *          a.2. 而对于堆调整来说, 是不用堆初始化堆的堆顶元素进行遍历的, 因此只需要进行n-1次循环即可完成, 即n-1次循环的时间复杂度为:
         *              log(n-1)+log(n-2)+...+log(2)+log(1) ≈ log[(n-1)!]
         *          a.3. 而由于n! <= n^n, 有log(n!) <= nlogn, 即有: log[(n-1)!] <= (n-1)log(n-1) <= log(n!) <= nlogn,
         *              因此, 更换堆顶元素后重建堆的时间复杂度为O(nlogn)
         * C. 空间复杂度:
         *      a. 由于堆排序没引入其他辅助空间, 是就地排序, 因此, 其空间复杂度为O(1)
         * D. 稳定性:
         *      a. 如果堆顶发生与末尾交换后, 在堆调整函数里, 当根节点和其左孩子元素相等, 却又比根结点的右孩子元素小, 那么需要发生一次堆调整,
         *         这时就会将根结点交换到右孩子结点或者其他结点位置, 就打乱了相等元素的相对顺序, 因此, 堆排序是不稳定的
         */
        // 从最后一个非叶子结点, 从下向上, 从右到左构建大顶堆
        int n = arr.length;
        for(int i = n/2-1; i >= 0; i--){
            adjustHeap(arr, i, n);
        }

        for(int i = n-1; i >= 0; i--){
            // 构建大顶堆后, 将堆顶元素与末尾元素进行交换
            swap(arr, 0, i);

            // 对剩余元素重新构建大顶堆, 调整堆结构, 使其满足堆定义
            adjustHeap(arr, 0, i);
        }

        return;
    }

    private void adjustHeap(int[] arr, int i, int length){// i为当前结点序号
        int temp = arr[i];// 克隆根节点的值

        // 由于交换后, 可能会导致堆结构混乱, 所以要从上到下, 从左到右, 遍历查找交换最大值到根节点, 然后把原来堆顶的值放到合适的位置上
        for(int j = 2*i+1; j < length; j = 2*j+1){// 初始化j为根节点的左孩子, 遍历完毕后, 当前结点指向孩子结点的左孩子
            // 如果同时存在左右孩子, 且根节点的左孩子的值 小于 右孩子, 则当前指针指向右孩子, 使得右孩子作为新遍历子树的根节点
            if(j+1 < length && arr[j] < arr[j+1]){
                j++;
            }

            // 比较根节点的值与初始根节点的值
            if(arr[j] > temp){// 如果大于初始根节点的值(这样比较也相当于默认赋值到了新根结点上), 则交换两值(不用交换, 最后统一交换)
                arr[i] = arr[j];// 交换最大值到根结点
                i = j;// 该孩子结点作为下一轮遍历的根结点
            }else {// 否则直接结束遍历(因为左右孩子的值都比根节点值小, 由于没发生过交换, 孩子子树肯定满足堆的性质, 所以没必要再遍历下去)
                break;
            }
        }

        // 退出循环后, 把备份的初始根节点的值放到合适的位置上
        arr[i] = temp;// i指向每轮的根节点, 指向合适的位置
    }

    private void swap(int[] arr, int i, int j){
        int temp = arr[i];
        arr[i] = arr[j];
        arr[j] = temp;
    }

    public static void main(String[] args) {
        int[] arr = {
                9, 8, 10, 4, 7, 2, 5, 5
        };

        HeapSort heapSort = new HeapSort();
        heapSort.heapSort(arr);

        for(int i = 0; i < arr.length; i++){
            System.out.println(arr[i]);
        }
    }
}