package com.tierriferreira.desafiofinal2.auth;

import com.tierriferreira.desafiofinal2.database.AuthStorage;
import com.tierriferreira.desafiofinal2.models.AuthCredentials;

public class AuthSingleton {
    // Instância do singleton, estática para ter a mesma instância em todos os contextos.
    private static AuthSingleton instance;

    // Não permitir instanciação fora da classe, porque esta é um singleton.
    private AuthSingleton() {
    }

    // Método de login, retorna false se o login não é válido.
    public boolean login(String username, String password, AuthStorage storage) {
        AuthCredentials credentials = storage.retrieveByUsername(username);
        if (credentials == null) return false;
        return credentials.getPassword().equals(password);
    }

    // Método para alterar palavra passe. Não é possível alterar username, porque este é único.
    public boolean changePassword(String username, String newPassword, AuthStorage storage) {
        AuthCredentials credentials = storage.retrieveByUsername(username);
        if (credentials == null) return false;
        credentials.setPassword(newPassword);
        storage.update(credentials, credentials.getId());
        return true;
    }

    // Obter a instância em qualquer contexto, sem instanciar.
    public static AuthSingleton getInstance() {
        if (instance == null) instance = new AuthSingleton();
        return instance;
    }
}
