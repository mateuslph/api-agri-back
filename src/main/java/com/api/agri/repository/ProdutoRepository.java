package com.api.agri.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.api.agri.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}
