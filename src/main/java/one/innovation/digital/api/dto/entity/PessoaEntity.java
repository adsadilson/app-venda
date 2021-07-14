package one.innovation.digital.api.dto.entity;

import java.time.LocalDate;

import lombok.Data;
import one.innovation.digital.domain.enums.Sexo;
import one.innovation.digital.domain.enums.Status;

@Data
public class PessoaEntity {

	private Long id;
	private String nome;
	private String cpf;
	private LocalDate cadastro;
	private Status status;
	private Sexo sexo;
}
