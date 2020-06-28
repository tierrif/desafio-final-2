package com.tierriferreira.desafiofinal2.auth;

import com.tierriferreira.desafiofinal2.database.AuthStorage;
import com.tierriferreira.desafiofinal2.models.AuthCredentials;

public class AuthSingleton {
    private static AuthSingleton instance;

    private AuthSingleton() {
    }

    public boolean login(String username, String password, AuthStorage storage) {
        AuthCredentials credentials = storage.retrieveByUsername(username);
        if (credentials == null) return false;
        return credentials.getPassword().equals(password);
    }

    public boolean changePassword(String username, String newPassword, AuthStorage storage) {
        AuthCredentials credentials = storage.retrieveByUsername(username);
        if (credentials == null) return false;
        credentials.setPassword(newPassword);
        storage.update(credentials, credentials.getId());
        return true;
    }

    public static AuthSingleton getInstance() {
        if (instance == null) instance = new AuthSingleton();
        return instance;
    }
}
