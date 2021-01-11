package com.starikcetin.ctis496.project2.q5;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.TreeSet;

public class Main {
    public static void main(String[] args) throws Exception {
        var person1 = makePersonWithInput();
        System.out.println();

        var person2 = makePersonWithInput();
        System.out.println();

        var person3 = makePersonWithInput();
        System.out.println();

        var sortedSet = new TreeSet<Person>();
        sortedSet.add(person1);
        sortedSet.add(person2);
        sortedSet.add(person3);

        System.out.println("In order of age: ");
        sortedSet.forEach(System.out::println);
    }

    private static Person makePersonWithInput() throws Exception {
        System.out.print("Enter full name: ");
        var fullname = readLine();

        System.out.print("Enter age: ");
        var age = readLine();

        return Person.withFullName(fullname, Integer.parseInt(age));
    }

    private static String readLine() throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
        return reader.readLine();
    }
}
