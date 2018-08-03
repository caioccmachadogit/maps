package base.imonitore.com.br.mapsapplication.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by ccouto on 23/01/2018.
 */

public class DateUtils {

    public static String dateNowStr() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    public static String formatyyyyMMdd(String data) {
        SimpleDateFormat sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf1.parse(data);

            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
            return sdf2.format(date);
        } catch (ParseException e) {
            e.printStackTrace();
            return dateNowStr();
        }
    }

    public static String formatyyyyMMddHHmmss(Date data) {
        if(data != null){
            SimpleDateFormat sdf2 = new SimpleDateFormat("yyyyMMddHHmmss");
            return sdf2.format(data);
        }
        return null;
    }

    public static String formatyyyyMMddHHmmss(Long value) {
        if(value != null){
            Date date = new Date(value);

            SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            return sdf.format(date);
        }
        return null;
    }

    public static Long convertDateToTimestamp(String data) {
        SimpleDateFormat sdf1;
        if (data != null) {
            sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            try {
                Date date = sdf1.parse(data);
                return date.getTime();
            }
            catch (ParseException e) {
                return null;
            }
        }
        return null;
    }

    public static Date convertddMMyyyyMaxMin(String data, boolean isMax) {
        SimpleDateFormat sdf1;
        if (isMax) {
            sdf1 = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            data = data + " 23:59:59";
        } else
            sdf1 = new SimpleDateFormat("dd/MM/yyyy");
        try {
            Date date = sdf1.parse(data);

            return date;
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static Date getDateNowMaxMin(boolean isMax) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(new Date());
        if (isMax) {
            cal.set(Calendar.HOUR_OF_DAY, 23);
            cal.set(Calendar.MINUTE, 59);
            cal.set(Calendar.SECOND, 59);
            cal.set(Calendar.MILLISECOND, 0);
        }
        else {
            cal.set(Calendar.HOUR_OF_DAY, 0);
            cal.set(Calendar.MINUTE, 0);
            cal.set(Calendar.SECOND, 0);
            cal.set(Calendar.MILLISECOND, 0);
        }
        return cal.getTime();
    }

    public static String convertHora(Date data) {
        SimpleDateFormat sdf1;
        sdf1 = new SimpleDateFormat("HH:mm:ss");

        String date = sdf1.format(data);
        return date;
    }

    public static Date getNowddMMyyyyHHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        String currentDate = sdf.format(new Date());

        try {
            return sdf.parse(currentDate);
        }
        catch (ParseException e) {
            return new Date();
        }
    }

    public static String getNowHHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("HH:mm:ss");
        String currentHora = sdf.format(new Date());

        return currentHora;
    }

    public static String getDataAgendamentoFixa() {
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
        String currentDate = sdf.format(new Date());
        return currentDate;
    }

    public static String getNowyyyyMMddHHmmss() {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        String currentDate = sdf.format(new Date());

        return currentDate;
    }

    public static Date convertyyyyMMddHHmmss(String data) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
        try {
            return sdf.parse(data);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }
}
