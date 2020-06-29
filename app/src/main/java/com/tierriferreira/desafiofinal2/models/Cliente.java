package com.tierriferreira.desafiofinal2.models;

public class Cliente extends Generic {
    private int id, idade;
    private String nome;

    public Cliente(String nome, int idade, String urlFoto) {
        this(-1, nome, idade, urlFoto);
    }

    public Cliente(int id, String nome, int idade, String urlFoto) {
        super(urlFoto);
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public int getIdade() {
        return idade;
    }

    public void setIdade(int idade) {
        this.idade = idade;
    }
}
