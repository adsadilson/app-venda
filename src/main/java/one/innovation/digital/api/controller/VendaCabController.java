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
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import one.innovation.digital.api.dto.entity.VendaCabEntity;
import one.innovation.digital.api.dto.input.VendaCabInput;
import one.innovation.digital.api.mapper.VendaCabMapper;
import one.innovation.digital.domain.entity.VendaCab;
import one.innovation.digital.domain.service.VendaCabService;

@RestController
@RequestMapping("/vendas")
@AllArgsConstructor
public class VendaCabController {

	private VendaCabService vendaCabService;

	private VendaCabMapper mapper;

	@PostMapping
	public ResponseEntity<String> adicionar(@Valid @RequestBody VendaCabInput vendaCab) {
		vendaCabService.adicionar(mapper.toDomain(vendaCab));
		return new ResponseEntity<String>("Venda realizada com sucesso!!!", HttpStatus.CREATED);
	}

	@PutMapping("/{id}")
	public ResponseEntity<VendaCab> atualizar(@Valid @RequestBody VendaCab vendaCab, @PathVariable Long id) {
		return ResponseEntity.ok(vendaCabService.atualizar(vendaCab, id));
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<String> excluir(@PathVariable Long id) {
		vendaCabService.buscarPorId(id);
		vendaCabService.excluir(id);
		return new ResponseEntity<String>("Venda excluida com sucesso!!!", HttpStatus.NO_CONTENT);
	}

	@GetMapping
	public ResponseEntity<List<VendaCabEntity>> listarTodos() {
		return ResponseEntity.ok(mapper.toCollectionEntity(vendaCabService.listarTodos()));
	}

	@GetMapping("/{id}")
	public ResponseEntity<VendaCabEntity> buscarPorId(@PathVariable Long id) {
		VendaCab vendaCab = vendaCabService.buscarPorId(id);
		return ResponseEntity.ok(mapper.toEntity(vendaCab));
	}

}
