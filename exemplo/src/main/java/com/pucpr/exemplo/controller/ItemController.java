package com.pucpr.exemplo.controller;

import com.pucpr.exemplo.entity.Carrinho;
import com.pucpr.exemplo.service.CartService;
import com.pucpr.exemplo.entity.Item;
import com.pucpr.exemplo.entity.ItemDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;


@RestController
@RequestMapping("api")
public class ItemController {
    @Autowired
    private CartService service;

    @GetMapping("/itens")
    public ResponseEntity<Iterable<Item>> getItens(){
        return ResponseEntity.ok().body(service.findAllItens());
    }

    @GetMapping("/itens/{id}")
    public ResponseEntity<Item> getItem(@PathVariable int id) {
        Item item = service.getItem(id);
        return item != null? ResponseEntity.ok().body(item):ResponseEntity.notFound().build();
    }

    //adiciona um item
    @PostMapping("/itens")
    public ResponseEntity<Item> addItem(@RequestBody  Item item){
        Item savedItem = service.addItem(item);
        URI uri = ServletUriComponentsBuilder.fromCurrentRequest().path("/{id}").buildAndExpand(savedItem.getId()).toUri();
        return ResponseEntity.created(uri).body(savedItem);
    }

    //atualiza um item
    @PutMapping("/itens/{id}")
    public ResponseEntity<Item> updateItem(@PathVariable int id, @RequestBody Item item) {
        return ResponseEntity.ok(service.updateItem(id, item));
    }

    //deleta um item
    @DeleteMapping("/itens/{id}")
    public ResponseEntity<Void> deleteItem(@PathVariable int id) {
        service.deleteItem(id);
        return ResponseEntity.ok().build();
    }

    //addItemCarrinho
    @PostMapping("/carrinho/{carrinhoId}/itens/{itemId}")
    public ResponseEntity<Carrinho> addItemCarrinho(@PathVariable long carrinhoId, @PathVariable int itemId, @RequestParam int quantidade) {
        Carrinho carrinho = service.addItemToCart(carrinhoId,itemId,quantidade);
        return carrinho != null ? ResponseEntity.ok().body(carrinho) : ResponseEntity.notFound().build();
    }

    //removeItemCarrinho
    @DeleteMapping("/carrinho/{carrinhoId}/itens/{itemId}")
    public ResponseEntity<Void> deleteItemCarrinho(@PathVariable long carrinhoId, @PathVariable int itemId) {
        Carrinho carrinho = service.deleteItemFromCart(carrinhoId,itemId);
        return carrinho != null ? ResponseEntity.ok().build() : ResponseEntity.notFound().build();
    }

    @GetMapping("/carrinho/{carrinhoId}/itens")
    public ResponseEntity<List<ItemDTO>> getItensCarrinho(@PathVariable long carrinhoId) {
        List<ItemDTO> itens = service.getItensCarrinho(carrinhoId);
        return itens != null ? ResponseEntity.ok().body(itens) : ResponseEntity.notFound().build();
    }

    //atualizaQuantidadeItemCarrinho
    @PutMapping("/carrinho/{carrinhoId}/itens/{itemId}/{quantidade}")
    public ResponseEntity<Carrinho> updateQuantidadeItemCarrinho(@PathVariable long carrinhoId, @PathVariable int itemId, @PathVariable int quantidade) {
        Carrinho carrinho = service.updateQuantidadeItemCarrinho(carrinhoId, itemId, quantidade);
        return carrinho != null?  ResponseEntity.ok().body(carrinho) : ResponseEntity.notFound().build();
    }
}