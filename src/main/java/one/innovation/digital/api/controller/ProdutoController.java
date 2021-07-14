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
import one.innovation.digital.api.dto.entity.ProdutoEntity;
import one.innovation.digital.api.dto.input.ProdutoInput;
import one.innovation.digital.api.mapper.ProdutoMapper;
import one.innovation.digital.domain.entity.Produto;
import one.innovation.digital.domain.service.ProdutoService;

@RestController
@RequestMapping("/produtos")
@AllArgsConstructor
public class ProdutoController {

	private ProdutoService produtoService;
	
	private ProdutoMapper mapper;

	@PostMapping
	@ResponseStatus(HttpStatus.CREATED)
	public ProdutoEntity adicionar(@Valid @RequestBody ProdutoInput input) {
		Produto produtoSalva = produtoService.adicionar(mapper.toDomain(input));
		return mapper.toEntity(produtoSalva);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ProdutoEntity> atualizar(@Valid @RequestBody ProdutoInput input, @PathVariable Long id) {
		Produto produtoAtualizado = produtoService.buscarPorId(id);
		mapper.copyToDomainObject(input, produtoAtualizado);
		return ResponseEntity.ok(mapper.toEntity(produtoAtualizado));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<?> excluir(@PathVariable Long id) {
		produtoService.buscarPorId(id);
		produtoService.excluir(id);
		return ResponseEntity.noContent().build();
	}

	@GetMapping
	public ResponseEntity<List<ProdutoEntity>> listarTodos() {
		return ResponseEntity.ok(mapper.toCollectionEntity(produtoService.listarTodos()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<ProdutoEntity> buscarPorId(@PathVariable Long id) {
		Produto produto = produtoService.buscarPorId(id);
		return ResponseEntity.ok(mapper.toEntity(produto));
	}
	
	@GetMapping("/{codigo}/codigo-barra")
	public ResponseEntity<ProdutoEntity> buscarPorCodigoBarra(@PathVariable String codigo) {
		Produto produto = produtoService.buscarPorCodigoBarra(codigo);
		return ResponseEntity.ok(mapper.toEntity(produto));
	}
	
	@GetMapping("/{nome}/nome")
	public ResponseEntity<List<ProdutoEntity>> consultarPorNome(@PathVariable String nome) {
		List<Produto> produto = produtoService.consultarPorNome(nome);
		return ResponseEntity.ok(mapper.toCollectionEntity(produto));
	}

}
