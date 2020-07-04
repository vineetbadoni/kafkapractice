package com.example.java8.handson;

import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

public class Streams {

    public static void main(String[] args) {
        List<String> stringsList = Arrays.asList("String 1","String 2","Vineet");

        Function<String, Boolean> stringFilter = value -> value.contains("str");
        System.out.println(stringsList.stream().map(stringFilter).collect(Collectors.counting()));

    }
}
