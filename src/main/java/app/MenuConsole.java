package app;

import cardapio.ItemCardapio;
import pedido.Pedido;

import java.util.Collection;
import java.util.HashSet;
import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuConsole {
    private Scanner sc = new Scanner(System.in);

    public void exibirMenuPrincipal() {
        System.out.println("\n=== RESTAURANTE ===");
        System.out.println("1 - Registrar novo pedido");
        System.out.println("2 - Listar pedidos do dia");
        System.out.println("3 - Fechar restaurante");
        System.out.println("4 - Modificar um pedido existente (Nível 3)");
        System.out.println("5 - Registrar gorjeta em um pedido (Bônus)");
        System.out.println("0 - Sair");
    }

    public int lerInteiroSeguro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                int valor = sc.nextInt();
                sc.nextLine();
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Entrada inválida! Por favor, digite apenas números inteiros.");
                sc.nextLine();
            }
        }
    }

    public double lerDoubleSeguro(String mensagem) {
        while (true) {
            try {
                System.out.print(mensagem);
                double valor = sc.nextDouble();
                sc.nextLine();
                if (valor < 0) {
                    System.out.println("⚠️ O valor não pode ser negativo.");
                    continue;
                }
                return valor;
            } catch (InputMismatchException e) {
                System.out.println("⚠️ Entrada inválida! Digite um valor numérico (ex: 5,00).");
                sc.nextLine();
            }
        }
    }

    public int lerOpcaoMenu() {
        return lerInteiroSeguro("Escolha uma opção: ");
    }

    public String lerNomeCliente() {
        while (true) {
            System.out.print("Digite o nome do cliente: ");
            String nome = sc.nextLine();
            if (nome != null && !nome.trim().isEmpty()) {
                return nome.trim();
            }
            System.out.println("⚠️ O nome do cliente não pode ficar em branco!");
        }
    }

    public int lerOpcaoLanche() {
        System.out.println("\n--- CARDÁPIO DE LANCHES ---");
        System.out.println("1 - X-Burger (R$ 16,00)");
        System.out.println("2 - X-Salada (R$ 18,00)");
        System.out.println("3 - X-Bacon  (R$ 22,00)");
        while (true) {
            int op = lerInteiroSeguro("Escolha o lanche (1 a 3): ");
            if (op >= 1 && op <= 3) return op;
            System.out.println("⚠️ Opção inexistente! Escolha 1, 2 ou 3.");
        }
    }

    public boolean perguntarSimNao(String pergunta) {
        while (true) {
            System.out.print(pergunta + " (S/N): ");
            String resp = sc.nextLine().trim().toUpperCase();
            if (resp.equals("S")) return true;
            if (resp.equals("N")) return false;
            System.out.println("⚠️ Responda apenas com S para Sim ou N para Não.");
        }
    }

    public int lerOpcaoAdicional() {
        System.out.println("\n--- ADICIONAIS ---");
        System.out.println("1 - + Cheddar (R$ 4,00)");
        System.out.println("2 - + Bacon Extra (R$ 6,00)");
        System.out.println("0 - Nenhum adicional / Concluir lanche");
        while (true) {
            int op = lerInteiroSeguro("Escolha um adicional (0 a 2): ");
            if (op >= 0 && op <= 2) return op;
            System.out.println("⚠️ Opção inválida! Escolha 0, 1 ou 2.");
        }
    }

    public int lerMenuModificacao() {
        System.out.println("\n--- MODIFICAR PEDIDO ---");
        System.out.println("1 - Acrescentar um novo item");
        System.out.println("2 - Remover um item existente");
        System.out.println("3 - Cancelar o pedido inteiro");
        System.out.println("0 - Voltar");
        while (true) {
            int op = lerInteiroSeguro("Escolha o que deseja fazer (0 a 3): ");
            if (op >= 0 && op <= 3) return op;
            System.out.println("⚠️ Opção inválida! Escolha entre 0 e 3.");
        }
    }

    public void exibirMensagem(String msg) {
        System.out.println(msg);
    }

    public void exibirPedido(Pedido p) {
        System.out.println("\n-----------------------------------------");
        System.out.println("Pedido #" + p.getNumero() + " | Cliente: " + p.getNomeCliente());
        if (p.getItens().isEmpty()) {
            System.out.println("  (Nenhum item neste pedido)");
        } else {
            for (int i = 0; i < p.getItens().size(); i++) {
                ItemCardapio item = p.getItens().get(i);
                System.out.printf("  %d. %s — R$ %.2f%n", (i + 1), item.getDescricao(), item.getPreco());
            }
        }
        System.out.printf("Total dos Itens: R$ %.2f%n", p.calcularTotal());
        if (p.getGorjeta() > 0) {
            System.out.printf("Gorjeta: R$ %.2f | Total Geral: R$ %.2f%n",
                    p.getGorjeta(), (p.calcularTotal() + p.getGorjeta()));
        }
        System.out.println("-----------------------------------------");
    }

    public void exibirListaPedidos(Collection<Pedido> pedidos) {
        if (pedidos.isEmpty()) {
            System.out.println("\n📭 Nenhum pedido registrado hoje ainda.");
            return;
        }
        System.out.println("\n=== PEDIDOS DO DIA ===");
        for (Pedido p : pedidos) {
            exibirPedido(p);
        }
    }

    public void exibirFechamentoDoDia(double totalVendido, double totalGorjetas,
                                      HashSet<String> combinacoes, HashSet<String> lanchesBase) {
        System.out.println("\n=========================================");
        System.out.println("       FECHAMENTO DO DIA - RELATÓRIO     ");
        System.out.println("=========================================");
        System.out.printf("💰 Total vendido em lanches: R$ %.2f%n", totalVendido);
        System.out.printf("🪙 Total de gorjetas (Bônus): R$ %.2f%n", totalGorjetas);
        System.out.printf("💵 Faturamento Geral no Caixa: R$ %.2f%n", (totalVendido + totalGorjetas));

        System.out.println("\n🍔 Combinações diferentes que saíram hoje (" + combinacoes.size() + "):");
        for (String c : combinacoes) {
            System.out.println("  • " + c);
        }

        System.out.println("\n🥖 Lanches base diferentes que saíram hoje (" + lanchesBase.size() + "):");
        for (String b : lanchesBase) {
            System.out.println("  • " + b);
        }
        System.out.println("=========================================");
    }
}