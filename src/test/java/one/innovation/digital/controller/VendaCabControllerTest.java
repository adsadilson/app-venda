package one.innovation.digital.controller;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import io.restassured.http.ContentType;
import one.innovation.digital.domain.entity.Pessoa;
import one.innovation.digital.domain.entity.Produto;
import one.innovation.digital.domain.enums.Sexo;
import one.innovation.digital.domain.enums.SituacaoPessoa;
import one.innovation.digital.domain.enums.Status;
import one.innovation.digital.domain.repository.PessoaRepository;
import one.innovation.digital.domain.repository.ProdutoRepository;
import one.innovation.digital.util.DatabaseCleaner;
import one.innovation.digital.util.ResourceUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
class VendaCabControllerTest {

	@Autowired
	private DatabaseCleaner databaseCleaner;
	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private PessoaRepository clientePessoaRepository;

	private static final int VENDA_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port2;

	private String venda;

	// @formatter:off
	
	@BeforeEach
	public void setUp() {
		// Habilitar o log no console para mostrar o erro apresentado.
		enableLoggingOfRequestAndResponseIfValidationFails();
		port = port2;
		basePath = "/vendas";
		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void deve_retornar_status200_quando_consultar_vendas() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
		
	@Test
	public void deve_retornar_resposta_status405_quando_consultar_venda_inexistente() {
		given()
			.pathParam("id", VENDA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deve_retornar_status201_quando_cadastrar_venda() {
		venda = ResourceUtils.getContentFromResource("/json/venda/vendaSucesso.json");
		given()
			.body(venda)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deve_retornar_status400_quando_cadastrar_venda_bloqueada() {
		venda = ResourceUtils.getContentFromResource("/json/venda/vendaBloqueada.json");
		given()
			.body(venda)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	@Test
	public void deve_retornar_status400_quando_cadastrar_venda_qtde_insuficiente() {
		venda = ResourceUtils.getContentFromResource("/json/venda/vendaQtdeInsuficiente.json");
		given()
			.body(venda)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}

	
	// @formatter:on

	private void prepararDados() {

		Produto p = new Produto();
		p.setCodigoBarra("0001");
		p.setNome("CEVERJA SKOL 600ML");
		p.setStatus(Status.ATIVO);
		p.setQuantidade(new BigDecimal("100"));
		p.setVlrCusto(new BigDecimal("4.75"));
		p.setVlrVenda(new BigDecimal("12.00"));
		produtoRepository.save(p);

		Produto p2 = new Produto();
		p2.setCodigoBarra("0002");
		p2.setNome("CEVERJA HEINEKEN 600ML");
		p2.setStatus(Status.ATIVO);
		p2.setQuantidade(new BigDecimal("850"));
		p2.setVlrCusto(new BigDecimal("8.50"));
		p2.setVlrVenda(new BigDecimal("17.00"));
		produtoRepository.save(p2);

		List<Produto> prod = produtoRepository.findAll();
		System.out.println(prod);

		Pessoa cliente = new Pessoa();
		cliente.setNome("JACSON DA SILVA");
		cliente.setCpf("009.165.125-30");
		cliente.setSexo(Sexo.MASCULINO);
		cliente.setSituacao(SituacaoPessoa.LIBERADO);
		cliente.setStatus(Status.ATIVO);
		cliente.setCadastro(LocalDate.now());
		clientePessoaRepository.save(cliente);

		Pessoa cliente2 = new Pessoa();
		cliente2.setNome("RONALDO OLIVEIRA");
		cliente2.setCpf("054.155.125-00");
		cliente2.setSexo(Sexo.MASCULINO);
		cliente2.setSituacao(SituacaoPessoa.BLOQUEADO);
		cliente2.setStatus(Status.ATIVO);
		cliente2.setCadastro(LocalDate.now());
		clientePessoaRepository.save(cliente2);

		List<Pessoa> listP = clientePessoaRepository.findAll();
		System.out.println(listP);

	}

}
