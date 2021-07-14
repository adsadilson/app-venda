package one.innovation.digital.api.execption;

public class EntidadeNaoEncontradaException extends RuntimeException {

	private static final long serialVersionUID = 1L;

	public EntidadeNaoEncontradaException(String mensagem) {
		super(mensagem);
	}

	public EntidadeNaoEncontradaException(String classe, Long id) {
		super(String.format("Não existe nenhum cadastro de %s com código %d", classe, id));
	}

}
