package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2016/2/19 14:53
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WordBean {

    /**
     * us-phonetic : wɑt
     * phonetic : wɒt
     * uk-phonetic : wɒt
     * explains : ["pron. 什么；多么；多少","adv. 到什么程度，在哪一方面","adj. 什么；多么；何等","int. 什么；多么"]
     */

    private BasicEntity basic;

    public BasicEntity getBasic() {
        return basic;
    }

    public void setBasic(BasicEntity basic) {
        this.basic = basic;
    }

    public static class BasicEntity{
        private List<String> explains;
        private String phonetic;
        private String uk_phonetic;
        private String us_phonetic;

        public List<String> getExplains() {
            return explains;
        }

        public void setExplains(List<String> explains) {
            this.explains = explains;
        }

        public String getPhonetic() {
            return phonetic;
        }

        public void setPhonetic(String phonetic) {
            this.phonetic = phonetic;
        }

        public String getUk_phonetic() {
            return uk_phonetic;
        }

        public void setUk_phonetic(String uk_phonetic) {
            this.uk_phonetic = uk_phonetic;
        }

        public String getUs_phonetic() {
            return us_phonetic;
        }

        public void setUs_phonetic(String us_phonetic) {
            this.us_phonetic = us_phonetic;
        }
    }
    /**
     * translation : ["什么"]
     * query : what
     * errorCode : 0
     * web : [{"value":["什么","做什么","是什么"],"key":"what"},{"value":["那又怎样","撒小乐团","呛声"],"key":"so what"},{"value":["说了什么","说什么","传播内容"],"key":"Says What"}]
     */

    private String query;
    private List<String> translation;
    /**
     * value : ["什么","做什么","是什么"]
     * key : what
     */
    public void setQuery(String query) {
        this.query = query;
    }

    public void setTranslation(List<String> translation) {
        this.translation = translation;
    }

    public String getQuery() {
        return query;
    }

    public List<String> getTranslation() {
        return translation;
    }
}
