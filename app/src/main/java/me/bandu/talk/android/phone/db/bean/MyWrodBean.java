package me.bandu.talk.android.phone.db.bean;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "MY_WROD_BEAN".
 */
public class MyWrodBean {

    private Long word_id;
    private String word_name;
    private String word_info;

    public MyWrodBean() {
    }

    public MyWrodBean(Long word_id) {
        this.word_id = word_id;
    }

    public MyWrodBean(Long word_id, String word_name, String word_info) {
        this.word_id = word_id;
        this.word_name = word_name;
        this.word_info = word_info;
    }

    public Long getWord_id() {
        return word_id;
    }

    public void setWord_id(Long word_id) {
        this.word_id = word_id;
    }

    public String getWord_name() {
        return word_name;
    }

    public void setWord_name(String word_name) {
        this.word_name = word_name;
    }

    public String getWord_info() {
        return word_info;
    }

    public void setWord_info(String word_info) {
        this.word_info = word_info;
    }

}
