package com.example.ernestoojea.app2;

import java.sql.Time;
import java.util.Date;

/**
 * Created by ernesto.ojea on 4/2/2019.
 */

public class TimeCount {
    public static String toLongTime(long m){
        if(m<60)return m+"segundo"+(m>1?"s ":" ") ;
        if (m<3600){
            return m/60+"minuto" +(m/60>1?"s ":" ")+ TimeCount.toLongTime(m%60);
        }
        if(m<86400){
            return m/3600+"hora"+(m/3600>1?"s ":" ")+TimeCount.toLongTime(m%3600);
        }
        return m/86400+"dia"+(m/86400>1?"s ":" ") + TimeCount.toLongTime(m%86400);
    }
    public static String toShortTime(long m){
        if(m<60)return m+"s";
        if (m<3600){
            return m/60+"m" + TimeCount.toLongTime(m%60);
        }
        if(m<86400){
            return m/3600+"h"+TimeCount.toLongTime(m%3600);
        }
        return m/86400+"d"+ TimeCount.toLongTime(m%86400);
    }


}
