package financeiro;

import java.util.HashSet;
import java.util.Set;

public class Caixa {
    private static Caixa instancia;
    private HashSet<Transacao> transacoes;

    private Caixa() {
        this.transacoes = new HashSet<>();
    }

    public static Caixa getInstance() {
        if (instancia == null) {
            instancia = new Caixa();
        }
        return instancia;
    }

    public void limparCaixaParaTestes() {
        this.transacoes.clear();
    }

    public boolean registrarTransacao(Transacao t) {
        transacoes.remove(t);
        return transacoes.add(t);
    }

    public void removerTransacaoPorPedido(int numeroPedido) {
        transacoes.remove(new Transacao(numeroPedido, 0, 0));
    }

    public Set<Transacao> getTransacoes() {
        return transacoes;
    }
}