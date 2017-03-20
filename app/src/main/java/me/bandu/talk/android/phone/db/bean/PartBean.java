package me.bandu.talk.android.phone.db.bean;

import me.bandu.talk.android.phone.db.dao.DaoSession;
import de.greenrobot.dao.DaoException;

import me.bandu.talk.android.phone.db.dao.LessonBeanDao;
import me.bandu.talk.android.phone.db.dao.PartBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "PART_BEAN".
 */
public class PartBean {

    private Long part_id;
    private String part_name;
    private Long lesson_id;
    private Integer centence_start;
    private String video_path;
    private String type;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient PartBeanDao myDao;

    private LessonBean lessonBean;
    private Long lessonBean__resolvedKey;


    public PartBean() {
    }

    public PartBean(Long part_id) {
        this.part_id = part_id;
    }

    public PartBean(Long part_id, String part_name, Long lesson_id, Integer centence_start, String video_path, String type) {
        this.part_id = part_id;
        this.part_name = part_name;
        this.lesson_id = lesson_id;
        this.centence_start = centence_start;
        this.video_path = video_path;
        this.type = type;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getPartBeanDao() : null;
    }

    public Long getPart_id() {
        return part_id;
    }

    public void setPart_id(Long part_id) {
        this.part_id = part_id;
    }

    public String getPart_name() {
        return part_name;
    }

    public void setPart_name(String part_name) {
        this.part_name = part_name;
    }

    public Long getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(Long lesson_id) {
        this.lesson_id = lesson_id;
    }

    public Integer getCentence_start() {
        return centence_start;
    }

    public void setCentence_start(Integer centence_start) {
        this.centence_start = centence_start;
    }

    public String getVideo_path() {
        return video_path;
    }

    public void setVideo_path(String video_path) {
        this.video_path = video_path;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    /** To-one relationship, resolved on first access. */
    public LessonBean getLessonBean() {
        Long __key = this.lesson_id;
        if (lessonBean__resolvedKey == null || !lessonBean__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            LessonBeanDao targetDao = daoSession.getLessonBeanDao();
            LessonBean lessonBeanNew = targetDao.load(__key);
            synchronized (this) {
                lessonBean = lessonBeanNew;
            	lessonBean__resolvedKey = __key;
            }
        }
        return lessonBean;
    }

    public void setLessonBean(LessonBean lessonBean) {
        synchronized (this) {
            this.lessonBean = lessonBean;
            lesson_id = lessonBean == null ? null : lessonBean.getLesson_id();
            lessonBean__resolvedKey = lesson_id;
        }
    }

    /** Convenient call for {@link AbstractDao#delete(Object)}. Entity must attached to an entity context. */
    public void delete() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.delete(this);
    }

    /** Convenient call for {@link AbstractDao#update(Object)}. Entity must attached to an entity context. */
    public void update() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.update(this);
    }

    /** Convenient call for {@link AbstractDao#refresh(Object)}. Entity must attached to an entity context. */
    public void refresh() {
        if (myDao == null) {
            throw new DaoException("Entity is detached from DAO context");
        }    
        myDao.refresh(this);
    }

}
