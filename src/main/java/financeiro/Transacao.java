package financeiro;

import java.util.Objects;

public class Transacao {
    private int numeroPedido;
    private double valorItens;
    private double valorGorjeta;

    public Transacao(int numeroPedido, double valorItens, double valorGorjeta) {
        this.numeroPedido = numeroPedido;
        this.valorItens = valorItens;
        this.valorGorjeta = valorGorjeta;
    }

    public int getNumeroPedido() { return numeroPedido; }
    public double getValorItens() { return valorItens; }
    public double getValorGorjeta() { return valorGorjeta; }
    public double getValorTotal() { return valorItens + valorGorjeta; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Transacao transacao = (Transacao) o;
        return numeroPedido == transacao.numeroPedido;
    }

    @Override
    public int hashCode() {
        return Objects.hash(numeroPedido);
    }
}