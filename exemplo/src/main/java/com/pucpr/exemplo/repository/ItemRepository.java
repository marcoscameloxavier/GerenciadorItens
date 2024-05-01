package com.pucpr.exemplo.repository;

import com.pucpr.exemplo.entity.Item;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ItemRepository extends JpaRepository<Item, Integer> {
    // Aqui você pode adicionar métodos de consulta personalizados, se necessário.
}