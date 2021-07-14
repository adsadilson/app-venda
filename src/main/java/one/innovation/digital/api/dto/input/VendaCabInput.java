package one.innovation.digital.api.dto.input;

import java.util.List;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

import lombok.Data;
import one.innovation.digital.domain.enums.FormaPagamento;

@Data
public class VendaCabInput {

	@NotNull
	private FormaPagamento formaPagto;

	private PessoaIdInput cliente;

	@NotNull
	@Valid
	private List<VendaDetInput> itens;

}
