package com.DFHT.base;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 创建者：Mckiera
 * <p>时间：2016-08-24  10:27
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class AIRecorderDetails {
    @JSONField(name = "char")
    private String charStr;
    private long dur;
    private long end;
    private long start;
    private int fluency;
    private int indict;
    private int score;
    private int senseref;
    private int sensescore;
    private int stressref;
    private int stressscore;
    private int toneref;
    private int tonescore;
    public String getCharStr() {
        return charStr;
    }
    public void setCharStr(String charStr) {
        this.charStr = charStr;
    }
    public long getDur() {
        return dur;
    }
    public void setDur(long dur) {
        this.dur = dur;
    }
    public long getEnd() {
        return end;
    }
    public void setEnd(long end) {
        this.end = end;
    }
    public long getStart() {
        return start;
    }
    public void setStart(long start) {
        this.start = start;
    }
    public int getFluency() {
        return fluency;
    }
    public void setFluency(int fluency) {
        this.fluency = fluency;
    }
    public int getIndict() {
        return indict;
    }
    public void setIndict(int indict) {
        this.indict = indict;
    }
    public int getScore() {
        return score;
    }
    public void setScore(int score) {
        this.score = score;
    }
    public int getSenseref() {
        return senseref;
    }
    public void setSenseref(int senseref) {
        this.senseref = senseref;
    }
    public int getSensescore() {
        return sensescore;
    }
    public void setSensescore(int sensescore) {
        this.sensescore = sensescore;
    }
    public int getStressref() {
        return stressref;
    }
    public void setStressref(int stressref) {
        this.stressref = stressref;
    }
    public int getStressscore() {
        return stressscore;
    }
    public void setStressscore(int stressscore) {
        this.stressscore = stressscore;
    }
    public int getToneref() {
        return toneref;
    }
    public void setToneref(int toneref) {
        this.toneref = toneref;
    }
    public int getTonescore() {
        return tonescore;
    }
    public void setTonescore(int tonescore) {
        this.tonescore = tonescore;
    }
}
