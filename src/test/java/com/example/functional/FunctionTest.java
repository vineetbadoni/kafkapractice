package com.example.functional;

import java.util.function.Function;

public class FunctionTest {

    public static void main(String[] args) {

        Function<Integer,Integer> incrementBy10Function = x -> x+10;

        System.out.println(incrementBy10(100));

        System.out.println(incrementBy10Function.apply(1000));

    }

    private static Integer incrementBy10(Integer input){
        return input + 10;
    }

}
