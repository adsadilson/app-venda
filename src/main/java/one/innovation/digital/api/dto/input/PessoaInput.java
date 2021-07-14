package one.innovation.digital.api.dto.input;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.Data;
import one.innovation.digital.domain.enums.Sexo;
import one.innovation.digital.domain.enums.Status;

@Data
public class PessoaInput {


	@NotBlank
	private String nome;
	
	@NotBlank
	private String cpf;

	@NotNull
	private Status status;

	@NotNull
	private Sexo sexo;
}
