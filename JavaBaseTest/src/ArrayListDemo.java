import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/*
迭代器（Iterator）

　　迭代器是一种设计模式，它是一个对象，它可以遍历并选择序列中的对象，而开发人员不需要了解该序列的底层结构。迭代器通常被称为“轻量级”对象，因为创建它的代价小。

　　Java中的Iterator功能比较简单，并且只能单向移动：
　　(1) 使用方法iterator()要求容器返回一个Iterator。第一次调用Iterator的next()方法时，它返回序列的第一个元素。注意：iterator()方法是java.lang.Iterable接口,被Collection继承。
　　(2) 使用next()获得序列中的下一个元素。
　　(3) 使用hasNext()检查序列中是否还有元素。
　　(4) 使用remove()将迭代器新返回的元素删除。
　　*/
public class ArrayListDemo {
	public static void main(String[] args) {
		test1();
		test2();
	}

	/*
	 * addAll和add有什么具体区别？
	 */
	public static void test1() {

		List<Integer> list = new ArrayList<Integer>();
		list.add(1);
		list.add(2);
		List<Integer> list1 = new ArrayList<Integer>();
		list1.add(3);
		list1.add(4);

		list.addAll(list1);

		Iterator<Integer> it = list.iterator();
		while (it.hasNext()) {
			Integer abc = it.next();
			System.out.println(abc.intValue());
		}
	}

	public static void test2() {
		List<String> list = new ArrayList<String>();
		list.add("王利虎");
		list.add("张三");
		list.add("李四");
		int size = list.size();
		String[] array = (String[]) list.toArray(new String[size]);
		for (int i = 0; i < array.length; i++) {
			System.out.println(array[i]);
		}
	}

}
