package com.cqu.exam.activity;

import android.app.Activity;
import android.nfc.NfcAdapter;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;


import com.cqu.exam.R;
import com.cqu.exam.util.ActivityManager;
import com.cqu.exam.view.LoadingDialog;

import org.xutils.view.annotation.Event;
import org.xutils.x;

/**
 * activity基类 
 */
public abstract class BaseActivity extends FragmentActivity {
	public Activity mActivity;
	public LoadingDialog mDialog;
	public Bundle savedInstanceState;


	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);// 去掉标题栏
		mActivity = this;
		this.savedInstanceState = savedInstanceState;
		ActivityManager.getScreenManager().addActivity(mActivity);// 管理当前activity，将其放在自定义栈中
		x.view().inject(this);// 开启注解要在setContentView函数之后才有效
		this.findExtras();
		this.initializationLayout();
		this.initializationData();
	}
	
	/**
	 * 获取界面传参
	 */
	public void findExtras(){
		
	}

	/**
	 * 初始化布局
	 */
	public abstract void initializationLayout() ;

	/**
	 * 初始化其它数据
	 */
	public abstract void initializationData() ;



	/**
	 * 显示加载
	 */
	public void showProgress(int resId){
		if(mDialog == null){
			mDialog = new LoadingDialog(mActivity);
		}
		mDialog.setText(resId);
		mDialog.setCancelable(false);
		mDialog.show();
	}
	
	/**
	 * 显示加载
	 */
	public void showProgress(String resId){
		if(mDialog == null){
			mDialog = new LoadingDialog(mActivity);
		}
		mDialog.setText(resId);
		mDialog.setCancelable(false);
		mDialog.show();
	}

	/**
	 * 关闭加载
	 */
	public void dismissProgress(){
		if(mDialog != null){
			mDialog.dismiss();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	/**
	 * 返回
	 * 
	 * @param v
	 */
	public void rebackClick(View v) {
		ActivityManager.getScreenManager().exitActivity(mActivity);
	}

	//返回键
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		if(keyCode==KeyEvent.KEYCODE_BACK){
			dismissProgress();
			if(getSupportFragmentManager().getBackStackEntryCount()==1){
				finish();
				return true;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

	/**
	 * 设置头部title
	 * @param title
	 */
	public void setTopTitle(int title){
		((TextView) findViewById(R.id.title)).setText(title);;
	}

	/**
	 * 设置头部title
	 * @param title
	 */
	public void setTopTitle(String title){
		((TextView) findViewById(R.id.title)).setText(title);;
	}

	@Event(R.id.left)
	private void Click_Left_Back_Btn(View view){
		rebackClick(view);
	}

	/**
	 * 隐藏软键盘
	 */
	public void hideTheSoftKeyboard() {
		try {
			View v = getCurrentFocus();
			if (v != null) {
				((InputMethodManager) getSystemService(INPUT_METHOD_SERVICE))
				.hideSoftInputFromWindow(v.getWindowToken(),
						InputMethodManager.HIDE_NOT_ALWAYS);
			}
		} catch (Exception e) {

		}
	}
}
