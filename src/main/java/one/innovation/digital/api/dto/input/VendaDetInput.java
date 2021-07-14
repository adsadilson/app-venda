package one.innovation.digital.api.dto.input;

import java.math.BigDecimal;

import javax.validation.Valid;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class VendaDetInput  {

	@Valid
	@NotNull
	private ProdutoIdInput produto;
	
	@DecimalMin(value = "1")
	private BigDecimal quantidade = BigDecimal.ZERO;


}
