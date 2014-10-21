import java.util.ArrayList;

import com.google.gson.Gson;

public class GsonDemo {
	static Gson gson;

	public static void main(String[] args) {
		test1();
		test2();
	}

	private static void test1() {
		Person person = new Person();
		person.id = 111;
		person.name = "张三";
		person.isMale = true;
		person.avatar = "http://aaaaaaaaa";

		gson = new Gson();

		String jsonStr = gson.toJson(person);
		System.out.println("=============" + jsonStr);
	}

	private static void test2() {
		ArrayList<Person> list = new ArrayList<Person>();

		Person person = new Person();
		person.id = 111;
		person.name = "张三";
		person.isMale = true;
		person.avatar = "http://aaaaaaaaa";
		list.add(person);

		person = new Person();
		// ArrayList<Person> list = new ArrayList<Person>();
		person.id = 222;
		person.name = "李四";
		person.isMale = false;
		person.avatar = "http://bbbbbbbbb";
		list.add(person);

		String jsonStr = gson.toJson(list);
		System.out.println("=============" + jsonStr);
	}

	private void test4(){
		final String JSON_STR = "{\"avatar\":\"http://aaaaaaaaa\",\"name\":\"博张三\",\"id\":111,\"isMale\":true}";
		 
		Person person = gson.fromJson(JSON_STR, Person.class);
	}
	public static class Person {
		public long id;
		public String name;
		public boolean isMale;
		public String avatar;

	}
}
