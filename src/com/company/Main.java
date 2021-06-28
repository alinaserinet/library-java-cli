package com.company;

import com.pages.AdminPage;
import com.pages.Auth;
import com.pages.MemberPage;

public class Main {
    public static Member session;

    public static void main(String[] args) {
        try {
            Auth auth = new Auth();
            session = auth.menu();

            while (session != null) {
                System.out.println("welcome " + session);
                if (session.getRole().equals("admin"))
                    AdminPage.menu();
                else
                    MemberPage.menu();
                if (session == null)
                    session = auth.menu();
            }
        } catch (Exception err) {
            System.err.println(err.getMessage());
        }
    }
}
