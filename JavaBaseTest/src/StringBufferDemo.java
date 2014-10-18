/*
 * StringBuffer的常用操作实例
 */
public class StringBufferDemo {

	public static void main(String[] args) {
		String s = "1.26";
		String str = "Hello java!kuaikuaijava-Java";
		char c;
		int iCount = 0, oCount = 0, jCount = 0;

		try {
			int i = Integer.parseInt(s);
		} catch (NumberFormatException e) {
			System.out.println(e.getMessage());
		}
		// str中含有多少个大写字母，小写字母和其他字母
		for (int i = 0; i < str.length(); i++) {
			c = str.charAt(i);
			// 判断是否是小写
			if (Character.isLowerCase(c)) {
				iCount++;
				// 判断是否是大写
			} else if (Character.isUpperCase(c)) {
				jCount++;
				// 其他的情况
			} else {
				oCount++;
			}
		}

		System.out.println("str中含有" + iCount + "个小写字母");
		System.out.println("str中含有" + jCount + "个大写字母");
		System.out.println("str中含有" + oCount + "个其他字符");

		// str中含有多少个java字符串
		int index = -1;
		int count = 0;
		String p = "java";
		while ((index = str.indexOf(p)) != -1) {
			count++;
			str = str.substring((index + p.length()), str.length());
		}
		System.out.println("str中共含有" + count + "个java字符串");

		// 从字符串str中解析出double类型二维数组的相应元素
		String str1 = "1,2;3,4,5;6,7,8";
		// 声明一个二维数组
		Double d[][];
		// 从“;”处把字符串分割成一个字符串数组
		String[] str2 = str1.split(";");
		// 实例化第一维数组
		d = new Double[str2.length][];
		for (int i = 0; i < str2.length; i++) {
			String[] str3 = str2[i].split(",");
			d[i] = new Double[str3.length];
			for (int j = 0; j < str3.length; j++) {
				// 将字符转化为Double类型
				d[i][j] = Double.parseDouble(str3[j]);
				System.out.print(d[i][j] + " ");
			}
			System.out.println();
		}

		// StringBuffer的使用
		String ss = "Hello java!";
		StringBuffer s1 = new StringBuffer();
		// 在s1后添加字符(串)
		s1.append(ss).append("你好！java！").append("呵呵!");
		System.out.println(s1);
		// 删除索引为0的位置的字符
		s1.deleteCharAt(0);
		System.out.println(s1);
		// 从索引为0的位置开始，截取11个字符
		ss = s1.substring(0, 11);
		System.out.println(ss);
		// 使s1内容反向
		s1.reverse();
		System.out.println(s1);
		// 在4索引后插入第二个参数的内容
		s1.insert(4, "这是插入的字符");
		System.out.println(s1);

	}

}
