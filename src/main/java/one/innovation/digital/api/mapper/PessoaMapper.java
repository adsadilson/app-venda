package one.innovation.digital.api.mapper;

import java.util.List;
import java.util.stream.Collectors;

import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import one.innovation.digital.api.dto.entity.PessoaEntity;
import one.innovation.digital.api.dto.input.PessoaInput;
import one.innovation.digital.domain.entity.Pessoa;

@Component
public class PessoaMapper {

	@Autowired
	private ModelMapper modelMapper;

	public PessoaEntity toEntity(Pessoa pessoa) {
		return modelMapper.map(pessoa, PessoaEntity.class);
	}

	public List<PessoaEntity> toCollectionEntity(List<Pessoa> pessoas) {
		return pessoas.stream().map(pessoa -> toEntity(pessoa)).collect(Collectors.toList());
	}

	public Pessoa toDomain(PessoaInput input) {
		return modelMapper.map(input, Pessoa.class);
	}

	public void copyToDomainObject(PessoaInput input, Pessoa pessoa) {
		modelMapper.map(input, pessoa);
	}

}
