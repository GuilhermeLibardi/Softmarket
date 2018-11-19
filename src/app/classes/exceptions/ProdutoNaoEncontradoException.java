package app.classes.exceptions;

public class ProdutoNaoEncontradoException extends Exception {
    public ProdutoNaoEncontradoException() {
        super("Produto não encontrado no banco de dados");
    }
}
