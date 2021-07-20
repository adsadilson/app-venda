package one.innovation.digital.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.time.LocalDate;

import javax.validation.ConstraintViolationException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import one.innovation.digital.api.execption.NegocioException;
import one.innovation.digital.domain.entity.Pessoa;
import one.innovation.digital.domain.enums.Sexo;
import one.innovation.digital.domain.enums.SituacaoPessoa;
import one.innovation.digital.domain.enums.Status;
import one.innovation.digital.domain.service.PessoaService;
import one.innovation.digital.util.DBService;
import one.innovation.digital.util.DatabaseCleaner;

@SpringBootTest()
@ExtendWith(SpringExtension.class)
@TestPropertySource("/application-test.properties")
public class PessoaRepositoryTest {

	@Autowired
	private PessoaService clienteService;

	@Autowired
	private DatabaseCleaner databaseCleaner;

	@Autowired
	private DBService dbService;

	@BeforeEach
	public void setUp() {
		databaseCleaner.clearTables();
		dbService.instanciaBaseDados();
	}

	@Test
	public void deve_atribuir_id_quando_cadastro_de_pessoa_com_sucesso() {
		Pessoa cliente = new Pessoa();
		cliente.setNome("CLEICK CRISTINA FROIS");
		cliente.setCpf("488.765.105-20");
		cliente.setSexo(Sexo.FEMININA);
		cliente.setSituacao(SituacaoPessoa.LIBERADO);
		cliente.setStatus(Status.ATIVO);
		cliente.setCadastro(LocalDate.now());
		cliente = clienteService.adicionar(cliente);

		assertThat(cliente).isNotNull();
		assertThat(cliente.getId()).isNotNull();
	}

	@Test
	public void deve_falhar_quando_cadastro_de_pessoa_sem_nome() {
		Pessoa cliente = new Pessoa();
		cliente.setNome(null);
		cliente.setCpf("400.765.105-21");
		cliente.setSexo(Sexo.FEMININA);
		cliente.setSituacao(SituacaoPessoa.LIBERADO);
		cliente.setStatus(Status.ATIVO);
		cliente.setCadastro(LocalDate.now());
		assertThrows(ConstraintViolationException.class, () -> clienteService.adicionar(cliente));
	}

	@Test
	public void deve_falhar_quando_cadastro_de_pessoa_sem_cpf() {
		Pessoa cliente = new Pessoa();
		cliente.setNome("CAMILA SOARES DA SILVA");
		cliente.setCpf(null);
		cliente.setSexo(Sexo.FEMININA);
		cliente.setSituacao(SituacaoPessoa.LIBERADO);
		cliente.setStatus(Status.ATIVO);
		cliente.setCadastro(LocalDate.now());
		assertThrows(ConstraintViolationException.class, () -> clienteService.adicionar(cliente));
	}

	@Test
	public void deve_falhar_quando_cadastro_de_pessoa_com_o_mesmo_cpf() {
		Pessoa cliente = new Pessoa();
		cliente.setNome("MARCELA GOMES");
		cliente.setCpf("009.165.125-30");
		cliente.setSexo(Sexo.FEMININA);
		cliente.setSituacao(SituacaoPessoa.LIBERADO);
		cliente.setStatus(Status.ATIVO);
		cliente.setCadastro(LocalDate.now());
		assertThrows(NegocioException.class, () -> clienteService.adicionar(cliente));
	}

	@Test
	public void deve_falhar_quando_excluir_pessoa_inexistente() {
		assertThrows(NegocioException.class, () -> clienteService.excluir(1000L));
	}
	
	@Test
	public void deve_falhar_quando_excluir_pessoa_em_uso() {
		assertThrows(NegocioException.class, () -> clienteService.excluir(1L));
	}

}
