package com.fcy.quyou;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

/**
 * @Program: quyou
 * @Description:
 * @Author: chaoyue.fan
 * @Create: 2018-11-22 13:18
 * @Version: 1.0.0
 **/
public class EnumerationTest {

    public static void main(String[] args) {
        Hashtable map = new Hashtable();
        map.put("aaa","111");
        map.put("bbb","222");

        Enumeration enumeration =  map.elements();
        while (enumeration.hasMoreElements()){
            System.out.println(enumeration.nextElement());
        }
    }

}
