package one.innovation.digital.api.dto.input;


import javax.validation.constraints.NotNull;

import lombok.Data;

@Data
public class ProdutoIdInput  {

	@NotNull
	private Long id;	

}
