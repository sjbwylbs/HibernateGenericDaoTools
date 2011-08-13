package com.ijqg.tools;

import java.io.File;

public class CurrentDirectory {
	public static void print(Object o) {
		System.out.println(o);
	}

	public static void show() {
		print(Thread.currentThread().getContextClassLoader().getResource(""));
		print(CurrentDirectory.class.getClassLoader().getResource(""));
		print(ClassLoader.getSystemResource(""));
		print(CurrentDirectory.class.getResource(""));
		print(CurrentDirectory.class.getResource("/"));
		print(new File("").getAbsolutePath());
		print(System.getProperty("user.dir"));
	}

	public static void main(String[] args) throws Exception {
		show();
	}
}
