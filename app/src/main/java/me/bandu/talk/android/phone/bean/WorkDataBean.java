package me.bandu.talk.android.phone.bean;

import android.content.Context;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import me.bandu.talk.android.phone.utils.UserUtil;

/**
 * 创建者：高楠
 * 时间：on 2016/3/29
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class WorkDataBean implements Serializable{

    public static int NOMAL = 1;//正常情况
    public static int RESET = 2;//重置作业的情况
    public static int ADDING = 3;//添加作业的情况
    /***************数据传输****************/
    private String unitid;
    private String unitname;
    private String lesson_id;
    private String lesson_name;
    private String quiz_id;
    private String quiz_name;
    private String class_name;
    private List<ClassStudentEntity> selects;
    private List<Quiz> quizList;
    private String deadlinestr;
    private int total;
    private String level_str = "无最低要求";


    public String getLevel_str() {
        return level_str;
    }

    public void setLevel_str(String level_str) {
        this.level_str = level_str;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }

    public String getDeadlinestr() {
        return deadlinestr;
    }

    public void setDeadlinestr(String deadlinestr) {
        this.deadlinestr = deadlinestr;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public List<ClassStudentEntity> getSelects() {
        return selects;
    }

    public void setSelects(List<ClassStudentEntity> selects) {
        this.selects = selects;
    }

    public String getClass_name() {
        return class_name;
    }

    public void setClass_name(String class_name) {
        this.class_name = class_name;
    }

    public String getQuiz_id() {
        return quiz_id;
    }

    public void setQuiz_id(String quiz_id) {
        this.quiz_id = quiz_id;
    }

    public String getQuiz_name() {
        return quiz_name;
    }

    public void setQuiz_name(String quiz_name) {
        this.quiz_name = quiz_name;
    }

    public String getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(String lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public String getUnitid() {
        return unitid;
    }

    public void setUnitid(String unitid) {
        this.unitid = unitid;
    }

    public String getUnitname() {
        return unitname;
    }

    public void setUnitname(String unitname) {
        this.unitname = unitname;
    }

    /***************数据提交****************/
   private String uid;
   private String cid;
   private String book_id;
   private ArrayList<String> uids;
   private String title;
   private String score_level = "0";
   private String description;
   private String deadline;
   private List<Map<String,String>> content;
   private int STATUS = NOMAL;

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public String getCid() {
        return cid;
    }

    public void setCid(String cid) {
        this.cid = cid;
    }

    public String getBook_id() {
        return book_id;
    }

    public void setBook_id(String book_id) {
        this.book_id = book_id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        if (title==null){
            return;
        }
        if (this.title==null||this.title.equals("")){
            this.title = title;
            return;
        }
        if (this.title.contains(title)){
            return;
        }
        this.title = this.title+";"+title;
    }

    public String getScore_level() {
        return score_level;
    }

    public void setScore_level(String score_level) {
        this.score_level = score_level;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
    public List<Map<String, String>> getContent() {
        List<Map<String,String>> maps = new ArrayList<>();
        for(int i = 0 ;i<quizList.size();i++){
            Quiz qz = quizList.get(i);

            // 这种判断太局限，新题型的类型以后不止这八种
            // 解决 视频配音只能跟读，其他七种只能朗读
            String quizname = qz.getQuiz_name();
            if ("朗读文本".equals(quizname) || "角色朗读".equals(quizname) || "选读答案".equals(quizname )
                    || "情景问答".equals(quizname) || "英汉转换".equals(quizname) || "选词填空".equals(quizname)
                    || "人机问答".equals(quizname)){
                qz.setRead_count(1);    // 朗读
                qz.setRepeat_count(0);  // 跟读
            }

            if(qz.getRead_count()!=0){
                Map<String,String> map = new HashMap<>();
                map.put("quiz_id",qz.getQuiz_id());
                map.put("type","2");
                map.put("times",qz.getRead_count()+"");
                maps.add(map);
            }
            if(qz.isRecit()){
                Map<String,String> map = new HashMap<>();
                map.put("quiz_id",qz.getQuiz_id());
                map.put("type","1");
                map.put("times","1");
                maps.add(map);
            }
            if(qz.getRepeat_count()!=0){
                Map<String,String> map = new HashMap<>();
                map.put("quiz_id",qz.getQuiz_id());
                map.put("type","0");
                map.put("times",qz.getRepeat_count()+"");
                maps.add(map);
            }
        }
        return maps;
    }

    public void setContent(List<Map<String, String>> content) {
        this.content = content;
    }

    public ArrayList<String> getUids() {
        uids = new ArrayList<>();
        if (selects==null||(selects!=null&&selects.size()==0)){
            return uids;
        }
        for (int i = 0;i<selects.size();i++){
            if (selects.get(i).isSelected()){
                uids.add(selects.get(i).getUid());
            }
        }
        return uids;
    }

    public void setUids(ArrayList<String> uids) {
        this.uids = uids;
    }

    public void addContent(List<Map<String, String>> clist){
        if (clist==null||clist.size()==0)
            return;
        if (content==null){
            content = new ArrayList<>();
        }
        content.addAll(clist);
    }
    public Map getDataMap(){
        Map data = new HashMap();
        data.put("uid",uid);
        data.put("cid",cid);
        data.put("book_id",book_id);
        if (title!=null&&title.startsWith(";")){
            title = title.substring(title.indexOf(";")+1);
        }
        data.put("title",title);
        data.put("score_level",score_level);
        data.put("description",description);
        data.put("deadline",deadline);
        data.put("content",getContent());
        data.put("uids",getUids());
        return data;
    }

    public int getSTATUS() {
        return STATUS;
    }

    public void setSTATUS(int STATUS) {
        this.STATUS = STATUS;
    }
    /********************数据判断*******************/
    public boolean isBookIdEmpty(){
        if (book_id==null||(book_id!=null&&book_id.equals(""))||(book_id!=null&&book_id.equals("0"))){
            return true;
        }
        return false;
    }
    public String getUpdateMsg(){
        List<Map<String, String>> mapList = getContent();
        if (mapList.size()==0){
            return "请选择题目";
        }
        return null;
    }
    //只在预览页点击加号时调用
    public boolean isCurrentQuizOk(){
        if (quizList==null||quizList.size()==0){
            return true;
        }
        int currentposition = quizList.size()-1;
        Quiz quiz = quizList.get(currentposition);
        if (quiz.getRead_count()==0&&quiz.getRepeat_count()==0&&!quiz.isRecit()){
            return false;
        }
        return true;
    }
    /****************数据处理*****************/
    /**
     * 数据重置
     */
    public void restWork(){
        setTitle("");
        setScore_level("0");
        setDescription("");
        setDeadline("");
        setUnitid("");
        setUnitname("");
        setLesson_name("");
        setLesson_id("");
        setQuiz_id("");
        setQuiz_name("");
        setSelects(new ArrayList<ClassStudentEntity>());
        setQuizList(new ArrayList<Quiz>());
        setDeadlinestr("");
        setTotal(0);
        setLevel_str("无最低要求");
        setSTATUS(NOMAL);
    }
//    public void addUids(String uid) {
//        if (uids==null)
//            uids = new ArrayList<>();
//        uids.add(uid);
//    }
    public boolean addQuiz(Quiz quiz){
        if (quizList==null)
            quizList = new ArrayList<>();
        boolean flag = true;
        for (int i = 0 ; i< quizList.size();i++){
            if (quizList.get(i).getQuiz_id().equals(quiz.getQuiz_id())){
                flag = false;
                break;
            }
        }
        if (flag){
            quizList.add(quiz);
        }
        return flag;
    }

    public void setCurrentReadCount(int count){
        if (quizList==null||quizList.size()==0){
            return;
        }
        quizList.get(quizList.size()-1).setRead_count(count);
    }
    public void setCurrentRepeadCount(int count){
        if (quizList==null||quizList.size()==0){
            return;
        }
        quizList.get(quizList.size()-1).setRepeat_count(count);
    }
    public void setCurrentReciteFlag(boolean flag){
        if (quizList==null||quizList.size()==0){
            return;
        }
        quizList.get(quizList.size()-1).setRecit(flag);
    }
    public int getCurrentReadCount(){
        if (quizList==null||quizList.size()==0){
            return 1;
        }else {
            return quizList.get(quizList.size()-1).getRead_count();
        }
    }
    public int getCurrentRepeadCount(){
        if (quizList==null||quizList.size()==0){
            return 0;
        }else {
            return quizList.get(quizList.size()-1).getRepeat_count();
        }
    }
    public boolean getCurrentReciteFlag(){
        if (quizList==null||quizList.size()==0){
            return false;
        }else {
            return quizList.get(quizList.size()-1).isRecit();
        }
    }

    /**
     * 获取quizid 的数组
     * @return
     */
    public List<String> getQuizIds(){
        List<String> list = new ArrayList<>();
        if (quizList==null||quizList.size()==0)
            return list;
        for (int i = 0 ; i < quizList.size();i++){
            list.add(quizList.get(i).getQuiz_id());
        }
        return list;
    }
    /**
     * 将选择项转化成数组
     * @return
     */
    public void selectToArray() {
        uids = new ArrayList<>();
        if (selects==null||(selects!=null&&selects.size()==0)){
            return ;
        }
        for (int i = 0;i<selects.size();i++){
            if (selects.get(i).isSelected()){
                uids.add(selects.get(i).getUid());
            }
        }
    }
}
