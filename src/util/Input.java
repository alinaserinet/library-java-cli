package util;

import java.util.Scanner;

public class Input {

    public static int nextInt(String message) {
        try {
            System.out.println(message);
            Scanner input = new Scanner(System.in);
            return input.nextInt();
        }
        catch (Exception err){
            return nextInt("please enter a number");
        }
    }

    public static long nextLong(String message) {
        try {
            System.out.println(message);
            Scanner input = new Scanner(System.in);
            return input.nextLong();
        }
        catch (Exception err){
            return nextLong("please enter a number");
        }
    }

    public static String next(String message) {
        try {
            System.out.println(message);
            Scanner input = new Scanner(System.in);
            return input.next();
        }
        catch (Exception err){
            return next("please enter an string!");
        }
    }

    public static String nextLine(String message) {
        try {
            System.out.println(message);
            Scanner input = new Scanner(System.in);
            return input.nextLine();
        }
        catch (Exception err){
            return nextLine("please enter an string line!");
        }
    }
}
