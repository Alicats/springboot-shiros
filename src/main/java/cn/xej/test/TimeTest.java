package cn.xej.test;


import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.regex.Pattern;

public class TimeTest {

    public final static String FORMAT_STRING = "yyyy-MM-dd HH:mm:ss";

    public final static String[] REPLACE_STRING = new String[]{"GMT+0800", "GMT+08:00"};

    public final static String SPLIT_STRING = "(中国标准时间)";

    public static Date str2Date(String dateString) {
        try {
            dateString = dateString.split(Pattern.quote(SPLIT_STRING))[0].replace(REPLACE_STRING[0], REPLACE_STRING[1]);
            SimpleDateFormat sf1 = new SimpleDateFormat("E MMM dd yyyy HH:mm:ss z", Locale.US);
            Date date = sf1.parse(dateString);
            return date;
        } catch (Exception e) {
            throw new RuntimeException("时间转化格式错误" + "[dateString=" + dateString + "]" + "[FORMAT_STRING=" + FORMAT_STRING + "]");
        }
    }


    public static void main(String[] args) throws ParseException {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        GregorianCalendar calendar = new GregorianCalendar();
        calendar.setTime(format.parse("2020-05-28"));
        //calendar.add(Calendar.DAY_OF_YEAR, 1);

        Date date = calendar.getTime();
        System.out.println(date);

        /*
        //获得一个时间格式的字符串
        String dateStr = "2020-05-28";
        //获得SimpleDateFormat类，我们转换为yyyy-MM-dd的时间格式
        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd");
        try {
            //使用SimpleDateFormat的parse()方法生成Date
            Date date = sf.parse(dateStr);
            //打印Date
            System.out.println(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
*/
//        Date date = str2Date("Tue Aug 21 2018 00:00:00 GMT+0800 (中国标准时间) 00:00:00");
//        System.out.println(date);

        /*
        Date now = new Date();
        System.out.println("cron "+CronDateUtils.getCron(now));

        String cron = "20 28 17 02 08 ? 2016";
        Date cronDate = CronDateUtils.getDate(cron);
        System.out.println("dateToString "+cronDate.toString());
        System.out.println("date "+cronDate);
        */
        /*
        String date = "2020-05-28 13:49:45";
        SimpleDateFormat sdf = new SimpleDateFormat("YYYY-MM-DD HH:MM:ss");
        Date date1 = sdf.parse(date);
        System.out.println(date1.toString());
        */
        /*
        SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd");
        String dstr="2008-4-24";
        Date date=sdf.parse(dstr);
        System.out.println(date.toString());*/
    }
}
