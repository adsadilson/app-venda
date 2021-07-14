package one.innovation.digital.api.dto.entity;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;
import one.innovation.digital.domain.enums.StatusVenda;

@Data
public class VendaCabEntity {

	private Long id;
	
	private String formaPagto;
	
	private LocalDate data;
	
	@JsonIgnore
	private LocalDateTime hora;
	
	private BigDecimal valor;
	
	private StatusVenda status;

	@JsonIgnoreProperties({ "cpf", "cadastro", "status", "sexo" })
	private PessoaEntity cliente;


}
