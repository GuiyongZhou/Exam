package com.cqu.exam.appliction;

import android.app.Application;
import android.graphics.Typeface;
import android.util.Log;

import com.cqu.exam.bean.Anwsers;
import com.cqu.exam.bean.Options;
import com.cqu.exam.bean.Question;

import org.xutils.BuildConfig;
import org.xutils.DbManager;
import org.xutils.db.table.TableEntity;
import org.xutils.x;

import java.io.File;

/**
 * Created by zgy on 2016/12/4.
 */

public class ExamApplication extends Application {

    private static ExamApplication instance;
    private DbManager.DaoConfig daoConfig;
    public static ExamApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        x.Ext.init(this);//Xutils初始化
        x.Ext.setDebug(BuildConfig.DEBUG); // 是否输出debug日志

        daoConfig = new DbManager.DaoConfig()
                .setDbName("Exam.db")
                // 不设置dbDir时, 默认存储在app的私有目录.
                //.setDbDir(new File("/sdcard")) // "sdcard"的写法并非最佳实践, 这里为了简单, 先这样写了.
                .setDbVersion(1)
                .setAllowTransaction(true)
                .setDbOpenListener(new DbManager.DbOpenListener() {
                    @Override
                    public void onDbOpened(DbManager db) {
                        // 开启WAL, 对写入加速提升巨大
                        db.getDatabase().enableWriteAheadLogging();
                    }
                })
                .setTableCreateListener(new DbManager.TableCreateListener() {
                    @Override
                    public void onTableCreated(DbManager db, TableEntity<?> table) {
                        Log.i("ExamDataBase", "onTableCreated：" + table.getName());
                    }
                })
                .setDbUpgradeListener(new DbManager.DbUpgradeListener() {
                    @Override
                    public void onUpgrade(DbManager db, int oldVersion, int newVersion) {
                        // TODO: ...
                        // db.addColumn(...);
                        // db.dropTable(...);
                        // ...
                        // or
                        // db.dropDb();
                    }
                });
    }

    public DbManager.DaoConfig getDaoConfig() {
        return daoConfig;
    }


    private Typeface Awesome,typeface;

    public Typeface getTypeface() {
        if (typeface == null) {
            typeface = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        }
        return typeface;
    }
    public Typeface getAwesome() {
        if (Awesome == null) {
            Awesome = Typeface.createFromAsset(getAssets(),"fonts/fontawesome-webfont.ttf");
        }
        return Awesome;
    }
}
