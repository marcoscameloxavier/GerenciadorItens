package com.pucpr.exemplo.entity;
import jakarta.persistence.*;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

@Entity
public class Carrinho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ElementCollection
    @CollectionTable(name = "carrinho_itens", joinColumns = @JoinColumn(name = "carrinho_id"))
    @MapKeyJoinColumn(name = "item_id")
    @Column(name = "quantidade")
    private Map<Item, Integer> itens = new HashMap<>();
    private Date dataCriacao = new Date();

    public Carrinho() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Map<Item, Integer> getItens() {
        return itens;
    }

    public void setItens(Map<Item, Integer> itens) {
        this.itens = itens;
    }

    // Métodos úteis para o carrinho

    public void addItem(Item item, int quantidade) {
        if(itens.containsKey(item)) {
            itens.put(item, itens.get(item) + quantidade);
        } else {
            itens.put(item, quantidade);
        }
    }

    public void updateItem(Item item, int quantidade) {
        if(itens.containsKey(item)) {
            itens.put(item, quantidade);
        }
    }

    public void removeItem(Item item) {
        itens.remove(item);
    }

    public int getQuantidadeDeItem(Item item) {
        return itens.getOrDefault(item, 0);
    }

}
