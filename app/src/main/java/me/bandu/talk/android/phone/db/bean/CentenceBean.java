package me.bandu.talk.android.phone.db.bean;

import me.bandu.talk.android.phone.db.dao.DaoSession;
import de.greenrobot.dao.DaoException;

import me.bandu.talk.android.phone.db.dao.CentenceBeanDao;
import me.bandu.talk.android.phone.db.dao.PartBeanDao;

// THIS CODE IS GENERATED BY greenDAO, DO NOT EDIT. Enable "keep" sections if you want to edit. 
/**
 * Entity mapped to table "CENTENCE_BEAN".
 */
public class CentenceBean {

    private Long centence_id;
    private Long part_id;
    private String url_exemple;
    private String url_best;
    private String url_current;
    private String english;
    private String chines;
    private String details;
    private Integer seconds;
    private Integer sorce_best;
    private Integer sorce_current;
    private Boolean done;
    private Integer video_time;
    private Integer video_start;
    private Integer video_end;
    private String mp3_url;

    /** Used to resolve relations */
    private transient DaoSession daoSession;

    /** Used for active entity operations. */
    private transient CentenceBeanDao myDao;

    private PartBean partBean;
    private Long partBean__resolvedKey;


    public CentenceBean() {
    }

    public CentenceBean(Long centence_id) {
        this.centence_id = centence_id;
    }

    public CentenceBean(Long centence_id, Long part_id, String url_exemple, String url_best, String url_current, String english, String chines, String details, Integer seconds, Integer sorce_best, Integer sorce_current, Boolean done, Integer video_time, Integer video_start, Integer video_end, String mp3_url) {
        this.centence_id = centence_id;
        this.part_id = part_id;
        this.url_exemple = url_exemple;
        this.url_best = url_best;
        this.url_current = url_current;
        this.english = english;
        this.chines = chines;
        this.details = details;
        this.seconds = seconds;
        this.sorce_best = sorce_best;
        this.sorce_current = sorce_current;
        this.done = done;
        this.video_time = video_time;
        this.video_start = video_start;
        this.video_end = video_end;
        this.mp3_url = mp3_url;
    }

    /** called by internal mechanisms, do not call yourself. */
    public void __setDaoSession(DaoSession daoSession) {
        this.daoSession = daoSession;
        myDao = daoSession != null ? daoSession.getCentenceBeanDao() : null;
    }

    public Long getCentence_id() {
        return centence_id;
    }

    public void setCentence_id(Long centence_id) {
        this.centence_id = centence_id;
    }

    public Long getPart_id() {
        return part_id;
    }

    public void setPart_id(Long part_id) {
        this.part_id = part_id;
    }

    public String getUrl_exemple() {
        return url_exemple;
    }

    public void setUrl_exemple(String url_exemple) {
        this.url_exemple = url_exemple;
    }

    public String getUrl_best() {
        return url_best;
    }

    public void setUrl_best(String url_best) {
        this.url_best = url_best;
    }

    public String getUrl_current() {
        return url_current;
    }

    public void setUrl_current(String url_current) {
        this.url_current = url_current;
    }

    public String getEnglish() {
        return english;
    }

    public void setEnglish(String english) {
        this.english = english;
    }

    public String getChines() {
        return chines;
    }

    public void setChines(String chines) {
        this.chines = chines;
    }

    public String getDetails() {
        return details;
    }

    public void setDetails(String details) {
        this.details = details;
    }

    public Integer getSeconds() {
        return seconds;
    }

    public void setSeconds(Integer seconds) {
        this.seconds = seconds;
    }

    public Integer getSorce_best() {
        return sorce_best;
    }

    public void setSorce_best(Integer sorce_best) {
        this.sorce_best = sorce_best;
    }

    public Integer getSorce_current() {
        return sorce_current;
    }

    public void setSorce_current(Integer sorce_current) {
        this.sorce_current = sorce_current;
    }

    public Boolean getDone() {
        return done;
    }

    public void setDone(Boolean done) {
        this.done = done;
    }

    public Integer getVideo_time() {
        return video_time;
    }

    public void setVideo_time(Integer video_time) {
        this.video_time = video_time;
    }

    public Integer getVideo_start() {
        return video_start;
    }

    public void setVideo_start(Integer video_start) {
        this.video_start = video_start;
    }

    public Integer getVideo_end() {
        return video_end;
    }

    public void setVideo_end(Integer video_end) {
        this.video_end = video_end;
    }

    public String getMp3_url() {
        return mp3_url;
    }

    public void setMp3_url(String mp3_url) {
        this.mp3_url = mp3_url;
    }

    /** To-one relationship, resolved on first access. */
    public PartBean getPartBean() {
        Long __key = this.part_id;
        if (partBean__resolvedKey == null || !partBean__resolvedKey.equals(__key)) {
            if (daoSession == null) {
                throw new DaoException("Entity is detached from DAO context");
            }
            PartBeanDao targetDao = daoSession.getPartBeanDao();
            PartBean partBeanNew = targetDao.load(__key);
            synchronized (this) {
                partBean = partBeanNew;
            	partBean__resolvedKey = __key;
            }
        }
        return partBean;
    }

    public void setPartBean(PartBean partBean) {
        synchronized (this) {
            this.partBean = partBean;
            part_id = partBean == null ? null : partBean.getPart_id();
            partBean__resolvedKey = part_id;
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
