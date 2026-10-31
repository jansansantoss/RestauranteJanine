package app;

import cardapio.ItemCardapio;
import cardapio.Lanche;
import pedido.Pedido;

public class Main {
    public static void main(String[] args) {
        RestauranteFacade sistema = new RestauranteFacade();
        MenuConsole menu = new MenuConsole();

        boolean executando = true;
        while (executando) {
            menu.exibirMenuPrincipal();
            int opcao = menu.lerOpcaoMenu();

            switch (opcao) {
                case 1:
                    registrarNovoPedidoFluxo(sistema, menu);
                    break;
                case 2:
                    menu.exibirListaPedidos(sistema.listarPedidos());
                    break;
                case 3:
                    menu.exibirFechamentoDoDia(
                            sistema.calcularTotalDoDia(),
                            sistema.calcularTotalGorjetasDoDia(),
                            sistema.obterCombinacoesDiferentes(),
                            sistema.obterLanchesBaseDiferentes()
                    );
                    break;
                case 4:
                    modificarPedidoFluxo(sistema, menu);
                    break;
                case 5:
                    registrarGorjetaFluxo(sistema, menu);
                    break;
                case 0:
                    menu.exibirMensagem("Encerrando o sistema do restaurante. Até logo!");
                    executando = false;
                    break;
                default:
                    menu.exibirMensagem("⚠️ Opção inválida! Escolha uma opção de 0 a 5.");
            }
        }
    }

    private static ItemCardapio montarItemInterativo(RestauranteFacade sistema, MenuConsole menu) {
        int opLanche = menu.lerOpcaoLanche();
        Lanche lanche = sistema.criarLancheBase(opLanche);

        if (menu.perguntarSimNao("Deseja retirar algum ingrediente deste lanche?")) {
            boolean queijo = !menu.perguntarSimNao("Retirar queijo?");
            boolean alface = !menu.perguntarSimNao("Retirar alface?");
            boolean tomate = !menu.perguntarSimNao("Retirar tomate?");
            boolean cebola = !menu.perguntarSimNao("Retirar cebola?");
            sistema.personalizarIngredientes(lanche, queijo, alface, tomate, cebola);
        }

        ItemCardapio itemFinal = lanche;
        while (true) {
            int opAdicional = menu.lerOpcaoAdicional();
            if (opAdicional == 0) break;
            itemFinal = sistema.aplicarAdicional(itemFinal, opAdicional);
            menu.exibirMensagem("✅ Adicional incluído! Lanche atual: " + itemFinal.getDescricao());
        }
        return itemFinal;
    }

    private static void registrarNovoPedidoFluxo(RestauranteFacade sistema, MenuConsole menu) {
        String nomeCliente = menu.lerNomeCliente();
        Pedido pedido = sistema.criarPedido(nomeCliente);

        boolean adicionando = true;
        while (adicionando) {
            ItemCardapio item = montarItemInterativo(sistema, menu);
            sistema.adicionarItemAoPedido(pedido.getNumero(), item);
            menu.exibirMensagem("🍔 Item adicionado ao Pedido #" + pedido.getNumero());
            adicionando = menu.perguntarSimNao("Deseja adicionar mais um item ao pedido?");
        }
        menu.exibirPedido(pedido);
    }

    private static void modificarPedidoFluxo(RestauranteFacade sistema, MenuConsole menu) {
        int numero = menu.lerInteiroSeguro("Digite o número do pedido que deseja modificar: ");
        Pedido pedido = sistema.buscarPedido(numero);

        if (pedido == null) {
            menu.exibirMensagem("⚠️ Pedido #" + numero + " não encontrado!");
            return;
        }

        menu.exibirPedido(pedido);
        int acao = menu.lerMenuModificacao();

        if (acao == 1) {
            ItemCardapio novoItem = montarItemInterativo(sistema, menu);
            sistema.adicionarItemAoPedido(numero, novoItem);
            menu.exibirMensagem("✅ Novo item acrescentado com sucesso!");
            menu.exibirPedido(pedido);
        } else if (acao == 2) {
            if (pedido.getItens().isEmpty()) {
                menu.exibirMensagem("⚠️ Este pedido não possui itens para remover.");
                return;
            }
            int numItem = menu.lerInteiroSeguro("Digite o número do item que deseja remover (1 a " + pedido.getItens().size() + "): ");
            try {
                sistema.removerItemDoPedido(numero, numItem - 1);
                menu.exibirMensagem("✅ Item removido com sucesso!");
                menu.exibirPedido(pedido);
            } catch (IllegalArgumentException e) {
                menu.exibirMensagem("⚠️ " + e.getMessage());
            }
        } else if (acao == 3) {
            sistema.cancelarPedido(numero);
            menu.exibirMensagem("🗑️ Pedido #" + numero + " foi cancelado e removido do fechamento do dia.");
        }
    }

    private static void registrarGorjetaFluxo(RestauranteFacade sistema, MenuConsole menu) {
        int numero = menu.lerInteiroSeguro("Digite o número do pedido para adicionar gorjeta: ");
        Pedido pedido = sistema.buscarPedido(numero);
        if (pedido == null) {
            menu.exibirMensagem("⚠️ Pedido #" + numero + " não encontrado!");
            return;
        }
        double valor = menu.lerDoubleSeguro("Digite o valor da gorjeta: R$ ");
        sistema.registrarGorjeta(numero, valor);
        menu.exibirMensagem("🪙 Gorjeta registrada com sucesso!");
        menu.exibirPedido(pedido);
    }
}