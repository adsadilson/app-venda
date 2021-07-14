package one.innovation.digital.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoTelefone {

	PARTICULAR("Particular"), 
	RESIDENCIAL("Residencial"), 
	COMERCIAL("Comercial");

	private String descricao;
}
