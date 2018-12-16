package io.renren.common.utils;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import java.util.TimeZone;

    /**
     *
     * 实用工具类
     * 一些简单的调用方法,验证字符串为空,日期格式转换等.
     * @author     黄金
     * @since      1.0
     */
    public class Tools {
        /**
         * 检测字符串是否不为空(null,"","null")
         *
         * @param s
         * @return 不为空则返回true，否则返回false
         */
        public static boolean notEmpty(String s)
        {
            return s != null && !"".equals(s) && !"null".equals(s);
        }
        /**
         * 检测字符串是否不为空(null,"","null")
         *
         * @param s
         * @return 不为空则返回String.trim()
         */
        public static String objToStr(String s)
        {
            return (s == null || "".equals(s) || "null".equals(s))?"":s.trim();
        }
        /**
         * 检测字符串是否为空(null,"","null")
         *
         * @param s
         * @return 为空则返回true，不否则返回false
         */
        public static boolean isEmpty(String s)
        {
            return s == null || "".equals(s) || "null".equals(s);
        }

        /**
         * 字符串转换为字符串数组
         *
         * @param str
         *            字符串
         * @param splitRegex
         *            分隔符
         * @return
         */
        public static String[] str2StrArray(String str, String splitRegex)
        {
            if (isEmpty(str))
            {
                return null;
            }
            return str.split(splitRegex);
        }

        /**
         * 用默认的分隔符(,)将字符串转换为字符串数组
         *
         * @param str
         *            字符串
         * @return
         */
        public static String[] str2StrArray(String str)
        {
            return str2StrArray(str, ",\\s*");
        }

        /**
         * 按照yyyy-MM-dd HH:mm:ss的格式，日期转字符串
         *
         * @param date
         * @return yyyy-MM-dd HH:mm:ss
         */
        public static String date2Str(Date date)
        {
            return date2Str(date, "yyyy-MM-dd HH:mm:ss");
        }

        /**
         * 按照yyyy-MM-dd HH:mm:ss的格式，字符串转日期
         *
         * @param date
         * @return
         */
        public static Date str2Date(String date)
        {
            if (notEmpty(date))
            {
                SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                try
                {
                    return sdf.parse(date);
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
                return new Date();
            } else
            {
                return null;
            }
        }

        /**
         * 按照参数format的格式，日期转字符串
         *
         * @param date
         * @param format
         * @return
         */
        public static String date2Str(Date date, String format)
        {
            if (date != null)
            {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                return sdf.format(date);
            } else
            {
                return "";
            }
        }
        /**
         * 为空转另一个字符串
         *
         * @param date
         * @param format
         * @return
         */
        public static String ifnull(String checkStr, String outStr)
        {
            return isEmpty(checkStr)?outStr:checkStr;
        }
        /**
         *
         * MD5方法 生成MD5字符串
         *
         * @param password 原字符串
         * @return md5加密后的字符串
         * @since 1.0
         */
        public static String makeMD5(String password) {
            return encrypByMD5(password);
        }

        public static String encrypByMD5(String originString) {
            if (originString != null) {
                try {
                    MessageDigest md = MessageDigest.getInstance("MD5");
                    byte[] results = md.digest(originString.getBytes());
                    String resultString = byteArrayToHex(results);
                    return resultString;
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
            return null;
        }

        private static String byteArrayToHex(byte[] b) {
            StringBuffer resultSb = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                resultSb.append(byteToHex(b[i]));
            }
            return resultSb.toString();
        }

        private static String byteToHex(byte b) {
            int n = b;
            if (n < 0)
                n = 256 + n;
            int d1 = n / 16;
            int d2 = n % 16;
            return hexArray[d1] + hexArray[d2];
        }

        private final static String[] hexArray = {"0", "1", "2", "3", "4", "5", "6", "7", "8", "9", "a",
                "b", "c", "d", "e", "f"};
        public static void main(String[] args) {
            //System.out.println(makeMD5(1331215646+"ilean5874269"));
            System.out.println(makeMD5("huawu2017"));
        }

        // add by wei.feng 2018/01/05 begin
        /**
         * <p>以utf-8解码</p>
         * <p>将传入的原字符串进行解码</p>
         * @return 解码后的字符串
         * @throws java.io.UnsupportedEncodingException
         */
        public static String decode(String original) throws UnsupportedEncodingException {
            if (original != null) {
                return URLDecoder.decode(original, "utf-8");
            }
            return "";
        }
        // add by wei.feng 2018/01/05 end

        public static String utc2Local(String utcTime, String utcTimePatten, String localTimePatten) {
            SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
            utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));//时区定义并进行时间获取
            Date gpsUTCDate = null;
            try {
                gpsUTCDate = utcFormater.parse(utcTime);
            } catch (ParseException e) {
                e.printStackTrace();
                return utcTime;
            }
            SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
            localFormater.setTimeZone(TimeZone.getDefault());
            String localTime = localFormater.format(gpsUTCDate.getTime());
            return localTime;
        }

        public static boolean isListDuplicate(List<String> list){
            Set<String> checkSet = new HashSet<String>();
            for (String bObj : list){
                checkSet.add(bObj);
            }

            if (checkSet.size() != list.size()){
                return true;
            }

            return false;
        }

        public static String getEncodeName(String fileName){
            String encodeName = "";
            try{
                encodeName = URLEncoder.encode(fileName, "UTF-8");
            }catch (Exception ex){

            }
            return encodeName;
        }

        public static String getFileUrl(String basePath, String figureId, String fileName){
            return basePath + "opt/queryFiles?figureId=" + figureId + "&figureLongDesc=" + getEncodeName(fileName);
        }

        // add by linhai 2018/11/29 start
        /**
         * 按照指定的格式，字符串转日期
         *
         * @param date
         * @param format
         * @return
         */
        public static Date str2FormatDate(String date, String format)
        {
            if (notEmpty(date))
            {
                SimpleDateFormat sdf = new SimpleDateFormat(format);
                try
                {
                    return sdf.parse(date);
                } catch (ParseException e)
                {
                    e.printStackTrace();
                }
                return new Date();
            } else
            {
                return null;
            }
        }
        // add by linhai 2018/11/29 end
    }

