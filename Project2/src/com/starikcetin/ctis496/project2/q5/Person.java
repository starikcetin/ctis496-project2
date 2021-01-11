package com.starikcetin.ctis496.project2.q5;

import java.text.MessageFormat;
import java.util.Objects;

public class Person implements Comparable<Person> {
    private final String name;
    private final String surname;

    // MET11-J. Ensure that keys used in comparison operations are immutable
    private final int age;

    public Person(String name, String surname, int age) throws Exception {
        if (name == null || name.isBlank()) {
            throw new Exception("Name cannot be empty.");
        }

        if (surname == null || surname.isBlank()) {
            throw new Exception("Surname cannot be empty.");
        }

        if (age <= 0) {
            throw new Exception("Age must be greater than zero.");
        }

        this.name = name;
        this.surname = surname;
        this.age = age;
    }

    // MET00-J. Validate method arguments
    public static Person withFullName(String fullName, int age) throws Exception {
        if (fullName == null || fullName.isBlank()) {
            throw new Exception("Full name cannot be empty.");
        }

        var split = fullName.split(" ");

        if (split.length != 2) {
            throw new IllegalArgumentException("Make sure full name is in the following format: Name Surname");
        }

        return new Person(split[0], split[1], age);
    }

    public String getName() {
        return name;
    }

    public String getSurname() {
        return surname;
    }

    public int getAge() {
        return age;
    }

    // MET09-J. Classes that define an equals() method must also define a hashCode() method
    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Person person = (Person) o;
        return getAge() == person.getAge() && getName().equals(person.getName()) && getSurname().equals(person.getSurname());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getName(), getSurname(), getAge());
    }

    // MET10-J. Follow the general contract when implementing the compareTo() method
    @Override
    public int compareTo(Person o) {
        return this.age - o.age;
    }

    @Override
    public String toString() {
        return MessageFormat.format("{0} {1} ({2})", name, surname, age);
    }
}
