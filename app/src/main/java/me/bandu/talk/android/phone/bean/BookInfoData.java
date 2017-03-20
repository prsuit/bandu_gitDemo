package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * Created by GaoNan on 2015/11/25.
 */
public class BookInfoData {
    private String book_name;//"人教版三年级上册"
    private List<BookInfoEntity> unit_list;
    private String cover;//"http://www.bandu.cn/cover/1.jpg"
    private int status; //教材状态 1正常0已下架
    private int type_code; // 教材的类型 0 普通教材，2是新题型

    public int getType_code(){
        return  type_code;
    }
    public int getStatus() {
        return status;
    }
    public void setType_code(int type_code){
        this.type_code = type_code;
    }
    public void setStatus(int status) {
        this.status = status;
    }

    public String getBook_name() {
        return book_name;
    }

    public void setBook_name(String book_name) {
        this.book_name = book_name;
    }

    public List<BookInfoEntity> getUnit_list() {
        return unit_list;
    }

    public void setUnit_list(List<BookInfoEntity> unit_list) {
        this.unit_list = unit_list;
    }

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }


    @Override
    public String toString() {
        return "BookInfoData{" +
                "book_name='" + book_name + '\'' +
                ", unit_list=" + unit_list +
                ", cover='" + cover + '\'' +
                ", status=" + status +
                ", type_code=" + type_code +
                '}';
    }
}
