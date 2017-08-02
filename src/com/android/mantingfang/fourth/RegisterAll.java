package com.android.mantingfang.fourth;

import java.util.Stack;

import android.app.Activity;

public class RegisterAll {
	
	private static RegisterAll register;
	private Stack<Activity> stack = new Stack<>();
	
	private RegisterAll() {}
	
	public static RegisterAll getInstance() {
		if (register == null) {
			register = new RegisterAll();
		}
		
		return register;
	}
	
	public void addActivity(Activity activity) {
		stack.add(activity);
	}
	
	public void destroyAll() {
		while (!stack.isEmpty()) {
			stack.pop();
		}
	}
}
