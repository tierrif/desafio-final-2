package com.tierriferreira.desafiofinal2.models;

public class Cliente extends Generic {
    private long id;
    private int idade;
    private String nome;

    public Cliente() {
        this(-1, null, -1, null);
    }

    public Cliente(String nome, int idade, String urlFoto) {
        this(-1, nome, idade, urlFoto);
    }

    public Cliente(long id, String nome, int idade, String urlFoto) {
        super(urlFoto);
        this.id = id;
        this.nome = nome;
        this.idade = idade;
    }

    public long getId() {
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
