package one.innovation.digital.api.controller;

import java.util.List;

import javax.validation.Valid;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import one.innovation.digital.api.dto.entity.PessoaEntity;
import one.innovation.digital.api.dto.input.PessoaInput;
import one.innovation.digital.api.mapper.PessoaMapper;
import one.innovation.digital.domain.entity.Pessoa;
import one.innovation.digital.domain.service.PessoaService;

@RestController
@RequestMapping("/pessoas")
@AllArgsConstructor
public class PessoaController {

	private PessoaService pessoaService;

	private PessoaMapper mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public PessoaEntity adicionar(@Valid @RequestBody PessoaInput pessoa) {
		Pessoa pessoaSalva = pessoaService.adicionar(mapper.toDomain(pessoa));
		return mapper.toEntity(pessoaSalva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<PessoaEntity> atualizar(@Valid @RequestBody PessoaInput input, @PathVariable Long id) {
		Pessoa pessoaSalva = pessoaService.buscarPorId(id);
		mapper.copyToDomainObject(input, pessoaSalva);
		pessoaService.atualizar(pessoaSalva);
		return ResponseEntity.ok(mapper.toEntity(pessoaSalva));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		pessoaService.buscarPorId(id);
		pessoaService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<Pessoa>> listarTodos() {
		return ResponseEntity.ok(pessoaService.listarTodos());
	}

	@GetMapping("/{id}")
	public ResponseEntity<PessoaEntity> buscarPorId(@PathVariable Long id) {
		Pessoa pessoa = pessoaService.buscarPorId(id);
		return ResponseEntity.ok(mapper.toEntity(pessoa));
	}

}
