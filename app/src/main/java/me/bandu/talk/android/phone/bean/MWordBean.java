package me.bandu.talk.android.phone.bean;

import com.DFHT.base.BaseBean;

import java.util.List;

/**
 * 创建者：gaoye
 * 时间：2016/3/21 15:40
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class MWordBean extends BaseBean{

    /**
     * status : 1
     * data : {"basic":{"explains":["n. [语] 单词；话语；消息；诺言；命令","vt. 用言辞表达","n. (Word)人名；(英)沃德"],"phonetic":"wɜːd"},"query":"word","translation":["词"]}
     * msg : 成功
     */

    /**
     * basic : {"explains":["n. [语] 单词；话语；消息；诺言；命令","vt. 用言辞表达","n. (Word)人名；(英)沃德"],"phonetic":"wɜːd"}
     * query : word
     * translation : ["词"]
     */
    private WordBean data;


    public void setData(WordBean data) {
        this.data = data;
    }

    public WordBean getData() {
        return data;
    }

}
