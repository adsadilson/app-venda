package one.innovation.digital.domain.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Sexo {

	FEMININA("Feminina"), 
	MASCULINO("Masculino"); 
	

	private String descricao;
}
