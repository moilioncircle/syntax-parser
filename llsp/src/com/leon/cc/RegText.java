package com.leon.cc;

import java.util.regex.Matcher;
import java.util.regex.Pattern;


/** @author : Leon
 * @since   : 2013-8-30
 * @see    : 
 */

public class RegText {
    public static void main(String[] args) {
        Pattern p = Pattern.compile("\\d+(?idmsux-idmsux:56)");
        Matcher m = p.matcher("152535456:152535456");
        m.find();
        System.out.println(m.group());
    }
}


