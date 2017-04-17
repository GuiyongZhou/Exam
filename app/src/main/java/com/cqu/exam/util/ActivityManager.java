package com.cqu.exam.util;

import android.app.Activity;

import java.util.Stack;

/**
 * 
 * ActivityManager
 * @author zgy
 * 
 * create at 2015年4月27日 下午2:19:38
 */
public class ActivityManager {
	private static Stack<Activity> activityStack;
	private static ActivityManager instance;

	private ActivityManager() {

	}

	public static ActivityManager getScreenManager() {
		if (instance == null) {
			instance = new ActivityManager();
		}
		return instance;
	}

	// 退出栈顶Activity
	public void exitActivity(Activity activity) {
		if (activity != null) {
			// 在从自定义集合中取出当前Activity时，也进行了Activity的关闭操作
			activity.finish();
			// activity.overridePendingTransition(R.anim.right_slip_out,R.anim.left_slip_in);
			activityStack.remove(activity);
			activity = null;
		}
	}

	// 获得当前栈顶Activity
	public Activity currentActivity() {
		Activity activity = null;
		if (!activityStack.empty())
			activity = activityStack.lastElement();
		return activity;
	}

	// 将当前Activity推入栈中
	public void addActivity(Activity activity) {
		if (activityStack == null) {
			activityStack = new Stack<Activity>();
		}
		activityStack.add(activity);
	}

	// 退出栈中所有Activity
	public void exitAllActivityExceptOne() {
		while (true) {
			Activity activity = currentActivity();
			if (activity == null) {
				break;
			}
			/*
			 * if (activity.getClass().equals(cls)) { Log.i("activity", "2");
			 * break; }
			 */
			exitActivity(activity);
		}
		System.exit(0);
//		Process.killProcess(Process.);//所有activity关闭后，结束进程
	}
}
