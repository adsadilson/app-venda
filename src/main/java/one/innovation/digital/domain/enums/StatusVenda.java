package one.innovation.digital.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum StatusVenda {

	FINALIZADA("FINALIZADA"), 
	CANCELADA("CANCELADA"), 
	PENDENTE("PENDENTE"); 
	
	private String descricao;
}
