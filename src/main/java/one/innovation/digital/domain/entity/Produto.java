package one.innovation.digital.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import one.innovation.digital.api.execption.NegocioException;
import one.innovation.digital.domain.enums.Status;

@Entity
@Table(name = "produto")
@SequenceGenerator(name = "PRODUTO_ID", sequenceName = "PRODUTO_ID_SEQ")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class Produto implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "PRODUTO_ID_SEQ")
	@EqualsAndHashCode.Include
	private Long id;

	@NotBlank
	@Size(min = 3, max = 255)
	@Column(nullable = false, length = 255)
	private String nome;

	@NotBlank
	@Size(min = 4, max = 15)
	@Column(name = "codigo_barra", length = 15, unique = true)
	private String codigoBarra;

	@Column(name = "quantidade", precision = 12, scale = 2)
	private BigDecimal quantidade = BigDecimal.ZERO;

	@Column(name = "vlr_custo", precision = 12, scale = 4)
	@DecimalMin(value = "0.01", message = "O campo 'CUSTO' tem quer ser maior que 0,00")
	private BigDecimal vlrCusto = BigDecimal.ZERO;

	@Column(name = "vlr_venda", precision = 12, scale = 2)
	@DecimalMin(value = "0.01", message = "O campo 'VENDA' tem quer ser maior que 0,00")
	private BigDecimal vlrVenda = BigDecimal.ZERO;

	@NotNull
	private Status status;

	public void atualizarEstoque(BigDecimal quantidade) {
		BigDecimal novaQuantidade = this.getQuantidade().add(quantidade);
		if (novaQuantidade.compareTo(BigDecimal.ZERO) < 0) {
			throw new NegocioException(
					String.format("Não ha disponibilidade no estoque de contém %.2f, %s", this.quantidade, this.nome));
		}
		this.setQuantidade(novaQuantidade);
	}

	public void verificaEstoque(BigDecimal qtde) {
		BigDecimal novaQuantidade = this.getQuantidade().subtract(qtde);
		if (novaQuantidade.compareTo(BigDecimal.ZERO) < 0) {
			throw new NegocioException(
					String.format("Não ha disponibilidade no estoque de contém %.2f, %s", this.quantidade, this.nome));
		}
	}

	public void adicionarEstoque(BigDecimal quantidade) {
		this.setQuantidade(this.getQuantidade().add(quantidade));
	}

	public void baixarEstoque(BigDecimal quantidade) {
		this.setQuantidade(this.getQuantidade().subtract(quantidade));
	}

}
