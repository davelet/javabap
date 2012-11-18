package cn.vash.mbca;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

public class ComparableTest {
	public static void main(String[] args) {
		Comparator<Student> comparator = new Comparator<Student>() {
			public int compare(Student s1, Student s2) {
				// 先排年龄
				if (s1.age != s2.age) {
					return s1.age - s2.age;
				} else {
					// 年龄相同则按姓名排序
					if (!s1.name.equals(s2.name)) {
						return s1.name.compareTo(s2.name);
					} else {
						// 姓名也相同则按学号排序
						return s1.id - s2.id;
					}
				}
			}
		};
		Student stu1 = new Student(1, "zhangsan", "male", 28, "cs");
		Student stu2 = new Student(2, "lisi", "female", 19, "cs");
		Student stu3 = new Student(3, "wangwu", "male", 22, "cs");
		Student stu4 = new Student(4, "zhaoliu", "female", 17, "cs");
		Student stu5 = new Student(5, "jiaoming", "male", 22, "cs");

		ArrayList<Student> List = new ArrayList<Student>();
		List.add(stu1);
		List.add(stu2);
		List.add(stu3);
		List.add(stu4);
		List.add(stu5);
		// 这里就会自动根据规则进行排序
		Collections.sort(List, comparator);
		display(List);
	}

	static void display(ArrayList<Student> lst) {
		for (Student s : lst)
			System.out.println(s);
	}
}

class Student {
	int age;
	int id;
	String gender;
	String name;
	String cs;

	Student(int id, String name, String gender, int age, String cs) {
		this.age = age;
		this.name = name;
		this.gender = gender;
		this.id = id;
		this.cs = cs;
	}

	public String toString() {
		return id + "  " + name + "  " + gender + "  " + age + "  " + cs;
	}
}