package one.innovation.digital.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.innovation.digital.api.dto.entity.ProdutoEntity;
import one.innovation.digital.api.dto.input.ProdutoInput;
import one.innovation.digital.domain.entity.Produto;

@Component
public class ProdutoMapper {

	@Autowired
	private ModelMapper modelMapper;

	public ProdutoEntity toEntity(Produto produto) {
		return modelMapper.map(produto, ProdutoEntity.class);
	}

	public List<ProdutoEntity> toCollectionEntity(List<Produto> produtos) {
		return produtos.stream().map(produto -> toEntity(produto)).collect(Collectors.toList());
	}

	public Produto toDomain(ProdutoInput input) {
		return modelMapper.map(input, Produto.class);
	}

	public void copyToDomainObject(ProdutoInput input, Produto produto) {
		modelMapper.map(input, produto);
	}

}
