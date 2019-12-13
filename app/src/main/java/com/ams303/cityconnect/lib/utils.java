package com.ams303.cityconnect.lib;

import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class utils {

    public static String getFormattedPrice(double value){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(value) + "€";
    }

    public static String getPrettyTimestamp(String timestamp) {
        DateFormat df1 = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try {
            date = df1.parse(timestamp);
        } catch (ParseException e) {
            return timestamp;
        }

        String day_of_week = "";
        String month = "";

        switch (date.getDay()) {
            case 0:
                day_of_week = "Domingo";
                break;
            case 1:
                day_of_week = "Segunda";
                break;
            case 2:
                day_of_week = "Terça";
                break;
            case 3:
                day_of_week = "Quarta";
                break;
            case 4:
                day_of_week = "Quinta";
                break;
            case 5:
                day_of_week = "Sexta";
                break;
            case 6:
                day_of_week = "Sábado";
                break;
        }


        switch (date.getMonth()) {
            case 0:
                month = "Janeiro";
                break;
            case 1:
                month = "Fevereiro";
                break;
            case 2:
                month = "Março";
                break;
            case 3:
                month = "Abril";
                break;
            case 4:
                month = "Maio";
                break;
            case 5:
                month = "Junho";
                break;
            case 6:
                month = "Julho";
                break;
            case 7:
                month = "Agosto";
                break;
            case 8:
                month = "Setembro";
                break;
            case 9:
                month = "Outubro";
                break;
            case 10:
                month = "Novembro";
                break;
            case 11:
                month = "Dezembro";
                break;
        }

        return day_of_week + ", " + date.getDate() + " de " + month + " de " + (1900 + date.getYear());
    }

    public static String calendarToString(Calendar cal) {
        Date date = cal.getTime();
        SimpleDateFormat format1 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format1.format(date);
    }

}
