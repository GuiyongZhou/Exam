package com.cqu.exam.bean;

import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by Administrator on 2017/4/16.
 */
@Table(name = "Exam_Options")
public class Options {
    @Column(name = "id", isId = true)
    private int id;

    @Column(name = "Option_A")
    public String Option_A;

    @Column(name = "Option_B")
    public String Option_B;

    @Column(name = "Option_C")
    public String Option_C;

    @Column(name = "Option_D")
    public String Option_D;

    @Column(name = "Option_E")
    public String Option_E;

    @Column(name = "Option_F")
    public String Option_F;

    @Column(name = "Option_G")
    public String Option_G;

    @Column(name = "AnwserTip")
    public String AnwserTip;

    //如果是一对一加上唯一约束
    @Column(name = "QId" ,property = "UNIQUE")
    private long QId; // 外键表id

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getOption_A() {
        return Option_A;
    }

    public void setOption_A(String option_A) {
        Option_A = option_A;
    }

    public String getOption_B() {
        return Option_B;
    }

    public void setOption_B(String option_B) {
        Option_B = option_B;
    }

    public String getOption_C() {
        return Option_C;
    }

    public void setOption_C(String option_C) {
        Option_C = option_C;
    }

    public String getOption_D() {
        return Option_D;
    }

    public void setOption_D(String option_D) {
        Option_D = option_D;
    }

    public String getOption_E() {
        return Option_E;
    }

    public void setOption_E(String option_E) {
        Option_E = option_E;
    }

    public String getOption_F() {
        return Option_F;
    }

    public void setOption_F(String option_F) {
        Option_F = option_F;
    }

    public String getOption_G() {
        return Option_G;
    }

    public void setOption_G(String option_G) {
        Option_G = option_G;
    }

    public long getQId() {
        return QId;
    }

    public void setQId(long QId) {
        this.QId = QId;
    }

    public String getAnwserTip() {
        return AnwserTip;
    }

    public void setAnwserTip(String anwserTip) {
        AnwserTip = anwserTip;
    }

    public Options( String option_A, String option_B, String option_C, String option_D, String option_E, String option_F, String option_G, String anwserTip, long QId) {
        Option_A = option_A;
        Option_B = option_B;
        Option_C = option_C;
        Option_D = option_D;
        Option_E = option_E;
        Option_F = option_F;
        Option_G = option_G;
        AnwserTip = anwserTip;
        this.QId = QId;
    }

    public Options() {
    }

    @Override
    public String toString() {
        return "Options{" +
                "id=" + id +
                ", Option_A='" + Option_A + '\'' +
                ", Option_B='" + Option_B + '\'' +
                ", Option_C='" + Option_C + '\'' +
                ", Option_D='" + Option_D + '\'' +
                ", Option_E='" + Option_E + '\'' +
                ", Option_F='" + Option_F + '\'' +
                ", Option_G='" + Option_G + '\'' +
                ", AnwserTip='" + AnwserTip + '\'' +
                ", QId=" + QId +
                '}';
    }
}
