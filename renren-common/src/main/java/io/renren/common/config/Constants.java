package io.renren.common.config;

/**
 *
 * 系统参数
 * 保存系统相关参数
 * @author
 * @since
 */
public class Constants {
    //SESSION中KEY
    public static final String LOGINID="User";
    //发邮件用参数,host,邮箱,用户名,密码
    public static final String emailHost="smtp.ym.163.com";//"smtp.XXXX.net";
    public static final String emailFrom="mes@dyzv-bearing.com";//"info@XXXXXX.net";
    public static final String emailUsername="mes@dyzv-bearing.com";//"info@XXXXX.net";
    public static final String emailPassword="aa123654789";//"******";
    //token默认名
    public static final Object DEFAULT_TOKEN_NAME = "TOKEN";
    //无token默认页面
    public static final String DEFAULT_TOKEN_MSG_JSP = "notoken.jsp";
    //token值
    public static String TOKEN_VALUE;
    //REDIS地址
    public static String REDIS_ADDRESS="127.0.0.1";
    //保存文件根目录
    public static String FILE_ROOT="/files";
    //mongoDB地址
    public static String MONGODB_ADDR="172.16.0.137";
    //mongoDB用户名
    public static String MONGODB_USER="";
    //mongoDB密码
    public static String MONGODB_PASSWORD="";
    //APP_ID
    public static String appId="huawuilean20170517";
    //请求文件的URL前缀
    //change download url begin
    //public static final String QUERY_FILE_PATH = "http://42.202.130.58:17081/api/";
    //public static final String QUERY_FILE_PATH = "http://192.168.1.252:7081/api/";
    //public static final String QUERY_FILE_PATH = "http://192.168.1.251:7081/api/";
    //public static final String QUERY_FILE_PATH = "http://192.168.5.220:7081/api/";
    public static final String QUERY_FILE_PATH = "http://mes.dyzv-bearing.com:7086/api/";
    //public static final String QUERY_FILE_PATH = "http://mes.dyzv-bearing.com:7081/api/";
    public static final String basePath="/ilean-web/";
    //public static final String basePath="/huawu-web";
    public static String MONGODB_NAME="TEST";
    //mongoDB数据库名
    //public static String MONGODB_NAME="HUAWUILEAN";
    //change download url end
    /*ILEAN系统用的邮箱信息*/
    // 邮箱地址
//    public static final String MAIL_FROM_ADDRESS = "hwilean@hua-wu.com"/*"hwilean@hua-wu.com"*/;
//    // 密码
//    public static final String MAIL_FROM_PASSWORD = "hw123456"/*"hw12345"*/;//graufwivjsklcacj
//    // smtp主机
//    public static final String MAIL_SMTP_HOST = "mail.hua-wu.com"/*"218.95.73.54"*/;

    public static final String MAIL_FROM_ADDRESS = ""/*"hwilean@hua-wu.com"*/;
    // 密码
    public static final String MAIL_FROM_PASSWORD = ""/*"hw12345"*/;//graufwivjsklcacj
    // smtp主机
    public static final String MAIL_SMTP_HOST = ""/*"218.95.73.54"*/;
    // 发送邮件用户名
    public static final String MAIL_FROM_USER = "ilean系统";


    public final  static String Q_ROLE_FIGURE_IMG_SUFFIX = "QImage";

    public static StringBuilder getReqCommonRes(){
        StringBuilder builder = new StringBuilder();
        String minJs = "<script src='" + Constants.basePath + "static/js/jquery.min.js'></script>";
        //builder.append("<script src='/huawu-web/static/js/jquery.min.js'></script>");
        builder.append(minJs);
        String uiJs = "<script src='" + Constants.basePath + "static/js/jquery.ui.custom.js'></script>";
        //builder.append("<script src='/huawu-web/static/js/jquery.ui.custom.js'></script>");
        builder.append(uiJs);
        String alertJs = "<script src='" + Constants.basePath + "static/js/jquery.alerts.js'></script>";
        //builder.append("<script src='/huawu-web/static/js/jquery.alerts.js' type='text/javascript'></script>");
        builder.append(alertJs);
        String linkStyle = "<link rel='stylesheet' href='" + Constants.basePath + "static/css/jquery.alerts.css' type='text/css' />";
        //builder.append("<link rel='stylesheet' href='/huawu-web/static/css/jquery.alerts.css' type='text/css' />");
        builder.append(linkStyle);
        builder.append("<script type='text/javascript' charset='UTF-8'>");
        return builder;
    }
}
