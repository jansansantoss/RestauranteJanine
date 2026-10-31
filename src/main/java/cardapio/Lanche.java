package cardapio;

public class Lanche implements ItemCardapio {
    private String nome;
    private double preco;
    private boolean queijo = true, alface = true, tomate = true, cebola = true;

    public Lanche(String nome, double preco) {
        setNome(nome);
        setPreco(preco);
    }
    public void setNome(String nome) {
        if (nome == null || nome.trim().isEmpty()) throw new IllegalArgumentException("Nome inválido.");
        this.nome = nome.trim();
    }
    public void setPreco(double preco) {
        if (preco < 0) throw new IllegalArgumentException("Preço não pode ser negativo.");
        this.preco = preco;
    }
    public void setQueijo(boolean q) { this.queijo = q; }
    public void setAlface(boolean a) { this.alface = a; }
    public void setTomate(boolean t) { this.tomate = t; }
    public void setCebola(boolean c) { this.cebola = c; }

    @Override
    public double getPreco() { return preco; }
    @Override
    public String getNomeBase() { return nome; }
    @Override
    public String getDescricao() {
        String desc = nome;
        if (!queijo) desc += " sem queijo";
        if (!alface) desc += " sem alface";
        if (!tomate) desc += " sem tomate";
        if (!cebola) desc += " sem cebola";
        return desc;
    }
}