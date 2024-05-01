package com.pucpr.exemplo.entity;

public class ItemDTO {
    private Integer id;
    private String nome;
    private Double valor;
    private Integer quantidade;

    public ItemDTO(Integer id, String nome, Double valor, Integer quantidade) {
        this.id = id;
        this.nome = nome;
        this.valor = valor;
        this.quantidade = quantidade;

        // inicializar outros campos conforme necessário
    }

    public Integer getQuantidade() {
        return quantidade;
    }

    public void setQuantidade(Integer quantidade) {
        this.quantidade = quantidade;
    }

    // Getters e setters para cada campo
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public Double getValor() {
        return valor;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

}
