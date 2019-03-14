package cn.test;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;

/**
 * Administrator
 * 2019/3/14
 */
public class TestJava01 {

    public static void main(String[] args) {

        SimpleDateFormat format = new SimpleDateFormat("yyyyMMdd HHmmss");

        DecimalFormat format1 = new DecimalFormat("#");

        DecimalFormat df1 = new DecimalFormat("0000.0");

        DecimalFormat df2 = new DecimalFormat("#.#");

        DecimalFormat df3 = new DecimalFormat("000.000");

        System.out.println(df1.format(12.34));

        System.out.println(df2.format(12.34));

        System.out.println(df3.format(12.34));
        DecimalFormat df4 = new DecimalFormat("#.#0");


        System.out.println(df4.format(12.3411));


    }

}
