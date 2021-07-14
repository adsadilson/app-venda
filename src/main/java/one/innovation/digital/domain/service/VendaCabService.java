package one.innovation.digital.domain.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import lombok.AllArgsConstructor;
import one.innovation.digital.api.execption.NegocioException;
import one.innovation.digital.domain.entity.Pessoa;
import one.innovation.digital.domain.entity.Produto;
import one.innovation.digital.domain.entity.VendaCab;
import one.innovation.digital.domain.enums.FormaPagamento;
import one.innovation.digital.domain.enums.SituacaoPessoa;
import one.innovation.digital.domain.enums.StatusVenda;
import one.innovation.digital.domain.repository.VendaCabRepository;

@Service
@AllArgsConstructor
public class VendaCabService {

	private VendaCabRepository vendaCabRepository;

	private ProdutoService produtoService;

	private PessoaService clienteService;

	public List<VendaCab> listarTodos() {
		return vendaCabRepository.findAll();
	}

	public VendaCab buscarPorId(Long id) {
		return vendaCabRepository.findById(id)
				.orElseThrow(() -> new NegocioException("Venda não encontrado com esse id: " + id));
	}

	@Transactional
	public VendaCab adicionar(VendaCab vendaCab) {
		return vendaCabRepository.save(finalizarVenda(vendaCab));
	}

	@Transactional
	public VendaCab atualizar(VendaCab vendaCab, Long id) {
		VendaCab vendaSalva = buscarPorId(id);
		vendaCab.setId(vendaSalva.getId());
		return adicionar(vendaCab);
	}

	@Transactional
	public void excluir(Long id) {
		try {
			atualizarEstoqueVenda(id);
			vendaCabRepository.deleteById(id);
			vendaCabRepository.flush();
		} catch (EmptyResultDataAccessException e) {
			throw new NegocioException(String.format("Não existe nenhum cadastro de %s com código %d", "Venda", id));
		} catch (DataIntegrityViolationException e) {
			throw new NegocioException(
					String.format("%s de código %d não pode ser excluido pois está em uso", "Venda", id));
		}
	}

	public VendaCab finalizarVenda(VendaCab vc) {

		if (vc.getFormaPagto().equals(FormaPagamento.NOTA_APRAZO)) {
			Pessoa p = clienteService.buscarPorId(vc.getCliente().getId());
			if (!p.getSituacao().equals(SituacaoPessoa.LIBERADO)) {
				throw new NegocioException("Cliente com restriçao para venda a prazo!");
			}
		}

		BigDecimal vlrTotalCab = BigDecimal.ZERO;

		for (int i = 0; i < vc.getItens().size(); i++) {

			BigDecimal vlrTotal = BigDecimal.ZERO;

			Produto p = produtoService.buscarPorId(vc.getItens().get(i).getProduto().getId());
			p.verificaEstoque(vc.getItens().get(i).getQuantidade());
			p.baixarEstoque(vc.getItens().get(i).getQuantidade());

			vlrTotal = vc.getItens().get(i).getQuantidade().multiply(p.getVlrVenda());

			vc.getItens().get(i).setValorTotal(vlrTotal);
			vc.getItens().get(i).setValorUnitario(p.getVlrVenda());
			vc.getItens().get(i).setProduto(p);
			vc.getItens().get(i).setVendaCab(vc);
			vlrTotalCab = vlrTotalCab.add(vc.getItens().get(i).getValorTotal());

		}
		vc.setData(LocalDate.now());
		vc.setHora(LocalDateTime.now());
		vc.setStatus(StatusVenda.FINALIZADA);
		vc.setValor(vlrTotalCab);

		return vc;
	}

	public void atualizarEstoqueVenda(Long id) {
		VendaCab vc = buscarPorId(id);
		for (int i = 0; i < vc.getItens().size(); i++) {
			Produto p = produtoService.buscarPorId(vc.getItens().get(i).getProduto().getId());
			p.adicionarEstoque(vc.getItens().get(i).getQuantidade());
		}
	}

}
