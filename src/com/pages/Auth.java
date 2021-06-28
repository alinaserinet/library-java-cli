package com.pages;

import com.company.Member;
import util.FileService;
import util.Input;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;

public class Auth {
    private Member member;
    private final FileService membersFile;

    public Auth() throws FileNotFoundException {
        membersFile = new FileService(new RandomAccessFile("Members.txt", "rw"));
        member = new Member(membersFile);
    }


    private Member login() throws IOException {
        long id = Input.nextLong("insert your id: ");
        String password = Input.next("insert password: ");
        if (!member.searchById(id) || !member.comparePassword(password)) {
            System.out.println("The userId or password is incorrect.");
            return null;
        }
        return member;
    }
    
    private Member register() throws IOException {
        String name = Input.nextLine("insert your name:");
        String family = Input.nextLine("insert your family:");
        String password = Input.nextLine("insert your password:");
        member = new Member(membersFile, name, family, password);
        return member;
    }

    public Member menu() throws IOException {
        System.out.println("---------- Auth Menu ----------");
        System.out.println("Login : Enter 1");
        System.out.println("Register : Enter 2");
        System.out.println("Quit : Enter 0");
        System.out.println("-------------------------------");
        int key = Input.nextInt("Enter menu key: ");
        Member result = null;
        switch (key){
            case 0:
                return null;
            case 1:
                result = login();
                break;
            case 2:
                result = register();
                break;
            default:
                System.out.println("menu not valid!");
        }
        if(result == null)
            menu();
        return result;
    }
}
