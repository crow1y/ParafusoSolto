package br.com.igor.parafusosolto;

// Modelo: uma invenção da feira
public class Invencao {

    private final String nome;
    private final String inventor;
    private final String categoria;
    private final String descricao;
    private final int imagemResId;

    public Invencao(String nome, String inventor, String categoria,
                    String descricao, int imagemResId) {
        this.nome = nome;
        this.inventor = inventor;
        this.categoria = categoria;
        this.descricao = descricao;
        this.imagemResId = imagemResId;
    }

    public String getNome()      { return nome; }
    public String getInventor()  { return inventor; }
    public String getCategoria() { return categoria; }
    public String getDescricao() { return descricao; }
    public int getImagemResId()  { return imagemResId; }
}
