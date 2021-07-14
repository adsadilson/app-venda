package one.innovation.digital.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum SituacaoPessoa {

	LIBERADO("LIBERADO"), 
	SUSPENSO("SUSPENSO"), 
	BLOQUEADO("BLOQUEADO"); 
	

	private String descricao;
}
