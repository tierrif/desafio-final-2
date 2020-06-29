package com.tierriferreira.desafiofinal2.models;

public class AuthCredentials {
    private int id;
    private String username, password;
    // Super admin permite manusear utilizadores.
    private boolean superAdmin;

    public AuthCredentials(String username, String password, boolean superAdmin) {
        this(-1, username, password, superAdmin);
    }

    public AuthCredentials(int id, String username, String password, boolean superAdmin) {
        this.id = id;
        this.username = username;
        this.password = password;
        this.superAdmin = superAdmin;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isSuperAdmin() {
        return superAdmin;
    }

    public void setSuperAdmin(boolean superAdmin) {
        this.superAdmin = superAdmin;
    }
}
