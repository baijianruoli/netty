package com.ut.lpf.netty.netty;

import io.netty.util.NettyRuntime;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class text {
    public static void main(String[] args) {
       /* ArrayList<Integer> list1=new ArrayList<Integer>();
        list1.add(1);
        list1.add(2);
        cc(list1);
        System.out.println(list1);
        ArrayList<Integer> list2=new ArrayList<Integer>();
        list2.add(1);
        list2.add(2);
        c(list2);
        System.out.println(list2);*/

      String[] str=new String[]{"li","qiqi","pp"};
        List<String> strings = Arrays.asList(str);
        System.out.println(strings);
        strings.add("p");
        System.out.println(strings);

    }
    public static void cc(ArrayList list)
    {
        list.remove(0);
    }
    public static void c(ArrayList list)
    {
       list=new ArrayList();
    }
}

