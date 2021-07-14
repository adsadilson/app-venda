package one.innovation.digital.api.dto.entity;

import java.math.BigDecimal;

import lombok.Data;
import one.innovation.digital.domain.enums.Status;

@Data
public class ProdutoEntity 	 {

	private Long id;
	private String nome;
	private String codigoBarra;
	private BigDecimal quantidade = BigDecimal.ZERO;
	private BigDecimal vlrCusto = BigDecimal.ZERO;
	private BigDecimal vlrVenda = BigDecimal.ZERO;
	private Status status;

}
