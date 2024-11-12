package br.senai.sp.gestaoro.enums;

public enum Encerramento {
    RO_CUMPRIDO("RO CUMPRIDO"),
    RO_NAO_CUMPRIDO("RO NÃO CUMPRIDO");

    private String descricao;

    Encerramento(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
