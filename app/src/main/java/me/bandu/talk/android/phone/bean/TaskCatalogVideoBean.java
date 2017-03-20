package me.bandu.talk.android.phone.bean;

import java.io.Serializable;
import java.util.ArrayList;

/**
 * 创建者：wanglei
 * <p>时间：16/8/4  13:44
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class TaskCatalogVideoBean implements Serializable {
    /*
{
  status: 1,
  data: {
    mp4: "http://xxxxxxxx.mp4",
    sentence_list: [
      {
        sentence_id: 1,
        en: 'hello',
        mp4_start_time: 100,
        mp4_end_time: 6000
      }
    ]
  },
  msg: '成功'
}
 */

    public int status;

    public TaskCatalogVideoData data;
    public String msg;

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public TaskCatalogVideoData getData() {
        return data;
    }

    public void setData(TaskCatalogVideoData data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public static class TaskCatalogVideoData {
        public String mp4;
        public ArrayList<SentenceList> sentence_list;

        public String getMp4() {
            return mp4;
        }

        public void setMp4(String mp4) {
            this.mp4 = mp4;
        }

        public ArrayList<SentenceList> getSentence_list() {
            return sentence_list;
        }

        public void setSentence_list(ArrayList<SentenceList> sentence_list) {
            this.sentence_list = sentence_list;
        }

        public static class SentenceList {
            public int sentence_id;
            public String en;
            public int mp4_start_time;
            public int mp4_end_time;

            public int getSentence_id() {
                return sentence_id;
            }

            public void setSentence_id(int sentence_id) {
                this.sentence_id = sentence_id;
            }

            public String getEn() {
                return en;
            }

            public void setEn(String en) {
                this.en = en;
            }

            public int getMp4_start_time() {
                return mp4_start_time;
            }

            public void setMp4_start_time(int mp4_start_time) {
                this.mp4_start_time = mp4_start_time;
            }

            public int getMp4_end_time() {
                return mp4_end_time;
            }

            public void setMp4_end_time(int mp4_end_time) {
                this.mp4_end_time = mp4_end_time;
            }
        }
    }
}

