package com.tierriferreira.desafiofinal2.models;

public class ImovelCarateristicas {
    private long id;
    private boolean sauna, areaComum;

    public ImovelCarateristicas() {
        this(-1, false, false);
    }

    public ImovelCarateristicas(boolean sauna, boolean areaComum) {
        this(-1, sauna, areaComum);
    }

    public ImovelCarateristicas(long id) {
        // Valores por defeito, chamando o próprio construtor.
        this(id, false, false);
    }

    public ImovelCarateristicas(long id, boolean sauna, boolean areaComum) {
        this.id = id;
        this.sauna = sauna;
        this.areaComum = areaComum;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
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
