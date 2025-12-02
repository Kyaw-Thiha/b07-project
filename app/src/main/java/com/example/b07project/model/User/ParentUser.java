package com.example.b07project.model.User;

import com.example.b07project.model.Medicine;

import java.util.Map;

public class ParentUser extends User {
    private Map<String, ChildUser> children;
    private Map<String, ProviderUser> providers;
    private Map<String, Medicine> inventory;

    public ParentUser() {
        super();
    }

    public ParentUser(String uid, String name, String email, Map<String, Boolean> roles) {
        super(uid, name, email, roles);
    }

    public Map<String, ChildUser> getChildren() {
        return children;
    }

    public void setChildren(Map<String, ChildUser> children) {
        this.children = children;
    }

    public Map<String, ProviderUser> getProviders() {
        return providers;
    }

    public void setProviders(Map<String, ProviderUser> providers) {
        this.providers = providers;
    }

    public Map<String, Medicine> getInventory() {
        return inventory;
    }

    public void setInventory(Map<String, Medicine> inventory) {
        this.inventory = inventory;
    }
}
