package com.woody.productwarehousingapi.controller;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class Test {

    public static void main(String[] args) {
        LocalDate todayDate = LocalDate.now();
        DateFormat stringFormat = new SimpleDateFormat("yyyyMMddHHmmss");
        System.out.println(todayDate.withDayOfMonth(1).format(DateTimeFormatter.ofPattern("yyyyMMdd")));
        System.out.println(Math.round(10 * Math.random()));
        System.out.println(stringFormat.format(new Date()));
        System.out.println((new Date()).getTime());
    }
}
