package pedido;

import cardapio.ItemCardapio;
import java.util.ArrayList;
import java.util.List;

public class Pedido {
    private int numero;
    private String nomeCliente;
    private List<ItemCardapio> itens;
    private double gorjeta;

    public Pedido(int numero, String nomeCliente) {
        this.numero = numero;
        this.nomeCliente = nomeCliente;
        this.itens = new ArrayList<>();
        this.gorjeta = 0.0;
    }

    public int getNumero() { return numero; }
    public String getNomeCliente() { return nomeCliente; }
    public List<ItemCardapio> getItens() { return itens; }
    public double getGorjeta() { return gorjeta; }

    public void adicionarItem(ItemCardapio item) {
        itens.add(item);
    }

    public void removerItem(int indice) {
        if (indice >= 0 && indice < itens.size()) {
            itens.remove(indice);
        } else {
            throw new IllegalArgumentException("Índice de item inválido.");
        }
    }

    public double calcularTotal() {
        double total = 0.0;
        for (ItemCardapio item : itens) {
            total += item.getPreco();
        }
        return total;
    }

    public void adicionarGorjeta(double valor) {
        if (valor > 0) this.gorjeta += valor;
    }
}