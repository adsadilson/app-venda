package one.innovation.digital.controller;

import static io.restassured.RestAssured.basePath;
import static io.restassured.RestAssured.enableLoggingOfRequestAndResponseIfValidationFails;
import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.port;
import static org.hamcrest.CoreMatchers.equalTo;

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
import one.innovation.digital.domain.enums.Sexo;
import one.innovation.digital.domain.enums.SituacaoPessoa;
import one.innovation.digital.domain.enums.Status;
import one.innovation.digital.domain.repository.PessoaRepository;
import one.innovation.digital.util.DatabaseCleaner;
import one.innovation.digital.util.ResourceUtils;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
class PessoaControllerTest {

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private PessoaRepository clientePessoaRepository;

	private static final int PESSOA_ID_INEXISTENTE = 100;

	@LocalServerPort
	private int port_;

	private String pessoa;

	// @formatter:off
	
	@BeforeEach
	public void setUp() {
		// Habilitar o log no console para mostrar o erro apresentado.
		enableLoggingOfRequestAndResponseIfValidationFails();
		port = port_;
		basePath = "/pessoas";
		databaseCleaner.clearTables();
		prepararDados();
	}

	@Test
	public void deve_retornar_status200_quando_consultar_pessoa() {
		given()
			.accept(ContentType.JSON)
		.when()
			.get()
		.then()
			.statusCode(HttpStatus.OK.value());
	}
	
		
	@Test
	public void deve_retornar_resposta_status200_quando_consultar_pessoa_por_id() {
		given()
			.pathParam("id", 1L)
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(HttpStatus.OK.value())
			.body("nome", equalTo("JACSON DA SILVA"));
	}
	
	@Test
	public void deve_retornar_resposta_status405_quando_consultar_pessoa_inexistente() {
		given()
			.pathParam("id", PESSOA_ID_INEXISTENTE)
			.accept(ContentType.JSON)
		.when()
			.get("/{id}")
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	@Test
	public void deve_retornar_status201_quando_cadastrar_pessoa() {
		pessoa = ResourceUtils.getContentFromResource("/json/pessoa/pessoaSucesso.json");
		given()
			.body(pessoa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.CREATED.value());
	}
	
	@Test
	public void deve_retornar_status400_quando_cadastrar_pessoa_com_o_mesmo_cpf() {
		pessoa = ResourceUtils.getContentFromResource("/json/pessoa/pessoaComOMesmo.json");
		given()
			.body(pessoa)
			.contentType(ContentType.JSON)
			.accept(ContentType.JSON)
		.when()
			.post()
		.then()
			.statusCode(HttpStatus.BAD_REQUEST.value());
	}
	
	
	
	// @formatter:on

	private void prepararDados() {

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
