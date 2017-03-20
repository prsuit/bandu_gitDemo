package me.bandu.talk.android.phone.bean;

import java.util.List;

/**
 * 创建者：wanglei
 * <p>时间：16/7/18  13:08
 * <p>类描述：
 * <p>修改人：
 * <p>修改时间：
 * <p>修改备注：
 */
public class CreatWorkContentBean1 {
    private String partName;
    private String type;
    private List<TaskSentenceEntity> list;

    public String getPartName() {
        return partName;
    }

    public void setPartName(String partName) {
        this.partName = partName;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public List<TaskSentenceEntity> getList() {
        return list;
    }

    public void setList(List<TaskSentenceEntity> list) {
        this.list = list;
    }

    public static class TaskSentenceEntity{
        private String text;
        private String map3;

        public String getText() {
            return text;
        }

        public void setText(String text) {
            this.text = text;
        }

        public String getMap3() {
            return map3;
        }

        public void setMap3(String map3) {
            this.map3 = map3;
        }
    }
}