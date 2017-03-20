package me.bandu.talk.android.phone.utils;

import java.util.List;

import me.bandu.talk.android.phone.bean.HomeWorkBean;
import me.bandu.talk.android.phone.dao.DaoListenter;
import me.bandu.talk.android.phone.dao.DaoUtils;
import me.bandu.talk.android.phone.dao.HomeWork;

/**
 * 创建者：高楠
 * 时间：on 2016/4/21
 * 类描述：
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public class StudentWorkUtils {
    static StudentWorkUtils utils;
    private StudentWorkUtils(){}
    public static StudentWorkUtils getInstance(){
        if (utils==null){
            utils = new StudentWorkUtils();
        }
        return utils;
    }
    public DaoListenter listenter;

    public static final int GET_HOMEWORK_DATA = 100;
    public void getHomework(final DaoListenter listenter, final HomeWorkBean dataFromDb, final HomeWorkBean dataFromNet){
        this.listenter = listenter;
        new Thread(new Runnable() {
            @Override
            public void run() {
                HomeWorkBean data_doing = null;
                if (DaoUtils.getInstance().isEmpty(dataFromDb)) {
                    data_doing = dataFromNet;
                } else {
//                    data_doing = DaoUtils.getInstance().mixtureDbAndDao(dataFromNet);
                }
                listenter.dbResult(GET_HOMEWORK_DATA,data_doing);
            }
        }).start();

    }
    public static final int GET_DB_DATA = 200;
    public static final int GET_DB_DATA_FAILE = 300;
    public void getDBData(final int request, final DaoListenter listenter){
        this.listenter = listenter;
        new Thread(new Runnable() {
            @Override
            public void run() {
                List<HomeWork> list = null;
                HomeWorkBean data = null;
                try {
                    list = DaoUtils.getInstance().getHomeWorkList();
                    for (int i = 0 ; i < list.size();){
                        HomeWork homeWork = list.get(i);
                        Long deadline = homeWork.getDeadline()*1000;
                        if (deadline<=System.currentTimeMillis()){
                            DaoUtils.getInstance().deleteHomework(homeWork);
                            list.remove(i);
                        }else {
                            i++;
                        }
                    }
                }catch (Exception e){
                    e.printStackTrace();
                }
                if (list!=null){
                    data = DaoUtils.getInstance().DaoToBean(list);
                }
                listenter.dbResult(request,data);
            }
        }).start();
    }


}
