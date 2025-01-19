package com.api.agri.controller;

import com.api.agri.model.Produto;
import com.api.agri.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Permitir requisições do frontend React
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private final ProdutoService produtoService;

    public ProdutoController(ProdutoService produtoService) {
        this.produtoService = produtoService;
    }

    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listar() {
        try {
            List<Produto> produtos = produtoService.listarProdutos();
            if (produtos.isEmpty()) {
                return ResponseEntity.ok(new ArrayList<Produto>()); // Retorna array vazio, evitando 204
            }
            return ResponseEntity.ok(produtos);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto) {
        try {
            Produto produtoSalvo = produtoService.salvarProduto(produto);
            return ResponseEntity.status(201).body(produtoSalvo); // Retorna o produto salvo com status 201 (Created)
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // Erro com mensagem JSON
        }
    }

    @PutMapping("/alterar/{id}")
    public ResponseEntity<Produto> alterar(@PathVariable UUID id, @RequestBody Produto produto) {
        try {
            Produto produtoAlterado = produtoService.alterarProduto(id, produto);
            return ResponseEntity.ok(produtoAlterado);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null);
        }
    }

    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Map<String, String>> remover(@PathVariable UUID id) {
        try {
            produtoService.deletarProduto(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Produto removido com sucesso!");
            return ResponseEntity.ok(response);
        } catch (Exception e) {
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao remover o produto."));
        }
    }
}
