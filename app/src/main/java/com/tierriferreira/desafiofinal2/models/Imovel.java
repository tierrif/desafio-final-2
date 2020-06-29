package com.tierriferreira.desafiofinal2.models;

public class Imovel extends Generic {
    private int id;
    private String descricao, tipologia, localizacao;
    private ImovelCarateristicas caracteristicas;
    private Cliente cliente;

    public Imovel(String descricao, String tipologia, String localizacao, String urlFoto, ImovelCarateristicas carateristicas) {
        // Quando não existe cliente para o imóvel, mantê-lo nulo.
        this(-1, descricao, tipologia, localizacao, urlFoto, carateristicas, null);
    }

    public Imovel(String descricao, String tipologia, String localizacao, String urlFoto, ImovelCarateristicas carateristicas, Cliente cliente) {
        this(-1, descricao, tipologia, localizacao, urlFoto, carateristicas, cliente);
    }

    public Imovel(int id, String descricao, String tipologia, String localizacao, String urlFoto, ImovelCarateristicas caracteristicas, Cliente cliente) {
        super(urlFoto);
        this.id = id;
        this.descricao = descricao;
        this.tipologia = tipologia;
        this.localizacao = localizacao;
        this.caracteristicas = caracteristicas;
        this.cliente = cliente;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDescricao() {
        return descricao;
    }

    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }

    public String getTipologia() {
        return tipologia;
    }

    public void setTipologia(String tipologia) {
        this.tipologia = tipologia;
    }

    public String getLocalizacao() {
        return localizacao;
    }

    public void setLocalizacao(String localizacao) {
        this.localizacao = localizacao;
    }

    public ImovelCarateristicas getCaracteristicas() {
        return caracteristicas;
    }

    public void setCaracteristicas(ImovelCarateristicas caracteristicas) {
        this.caracteristicas = caracteristicas;
    }

    public Cliente getCliente() {
        return cliente;
    }

    public void setCliente(Cliente cliente) {
        this.cliente = cliente;
    }
}
