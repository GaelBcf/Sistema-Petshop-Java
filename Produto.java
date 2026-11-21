
public class Produto {

    // Atributos privados
    private String nome;
    private double preco;
    private int quantidadeEstoque;
    private int codigo;

    // Construtor padrao
    public Produto() {
        this("Produto sem nome", 1.00, 0, 0);
    }

    // Construtor com parametros
    public Produto(String nome, double preco, int quantidadeEstoque, int codigo) {
        this.setNome(nome);
        this.setPreco(preco);
        this.setQuantidadeEstoque(quantidadeEstoque);
        this.setCodigo(codigo);
    }

    // GETTERS E SETTERS

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) {
            throw new IllegalArgumentException("O nome nao pode ser vazio.");
        }
        this.nome = nome.trim();
    }

    public double getPreco() {
        return preco;
    }

    public void setPreco(double preco) {
        if (Double.isNaN(preco) || Double.isInfinite(preco) || preco <= 0) {
            throw new IllegalArgumentException("O preco deve ser maior que zero.");
        }
        this.preco = preco;
    }

    public int getQuantidadeEstoque() {
        return quantidadeEstoque;
    }

    public void setQuantidadeEstoque(int quantidadeEstoque) {
        if (quantidadeEstoque < 0) {
            throw new IllegalArgumentException("O estoque nao pode ser negativo.");
        }
        this.quantidadeEstoque = quantidadeEstoque;
    }

    public int getCodigo() {
        return codigo;
    }

    public void setCodigo(int codigo) {
        if (codigo < 0) {
            throw new IllegalArgumentException("O codigo nao pode ser negativo.");
        }
        this.codigo = codigo;
    }

    // METODOS DE NEGOCIO

    // Aplica desconto percentual ao preco do produto
    public void darDesconto(double percentual) {
        if (Double.isNaN(percentual) || Double.isInfinite(percentual)
                || percentual <= 0 || percentual >= 100) {
            throw new IllegalArgumentException(
                    "O desconto deve ser maior que 0 e menor que 100.");
        }

        setPreco(preco * (1 - percentual / 100));
    }

    // Adiciona unidades ao estoque
    public void adicionarEstoque(int qtd) {
        if (qtd <= 0 || qtd > Integer.MAX_VALUE - quantidadeEstoque) {
            throw new IllegalArgumentException(
                    "A quantidade para reposicao e invalida.");
        }

        setQuantidadeEstoque(quantidadeEstoque + qtd);
    }

    // Retira unidades do estoque
    public boolean darBaixaEstoque(int qtd) {
        if (qtd <= 0) {
            throw new IllegalArgumentException(
                    "A quantidade de venda deve ser positiva.");
        }

        if (qtd > quantidadeEstoque) {
            return false;
        }

        setQuantidadeEstoque(quantidadeEstoque - qtd);
        return true;
    }
}
