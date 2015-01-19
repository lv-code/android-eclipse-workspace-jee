
/**
 * @author zg
 * 冒泡排序
 *
 */
public class BubbleSort {

	public static void main(String[] args) {
		

	}

    // 冒泡排序
    // 特点：从第一个元素开始，如果需要交换，就一直冒泡到底，如果不需要交换，就从下一个元素开始比较
    public void bubble_Sort(int[] array, int size) {
        int i, j, temp;
        for (i = size - 1; i > 1; i--)
            for (j = 0; j < i; j++)
                if (array[j] > array[j + 1]) {
                    temp = array[j + 1];
                    array[j + 1] = array[j];
                    array[j] = temp;
                }
    }
    
    // 交换排序
    // 特点：始终是第一个元素与其他元素一一比较，交互后，继续用第一个元素与后面元素一一比较，重复下去。
    public int[] change_Sort(int[] array, int size) {
        int i, j, temp;
        for (i = 0; i < size; i++)
            for (j = i + 1; j < size; j++)
                if (array[i] > array[j]) {
                    temp = array[j];
                    array[j] = array[i];
                    array[i] = temp;
                }
        return array;
    }
}
