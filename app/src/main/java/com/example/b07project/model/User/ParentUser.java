package com.example.b07project.model.User;

import com.example.b07project.model.Medicine;

import java.util.ArrayList;
import java.util.Map;

public class ParentUser extends User {
    private ArrayList<ChildUser> children;
    private ArrayList<ProviderUser> providers;
    private ArrayList<Medicine> inventory;

    public ParentUser() {
        super();
    }

    public ParentUser(String uid, String name, String email, Map<String, Boolean> roles) {
        super(uid, name, email, roles);
    }

    public ArrayList<ChildUser> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ChildUser> children) {
        this.children = children;
    }

    public ArrayList<ProviderUser> getProviders() {
        return providers;
    }

    public void setProviders(ArrayList<ProviderUser> providers) {
        this.providers = providers;
    }

    public ArrayList<Medicine> getInventory() {
        return inventory;
    }

    public void setInventory(ArrayList<Medicine> inventory) {
        this.inventory = inventory;
    }
}
