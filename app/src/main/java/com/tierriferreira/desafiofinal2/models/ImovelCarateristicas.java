package com.tierriferreira.desafiofinal2.models;

public class ImovelCarateristicas {
    private int id;
    private boolean sauna, areaComum;

    public ImovelCarateristicas(int id) {
        // Valores por defeito, chamando o próprio construtor.
        this(id, false, false);
    }

    public ImovelCarateristicas(int id, boolean sauna, boolean areaComum) {
        this.id = id;
        this.sauna = sauna;
        this.areaComum = areaComum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public boolean hasSauna() {
        return sauna;
    }

    public void setSauna(boolean sauna) {
        this.sauna = sauna;
    }

    public boolean hasAreaComum() {
        return areaComum;
    }

    public void setAreaComum(boolean areaComum) {
        this.areaComum = areaComum;
    }
}