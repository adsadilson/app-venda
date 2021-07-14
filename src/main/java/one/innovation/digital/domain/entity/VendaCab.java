package one.innovation.digital.domain.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Table;
import javax.validation.constraints.NotNull;

import org.hibernate.annotations.CreationTimestamp;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import one.innovation.digital.domain.enums.FormaPagamento;
import one.innovation.digital.domain.enums.StatusVenda;

@Entity
@Table(name = "venda_cab")
@SequenceGenerator(name = "VENDA_CAB_ID", sequenceName = "VENDA_CAB_ID_SEQ")
@Data
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
public class VendaCab implements Serializable {

	private static final long serialVersionUID = 1L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY, generator = "VENDA_CAB_ID_SEQ")
	@EqualsAndHashCode.Include
	private Long id;

	@NotNull	
	@Enumerated(EnumType.STRING)
	@Column(length = 25)
	private FormaPagamento formaPagto;

	@CreationTimestamp
	private LocalDate data;

	@CreationTimestamp
	private LocalDateTime hora;

	@Column(precision = 10, scale = 2)
	private BigDecimal valor;

	@ManyToOne
	@JoinColumn(name = "cliente_id")
	private Pessoa cliente;

	@NotNull
	@OneToMany(mappedBy = "vendaCab", cascade = CascadeType.ALL, orphanRemoval = true, fetch = FetchType.LAZY)
	private List<VendaDet> itens = new ArrayList<>();

	@NotNull
	@Enumerated(EnumType.STRING)
	@Column(length = 15)
	private StatusVenda status;

}
