package br.com.igor.parafusosolto;

// Chaves dos extras usados nas Intents
public final class Extras {

    private Extras() { }

    // Feira -> Ficha -> Câmara de Testes
    public static final String EXTRA_NOME = "br.com.igor.parafusosolto.NOME";
    public static final String EXTRA_INVENTOR = "br.com.igor.parafusosolto.INVENTOR";
    public static final String EXTRA_CATEGORIA = "br.com.igor.parafusosolto.CATEGORIA";
    public static final String EXTRA_DESCRICAO = "br.com.igor.parafusosolto.DESCRICAO";
    public static final String EXTRA_IMAGEM = "br.com.igor.parafusosolto.IMAGEM";

    // Câmara de Testes -> Feira (retorno com CLEAR_TOP)
    public static final String EXTRA_RESULTADO_NOME = "br.com.igor.parafusosolto.RESULTADO_NOME";
    public static final String EXTRA_RESULTADO = "br.com.igor.parafusosolto.RESULTADO";

    // Tag usada no Logcat para os métodos do ciclo de vida
    public static final String TAG_CICLO = "CicloFeira";
}
