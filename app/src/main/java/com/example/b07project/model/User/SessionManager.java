package com.example.b07project.model.User;

public class SessionManager {
    private static User user;
    public static void setUser(User inputUser) {
        user = inputUser;
    }
    public static User getUser() {
        return user;
    }
}
