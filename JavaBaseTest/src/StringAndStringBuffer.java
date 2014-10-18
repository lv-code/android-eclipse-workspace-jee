/*
 * 在java中有3个类来负责字符的操作。

1.Character 是进行单个字符操作的，
2.String 对一串字符进行操作。不可变类。
3.StringBuffer 也是对一串字符进行操作，但是可变类。

备注：

str += "here";
的处理步骤实际上是通过建立一个StringBuffer,让侯调用append(),最后
再将StringBuffer toSting();

所以str += "here";可以等同于

StringBuffer sb = new StringBuffer(str);
sb.append("here");
str = sb.toString();
 */
public class StringAndStringBuffer {
	public static void main(String[] args) {
		test1();
		test2();
		test3();
	}

	/*
	 * 将26个英文字母重复加了5000次 可惜我的计算机不是超级计算机，得到的结果每次不一定一样一般为 46687左右。 也就是46秒。
	 */
	private static void test1() {
		String tempstr = "abcdefghijklmnopqrstuvwxyz";
		int times = 5000;
		long lstart1 = System.currentTimeMillis();
		String str = "";
		for (int i = 0; i < times; i++) {
			str += tempstr;
		}
		long lend1 = System.currentTimeMillis();
		long time = (lend1 - lstart1);
		System.out.println(time);
	}

	/*
	 * 得到的结果为 16 有时还是 0 所以结论很明显，StringBuffer 的速度几乎是String 上万倍。 当然这个数据不是很准确。
	 * 因为循环的次数在100000次的时候，差异更大。
	 */
	private static void test2() {
		String tempstr = "abcdefghijklmnopqrstuvwxyz";
		int times = 5000;
		long lstart2 = System.currentTimeMillis();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < times; i++) {
			sb.append(tempstr);
		}
		long lend2 = System.currentTimeMillis();
		long time2 = (lend2 - lstart2);
		System.out.println(time2);
	}

	/*
	 * 平均执行时间为46922左右，也就是46秒
	 */
	private static void test3() {
		String tempstr = "abcdefghijklmnopqrstuvwxyz";
		int times = 5000;
		long lstart2 = System.currentTimeMillis();
		String str = "";
		for (int i = 0; i < times; i++) {
			StringBuffer sb = new StringBuffer(str);
			sb.append(tempstr);
			str = sb.toString();
		}
		long lend2 = System.currentTimeMillis();
		long time2 = (lend2 - lstart2);
		System.out.println(time2);
	}
}
