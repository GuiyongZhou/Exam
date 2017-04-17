package com.cqu.exam.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.DrawableUtils;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.cqu.exam.R;
import com.cqu.exam.appliction.ExamApplication;
import com.cqu.exam.bean.AnSwerInfo;
import com.cqu.exam.bean.Anwsers;
import com.cqu.exam.bean.Options;
import com.cqu.exam.bean.Question;
import com.cqu.exam.util.CustomDialogUpd;
import com.cqu.exam.util.FileUtils;
import com.cqu.exam.util.PreferencesUtils;

import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.view.annotation.ContentView;
import org.xutils.view.annotation.Event;
import org.xutils.view.annotation.ViewInject;
import org.xutils.x;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 * 系统设置
 * Created by zgy on 2017/4/16.
 */
@ContentView(value = R.layout.activity_setting)
public class SystemSettingActivity extends BaseActivity {

    @ViewInject(value = R.id.right)
    private ImageView Import_Text_Btn;
    @ViewInject(value = R.id.activity_setting_tv_once_questions)
    private TextView activity_setting_textview_once_questions;
    @ViewInject(value = R.id.activity_setting_tv_title_show_seconds)
    private TextView activity_setting_textview_title_show_seconds;
    @ViewInject(value = R.id.activity_setting_tv_question_show_seconds)
    private TextView activity_setting_textview_question_show_seconds;

    private static final int REQUEST_CHOOSER = 1234;
    private Integer  once_questions;
    private Integer  title_show_seconds;
    private Integer  question_show_seconds;

    @Override
    public void initializationLayout() {
        setTopTitle(R.string.SystemSetting);
        Import_Text_Btn.setVisibility(View.VISIBLE);
        Import_Text_Btn.setBackground(ContextCompat.getDrawable(this,R.drawable.export));
    }

    @Override
    public void initializationData() {
        once_questions = PreferencesUtils.getInt(this,"once_questions",Integer.parseInt(getString(R.string.once_questions)));
        title_show_seconds = PreferencesUtils.getInt(this,"title_show_seconds",Integer.parseInt(getString(R.string.title_show_seconds)));
        question_show_seconds = PreferencesUtils.getInt(this,"question_show_seconds",Integer.parseInt(getString(R.string.question_show_seconds)));
        activity_setting_textview_once_questions.setText(once_questions.toString());
        activity_setting_textview_title_show_seconds.setText(title_show_seconds.toString());
        activity_setting_textview_question_show_seconds.setText(question_show_seconds.toString());
    }

    @Event(value = R.id.right)
    private void Click_Import_Text_Btn(View view){
        //选择导入题目文件
        Intent getContentIntent = FileUtils.createGetContentIntent();
        Intent intent = Intent.createChooser(getContentIntent, "Select a file");
        startActivityForResult(intent, REQUEST_CHOOSER);
    }
    @Event(value = R.id.activity_setting_once_questions)
    private void Click_activity_setting_once_questions(View view){
        ShowDialog(1,getString(R.string.SystemSetting_1),once_questions);
    }
    @Event(value = R.id.activity_setting_title_show_seconds)
    private void Click_activity_setting_title_show_seconds(View view){
        ShowDialog(2,getString(R.string.SystemSetting_2),title_show_seconds);
    }
    @Event(value = R.id.activity_setting_title_question_show_seconds)
    private void Click_activity_setting_title_question_show_seconds(View view){
        ShowDialog(3,getString(R.string.SystemSetting_3),question_show_seconds);
    }
    @Event(value = R.id.activity_setting_title_update_password)
    private void Click_activity_setting_title_update_password(View view){
        ShowDialog(4,"密码",null);
    }

    private void ShowDialog(final int type,final String title,Integer value){
        final CustomDialogUpd.Builder builder = new CustomDialogUpd.Builder(this);
        builder.setTitle("修改"+title);
        if(value != null)
        {
            builder.setTips("请输入"+title);
            builder.setMessage(value.toString());
        }
        else if(type == 4)
        {
            builder.setTips("请输入新密码");
        }
        builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int arg1) {
                String input_String = builder.getEditText().getText().toString();
                if(TextUtils.isEmpty(input_String))
                {
                    Toast.makeText(getApplicationContext(),"请输入"+title,Toast.LENGTH_SHORT).show();
                }else{
                    int new_Int = Integer.parseInt(input_String);
                    switch (type)
                    {
                        case 1:
                            PreferencesUtils.putInt(getApplicationContext(),"once_questions",new_Int);
                            activity_setting_textview_once_questions.setText(input_String);
                            break;
                        case 2:
                            PreferencesUtils.putInt(getApplicationContext(),"title_show_seconds",new_Int);
                            activity_setting_textview_title_show_seconds.setText(input_String);
                            break;
                        case 3:
                            PreferencesUtils.putInt(getApplicationContext(),"question_show_seconds",new_Int);
                            activity_setting_textview_question_show_seconds.setText(input_String);
                            break;
                        case 4:
                            PreferencesUtils.putString(getApplicationContext(),"password",input_String);
                            break;
                    }
                    dialog.dismiss();
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

    /**
     * 获取文件路径
     *
     * @param requestCode
     * @param resultCode
     * @param data
     */
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        //初始化
        AnSwerInfo anSwerInfo = new AnSwerInfo();
        switch (requestCode) {
            case REQUEST_CHOOSER:
                if (resultCode == RESULT_OK) {
                    final Uri uri = data.getData();
                    String path = FileUtils.getPath(this, uri);
                    if (path != null && FileUtils.isLocal(path)) {
                        try {
                            DbManager dbManager = x.getDb(ExamApplication.getInstance().getDaoConfig());
                            //清空原来的 试题
                            dbManager.delete(Question.class);
                            dbManager.delete(Options.class);
                            dbManager.delete(Anwsers.class);
                            //下面开始导入新的试题

                            //题目类型    0填空题  1单选题  2判断题  3多选题
                            int QuestionType = 1;
                            //成功导入的题目个数
                            int Success = 0;
                            File file = new File(path);
                            if (file.isFile() && file.exists()) { //判断文件是否存在
                                InputStreamReader read = new InputStreamReader(new FileInputStream(file),getCharset(file));//考虑到编码格式
                                BufferedReader bufferedReader = new BufferedReader(read);
                                String lineTxt = null;
                                while ((lineTxt = bufferedReader.readLine()) != null) {
                                    Log.e("XXXXXX",lineTxt);
                                    if(!(filter(lineTxt,0).equals("")))
                                    {
                                        boolean title_in = false;
                                        boolean option_in = false;
                                        boolean anwser_in = false;
                                        if(lineTxt.contains("填空题") || lineTxt.contains("填空"))
                                        {
                                            QuestionType = 0;
                                            continue;
                                        }
                                        if(lineTxt.contains("单选题") || lineTxt.contains("单选"))
                                        {
                                            QuestionType = 1;
                                            continue;
                                        }
                                        if(lineTxt.contains("判断题") || lineTxt.contains("判断"))
                                        {
                                            QuestionType = 2;
                                            continue;
                                        }
                                        if(lineTxt.contains("多选题") || lineTxt.contains("多选"))
                                        {
                                            QuestionType = 3;
                                            continue;
                                        }
                                        Question question = new Question();
                                        question.setType(QuestionType);
                                        question.setTitle(filter(lineTxt,1));//过滤出标题
                                        if(dbManager.saveBindingId(question))//保存题目
                                        {
                                            title_in = true;
                                            Options options = new Options();
                                            options.setQId(question.getId());
                                            //由于这4种类型 至少都是两个选项
                                            //选项A
                                            options.setOption_A(filter(bufferedReader.readLine(), 0));
                                            //选项B
                                            options.setOption_B(filter(bufferedReader.readLine(), 0));
                                            lineTxt =  filter(bufferedReader.readLine(), 0);
                                            if(lineTxt.startsWith("C") || lineTxt.startsWith("c")){
                                                options.setOption_C(lineTxt);
                                                lineTxt =  filter(bufferedReader.readLine(), 0);
                                            }
                                            if(lineTxt.startsWith("D") || lineTxt.startsWith("d")){
                                                options.setOption_D(lineTxt);
                                                lineTxt =  filter(bufferedReader.readLine(), 0);
                                            }
                                            if(lineTxt.startsWith("E") || lineTxt.startsWith("e")){
                                                options.setOption_E(lineTxt);
                                                lineTxt =  filter(bufferedReader.readLine(), 0);
                                            }
                                            if(lineTxt.startsWith("F") || lineTxt.startsWith("f")){
                                                options.setOption_F(lineTxt);
                                                lineTxt =  filter(bufferedReader.readLine(), 0);
                                            }
                                            if(lineTxt.startsWith("G") || lineTxt.startsWith("g")){
                                                options.setOption_G(lineTxt);
                                                lineTxt =  filter(bufferedReader.readLine(), 0);
                                            }
                                            //正确答案
                                            if(lineTxt.startsWith("【") || lineTxt.startsWith("[") ){
                                                char[] result_char = lineTxt.toCharArray();
                                                boolean all_anwsers_in = true;
                                                for(int i = 0;i < result_char.length;i++)
                                                {
                                                    if((result_char[i] >= 'A' && result_char[i] <= 'G') || (result_char[i] >= 'a' && result_char[i] <= 'g'))
                                                    {
                                                        Anwsers anwsers = new Anwsers();
                                                        anwsers.setCorrect_Anwser(String.valueOf(result_char[i]).toUpperCase());
                                                        anwsers.setQId(question.getId());
                                                        //保存正确答案
                                                        if(!dbManager.saveBindingId(anwsers))
                                                        {
                                                            all_anwsers_in = false;
                                                        }
                                                    }
                                                }
                                                if(all_anwsers_in)
                                                {
                                                    anwser_in = true;
                                                }
                                            }
                                            //答案解析
                                            options.setAnwserTip(filter(bufferedReader.readLine(), 0));
                                            if(dbManager.saveBindingId(options))
                                            {
                                                option_in = true;
                                            }
                                            if(title_in && option_in && anwser_in)
                                            {
                                                Success ++;//导入数量
                                            }
                                        }
                                    }
                                }
                                Toast.makeText(this,"成功导入 "+Success+" 个题目",Toast.LENGTH_LONG).show();
                                read.close();
                                dbManager.close();
                            } else {
                                Toast.makeText(this,"找不到指定的文件",Toast.LENGTH_SHORT).show();
                            }
                        } catch (Throwable e) {
                            Toast.makeText(this,"错误,原因:"+e.getMessage(),Toast.LENGTH_SHORT).show();
                        }
                    }
                }
                break;
        }
    }

    /**
     * 字符串筛选
     * @param str
     * @param mode 0去掉头尾空格  1过滤题目
     * @return
     */
    public String filter(String str,int mode) {
        str = str.trim();//去掉头尾空格
        switch (mode)
        {
            //题目 字符串过滤
            case 1 :
            String[]  split_result = str.split("[ ]+",2);//分成两部分
            if(split_result.length < 2)
            {
                //序号和题目之间没有空格分开
                str =  split_result[0];
                String[]  split_result_second_times = str.split(".",2);//分成两部分
                if(split_result_second_times.length == 2)
                {
                    return split_result_second_times[1];
                }
                String[]  split_result_third_times = split_result_second_times[0].split("、",2);//分成两部分
                if(split_result_third_times.length == 2)
                {
                    return split_result_third_times[1];
                }
            }else{
                //符合要求
                return split_result[1];
            }
            break;
        }
        return str;
    }

    /**
     * 获取Txt文件编码格式
     * @param file
     * @return
     * @throws IOException
     */
    private String getCharset(File file) throws IOException {
        BufferedInputStream bin = new BufferedInputStream(new FileInputStream(file));
        int p = (bin.read() << 8) + bin.read();
        String code = null;
        switch (p) {
            case 0xefbb:
                code = "UTF-8";
                break;
            case 0xfffe:
                code = "Unicode";
                break;
            case 0xfeff:
                code = "UTF-16BE";
                break;
            default:
                code = "GBK";
        }
        bin.close();
        return code;
    }
}
