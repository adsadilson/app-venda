package one.innovation.digital.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.math.BigDecimal;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import one.innovation.digital.api.execption.NegocioException;
import one.innovation.digital.domain.entity.Pessoa;
import one.innovation.digital.domain.entity.Produto;
import one.innovation.digital.domain.entity.VendaCab;
import one.innovation.digital.domain.entity.VendaDet;
import one.innovation.digital.domain.enums.FormaPagamento;
import one.innovation.digital.domain.service.PessoaService;
import one.innovation.digital.domain.service.ProdutoService;
import one.innovation.digital.domain.service.VendaCabService;
import one.innovation.digital.util.DBService;
import one.innovation.digital.util.DatabaseCleaner;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
public class VendaCabRepositoryTest {

	@Autowired
	private VendaCabService cabService;

	@Autowired
	private PessoaService clienteService;

	@Autowired
	private ProdutoService produtoService;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private DBService dbService;

	private VendaDet item;

	@BeforeEach
	public void setUp() {
		databaseCleaner.clearTables();
		dbService.instanciaBaseDados();
	}

	@Test
	public void deve_retorna_id_se_a_venda_for_realizada_com_sucesso() throws Exception {

		Pessoa c = clienteService.buscarPorId(1L);
		Produto p = produtoService.buscarPorId(1L);

		item = new VendaDet();
		item.setProduto(p);
		item.setQuantidade(new BigDecimal("15"));

		VendaCab vc = new VendaCab();

		vc.setFormaPagto(FormaPagamento.DINHEIRO);
		vc.setCliente(c);
		vc.getItens().add(item);

		vc = cabService.adicionar(vc);

		assertThat(vc.getId()).isEqualTo(1L);
		assertThat(vc.getItens().size() > 0);
	}

	@Test
	public void deve_falha_se_cliente_estive_bloqueado_para_venda_aprazo() {

		Pessoa c = clienteService.buscarPorId(2L);
		Produto p = produtoService.buscarPorId(1L);

		item = new VendaDet();
		item.setProduto(p);
		item.setQuantidade(new BigDecimal("15"));

		VendaCab vc = new VendaCab();

		vc.setFormaPagto(FormaPagamento.NOTA_APRAZO);
		vc.setCliente(c);
		vc.getItens().add(item);

		assertThrows(NegocioException.class, () -> cabService.adicionar(vc));
	}

	@Test
	public void deve_falha_se_estoque_for_insuficiente() {

		Pessoa c = clienteService.buscarPorId(1L);
		Produto p = produtoService.buscarPorId(1L);

		item = new VendaDet();
		item.setProduto(p);
		item.setQuantidade(new BigDecimal("1000"));

		VendaCab vc = new VendaCab();

		vc.setFormaPagto(FormaPagamento.DINHEIRO);
		vc.setCliente(c);
		vc.getItens().add(item);

		assertThrows(NegocioException.class, () -> cabService.adicionar(vc));
	}

	@Test
	public void deve_falha_se_buscar_venda_inexistente() {
		assertThrows(NegocioException.class, () -> cabService.buscarPorId(-1L));
	}


}
