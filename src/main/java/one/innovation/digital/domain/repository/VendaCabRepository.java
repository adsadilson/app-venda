package one.innovation.digital.domain.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import one.innovation.digital.domain.entity.Pessoa;
import one.innovation.digital.domain.entity.VendaCab;

@Repository
public interface VendaCabRepository extends JpaRepository<VendaCab, Long> {


	Optional<VendaCab> findById(String string);
	
	Optional<VendaCab> findByCliente(Pessoa cliente);

}
