package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class MyClass {
    public  static void main(String [] args) throws Exception {
        //1-- 2.6
        //2 --3.0
        //3-- 3.1
        //4 --3.1.2   3.2
        //5 -- 3.3
        //7 -- 3.3
        Schema schema = new Schema(7, "me.bandu.talk.android.phone.dao");
        Entity work = addWork(schema);
        Entity quize = addQuize(schema);
        addSentence(schema);
        addComment(schema);
        addWordQuize(schema,work,quize);
        new DaoGenerator().generateAll(schema,"C:\\Users\\Admin\\Desktop\\jajva");
    }

    private static void addWordQuize(Schema schema, Entity work, Entity quize) {
        Entity workQuize = schema.addEntity("WorkQuize");
        workQuize.addLongProperty("wq_id").primaryKey();
        Property property = workQuize.addLongProperty("stu_job_id").getProperty();
        workQuize.addToOne(work,property);
        Property property2 = workQuize.addLongProperty("quiz_id").getProperty();
        workQuize.addToOne(quize,property2);
    }

    private static Entity addWork(Schema schema){
        Entity note = schema.addEntity("HomeWork");
        note.addLongProperty("stu_job_id").notNull().primaryKey();
        note.addIntProperty("job_id");
        note.addStringProperty("job_status");
        note.addStringProperty("stu_job_status");
        note.addStringProperty("title");
        note.addStringProperty("cdate");
        note.addStringProperty("cday");
        note.addLongProperty("deadline");
        note.addIntProperty("percent");
        note.addBooleanProperty("isfirst");
        return note;
    }
    private static Entity addQuize(Schema schema){
        Entity note = schema.addEntity("Quiz");
//        note.addIdProperty().primaryKey().notNull();//3.3
        note.addLongProperty("quiz_id").notNull().primaryKey();//3.3
 //       note.addLongProperty("quiz_id");//3.3
        note.addIntProperty("score");
        note.addLongProperty("stu_job_id");
        note.addStringProperty("title");
        note.addStringProperty("description");
        note.addIntProperty("level");
        note.addStringProperty("commit_time");
        note.addStringProperty("spend_time");
        note.addStringProperty("quiz_name");
        note.addIntProperty("read_quiz_id");
        note.addIntProperty("read_times");
        note.addIntProperty("read_count");
        note.addIntProperty("repeat_quiz_id");
        note.addIntProperty("repeat_times");
        note.addIntProperty("repeat_count");
        note.addIntProperty("recite_quiz_id");
        note.addIntProperty("recite_times");
        note.addIntProperty("recite_count");
        note.addStringProperty("upload_spend_time");//3.3 上传服务器 累积时间。
        return note;
    }
    private static Entity addSentence(Schema schema){
        Entity note = schema.addEntity("Sentence");
        note.addIdProperty().notNull().primaryKey();
        note.addLongProperty("sentence_id");
        note.addLongProperty("hw_quiz_id");
        note.addLongProperty("stu_job_id");
        note.addLongProperty("quiz_id");
        note.addStringProperty("en");
        note.addStringProperty("mp3");
        note.addBooleanProperty("stu_done");
        note.addStringProperty("stu_mp3");
        note.addStringProperty("stu_score");
        note.addStringProperty("current_mp3");
        note.addIntProperty("current_score");
        note.addStringProperty("current_words_score");//3.0新加
        note.addStringProperty("words_score");
        note.addIntProperty("stu_seconds");//3.1新加
        note.addIntProperty("current_stu_seconds");//3.1新加
//        note.addStringProperty("zh");//3.1.2.1新加
        note.addStringProperty("mp3_url");//3.3新加
//        note.addStringProperty("score_detail");
//        note.addStringProperty("content");
//        note.addBooleanProperty("todo");
        return note;
    }
    private static void addComment(Schema schema){//3.1新加
        Entity note = schema.addEntity("Comment");
        note.addIdProperty().notNull().primaryKey();
        note.addStringProperty("comment").notNull();
        note.addStringProperty("name").notNull();
        note.addStringProperty("value1");
        note.addIntProperty("value2");
    }
}
