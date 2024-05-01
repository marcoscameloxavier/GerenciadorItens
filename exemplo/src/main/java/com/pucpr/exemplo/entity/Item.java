package com.pucpr.exemplo.entity;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Item {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String nome;
    private int qtdade;
    private double valor;
    private String descritivo;


    public Item() {
        // Construtor vazio necessário para a JPA
    }
    public Item(String nome, int quantidade, double valor, String descritivo) {
        this.nome = nome;
        this.qtdade = quantidade;
        this.valor = valor;
        this.descritivo = descritivo;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getQtdade() {
        return qtdade;
    }
    public void setQtdade(int quantidade) {
        this.qtdade = quantidade;
    }
    public double getValor() {
        return valor;
    }
    public void setValor(double valor) {
        this.valor = valor;
    }
    public String getDescritivo() {
        return descritivo;
    }
    public void setDescritivo(String descritivo) {
        this.descritivo = descritivo;
    }

    public int getId() {
        return id;
    }
    public void setId(int id) {
        this.id = id;
    }
}
