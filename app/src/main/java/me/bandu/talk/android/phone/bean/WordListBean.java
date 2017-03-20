package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2016/2/19 14:18
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WordListBean extends BaseBean {

    private DataEntity data;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public DataEntity getData() {
        return data;
    }

    public static class DataEntity {
        /**
         * word_id : 1
         * word : hello
         * content : {}
         */

        private List<ListEntity> list;

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            private int word_id;
            private String word;
            private WordBean content;

            public void setWord_id(int word_id) {
                this.word_id = word_id;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public void setContent(WordBean content) {
                this.content = content;
            }

            public int getWord_id() {
                return word_id;
            }

            public String getWord() {
                return word;
            }

            public WordBean getContent() {
                return content;
            }
        }
    }
}
