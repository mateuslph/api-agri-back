package com.api.agri.controller;

import com.api.agri.model.Produto;
import com.api.agri.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

@RestController
//@CrossOrigin(origins = "http://localhost:3000") // Permitir requisições do frontend React (descomente conforme necessário)
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Endpoint para listar todos os produtos
    @GetMapping("/listar")
    public ResponseEntity<List<Produto>> listar() {
        try {
            List<Produto> produtos = produtoService.listarProdutos();
            if (produtos.isEmpty()) {
                return ResponseEntity.noContent().build(); // 204 se não houver produtos
            }
            return ResponseEntity.ok(produtos); // 200 se houver produtos
        } catch (Exception e) {
            // Tratamento de exceções
            return ResponseEntity.status(500).body(null); // 500 em caso de erro interno
        }
    }

    // Endpoint para cadastrar um novo produto
    @PostMapping("/cadastrar")
    public ResponseEntity<Produto> cadastrar(@RequestBody Produto produto) {
        try {
            Produto novoProduto = produtoService.salvarProduto(produto);
            return ResponseEntity.ok(novoProduto); // 200 se o cadastro for bem-sucedido
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // 500 em caso de erro
        }
    }

    // Endpoint para alterar um produto existente
    @PutMapping("/alterar")
    public ResponseEntity<Produto> alterar(@RequestBody Produto produto) {
        try {
            Produto produtoAlterado = produtoService.alterarProduto(produto);
            return ResponseEntity.ok(produtoAlterado); // 200 se a alteração for bem-sucedida
        } catch (Exception e) {
            return ResponseEntity.status(500).body(null); // 500 em caso de erro interno
        }
    }

    // Endpoint para remover um produto por ID
    @DeleteMapping("/remover/{id}")
    public ResponseEntity<Map<String, String>> remover(@PathVariable UUID id) {
        try {
            produtoService.deletarProduto(id);
            Map<String, String> response = new HashMap<>();
            response.put("mensagem", "Produto removido com sucesso!");
            return ResponseEntity.ok(response); // 200 se a remoção for bem-sucedida
        } catch (Exception e) {
            // Pode-se tratar exceções específicas, como recurso não encontrado
            return ResponseEntity.status(500).body(Map.of("erro", "Erro ao remover o produto."));
        }
    }
}
