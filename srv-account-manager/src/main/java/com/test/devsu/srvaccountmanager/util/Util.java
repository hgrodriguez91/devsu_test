package com.test.devsu.srvaccountmanager.util;

import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class Util {

    public static Timestamp getDateFromQueryParams(String date)  {
        try {
            Date tempDate =  new SimpleDateFormat("dd-MM-yyyy").parse(date);
            return new Timestamp(tempDate.getTime());
        }catch (ParseException e){
            return null;
        }
    }
}
