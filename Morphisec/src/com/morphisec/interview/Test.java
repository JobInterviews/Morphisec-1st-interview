package com.morphisec.interview;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Test {

    public static List<Integer> shift(List<Integer> list){
        if(list == null)
            return null;

        LinkedList<Integer> linkedList = new LinkedList<>(list);
        lastToHead(linkedList);

        return linkedList;
    }

    private static void lastToHead(LinkedList<Integer> list) {
        Integer last = list.removeLast();
        list.addFirst(last);
    }


    public static List<Integer> shift(int n, List<Integer> list){
        if(list == null)
            return null;

        int shifting = n % list.size();
        LinkedList<Integer> linkedList = new LinkedList<>(list);

        for (int i=0; i < shifting; i++)
            lastToHead(linkedList);

        return linkedList;
    }


    public static void main(String[] args) {
        List<Integer> list = new ArrayList<>();
        list.add(1);
        list.add(2);
        list.add(3);

        System.out.println(list);
        list = shift(1, list);
        System.out.println(list);
    }

}
