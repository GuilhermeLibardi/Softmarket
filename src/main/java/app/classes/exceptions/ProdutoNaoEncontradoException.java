package app.classes.exceptions;

public class ProdutoNaoEncontradoException extends Exception {
    public ProdutoNaoEncontradoException(String message) {
        super("Produto não encontrado no banco de dados " + message);
    }
}
