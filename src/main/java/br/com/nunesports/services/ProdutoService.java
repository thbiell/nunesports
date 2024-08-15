package br.com.nunesports.services;



import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.nunesports.dto.ProdutoDTO;
import br.com.nunesports.models.Produto;
import br.com.nunesports.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository produtoRepository;

    // Método para listar todos os produtos
    public List<ProdutoDTO> listarTodos() {
        List<Produto> produtos = produtoRepository.findAll();
        return produtos.stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    // Método para buscar produto por ID
    public ProdutoDTO buscarPorId(Long id) {
        Optional<Produto> produto = produtoRepository.findById(id);
        return produto.map(this::convertToDTO).orElse(null);
    }

    // Método para criar um novo produto
    public ProdutoDTO criarProduto(ProdutoDTO produtoDTO) {
        Produto produto = convertToEntity(produtoDTO);
        Produto produtoSalvo = produtoRepository.save(produto);
        return convertToDTO(produtoSalvo);
    }

    // Método para atualizar um produto existente
    public ProdutoDTO atualizarProduto(Long id, ProdutoDTO produtoDTO) {
        Optional<Produto> produtoExistente = produtoRepository.findById(id);
        if (produtoExistente.isPresent()) {
            Produto produto = convertToEntity(produtoDTO);
            produto.setId(id);
            Produto produtoAtualizado = produtoRepository.save(produto);
            return convertToDTO(produtoAtualizado);
        }
        return null;
    }

    // Método para deletar um produto
    public boolean deletarProduto(Long id) {
        if (produtoRepository.existsById(id)) {
            produtoRepository.deleteById(id);
            return true;
        }
        return false;
    }

    // Métodos de conversão entre Produto e ProdutoDTO
    private ProdutoDTO convertToDTO(Produto produto) {
        return new ProdutoDTO(
                produto.getId(),
                produto.getNome(),
                produto.getCodigo(),
                produto.getDescricao(),
                produto.getPreco()
        );
    }

    private Produto convertToEntity(ProdutoDTO produtoDTO) {
        return new Produto(
                produtoDTO.getNome(),
                produtoDTO.getCodigo(),
                produtoDTO.getDescricao(),
                produtoDTO.getPreco()
        );
    }
}
