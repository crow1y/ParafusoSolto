package br.com.igor.parafusosolto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.animation.OvershootInterpolator;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;

import br.com.igor.parafusosolto.databinding.ActivityTesteBinding;

// Tela 3: Câmara de Testes
public class TesteActivity extends AppCompatActivity {

    private static final int MIN_OBSERVACAO = 10;
    private static final long DURACAO_SELO = 600; // ms

    private ActivityTesteBinding binding;

    private String nome;
    private String resultado; // fica null até o teste ser confirmado

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Extras.TAG_CICLO, "TesteActivity: onCreate");
        binding = ActivityTesteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        // Dados da invenção recebida
        Intent intent = getIntent();
        nome = intent.getStringExtra(Extras.EXTRA_NOME);
        String categoria = intent.getStringExtra(Extras.EXTRA_CATEGORIA);
        int imagemResId = intent.getIntExtra(Extras.EXTRA_IMAGEM, 0);

        if (nome == null || nome.trim().isEmpty() || categoria == null || imagemResId == 0) {
            Toast.makeText(this, R.string.erro_dados_invalidos, Toast.LENGTH_SHORT).show();
            finish();
            return;
        }

        binding.imageTeste.setImageResource(imagemResId);
        binding.textNomeTeste.setText(nome);
        binding.textCategoriaTeste.setText(categoria);

        binding.buttonConfirmarTeste.setOnClickListener(view -> confirmarTeste());

        // Rever ficha: só fecha esta tela e volta para a ficha
        binding.buttonReverFicha.setOnClickListener(view -> finish());

        binding.buttonEncerrarAvaliacao.setOnClickListener(view -> encerrarAvaliacao());
    }

    private void confirmarTeste() {
        int idSelecionado = binding.radioGroupResultado.getCheckedRadioButtonId();
        String observacao = binding.editObservacao.getText().toString().trim();

        if (idSelecionado == -1) {
            Toast.makeText(this, R.string.erro_resultado, Toast.LENGTH_SHORT).show();
            return;
        }
        if (observacao.length() < MIN_OBSERVACAO) {
            binding.editObservacao.setError(getString(R.string.erro_observacao));
            binding.editObservacao.requestFocus();
            return;
        }

        // Escolhe texto, selo e cor de acordo com o resultado
        int selo;
        int cor;
        if (idSelecionado == R.id.radioAprovada) {
            resultado = getString(R.string.resultado_aprovada);
            selo = R.drawable.selo_aprovada;
            cor = R.color.selo_aprovada;
        } else if (idSelecionado == R.id.radioAjustes) {
            resultado = getString(R.string.resultado_ajustes);
            selo = R.drawable.selo_ajustes;
            cor = R.color.selo_ajustes;
        } else {
            resultado = getString(R.string.resultado_reprovada);
            selo = R.drawable.selo_reprovada;
            cor = R.color.selo_reprovada;
        }

        mostrarSelo(selo, cor);
    }

    // Selo animado com ViewPropertyAnimator: escala + transparência
    private void mostrarSelo(int selo, int cor) {
        binding.imageSelo.setImageResource(selo);
        binding.imageSelo.setVisibility(View.VISIBLE);
        binding.imageSelo.setAlpha(0f);
        binding.imageSelo.setScaleX(0.3f);
        binding.imageSelo.setScaleY(0.3f);
        binding.imageSelo.setRotation(-25f);

        binding.imageSelo.animate()
                .alpha(1f)
                .scaleX(1f)
                .scaleY(1f)
                .rotation(0f)
                .setDuration(DURACAO_SELO)
                .setInterpolator(new OvershootInterpolator())
                .start();

        binding.textSelo.setText(getString(R.string.formato_selo, resultado));
        binding.textSelo.setTextColor(ContextCompat.getColor(this, cor));
        binding.textSelo.setAlpha(0f);
        binding.textSelo.animate().alpha(1f).setDuration(DURACAO_SELO).start();
    }

    // Volta para a feira, removendo Ficha e Câmara da pilha
    private void encerrarAvaliacao() {
        if (resultado == null) {
            Toast.makeText(this, R.string.erro_confirmar_antes, Toast.LENGTH_SHORT).show();
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_SINGLE_TOP);
        intent.putExtra(Extras.EXTRA_RESULTADO_NOME, nome);
        intent.putExtra(Extras.EXTRA_RESULTADO, resultado);
        startActivity(intent);
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Extras.TAG_CICLO, "TesteActivity: onResume");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Extras.TAG_CICLO, "TesteActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Extras.TAG_CICLO, "TesteActivity: onDestroy");
    }
}
