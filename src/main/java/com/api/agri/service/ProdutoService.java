package com.api.agri.service;

import com.api.agri.model.Produto;
import com.api.agri.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Listar todos os produtos
    @Transactional
    public List<Produto> listarProdutos() {
        return produtoRepository.findAll();
    }

    // Salvar um novo produto
    @Transactional
    public Produto salvarProduto(Produto produto) {
        return produtoRepository.save(produto);
    }

    // Alterar um produto existente
    @Transactional
    public Produto alterarProduto(Produto produto) {
        // Verifica se o produto existe no banco pelo ID
        Optional<Produto> produtoExistente;
        produtoExistente = produtoRepository.findById(produto.getId());

        if (produtoExistente.isPresent()) {
            // Atualiza os dados do produto existente
            Produto produtoAtualizado = produtoExistente.get();
            produtoAtualizado.setNome(produto.getNome());
            produtoAtualizado.setPreco(produto.getPreco());
            produtoAtualizado.setQuantidade(produto.getQuantidade());
            produtoAtualizado.setDescricao(produto.getDescricao());
            // Adicione outros campos necessários

            // Salva o produto atualizado no banco
            return produtoRepository.save(produtoAtualizado);
        } else {
            throw new IllegalArgumentException("Produto com ID " + produto.getId() + " não encontrado.");
        }
    }

    // Deletar um produto pelo ID
    @Transactional
    public void deletarProduto(UUID id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
        } else {
            throw new IllegalArgumentException("Produto com ID " + id + " não encontrado.");
        }
    }
}
