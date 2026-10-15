package br.com.igor.parafusosolto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import br.com.igor.parafusosolto.databinding.ActivityFichaBinding;

// Tela 2: Ficha da Invenção
public class FichaActivity extends AppCompatActivity {

    private ActivityFichaBinding binding;

    private String nome;
    private String inventor;
    private String categoria;
    private String descricao;
    private int imagemResId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Extras.TAG_CICLO, "FichaActivity: onCreate");
        binding = ActivityFichaBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Se algum dado obrigatório for inválido, avisa e fecha
        if (!recuperarDados()) {
            Toast.makeText(this, R.string.erro_dados_invalidos, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        exibirDados();

        // Animação de entrada criada em res/anim
        Animation entrada = AnimationUtils.loadAnimation(this, R.anim.entrada_ficha);
        binding.layoutConteudoFicha.startAnimation(entrada);

        binding.buttonVoltarFeira.setOnClickListener(view -> finish());
        binding.buttonTestarPrototipo.setOnClickListener(view -> abrirCamaraDeTestes());
    }

    // Lê e valida os extras
    private boolean recuperarDados() {
        Intent intent = getIntent();
        nome = intent.getStringExtra(Extras.EXTRA_NOME);
        inventor = intent.getStringExtra(Extras.EXTRA_INVENTOR);
        categoria = intent.getStringExtra(Extras.EXTRA_CATEGORIA);
        descricao = intent.getStringExtra(Extras.EXTRA_DESCRICAO);
        imagemResId = intent.getIntExtra(Extras.EXTRA_IMAGEM, 0);

        return textoValido(nome) && textoValido(inventor) && textoValido(categoria)
                && textoValido(descricao) && imagemResId != 0;
    }

    private boolean textoValido(String texto) {
        return texto != null && !texto.trim().isEmpty();
    }

    private void exibirDados() {
        binding.imageFicha.setImageResource(imagemResId);
        binding.textNomeFicha.setText(nome);
        binding.textInventorFicha.setText(getString(R.string.formato_inventor, inventor));
        binding.textCategoriaFicha.setText(getString(R.string.formato_categoria, categoria));
        binding.textDescricaoFicha.setText(descricao);
    }

    // Abre a terceira tela (a ficha continua na pilha)
    private void abrirCamaraDeTestes() {
        Intent intent = new Intent(this, TesteActivity.class);
        intent.putExtra(Extras.EXTRA_NOME, nome);
        intent.putExtra(Extras.EXTRA_CATEGORIA, categoria);
        intent.putExtra(Extras.EXTRA_IMAGEM, imagemResId);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Extras.TAG_CICLO, "FichaActivity: onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Extras.TAG_CICLO, "FichaActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Extras.TAG_CICLO, "FichaActivity: onDestroy");
    }
}
