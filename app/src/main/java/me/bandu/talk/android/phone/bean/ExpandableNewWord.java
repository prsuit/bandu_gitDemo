package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * author by Mckiera
 * time: 2016/2/22  10:15
 * description:
 * updateTime:
 * update description:
 */
public class ExpandableNewWord {

    /**
     * list : [{"content":{"basic":{"explains":["n. 经验；经历；体验","vt. 经验；经历；体验"],"phonetic":"ɪk'spɪərɪəns; ek-"},"query":"experience","translation":["经验"]},"word":"experience","word_id":"206"},{"content":{"query":"Bandu","translation":["制度"]},"word":"Bandu","word_id":"205"},{"content":{"basic":{"explains":["n. 表示问候， 惊奇或唤起注意时的用语","n. (Hello)人名；(法)埃洛","int. 喂；哈罗"],"phonetic":"hə'ləʊ; he-"},"query":"Hello","translation":["你好"]},"word":"Hello","word_id":"204"},{"content":{"basic":{"explains":["pron. 我的","int. 哎呀（表示惊奇等）；喔唷","n. (My)人名；(越)美；(老、柬)米"],"phonetic":"maɪ"},"query":"my","translation":["我的"]},"word":"my","word_id":"203"},{"content":{"basic":{"explains":["n. 朋友；助手；赞助者","n. (Friend)人名；(英)弗兰德"],"phonetic":"frend"},"query":"friend","translation":["的朋友"]},"word":"friend","word_id":"202"},{"content":{"basic":{"explains":["n. 助手，助理，助教","adj. 辅助的，助理的；有帮助的"],"phonetic":"ə'sɪst(ə)nt"},"query":"assistant","translation":["助理"]},"word":"assistant","word_id":"201"}]
     * total : 6
     */

    private DataEntity data;
    /**
     * data : {"list":[{"content":{"basic":{"explains":["n. 经验；经历；体验","vt. 经验；经历；体验"],"phonetic":"ɪk'spɪərɪəns; ek-"},"query":"experience","translation":["经验"]},"word":"experience","word_id":"206"},{"content":{"query":"Bandu","translation":["制度"]},"word":"Bandu","word_id":"205"},{"content":{"basic":{"explains":["n. 表示问候， 惊奇或唤起注意时的用语","n. (Hello)人名；(法)埃洛","int. 喂；哈罗"],"phonetic":"hə'ləʊ; he-"},"query":"Hello","translation":["你好"]},"word":"Hello","word_id":"204"},{"content":{"basic":{"explains":["pron. 我的","int. 哎呀（表示惊奇等）；喔唷","n. (My)人名；(越)美；(老、柬)米"],"phonetic":"maɪ"},"query":"my","translation":["我的"]},"word":"my","word_id":"203"},{"content":{"basic":{"explains":["n. 朋友；助手；赞助者","n. (Friend)人名；(英)弗兰德"],"phonetic":"frend"},"query":"friend","translation":["的朋友"]},"word":"friend","word_id":"202"},{"content":{"basic":{"explains":["n. 助手，助理，助教","adj. 辅助的，助理的；有帮助的"],"phonetic":"ə'sɪst(ə)nt"},"query":"assistant","translation":["助理"]},"word":"assistant","word_id":"201"}],"total":6}
     * msg : 成功
     * status : 1
     */

    private String msg;
    private int status;

    public void setData(DataEntity data) {
        this.data = data;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public DataEntity getData() {
        return data;
    }

    public String getMsg() {
        return msg;
    }

    public int getStatus() {
        return status;
    }

    public static class DataEntity {
        private int total;
        /**
         * content : {"basic":{"explains":["n. 经验；经历；体验","vt. 经验；经历；体验"],"phonetic":"ɪk'spɪərɪəns; ek-"},"query":"experience","translation":["经验"]}
         * word : experience
         * word_id : 206
         */

        private List<ListEntity> list;

        public void setTotal(int total) {
            this.total = total;
        }

        public void setList(List<ListEntity> list) {
            this.list = list;
        }

        public int getTotal() {
            return total;
        }

        public List<ListEntity> getList() {
            return list;
        }

        public static class ListEntity {
            /**
             * basic : {"explains":["n. 经验；经历；体验","vt. 经验；经历；体验"],"phonetic":"ɪk'spɪərɪəns; ek-"}
             * query : experience
             * translation : ["经验"]
             */

            private ContentEntity content;
            private String word;
            private String word_id;
            private boolean isClick = false;

            public boolean isClick() {
                return isClick;
            }

            public void setClick(boolean click) {
                isClick = click;
            }

            public void setContent(ContentEntity content) {
                this.content = content;
            }

            public void setWord(String word) {
                this.word = word;
            }

            public void setWord_id(String word_id) {
                this.word_id = word_id;
            }

            public ContentEntity getContent() {
                return content;
            }

            public String getWord() {
                return word;
            }

            public String getWord_id() {
                return word_id;
            }

            public static class ContentEntity {
                /**
                 * explains : ["n. 经验；经历；体验","vt. 经验；经历；体验"]
                 * phonetic : ɪk'spɪərɪəns; ek-
                 */

                private BasicEntity basic;
                private String query;
                private List<String> translation;

                public void setBasic(BasicEntity basic) {
                    this.basic = basic;
                }

                public void setQuery(String query) {
                    this.query = query;
                }

                public void setTranslation(List<String> translation) {
                    this.translation = translation;
                }

                public BasicEntity getBasic() {
                    return basic;
                }

                public String getQuery() {
                    return query;
                }

                public List<String> getTranslation() {
                    return translation;
                }

                public static class BasicEntity {
                    private String phonetic;
                    private List<String> explains;

                    public void setPhonetic(String phonetic) {
                        this.phonetic = phonetic;
                    }

                    public void setExplains(List<String> explains) {
                        this.explains = explains;
                    }

                    public String getPhonetic() {
                        return phonetic;
                    }

                    public List<String> getExplains() {
                        return explains;
                    }
                }
            }
        }
    }
}
