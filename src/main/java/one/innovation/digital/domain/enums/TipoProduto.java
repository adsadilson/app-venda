package one.innovation.digital.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum TipoProduto {

	REVENDA("REVENDA", "R"), 
	CONSUMO("CONSUMO", "C"), 
	TRANSFORMACAO("TRANSFORMACAO", "T"), 
	SERVICO("SERVIÇO", "S");

	private String descricao;
	private String sigla;

	


}