
import javax.swing.JOptionPane;

public class SistemaPetShop2 {

    public static void main(String[] args) {

        // Criacao dos objetos da classe Produto
        Produto petisco = new Produto("Petisco", 12.90, 0, 1);
        Produto racao = new Produto("Racao", 59.90, 0, 2);
        Produto brinquedo = new Produto("Brinquedo", 24.90, 0, 3);

        // Construtor padrao e uso dos setters
        Produto shampoo = new Produto();
        shampoo.setNome("Shampoo");
        shampoo.setPreco(19.90);
        shampoo.setCodigo(4);

        // Variaveis de controle e estatisticas
        int estoqueMinimo = 5;
        int quantidadeVendas = 0;

        double totalFaturado = 0;
        double maiorVenda = 0;
        double menorVenda = 0;
        double mediaVendas = 0;

        // ============================================
        // CADASTRO INICIAL DO ESTOQUE
        // ============================================

        JOptionPane.showMessageDialog(null,
                "CADASTRO INICIAL DO ESTOQUE\n" +
                        "Informe a quantidade disponivel de cada produto.");

        if (!cadastrarEstoqueInicial(petisco, estoqueMinimo)
                || !cadastrarEstoqueInicial(racao, estoqueMinimo)
                || !cadastrarEstoqueInicial(brinquedo, estoqueMinimo)
                || !cadastrarEstoqueInicial(shampoo, estoqueMinimo)) {

            JOptionPane.showMessageDialog(null,
                    "Cadastro cancelado. Sistema encerrado.");
            return;
        }

        int opcao = 0;

        // ============================================
        // MENU PRINCIPAL
        // ============================================

        do {

            Integer escolha = lerInteiro(
                    "===== SISTEMA PETSHOP =====\n\n" +
                            "1 - Registrar Venda\n" +
                            "2 - Consultar Estoque\n" +
                            "3 - Gerenciar Produtos\n" +
                            "4 - Sair\n\n" +
                            "Escolha uma opcao:",
                    1, 4);

            if (escolha == null) {
                opcao = 4;
            } else {
                opcao = escolha;
            }

            switch (opcao) {

                // ============================================
                // OPCAO 1 - REGISTRAR VENDA
                // ============================================

                case 1: {

                    // Validacao da senha do operador
                    String senha = JOptionPane.showInputDialog(
                            "Digite a senha do operador.\n" +
                                    "Minimo de 4 caracteres, comecando com P:");

                    while (senha != null &&
                            (senha.trim().length() < 4 ||
                                    !senha.trim().toUpperCase().startsWith("P"))) {

                        JOptionPane.showMessageDialog(null,
                                "Senha invalida! Use 4 caracteres ou mais, comecando com P.");

                        senha = JOptionPane.showInputDialog(
                                "Digite a senha novamente:");
                    }

                    if (senha == null) {
                        JOptionPane.showMessageDialog(null,
                                "Venda cancelada.");
                        break;
                    }

                    // Cadastro do cliente
                    String nomeCliente = JOptionPane.showInputDialog(
                            "Digite o nome do cliente:");

                    if (nomeCliente == null || nomeCliente.trim().isEmpty()) {
                        nomeCliente = "CLIENTE NAO INFORMADO";
                    } else {
                        nomeCliente = nomeCliente.trim().toUpperCase();
                    }

                    // Escolha do produto
                    Produto produto = escolherProduto(
                            petisco, racao, brinquedo, shampoo);

                    if (produto == null) {
                        JOptionPane.showMessageDialog(null,
                                "Venda cancelada.");
                        break;
                    }

                    // Define categoria e custo do produto
                    String categoria;
                    double custoUnitario;

                    switch (produto.getCodigo()) {

                        case 1:
                            categoria = "Alimento";
                            custoUnitario = 7.00;
                            break;

                        case 2:
                            categoria = "Alimento";
                            custoUnitario = 40.00;
                            break;

                        case 3:
                            categoria = "Brinquedo";
                            custoUnitario = 15.00;
                            break;

                        default:
                            categoria = "Higiene";
                            custoUnitario = 11.00;
                            break;
                    }

                    // Calcula o valor do estoque
                    double valorEstoque = produto.getPreco() * produto.getQuantidadeEstoque();

                    JOptionPane.showMessageDialog(null,
                            String.format(
                                    "Produto: %s\n" +
                                            "Categoria: %s\n" +
                                            "Preco: R$ %.2f\n" +
                                            "Estoque: %d\n" +
                                            "Valor em estoque: R$ %.2f",
                                    produto.getNome().toUpperCase(),
                                    categoria,
                                    produto.getPreco(),
                                    produto.getQuantidadeEstoque(),
                                    valorEstoque));

                    // Quantidade desejada pelo cliente
                    Integer quantidade = lerInteiro(
                            "Quantidade que o cliente deseja comprar:",
                            1, Integer.MAX_VALUE);

                    if (quantidade == null) {
                        JOptionPane.showMessageDialog(null,
                                "Venda cancelada.");
                        break;
                    }

                    // Verifica o estoque disponivel
                    if (quantidade > produto.getQuantidadeEstoque()) {

                        JOptionPane.showMessageDialog(null,
                                "Estoque insuficiente!\n" +
                                        "Disponivel: " + produto.getQuantidadeEstoque());

                        break;
                    }

                    // Forma de pagamento
                    Integer pagamento = lerInteiro(
                            "Escolha a forma de pagamento:\n\n" +
                                    "1 - Dinheiro\n" +
                                    "2 - PIX\n" +
                                    "3 - Cartao",
                            1, 3);

                    if (pagamento == null) {
                        JOptionPane.showMessageDialog(null,
                                "Venda cancelada.");
                        break;
                    }

                    // ============================================
                    // CALCULOS DA VENDA
                    // ============================================

                    double valorCompra = produto.getPreco() * quantidade;
                    double desconto = 0;

                    // Desconto progressivo
                    if (valorCompra >= 200) {
                        desconto = valorCompra * 0.10;

                    } else if (valorCompra >= 100) {
                        desconto = valorCompra * 0.05;
                    }

                    // Desconto pela forma de pagamento
                    if (pagamento == 1) {

                        desconto = desconto + (valorCompra * 0.05);

                    } else if (pagamento == 2) {

                        desconto = desconto + (valorCompra * 0.10);
                    }

                    // Valor final
                    double valorFinal = valorCompra - desconto;

                    // Lucro e margem de lucro
                    double lucro = valorFinal - (custoUnitario * quantidade);

                    double margemLucro = (lucro / valorFinal) * 100;

                    // ============================================
                    // ATUALIZACAO DO ESTOQUE
                    // ============================================

                    // Chamada do metodo da classe Produto
                    if (!produto.darBaixaEstoque(quantidade)) {

                        JOptionPane.showMessageDialog(null,
                                "Nao foi possivel dar baixa no estoque.");
                        break;
                    }

                    // Verifica estoque minimo
                    if (produto.getQuantidadeEstoque() < estoqueMinimo) {

                        JOptionPane.showMessageDialog(null,
                                "ATENCAO!\n" +
                                        "Estoque de " + produto.getNome() +
                                        " abaixo do minimo!");
                    }

                    // ============================================
                    // ACUMULADORES E ESTATISTICAS DIARIAS
                    // ============================================

                    if (quantidadeVendas == 0) {

                        maiorVenda = valorFinal;
                        menorVenda = valorFinal;

                    } else {

                        if (valorFinal > maiorVenda) {
                            maiorVenda = valorFinal;
                        }

                        if (valorFinal < menorVenda) {
                            menorVenda = valorFinal;
                        }
                    }

                    totalFaturado = totalFaturado + valorFinal;
                    quantidadeVendas++;

                    mediaVendas = totalFaturado / quantidadeVendas;

                    // ============================================
                    // COMPROVANTE DA VENDA
                    // ============================================

                    String comprovante = String.format(
                            "===== PETSHOP =====\n\n" +
                                    "Cliente: %s\n" +
                                    "Produto: %s\n" +
                                    "Categoria: %s\n" +
                                    "Preco unitario: R$ %.2f\n" +
                                    "Quantidade comprada: %d\n\n" +
                                    "Valor da compra: R$ %.2f\n" +
                                    "Desconto: R$ %.2f\n" +
                                    "Valor final: R$ %.2f\n\n" +
                                    "Lucro estimado: R$ %.2f\n" +
                                    "Margem de lucro: %.2f%%\n" +
                                    "Estoque restante: %d",

                            nomeCliente,
                            produto.getNome().toUpperCase(),
                            categoria,
                            produto.getPreco(),
                            quantidade,
                            valorCompra,
                            desconto,
                            valorFinal,
                            lucro,
                            margemLucro,
                            produto.getQuantidadeEstoque());

                    JOptionPane.showMessageDialog(null, comprovante);

                    break;
                }

                // ============================================
                // OPCAO 2 - CONSULTAR ESTOQUE
                // ============================================

                case 2: {

                    double valorTotalEstoque = petisco.getPreco() * petisco.getQuantidadeEstoque()
                            + racao.getPreco() * racao.getQuantidadeEstoque()
                            + brinquedo.getPreco() * brinquedo.getQuantidadeEstoque()
                            + shampoo.getPreco() * shampoo.getQuantidadeEstoque();

                    String consulta = "===== ESTOQUE DO PETSHOP =====\n\n" +

                            resumoProduto(petisco, "Alimento", estoqueMinimo) +

                            resumoProduto(racao, "Alimento", estoqueMinimo) +

                            resumoProduto(brinquedo, "Brinquedo", estoqueMinimo) +

                            resumoProduto(shampoo, "Higiene", estoqueMinimo) +

                            String.format(
                                    "Valor total em estoque: R$ %.2f\n" +
                                            "Estoque minimo: %d unidades",
                                    valorTotalEstoque,
                                    estoqueMinimo);

                    JOptionPane.showMessageDialog(null, consulta);

                    break;
                }

                // ============================================
                // OPCAO 3 - GERENCIAR PRODUTOS
                // ============================================

                case 3: {

                    Integer acao = lerInteiro(
                            "===== GERENCIAR PRODUTOS =====\n\n" +
                                    "1 - Alterar preco\n" +
                                    "2 - Aplicar desconto ao preco\n" +
                                    "3 - Adicionar unidades ao estoque",
                            1, 3);

                    if (acao == null) {
                        break;
                    }

                    Produto produto = escolherProduto(
                            petisco, racao, brinquedo, shampoo);

                    if (produto == null) {
                        break;
                    }

                    // ALTERAR PRECO
                    if (acao == 1) {

                        Double novoPreco = lerDecimalPositivo(
                                "Digite o novo preco do produto:");

                        if (novoPreco != null) {

                            produto.setPreco(novoPreco);

                            JOptionPane.showMessageDialog(null,
                                    String.format(
                                            "Novo preco de %s: R$ %.2f",
                                            produto.getNome(),
                                            produto.getPreco()));
                        }

                        // DAR DESCONTO
                    } else if (acao == 2) {

                        Double percentual = lerPercentual(
                                "Qual desconto percentual deseja aplicar?\n" +
                                        "Exemplo: 10 para 10%");

                        if (percentual != null) {

                            produto.darDesconto(percentual);

                            JOptionPane.showMessageDialog(null,
                                    String.format(
                                            "Preco de %s com desconto: R$ %.2f",
                                            produto.getNome(),
                                            produto.getPreco()));
                        }

                        // ADICIONAR ESTOQUE
                    } else {

                        Integer qtd = lerInteiro(
                                "Quantas unidades deseja adicionar?",
                                1, Integer.MAX_VALUE);

                        if (qtd != null) {

                            try {

                                produto.adicionarEstoque(qtd);

                                JOptionPane.showMessageDialog(null,
                                        "Estoque atualizado de " +
                                                produto.getNome() +
                                                ": " +
                                                produto.getQuantidadeEstoque());

                            } catch (IllegalArgumentException erro) {

                                JOptionPane.showMessageDialog(null,
                                        erro.getMessage());
                            }
                        }
                    }

                    break;
                }

                // ============================================
                // OPCAO 4 - SAIR E MOSTRAR ESTATISTICAS
                // ============================================

                case 4: {

                    if (quantidadeVendas > 0) {

                        JOptionPane.showMessageDialog(null,
                                String.format(
                                        "===== RESUMO DO DIA =====\n\n" +
                                                "Quantidade de vendas: %d\n" +
                                                "Total faturado: R$ %.2f\n" +
                                                "Maior venda: R$ %.2f\n" +
                                                "Menor venda: R$ %.2f\n" +
                                                "Media das vendas: R$ %.2f",

                                        quantidadeVendas,
                                        totalFaturado,
                                        maiorVenda,
                                        menorVenda,
                                        mediaVendas));

                    } else {

                        JOptionPane.showMessageDialog(null,
                                "Nenhuma venda foi registrada hoje.");
                    }

                    JOptionPane.showMessageDialog(null,
                            "Sistema encerrado. Obrigado!");

                    break;
                }
            }

        } while (opcao != 4);
    }

    // ============================================
    // METODO PARA CADASTRAR ESTOQUE INICIAL
    // ============================================

    private static boolean cadastrarEstoqueInicial(
            Produto produto, int estoqueMinimo) {

        Integer quantidade = lerInteiro(
                "Digite a quantidade inicial de " + produto.getNome() + ":",
                0, Integer.MAX_VALUE);

        if (quantidade == null) {
            return false;
        }

        produto.setQuantidadeEstoque(quantidade);

        if (produto.getQuantidadeEstoque() < estoqueMinimo) {

            JOptionPane.showMessageDialog(null,
                    "ATENCAO! Estoque de " + produto.getNome() +
                            " abaixo do minimo.");
        }

        return true;
    }

    // ============================================
    // METODO PARA ESCOLHER PRODUTO
    // ============================================

    private static Produto escolherProduto(
            Produto petisco,
            Produto racao,
            Produto brinquedo,
            Produto shampoo) {

        String escolha = JOptionPane.showInputDialog(
                "Escolha pelo numero ou pelo nome:\n\n" +
                        "1 - Petisco\n" +
                        "2 - Racao\n" +
                        "3 - Brinquedo\n" +
                        "4 - Shampoo");

        if (escolha == null) {
            return null;
        }

        escolha = escolha.trim();

        if (escolha.equals("1") ||
                escolha.equalsIgnoreCase("Petisco")) {

            return petisco;

        } else if (escolha.equals("2") ||
                escolha.equalsIgnoreCase("Racao") ||
                escolha.equalsIgnoreCase("Ração")) {

            return racao;

        } else if (escolha.equals("3") ||
                escolha.equalsIgnoreCase("Brinquedo")) {

            return brinquedo;

        } else if (escolha.equals("4") ||
                escolha.equalsIgnoreCase("Shampoo")) {

            return shampoo;
        }

        JOptionPane.showMessageDialog(null,
                "Produto nao encontrado!");

        return null;
    }

    // ============================================
    // METODO PARA MOSTRAR O ESTOQUE
    // ============================================

    private static String resumoProduto(
            Produto produto,
            String categoria,
            int estoqueMinimo) {

        String situacao = "Normal";

        if (produto.getQuantidadeEstoque() < estoqueMinimo) {
            situacao = "ABAIXO DO MINIMO";
        }

        return String.format(
                "%s (%s) - Codigo: %d\n" +
                        "Quantidade: %d\n" +
                        "Preco unitario: R$ %.2f\n" +
                        "Valor em estoque: R$ %.2f\n" +
                        "Situacao: %s\n\n",

                produto.getNome(),
                categoria,
                produto.getCodigo(),
                produto.getQuantidadeEstoque(),
                produto.getPreco(),
                produto.getQuantidadeEstoque() * produto.getPreco(),
                situacao);
    }

    // ============================================
    // METODO PARA VALIDAR NUMEROS INTEIROS
    // ============================================

    private static Integer lerInteiro(
            String mensagem, int minimo, int maximo) {

        while (true) {

            String texto = JOptionPane.showInputDialog(mensagem);

            if (texto == null) {
                return null;
            }

            try {

                int valor = Integer.parseInt(texto.trim());

                if (valor >= minimo && valor <= maximo) {
                    return valor;
                }

            } catch (NumberFormatException erro) {
                // Entrada invalida
            }

            JOptionPane.showMessageDialog(null,
                    "Digite um numero inteiro valido entre " +
                            minimo + " e " + maximo + ".");
        }
    }

    // ============================================
    // METODO PARA VALIDAR PRECO
    // ============================================

    private static Double lerDecimalPositivo(String mensagem) {

        while (true) {

            String texto = JOptionPane.showInputDialog(mensagem);

            if (texto == null) {
                return null;
            }

            try {

                double valor = Double.parseDouble(
                        texto.trim().replace(",", "."));

                if (Double.isFinite(valor) && valor > 0) {
                    return valor;
                }

            } catch (NumberFormatException erro) {
                // Entrada invalida
            }

            JOptionPane.showMessageDialog(null,
                    "Digite um preco valido maior que zero!");
        }
    }

    // ============================================
    // METODO PARA VALIDAR DESCONTO PERCENTUAL
    // ============================================

    private static Double lerPercentual(String mensagem) {

        while (true) {

            String texto = JOptionPane.showInputDialog(mensagem);

            if (texto == null) {
                return null;
            }

            try {

                double valor = Double.parseDouble(
                        texto.trim().replace(",", "."));

                if (Double.isFinite(valor) &&
                        valor > 0 && valor < 100) {

                    return valor;
                }

            } catch (NumberFormatException erro) {
                // Entrada invalida
            }

            JOptionPane.showMessageDialog(null,
                    "Digite um percentual maior que 0 e menor que 100!");
        }
    }
}
