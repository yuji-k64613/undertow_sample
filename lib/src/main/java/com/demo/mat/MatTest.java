package com.demo.mat;

public class MatTest {
	private String name;
	private MatObject[] arrays = null;
	
	public MatTest(String name, int size) {
		this.name = name;
		arrays = new MatObject[size];
		for (int i = 0; i < size; i++) {
			arrays[i] = new MatObject();
		}
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}
}
