package com.example.functional;

import com.example.helper.Person;

import java.util.Arrays;
import java.util.List;

import static com.example.helper.Gender.*;

public class CollectorTest {

    public static void main(String[] args) {
        List<Person> people = Arrays.asList(
                new Person("Vineet", MALE),
                new Person("Kiaara", FEMALE),
                new Person("Supriya", FEMALE),
                new Person("Suneet", MALE),
                new Person("Uma", FEMALE),
                new Person("Gaurav", MALE)
        );

        //declarative
        for (Person person : people)
        {
            if(person.getGender().equals(FEMALE)){
                System.out.println(person);
            }
        }

        System.out.println();
        //imperative
        //filter takes a predicate
        people.stream().filter(person -> person.getGender().equals(FEMALE)).
                        forEach(System.out::println);
    }

}
