package com.ams303.cityconnect.lib;

import java.math.RoundingMode;
import java.text.DecimalFormat;

public class utils {

    public static String getFormattedPrice(double value){
        DecimalFormat df = new DecimalFormat("#.##");
        df.setRoundingMode(RoundingMode.CEILING);
        return df.format(value) + "€";
    }

}
