package br.senai.sp.gestaoro.enums;

public enum TipoRegistro {
    REGISTRO("RO DE REGISTRO"),
    ENCAMINHAMENTO("RO DE ENCAMINHAMENTO");

    private String descricao;

    TipoRegistro(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
