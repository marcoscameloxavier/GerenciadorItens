package com.pucpr.exemplo.repository;

import com.pucpr.exemplo.entity.Carrinho;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarrinhoRepository extends JpaRepository<Carrinho, Long>{
}
