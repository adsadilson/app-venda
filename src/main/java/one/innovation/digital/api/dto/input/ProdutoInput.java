package one.innovation.digital.api.dto.input;

import java.math.BigDecimal;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.Data;
import one.innovation.digital.domain.enums.Status;

@Data
public class ProdutoInput {

	@NotBlank
	private String nome;

	@NotBlank
	@Size(min = 4, max = 15)
	private String codigoBarra;

	@DecimalMin(value = "1")
	private BigDecimal quantidade = BigDecimal.ZERO;

	@DecimalMin(value = "0.01")
	private BigDecimal vlrCusto = BigDecimal.ZERO;

	@DecimalMin(value = "0.01")
	private BigDecimal vlrVenda = BigDecimal.ZERO;

	@NotNull
	private Status status;

}
