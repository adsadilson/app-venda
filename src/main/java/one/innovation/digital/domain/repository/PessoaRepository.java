package one.innovation.digital.domain.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.innovation.digital.domain.entity.Pessoa;

@Repository
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	List<Pessoa> findByNome(String nome);

	Optional<Pessoa> findById(String string);
	
	Optional<Pessoa> findByCpf(String string);

}
