package com.room.reservation.program.utils;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class SystemUtils {

    public static void clearScreen() {
        try {
            new ProcessBuilder("cmd", "/c", "cls").inheritIO().start().waitFor();
        } catch (InterruptedException e) {
            System.err.println("An unexpected error occurred.");
        } catch (IOException e) {
            System.err.println("An unexpected error occurred.");
        }
    }

    public static int getIntegerInput( final String prompt ) {
        System.out.println(prompt);
        System.out.print("Enter choice: ");

        return new Scanner(System.in).nextInt();
    }

    public static String generateGUID() {
        Random random = new Random();
        return String.valueOf(random.nextInt(999999999-100000000) + 100000000);
    }

    public static String generatePassword() {
        final String corpus = "0123456789abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ!@%^&*()_+-=?<#>";
        List<Character> characters = corpus.chars().mapToObj(c -> (char) c).collect(Collectors.toList());

        Collections.shuffle(characters);

        return characters.subList(0, 8).stream().map(c -> c+"").collect(Collectors.joining(""));
    }
}
