import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

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
