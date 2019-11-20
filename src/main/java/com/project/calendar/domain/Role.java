package com.project.calendar.domain;

public enum Role {
    ADMIN("Admin"), USER("User");

    String description;

    Role(String description) {
        this.description = description;
    }

    public String getDescription() {
        return description;
    }
    //todo override valueOf
    public static Role valueOfByName(String roleName){
        if(roleName == null){
//            return
        }
        return Role.ADMIN;
    }

    public static void main(String[] args) {
        String roleName = null;
        final Role role = Role.valueOf(roleName);
        System.out.println(role);
    }
}
