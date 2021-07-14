package one.innovation.digital.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.innovation.digital.api.dto.entity.VendaCabEntity;
import one.innovation.digital.api.dto.input.VendaCabInput;
import one.innovation.digital.domain.entity.VendaCab;

@Component
public class VendaCabMapper {

	@Autowired
	private ModelMapper modelMapper;

	public VendaCabEntity toEntity(VendaCab vendaCab) {
		return modelMapper.map(vendaCab, VendaCabEntity.class);
	}

	public List<VendaCabEntity> toCollectionEntity(List<VendaCab> produtos) {
		return produtos.stream().map(vendaCab -> toEntity(vendaCab)).collect(Collectors.toList());
	}

	public VendaCab toDomain(VendaCabInput input) {
		return modelMapper.map(input, VendaCab.class);
	}

	public void copyToDomainObject(VendaCabInput input, VendaCab vendaCab) {
		modelMapper.map(input, vendaCab);
	}

}
