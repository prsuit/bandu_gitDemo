package me.bandu.talk.android.phone;

/**
 * 创建者：Mcablylx
 * 时间：2015/11/25 09:40
 * 类描述：全局常量类.因为是个interface,所以所有变量都是 public static final的
 * 修改人：
 * 修改时间：
 * 修改备注：
 */
public interface ConstantValue {


    String DBNAME = "my_bandu";//数据库名 todo 名字还没有找到，待定
    int SCHEMA_VERSION = 4;//数据库版本号


    /**
     * SharedPreferences 文件名
     */
    String SPCONFIG = "config";
    /**
     * 编码UTF8
     */
    String ENCODING = "UTF-8";
    /**
     * Base url
     */
//   String BASE_URL = "http://test.new.api.bandu.in/";//测试
//    String BASE_URL = "http://new.api.bandu.in/";//线上测试环境
    String BASE_URL = "http://new.api.bandu.cn/";//线上环境
//
    //分享相关
//    String BASE_SHARE_URL = "http://test.new.mobile.bandu.in/";//测试环境
//    String BASE_SHARE_URL = "http://mobile.bandu.in/";//线上测试环境
    String BASE_SHARE_URL = "http://mobile.bandu.cn/";//线上环境

    /**
     * 创建班级
     */
    String CREATE_CLASS = BASE_URL + "class/create";
    /**
     * 教师修改班级信息
     */
    String CLASS_MODIFY = BASE_URL + "class/modify";

    /**
     * 获取班级学生
     */
    String CLASS_STUDENT = BASE_URL + "class/member";
    /**
     * 删除班级
     */
    String DELETE_CLASS = BASE_URL + "class/delete";
    /**
     * 获取绑定教材信息
     */
    String TEXTBOOK_INFO = BASE_URL + "book/contentofbook";
    /**
     * 根据年级获取教材
     */
    String GRADE_TEXTBOOK = BASE_URL + "v3/book/listofgrade";
    /**
     * 获取待审核学生
     */
    //String VERIFY_STUDENTS = BASE_URL + "verifystudents";
    /**
     * 通过/拒绝学生
     */
    String ACCESS_REFUSE_STUDENTS = BASE_URL + "class/verifystudents";

    /**
     * 绑定教材
     */
    String BIND_TEXTBOOK = BASE_URL + "class/bindbook";
    /**
     * 教材中单元
     */
    String UNIT_BOOK = BASE_URL + "/book/unitsofbook";
    /**
     * 单元中的课
     */
    String LESSON_UNIT = BASE_URL + "/book/lessonsofunit";
    /**
     * 课中的题
     */
    String QUIZE_LESSON = BASE_URL + "/book/quizsoflesson";
    /**
     * 题中的句子
     */
    String SENTENCE_QUIZ = BASE_URL + "/book/sentencesofquiz";
    /**
     * 单元详情
     */
    String SELECT_UNIT = BASE_URL + "bindtextbook";
    /**
     * 班级转让
     */
    String CLASS_TRANS = BASE_URL + "class/transfer";
    /**
     * 查看作业
     */
    String SEE_WORK = BASE_URL + "homework/studentlist";
    /**
     * 检查作业
     */
    //String CHECK_WORK = BASE_URL + "homework/listofteacher";
    String CHECK_WORK = BASE_URL + "v3/homework/bigdata";
    /**
     * 删除作业
     */
    String DELET_WORK = BASE_URL + "homework/delete";
    /**
     * 获取手机验证码
     */
    String GET_CAPTCHA = BASE_URL + "sms/getcaptcha";
    /**
     * 注册账号
     */
    String REGISTER = BASE_URL + "user/register";
    /**
     * 用户登录
     */
    String LOGIN = BASE_URL + "user/login";
    /**
     * 忘记密码
     */
    String RESET_PASSWORD = BASE_URL + "user/resetpassword";
    /**
     * 修改手机号
     */
    String MODIFY_PHONE = BASE_URL + "user/modifyphone";
    /**
     * 修改个人信息
     */
    String MODIFY_USERINFO = BASE_URL + "user/modifyuserinfo";
    /**
     * 上传头像
     */
    String MODIFY_AVATAR = BASE_URL + "user/modifyavatar";
    /**
     * 地区检索
     */
    String GET_REGION = BASE_URL + "region/list";
    /**
     * 学校检索
     */
    String GET_SCHOOL = BASE_URL + "school/list";
    /**
     * 教师创建学校
     */
    String CREATE_SCHOOL = BASE_URL + "school/create";
    /**
     * 教师查看单个学生的作业
     */
    String DETAIL_OF_STUDENT = BASE_URL + "homework/detailofstudent";
    /**
     * 系统消息
     */
    String SYS_MSG = BASE_URL + "sysmsg/list";
    /**
     * 读消息
     */
    String READ_MSG = BASE_URL + "sysmsg/read";
    /**
     * 未读消息数量
     */
    String NO_READ_MSG = BASE_URL + "sysmsg/stat";
    /**
     * 意见反馈
     */
    String FEEDBACK = BASE_URL + "feedback/create";
    /**
     * 教师班级作业
     */
    String LIST_OF_TEACHER = BASE_URL + "class/listofteacher";
    /**
     * 通过班级编号和手机号查询班级信息
     */
    //String QUERY_CLASS = BASE_URL + "class/show";
    String QUERY_CLASS = BASE_URL + "class/query";
    /**
     * 通过班级编号查询班级信息
     */
    String JOIN_CLASS = BASE_URL + "class/join";
    /**
     * 学生作业列表
     */
    String LIST_HOMEWORK = BASE_URL + "v3/homework/listofstudent";
    /**
     * 学生作业目录
     */
    String HOMEWORK_CATLOG = BASE_URL + "homework/homeworkcatalog";
    /**
     * 练习下载列表
     */
    String EXERCISE_DOWNLOAD_LIST = BASE_URL + "book/contentofbook";
    /**
     * 练习下载详情
     */
    String EXERCISE_DOWNLOAD_INFO = BASE_URL + "book/contentofunit";
    /**
     * 练习选择教材
     */
    String EXERCISE_TEXTBOOK_CHOSE = BASE_URL + "book/listofexecise";
    /**
     * 学生加入班级
     */
    String ADD_CLASS = BASE_URL + "class/join";
    /**
     * 获取年级列表
     */
    String GET_GRADES = BASE_URL + "grade/list";
    /**
     * 布置作业
     */
    String CREAT_WORK = BASE_URL + "homework/create";
    /**
     * 作业统计
     */
    String WORK_STATIS = BASE_URL + "homework/stat";
    /**
     * 单个题统计
     */
    String SINGLE_STATIS = BASE_URL + "homework/statofquiz";
    /**
     * 布置作业
     */
    String WORK_CATALOG = BASE_URL + "homework/homeworkcatalog";
    /**
     * 评分引擎的切换data.engine 值为0 表示调用驰声
                    data.engine 值为1 表示调用其他接口
     */
    String CHOOSEENGINE = BASE_URL + "app/chooseengine";
    /**
     * 学生作业回放
     */
    String HOMEWORK_DETAIL = BASE_URL + "homework/detail";
    /**
     * 获取作业所属的单元和课
     */
    String UNIT_LESSON = BASE_URL + "homework/unitandlesson";
    /**
     * 学生完成作业
     */
    String FINISH_WORK = BASE_URL + "homework/done";
    /**
     * 根据班级编号查询班级信息
     */
    String CLASS_INFO = BASE_URL + "class/show";
    /**
     * 上传作业
     */
    String UP_LOAD = BASE_URL + "homework/upload";
    /**
     * 加入生词本
     */
    String CREATE_WORDS = BASE_URL + "word/create";
    /**
     * 生词本列表
     */
    String WORDS_LIST = BASE_URL + "word/list";
    /**
     * 删除生词本
     */
    String DELETE_WORDS = BASE_URL + "word/delete";
    /**
     * 上传作业
     */
    String START_PAGE = BASE_URL + "app/dailysentences";
    /**
     * 查询作业的待评价学生
     */
    String CHOSE_STUDENT_LIST = BASE_URL + "v3/homework/listofevaluate";
    /**
     * 提交作业评价
     */
    String EVALUATE = BASE_URL + "homework/evaluate";
    /**
     * 反馈标签
     */
    String FEED_BAVK_TAG = BASE_URL + "feedback/tags";
    /**
     * 获取学生教材类别
     */
    String STUDENT_TEXTBOOK_TYPES = BASE_URL + "book/category";
    /**
     * 获取学生教材类别
     */
    String WORKCONTENT_SENTENCE_QUIZ = BASE_URL + "book/sentencesofquizs";

    /**
     * 获取单词翻译
     */
    String GET_WORD = BASE_URL + "word/query";
    /**
     * 提交错误信息
     */
    String BOOK_ERRORSENTENCE = BASE_URL + "book/errorsentence";
    /**
     * 【批量上传作业-音频
     */
    String UPLOAD_ZIP = BASE_URL + "homework/uploadzip";
    /**
     * 【批量上传作业-文本
     */
    String UPLOAD_TEXT = BASE_URL + "homework/submit";
    /**
     * 【批量上传作业-文本 v3
     */
    String UPLOAD_TEXT_V3 = BASE_URL + "v3/homework/submit";
    /**
     * 获取KEY
     */
    String GET_KEY = BASE_URL + "app/getkey";

    //wx7760c1e4e08900f3  公司
    String WX_APP_ID = "wx7760c1e4e08900f3";
    //qq
    String QQ_APP_ID = "1103589987";
    //微博
    String WB_APP_ID = "2151666193";


    String YOUDAO = "http://fanyi.youdao.com/openapi.do";

    /**
     * 个人成长曲线
     */
    String MYSELF_PULLULATE_URL = BASE_URL + "v3/homework/record";
    /**
     * 第一次进入个人成长曲线
     */
    String FIRST_MYSELF_PULLULATE_URL = BASE_URL + "v3/homework/records";
    /**
     * 排行榜
     */
    String RANK_URL = BASE_URL + "v3/homework/ranks";
    /**
     * 作业统计(饼状体)
     */
    String STATISTICS_URL = BASE_URL + "v3/homework/statchart";
    /**
     * 老师页面进入学生曲线图
     */
    String SUDENT_PULLULATE_URL = BASE_URL + "...";
    /**
     * 各班统计
     */
    String STATISTICS_CLASS = BASE_URL + "v3/homework/statofclasses";
    /**
     * 获取玩一玩界面的前八个用户
     */
    String GET_USER = BASE_URL + "v3/execise/getgoodusers";

    /**
     * 获取玩一玩界面的随机用户
     */
    String GET_RANDOMUSER = BASE_URL + "v3/execise/getrandomusers";

    /**
     * 获取玩一玩界面用户的录音
     */
    String GET_USERRECORD = BASE_URL + "v3/execise/getvoices";


    /**
     * 练习录音上传
     */
    String RECORD_UPDATE = BASE_URL + "v3/execise/submit";
    /**
     * 名单页排序
     */
    String NAMELIST_SORT = BASE_URL + "v3/homework/statlist";
    /**
     * 全部完成的接口
     */
    String TOTAL_DONE = BASE_URL + "v3/homework/done";
    /**
     * 薄弱句子排序
     */
    String WEAK_RANK = BASE_URL + "v3/homework/weaksentencerank";


    /**
     * 获取学生班级
     */
    String GET_CLASSLIST = BASE_URL + "v3/class/listofstudent";

    /**
     * 取消申请班级
     */
    String CANCLE_CLASS = BASE_URL + "v3/class/canceljoin";
    /**
     * 删除学生
     */
    String DELETE_STUDENT = BASE_URL + "v3/class/deletestudents";
    /**
     * 21.	获取配音类型的试题MP4信息
     */
    String GETVIDEOOFQUIZ = BASE_URL + "v3/book/getvideoofquiz";
    /**
     * 46.	检查升级
     */
    String APPUPDATE = BASE_URL + "app/checkupdate";
}
