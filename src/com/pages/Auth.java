package com.pages;

import com.company.Member;
import util.FileService;
import util.Input;
import util.ResultType;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Auth {
    private final FileService membersFile;

    public Auth() throws FileNotFoundException {
        membersFile = new FileService(new RandomAccessFile("Members.txt", "rw"));
    }


    private Member login() throws IOException {
        Member member = new Member(membersFile);
        long id = Input.nextLong("insert your id: ");
        String password = Input.next("insert password: ");
        ResultType<Boolean, Member> result = member.search(id);
        if (!result.getValue1() || !result.getValue2().comparePassword(password)) {
            System.out.println("The userId or password is incorrect.");
            return null;
        }
        return result.getValue2();
    }

    private Member register() throws IOException {
        String name = Input.nextLine("insert your name:");
        String family = Input.nextLine("insert your family:");
        String password = Input.nextLine("insert your password:");
        return new Member(membersFile, name, family, password);
    }

    public Member menu() throws IOException {
        System.out.println("---------- Auth Menu ----------");
        System.out.println("Login : Enter 1");
        System.out.println("Register : Enter 2");
        System.out.println("Quit : Enter 0");
        System.out.println("-------------------------------");
        int key = Input.nextInt("Enter menu key: ");
        switch (key) {
            case 0:
                return null;
            case 1:
                Member result = login();
                if(result != null)
                    return result;
                break;
            case 2:
                return register();
            default:
                System.out.println("menu not valid!");
        }
        return menu();
    }
}
