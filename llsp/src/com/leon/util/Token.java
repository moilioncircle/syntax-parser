package com.leon.util;


/** @author : Leon
 * @since   : 2013-8-12
 * @see    : 
 */

public class Token {
    private String[] str;
    private int index;
    public Token(String[] str){
        this.str = str;
    }
    public String next_token(){
        if(index<str.length){
            return str[index++];
        }
        return null;
    }
    
    public String current_token(){
        if(index<str.length){
            return str[index];
        }
        return null;
    }
}


