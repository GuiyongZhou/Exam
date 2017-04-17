package com.cqu.exam.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by zgy on 2017/4/16.
 */
@Table(name = "Exam_Anwsers")
public class Anwsers {
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "Correct_Anwser")
    public String Correct_Anwser;

    @Column(name = "QId" /*,property = "UNIQUE"    //如果是一对一加上唯一约束*/)
    private long QId; // 外键表id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getCorrect_Anwser() {
        return Correct_Anwser;
    }

    public void setCorrect_Anwser(String correct_Anwser) {
        Correct_Anwser = correct_Anwser;
    }

    public long getQId() {
        return QId;
    }

    public void setQId(long QId) {
        this.QId = QId;
    }

    public Anwsers( String correct_Anwser, long QId) {
        Correct_Anwser = correct_Anwser;
        this.QId = QId;
    }

    public Anwsers() {
    }

    @Override
    public String toString() {
        return "Anwsers{" +
                "id=" + id +
                ", Correct_Anwser='" + Correct_Anwser + '\'' +
                ", QId=" + QId +
                '}';
    }
}
