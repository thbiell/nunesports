package br.com.nunesports.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import br.com.nunesports.dto.ProdutoDTO;
import br.com.nunesports.services.ProdutoService;

import java.util.List;

@Controller
@RequestMapping("/produtos")
public class ProdutoController {

    @Autowired
    private ProdutoService produtoService;

    // Endpoint para listar todos os produtos
    @GetMapping
    public String listarTodos(Model model) {
        List<ProdutoDTO> produtos = produtoService.listarTodos();
        model.addAttribute("produtos", produtos);
        return "produtos/listagem";
    }

    // Endpoint para buscar produto por ID e exibir formulário de edição
    @GetMapping("/editar/{id}")
    public String editarProduto(@PathVariable Long id, Model model) {
        ProdutoDTO produtoDTO = produtoService.buscarPorId(id);
        if (produtoDTO == null) {
            return "redirect:/produtos";
        }
        model.addAttribute("produto", produtoDTO);
        return "produtos/formulario";
    }

    // Endpoint para exibir formulário de criação
    @GetMapping("/novo")
    public String novoProduto(Model model) {
        model.addAttribute("produto", new ProdutoDTO());
        return "produtos/formulario";
    }

    // Endpoint para criar ou atualizar produto
    @PostMapping
    public String salvarProduto(@ModelAttribute ProdutoDTO produtoDTO) {
        if (produtoDTO.getId() == null) {
            produtoService.criarProduto(produtoDTO);
        } else {
            produtoService.atualizarProduto(produtoDTO.getId(), produtoDTO);
        }
        return "redirect:/produtos";
    }

    // Endpoint para deletar produto
    @GetMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable Long id) {
        produtoService.deletarProduto(id);
        return "redirect:/produtos";
    }
}
