package com.pucpr.exemplo.service;

import com.pucpr.exemplo.entity.Carrinho;
import com.pucpr.exemplo.entity.Item;
import com.pucpr.exemplo.entity.ItemDTO;
import com.pucpr.exemplo.repository.CarrinhoRepository;
import com.pucpr.exemplo.repository.ItemRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ItemRepository itemRepository;

    public Iterable<Carrinho> findAll() {
        return carrinhoRepository.findAll(Sort.by("id"));
    }
    public Iterable<Item> findAllItens() {
        return itemRepository.findAll(Sort.by("id"));
    }

    public Item getItem(int id){
        return itemRepository.findById(id).orElse(null);
    }

    public Item addItem(Item item){
        return  itemRepository.save(item);
    }

    @Transactional
    public Item updateItem(int id, Item item){
        Item updatableItem = itemRepository.findById(id).orElse(null);
        if (updatableItem != null) {
            updatableItem.setNome(item.getNome());
            updatableItem.setQtdade(item.getQtdade());
            updatableItem.setValor(item.getValor());
            updatableItem.setDescritivo(item.getDescritivo());
            itemRepository.save(updatableItem);
        }
        return updatableItem;
    }

    public void deleteItem(int id){
        itemRepository.deleteById(id);
    }

    @Transactional
    public Carrinho addItemToCart(long carrinhoId, int itemId, int quatidade){
        Item item = itemRepository.findById(itemId).orElse(null);
        return carrinhoRepository.findById(carrinhoId)
                .map(cart -> {
                    cart.addItem(item, quatidade);
                    return carrinhoRepository.save(cart);
                }).orElse(null);
    }

    @Transactional
    public Carrinho deleteItemFromCart(long carrinhoId, int itemId){
        Item item = itemRepository.findById(itemId).orElse(null);
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElse(null);
        if (carrinho != null && item != null) {
            carrinho.removeItem(item);
            carrinhoRepository.save(carrinho);
            return carrinho;
        }
        else{
            return null;
        }
    }

    @Transactional
    public List<ItemDTO> getItensCarrinho(long carrinhoId) {
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElse(null);
        if (carrinho != null) {
            List<ItemDTO> itemDTOs = carrinho.getItens()
                    .entrySet()
                    .stream()
                    .map(entry -> new ItemDTO(
                            entry.getKey().getId(),
                            entry.getKey().getNome(),
                            entry.getKey().getValor(),
                            entry.getValue()))
                    .collect(Collectors.toList());
            //listar ordenada por id
            itemDTOs.sort((i1, i2) -> i1.getId().compareTo(i2.getId()));
            return itemDTOs;
        }
        else{
            return null;
        }
    }

    @Transactional
    public Carrinho updateQuantidadeItemCarrinho(long carrinhoId, int itemId, int quantidade){
        Carrinho carrinho = carrinhoRepository.findById(carrinhoId).orElse(null);
        Item item = itemRepository.findById(itemId).orElse(null);
        if (carrinho != null && item != null) {
            carrinho.updateItem(item, quantidade);
            carrinhoRepository.save(carrinho);
            return carrinho;
        } else {
            return null;
        }
    }
}
