package com.example;

import de.greenrobot.daogenerator.DaoGenerator;
import de.greenrobot.daogenerator.Entity;
import de.greenrobot.daogenerator.Property;
import de.greenrobot.daogenerator.Schema;

public class GreedDaoClass {
    public static void main(String args[]) throws Exception {
        Schema schema = new Schema(17, "me.bandu.talk.android.phone.db.bean");
        schema.setDefaultJavaPackageDao("me.bandu.talk.android.phone.db.dao");

        Entity entity = initExerciseUnitBean(schema);
        entity = initExerciseLessonBean(schema,entity);
        entity = initExercisePartBean(schema,entity);
        initExerciseCentenceBean(schema,entity);
        initDownloadInfoBean(schema);
        initWordBean(schema);
        //addWork(schema);
        //addQuize(schema);
        //addSentence(schema);

       // new DaoGenerator().generateAll(schema, "E:\\as\\newbandu\\app\\src\\main\\java");// 自动创建
        new DaoGenerator().generateAll(schema, "C:\\Users\\tg\\Desktop\\Add");// 自动创建
    }

    private static void initWordBean(Schema schema) {
        Entity myWrodBean = schema.addEntity("MyWrodBean");
        myWrodBean.addLongProperty("word_id").primaryKey();
        myWrodBean.addStringProperty("word_name");
        myWrodBean.addStringProperty("word_info");
    }

    private static void initDownloadInfoBean(Schema schema) {
        Entity downloadBean = schema.addEntity("DownloadBean");
        downloadBean.addLongProperty("download_id").primaryKey();
        downloadBean.addStringProperty("download_name");
        downloadBean.addIntProperty("download_state");
        downloadBean.addIntProperty("download_progress");
        downloadBean.addStringProperty("download_message");
        downloadBean.addIntProperty("download_subject");
        downloadBean.addIntProperty("download_category");
    }

    private static Entity initExerciseUnitBean(Schema schema) {
        Entity unitBean = schema.addEntity("UnitBean");
        unitBean.addLongProperty("unit_id").primaryKey();
        unitBean.addStringProperty("unit_name");
        unitBean.addIntProperty("textbook_id");
        unitBean.addStringProperty("textbook_name");
        unitBean.addIntProperty("textbook_subject");
        unitBean.addLongProperty("insert_time");
        unitBean.addIntProperty("category");
        return unitBean;
    }

    private static Entity initExerciseLessonBean(Schema schema,Entity unitBean) {
        Entity lessonBean = schema.addEntity("LessonBean");
        lessonBean.addLongProperty("lesson_id").primaryKey().index();
        lessonBean.addStringProperty("lesson_name");
        Property property = lessonBean.addLongProperty("unit_id").getProperty();
        lessonBean.addToOne(unitBean,property);
        lessonBean.addBooleanProperty("isadd");
        return lessonBean;
    }

    private static Entity initExercisePartBean(Schema schema,Entity lessonBean) {
        Entity partBean = schema.addEntity("PartBean");
        partBean.addLongProperty("part_id").primaryKey();
        partBean.addStringProperty("part_name");
        Property property = partBean.addLongProperty("lesson_id").getProperty();
        partBean.addToOne(lessonBean,property);
        partBean.addIntProperty("centence_start");
        partBean.addStringProperty("video_path");
        partBean.addStringProperty("type");
        return partBean;
    }

    private static void initExerciseCentenceBean(Schema schema,Entity partBean) {
        Entity centenceBean = schema.addEntity("CentenceBean");
        centenceBean.addLongProperty("centence_id").primaryKey();
        Property property = centenceBean.addLongProperty("part_id").getProperty();
        centenceBean.addToOne(partBean,property);
        centenceBean.addStringProperty("url_exemple");
        centenceBean.addStringProperty("url_best");
        centenceBean.addStringProperty("url_current");
        centenceBean.addStringProperty("english");
        centenceBean.addStringProperty("chines");
        centenceBean.addStringProperty("details");
        centenceBean.addIntProperty("seconds");
        centenceBean.addIntProperty("sorce_best");
        centenceBean.addIntProperty("sorce_current");
        centenceBean.addBooleanProperty("done");
        centenceBean.addIntProperty("video_time");
        centenceBean.addIntProperty("video_start");
        centenceBean.addIntProperty("video_end");
        centenceBean.addStringProperty("mp3_url"); //3.3 新加
    }

}
