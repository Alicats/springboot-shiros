package cn.xej.common.my;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class CronDateUtils {

    private static final String CRON_DATE_FORMAT = "ss mm HH dd MM ? yyyy";

    // 传入date ， 返回 cron
    public static String getCron(final Date date){
        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        String formatTimeStr = "";
        if(date != null){
            formatTimeStr = sdf.format(date);
        }
        return formatTimeStr;
    }

    public static Date getDate(final String cron){
        if(cron == null){
            return null;
        }

        SimpleDateFormat sdf = new SimpleDateFormat(CRON_DATE_FORMAT);
        Date date = null;
        try {
            date = sdf.parse(cron);
        }catch (ParseException e){
            return null;
        }
        return date;

    }


}
