package com.cqu.exam.bean;

import org.xutils.DbManager;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;
import org.xutils.ex.DbException;

import java.util.List;

/**
 * Created by zgy on 2017/4/16.
 */
@Table(name = "Exam_Question")
public class Question {
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "title")
    public String title;

    //问题类型: 0填空题  1单选题  2判断题  3多选题
    @Column(name = "type")
    public int type;

    //选项 一对一
    public Options getOptions(DbManager db) throws DbException {
        return db.selector(Options.class).where("QId", "=", this.id).findFirst();
    }

    //答案 一对多
    public List<Anwsers> getAnwsers(DbManager db) throws DbException {
        return db.selector(Anwsers.class).where("QId", "=", this.id).findAll();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public Question() {

    }

    public Question(String title, int type) {
        this.title = title;
        this.type = type;
    }
}
