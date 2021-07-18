package one.innovation.digital.util;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import one.innovation.digital.domain.entity.Pessoa;
import one.innovation.digital.domain.entity.Produto;
import one.innovation.digital.domain.enums.Sexo;
import one.innovation.digital.domain.enums.SituacaoPessoa;
import one.innovation.digital.domain.enums.Status;
import one.innovation.digital.domain.repository.PessoaRepository;
import one.innovation.digital.domain.repository.ProdutoRepository;

@Service
public class DBService {

	private Produto produto;
	private Pessoa cliente;

	@Autowired
	private ProdutoRepository produtoRepository;
	@Autowired
	private PessoaRepository clienteRepository;
	
	public void instanciaBaseDados() {
		produto = new Produto();
		produto.setCodigoBarra("0001");
		produto.setNome("CEVERJA SKOL 600ML");
		produto.setStatus(Status.ATIVO);
		produto.setQuantidade(new BigDecimal("100"));
		produto.setVlrCusto(new BigDecimal("4.75"));
		produto.setVlrVenda(new BigDecimal("12.00"));
		produtoRepository.save(produto);

		produto = new Produto();
		produto.setCodigoBarra("0002");
		produto.setNome("CEVERJA HEINEKEN 600ML");
		produto.setStatus(Status.ATIVO);
		produto.setQuantidade(new BigDecimal("850"));
		produto.setVlrCusto(new BigDecimal("8.50"));
		produto.setVlrVenda(new BigDecimal("17.00"));
		produtoRepository.save(produto);

		List<Produto> prod = produtoRepository.findAll();
		System.out.println(prod);

		cliente = new Pessoa();
		cliente.setNome("JACSON DA SILVA");
		cliente.setCpf("009.165.125-30");
		cliente.setSexo(Sexo.MASCULINO);
		cliente.setSituacao(SituacaoPessoa.LIBERADO);
		cliente.setStatus(Status.ATIVO);
		cliente.setCadastro(LocalDate.now());
		clienteRepository.save(cliente);

		cliente = new Pessoa();
		cliente.setNome("RONALDO OLIVEIRA");
		cliente.setCpf("054.155.125-00");
		cliente.setSexo(Sexo.MASCULINO);
		cliente.setSituacao(SituacaoPessoa.BLOQUEADO);
		cliente.setStatus(Status.ATIVO);
		cliente.setCadastro(LocalDate.now());
		clienteRepository.save(cliente);

	}
}
