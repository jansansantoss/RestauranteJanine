package pedido;

import cardapio.ItemCardapio;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;

public class GerenciadorPedidos {
    private HashMap<Integer, Pedido> pedidos = new HashMap<>();
    private int contadorPedidos = 1;

    public Pedido criarPedido(String nomeCliente) {
        Pedido novo = new Pedido(contadorPedidos, nomeCliente);
        pedidos.put(novo.getNumero(), novo);
        contadorPedidos++;
        return novo;
    }

    public Pedido buscarPedido(int numero) {
        return pedidos.get(numero);
    }

    public boolean cancelarPedido(int numero) {
        if (pedidos.containsKey(numero)) {
            pedidos.remove(numero);
            return true;
        }
        return false;
    }

    public Collection<Pedido> getPedidos() {
        return pedidos.values();
    }

    public double calcularTotalDoDia() {
        double total = 0.0;
        for (Pedido p : pedidos.values()) {
            total += p.calcularTotal();
        }
        return total;
    }

    public double calcularTotalGorjetasDoDia() {
        double totalGorjetas = 0.0;
        for (Pedido p : pedidos.values()) {
            totalGorjetas += p.getGorjeta();
        }
        return totalGorjetas;
    }

    public HashSet<String> obterCombinacoesDiferentes() {
        HashSet<String> combinacoes = new HashSet<>();
        for (Pedido p : pedidos.values()) {
            for (ItemCardapio item : p.getItens()) {
                combinacoes.add(item.getDescricao());
            }
        }
        return combinacoes;
    }

    public HashSet<String> obterLanchesBaseDiferentes() {
        HashSet<String> bases = new HashSet<>();
        for (Pedido p : pedidos.values()) {
            for (ItemCardapio item : p.getItens()) {
                bases.add(item.getNomeBase());
            }
        }
        return bases;
    }
}