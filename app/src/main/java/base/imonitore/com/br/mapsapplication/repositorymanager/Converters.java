package base.imonitore.com.br.mapsapplication.repositorymanager;

import android.arch.persistence.room.TypeConverter;

import java.util.Date;

/**
 * Created by ccouto on 24/01/2018.
 */

public class Converters {

    @TypeConverter
    public static Date fromTimestamp(Long value) {
        return value == null ? null : new Date(value);
    }

    @TypeConverter
    public static Long dateToTimestamp(Date date) {
        return date == null ? null : date.getTime();
    }

//    @TypeConverter
//    public static Integer statusMidiaEnum(StatusMidiaEnum valorEnum) {
//        return valorEnum == null ? null : valorEnum.ordinal();
//    }
//
//    @TypeConverter
//    public static StatusMidiaEnum fromStatusMidiaEnum(Integer valorEnum) {
//        return valorEnum == null ? null : StatusMidiaEnum.values()[valorEnum];
//    }
}
