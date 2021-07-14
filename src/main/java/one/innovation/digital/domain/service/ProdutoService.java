package one.innovation.digital.domain.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.innovation.digital.api.execption.NegocioException;
import one.innovation.digital.domain.entity.Produto;
import one.innovation.digital.domain.repository.ProdutoRepository;

@Service
@AllArgsConstructor
public class ProdutoService {

	private ProdutoRepository produtoRepository;

	public List<Produto> listarTodos() {
		return produtoRepository.findAll();
	}

	public Produto buscarPorId(Long id) {
		return produtoRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Produto não encontrado com esse id: " + id));
	}

	public Produto buscarPorCodigoBarra(String codigo) {
		return produtoRepository.findByCodigoBarra(codigo)
				.orElseThrow(() -> new NegocioException("Produto não encontrado com esse código de barra: " + codigo));
	}

	public List<Produto> consultarPorNome(String nome) {
		return produtoRepository.findByNome(nome);
	}

	@Transactional
	public Produto adicionar(Produto produto) {
		produtoExistente(produto);
		return produtoRepository.save(produto);
	}

	@Transactional
	public Produto atualizar(Produto produto) {
		return adicionar(produto);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			produtoRepository.deleteById(id);
			produtoRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new NegocioException(String.format("Não existe nenhum cadastro de %s com código %d", "Produto", id));
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(
					String.format("%s de código %d não pode ser excluido pois está em uso", "Produto", id));
		}
	}

	public void produtoExistente(Produto produto) {
		boolean result = false;

		result = produtoRepository.findByNomeAndCodigoBarra(produto.getNome(), produto.getCodigoBarra()).stream()
				.anyMatch(prod -> !prod.equals(produto));
		if (result) {
			throw new NegocioException("Já existe um cadastro de produto para esse nome e código de barra!");
		}

		result = produtoRepository.findByCodigoBarra(produto.getCodigoBarra()).stream()
				.anyMatch(prod -> !prod.equals(produto));

		if (result) {
			throw new NegocioException(
					"Já existe um cadastro de produto com esse código de barra: " + produto.getCodigoBarra());
		}
	}
}
