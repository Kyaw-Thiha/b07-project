package com.example.b07project.model.User;

import java.util.Map;

public class ProviderUser extends User {
    private String organization;

    public ProviderUser() {
        super();
    }

    public ProviderUser(String uid, String name, String email, Map<String, Boolean> roles) {
        super(uid, name, email, roles);
    }

    public ProviderUser(String uid, String name, String email, Map<String, Boolean> roles, String organization) {
        super(uid, name, email, roles);
        this.organization = organization;
    }

    public String getOrganization() {
        return organization;
    }

    public void setOrganization(String organization) {
        this.organization = organization;
    }
}
