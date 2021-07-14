package one.innovation.digital.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum FormaPagamento {

	DINHEIRO("DINHEIRO"), 
	CARTAO_DE_CREDITO("CARTAO DE CREDITO"),
	NOTA_APRAZO("NOTA APRAZO");

	private String descricao;

}