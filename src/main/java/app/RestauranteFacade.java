package app;

import cardapio.*;
import financeiro.Caixa;
import financeiro.Transacao;
import pedido.GerenciadorPedidos;
import pedido.Pedido;

import java.util.Collection;
import java.util.HashSet;

public class RestauranteFacade {
    private GerenciadorPedidos gerenciadorPedidos;
    private Caixa caixa;

    public RestauranteFacade() {
        this.gerenciadorPedidos = new GerenciadorPedidos();
        this.caixa = Caixa.getInstance();
    }

    public Pedido criarPedido(String nomeCliente) {
        Pedido p = gerenciadorPedidos.criarPedido(nomeCliente);
        atualizarCaixa(p);
        return p;
    }

    public Lanche criarLancheBase(int opcaoCardapio) {
        switch (opcaoCardapio) {
            case 1: return LancheFactory.xBurger();
            case 2: return LancheFactory.xSalada();
            case 3: return LancheFactory.xBacon();
            default: throw new IllegalArgumentException("Opção de lanche inválida.");
        }
    }

    public void personalizarIngredientes(Lanche lanche, boolean queijo, boolean alface, boolean tomate, boolean cebola) {
        lanche.setQueijo(queijo);
        lanche.setAlface(alface);
        lanche.setTomate(tomate);
        lanche.setCebola(cebola);
    }

    public ItemCardapio aplicarAdicional(ItemCardapio itemAtual, int opcaoAdicional) {
        switch (opcaoAdicional) {
            case 1: return new ComCheddar(itemAtual);
            case 2: return new ComBaconExtra(itemAtual);
            default: return itemAtual;
        }
    }

    public void adicionarItemAoPedido(int numeroPedido, ItemCardapio item) {
        Pedido p = gerenciadorPedidos.buscarPedido(numeroPedido);
        if (p != null) {
            p.adicionarItem(item);
            atualizarCaixa(p);
        }
    }

    public void removerItemDoPedido(int numeroPedido, int indiceItem) {
        Pedido p = gerenciadorPedidos.buscarPedido(numeroPedido);
        if (p != null) {
            p.removerItem(indiceItem);
            atualizarCaixa(p);
        }
    }

    public boolean cancelarPedido(int numeroPedido) {
        boolean cancelado = gerenciadorPedidos.cancelarPedido(numeroPedido);
        if (cancelado) {
            caixa.removerTransacaoPorPedido(numeroPedido);
        }
        return cancelado;
    }

    public void registrarGorjeta(int numeroPedido, double valor) {
        Pedido p = gerenciadorPedidos.buscarPedido(numeroPedido);
        if (p != null) {
            p.adicionarGorjeta(valor);
            atualizarCaixa(p);
        }
    }

    private void atualizarCaixa(Pedido p) {
        caixa.registrarTransacao(new Transacao(p.getNumero(), p.calcularTotal(), p.getGorjeta()));
    }

    public Pedido buscarPedido(int numero) {
        return gerenciadorPedidos.buscarPedido(numero);
    }

    public Collection<Pedido> listarPedidos() {
        return gerenciadorPedidos.getPedidos();
    }

    public double calcularTotalDoDia() {
        return gerenciadorPedidos.calcularTotalDoDia();
    }

    public double calcularTotalGorjetasDoDia() {
        return gerenciadorPedidos.calcularTotalGorjetasDoDia();
    }

    public HashSet<String> obterCombinacoesDiferentes() {
        return gerenciadorPedidos.obterCombinacoesDiferentes();
    }

    public HashSet<String> obterLanchesBaseDiferentes() {
        return gerenciadorPedidos.obterLanchesBaseDiferentes();
    }
}