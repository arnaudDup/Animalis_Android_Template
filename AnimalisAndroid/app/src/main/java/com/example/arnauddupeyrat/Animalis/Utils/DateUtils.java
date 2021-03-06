package com.example.arnauddupeyrat.Animalis.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by arnauddupeyrat on 17/07/16.
 */
public class DateUtils {

    public static final String SQLWithoutMinute = "yyyy-MM-dd";
    public static final String SQLWithMinute = "yyyy-MM-dd hh:mm:ss";

    /**
     * return String from date in wanted format.
     * @param dateToTransform
     * @return
     */
    public static String toDisplayFormat (Date dateToTransform){

        SimpleDateFormat formatter = new SimpleDateFormat(SQLWithoutMinute);
        String folderName = formatter.format(dateToTransform);
        return folderName;
    }

    public static String calculateAge (Date tempDate){
        int actualYear = new Date().getYear();
        int age = actualYear - tempDate.getYear();
        return ""+ age;
    }

}
