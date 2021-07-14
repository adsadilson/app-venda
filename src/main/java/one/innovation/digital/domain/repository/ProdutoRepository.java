package one.innovation.digital.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.innovation.digital.domain.entity.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Long> {

	List<Produto> findByNome(String nome);

	Optional<Produto> findById(String string);
	
	Optional<Produto> findByCodigoBarra(String string);
	
	Optional<Produto> findByNomeAndCodigoBarra(String nome, String codigoBarra);
	

}
