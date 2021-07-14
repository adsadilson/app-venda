package one.innovation.digital.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "venda_det")
@SequenceGenerator(name = "VENDA_DET_ID", sequenceName = "VENDA_DET_ID_SEQ")
@Data
@AllArgsConstructor
@NoArgsConstructor
public class VendaDet implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "VENDA_DET_ID_SEQ")
	private Long id;

	@Column(nullable = false, length = 3)
	private BigDecimal quantidade = new BigDecimal("1");

	@Column(name = "valor_unitario", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorUnitario = BigDecimal.ZERO;

	@Column(name = "valor_total", nullable = false, precision = 10, scale = 2)
	private BigDecimal valorTotal = BigDecimal.ZERO;

	@ManyToOne
	@JoinColumn(name = "produto_id", nullable = false)
	private Produto produto;

	@ManyToOne
	@JoinColumn(name = "venda_cab_id", nullable = false)
	private VendaCab vendaCab;


	public boolean isInclusao() {
		return getId() == null ? true : false;
	}

	public boolean isEditando() {
		return !isInclusao();
	}


	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((id == null) ? 0 : id.hashCode());
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VendaDet other = (VendaDet) obj;
		if (id == null && other.id == null) {
			if (other.produto.equals(this.produto) && other.getQuantidade().equals(this.getQuantidade())
					&& other.getValorTotal().equals(this.getValorTotal()))
				return true;
			else
				return false;
		} else if (!id.equals(other.id))
			return false;
		return true;
	}

}
