package me.bandu.talk.android.phone.db.bean;

import me.bandu.talk.android.phone.db.dao.DaoSession;
import de.greenrobot.dao.DaoException;

import me.bandu.talk.android.phone.db.dao.LessonBeanDao;
import me.bandu.talk.android.phone.db.dao.UnitBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "LESSON_BEAN".
 */
public class LessonBean {

    private Long lesson_id;
    private String lesson_name;
    private Long unit_id;
    private Boolean isadd;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient LessonBeanDao myDao;

    private UnitBean unitBean;
    private Long unitBean__resolvedKey;


    public LessonBean() {
    }

    public LessonBean(Long lesson_id) {
        this.lesson_id = lesson_id;
    }

    public LessonBean(Long lesson_id, String lesson_name, Long unit_id, Boolean isadd) {
        this.lesson_id = lesson_id;
        this.lesson_name = lesson_name;
        this.unit_id = unit_id;
        this.isadd = isadd;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getLessonBeanDao() : null;
    }

    public Long getLesson_id() {
        return lesson_id;
    }

    public void setLesson_id(Long lesson_id) {
        this.lesson_id = lesson_id;
    }

    public String getLesson_name() {
        return lesson_name;
    }

    public void setLesson_name(String lesson_name) {
        this.lesson_name = lesson_name;
    }

    public Long getUnit_id() {
        return unit_id;
    }

    public void setUnit_id(Long unit_id) {
        this.unit_id = unit_id;
    }

    public Boolean getIsadd() {
        return isadd;
    }

    public void setIsadd(Boolean isadd) {
        this.isadd = isadd;
    }

    /** To-one relationship, resolved on first access. */
    public UnitBean getUnitBean() {
        Long __key = this.unit_id;
        if (unitBean__resolvedKey == null || !unitBean__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            UnitBeanDao targetDao = daoSession.getUnitBeanDao();
            UnitBean unitBeanNew = targetDao.load(__key);
            synchronized (this) {
                unitBean = unitBeanNew;
            	unitBean__resolvedKey = __key;
            }
        }
        return unitBean;
    }

    public void setUnitBean(UnitBean unitBean) {
        synchronized (this) {
            this.unitBean = unitBean;
            unit_id = unitBean == null ? null : unitBean.getUnit_id();
            unitBean__resolvedKey = unit_id;
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
