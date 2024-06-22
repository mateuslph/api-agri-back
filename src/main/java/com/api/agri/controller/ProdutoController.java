package com.api.agri.controller;

import com.api.agri.model.Produto;
import com.api.agri.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@CrossOrigin(origins = "http://localhost:3000") // Permitir requisições do frontend React
@RequestMapping("/produtos")
public class ProdutoController {
    @Autowired
    private ProdutoService produtoService;

    @GetMapping("/listar")
    public List<Produto> listar() {
        return produtoService.listarProdutos();
    }

    @PostMapping("/cadastrar")
    public Produto cadastrar(@RequestBody Produto produto) {
        return produtoService.salvarProduto(produto);
    }

    @PutMapping("/alterar")
    public Produto alterar(@RequestBody Produto produto) {
        return produtoService.alterarProduto(produto);
    }

    @DeleteMapping("/remover/{id}")
    public Map<String, String> remover(@PathVariable Long id) {
        produtoService.removerProduto(id);
        Map<String, String> response = new HashMap<>();
        response.put("mensagem", "Produto removido com sucesso!");
        return response;
    }
}
