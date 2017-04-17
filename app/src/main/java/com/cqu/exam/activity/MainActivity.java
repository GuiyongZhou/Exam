package com.cqu.exam.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.cqu.exam.R;
import com.cqu.exam.util.CustomDialogUpd;
import com.cqu.exam.util.PreferencesUtils;

import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;

/**
 * 首页
 * @author zgy
 */
@ContentView(value = R.layout.activity_main)
public class MainActivity extends BaseActivity {

    @ViewInject(value = R.id.left)
    private ImageView left;
    @ViewInject(value = R.id.right)
    private ImageView SystemSetting;

    @Override
    public void initializationLayout() {
        setTopTitle(R.string.app_name);
        left.setVisibility(View.GONE);
        SystemSetting.setVisibility(View.VISIBLE);
    }

    @Override
    public void initializationData() {
        if(TextUtils.isEmpty(PreferencesUtils.getString(this,"password",null)))
        {
            PreferencesUtils.putString(this,"password",getString(R.string.password));
        }
    }

    @Event(value = R.id.right)
    private void Click_SystemSetting(View view){
        ShowDialog();
    }

    private void ShowDialog(){
        final CustomDialogUpd.Builder builder = new CustomDialogUpd.Builder(this);
        builder.setTitle("管理员验证");
        builder.setTips("请输入管理员密码");
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                if(builder.getEditText().getText().toString().equals(PreferencesUtils.getString(getApplicationContext(),"password",null)))
                {

                    Intent it = new Intent(getApplicationContext(),SystemSettingActivity.class);
                    startActivity(it);
                    dialog.dismiss();
                }else
                {
                    Toast.makeText(getApplicationContext(),"密码错误",Toast.LENGTH_SHORT).show();
                }
            }
        });
        // 当点取消按钮时进行登录
        builder.setNegativeButton("取消", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
            }
        });
        builder.create().show();
    }
}
