package br.com.nunesports.repository;



import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import br.com.nunesports.models.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {
}

