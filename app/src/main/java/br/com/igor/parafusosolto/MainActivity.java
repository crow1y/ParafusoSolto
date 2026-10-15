package br.com.igor.parafusosolto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.google.android.material.dialog.MaterialAlertDialogBuilder;

import java.util.ArrayList;

import br.com.igor.parafusosolto.databinding.ActivityMainBinding;

// Tela 1: Feira de Invenções
public class MainActivity extends AppCompatActivity {

    private static final int MIN_CARACTERES = 3;
    private static final int COLUNAS_GRADE = 2;
    private static final long DURACAO_ANIMACAO = 400; // ms

    private ActivityMainBinding binding;
    private final ArrayList<Invencao> invencoes = new ArrayList<>();
    private InvencaoAdapter adapter;
    private boolean emGrade = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.d(Extras.TAG_CICLO, "MainActivity: onCreate");
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        configurarSpinner();
        criarInvencoesIniciais();
        configurarLista();

        binding.buttonCadastrar.setOnClickListener(view -> cadastrarInvencao());
        binding.buttonAlternarLayout.setOnClickListener(view -> alternarLayout());
    }

    // Categorias vindas do strings.xml
    private void configurarSpinner() {
        ArrayAdapter<CharSequence> adapterCategorias = ArrayAdapter.createFromResource(
                this, R.array.categorias, android.R.layout.simple_spinner_item);
        adapterCategorias.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.spinnerCategoria.setAdapter(adapterCategorias);
    }

    private void criarInvencoesIniciais() {
        invencoes.add(new Invencao(getString(R.string.inv1_nome), getString(R.string.inv1_inventor),
                getString(R.string.inv1_categoria), getString(R.string.inv1_descricao),
                R.drawable.img_inv_guarda_chuva));
        invencoes.add(new Invencao(getString(R.string.inv2_nome), getString(R.string.inv2_inventor),
                getString(R.string.inv2_categoria), getString(R.string.inv2_descricao),
                R.drawable.img_inv_torradeira));
        invencoes.add(new Invencao(getString(R.string.inv3_nome), getString(R.string.inv3_inventor),
                getString(R.string.inv3_categoria), getString(R.string.inv3_descricao),
                R.drawable.img_inv_patins));
        invencoes.add(new Invencao(getString(R.string.inv4_nome), getString(R.string.inv4_inventor),
                getString(R.string.inv4_categoria), getString(R.string.inv4_descricao),
                R.drawable.img_inv_robo));
        invencoes.add(new Invencao(getString(R.string.inv5_nome), getString(R.string.inv5_inventor),
                getString(R.string.inv5_categoria), getString(R.string.inv5_descricao),
                R.drawable.img_inv_oculos));
    }

    private void configurarLista() {
        adapter = new InvencaoAdapter(invencoes, this::abrirFicha, this::confirmarRemocao);
        binding.recyclerInvencoes.setLayoutManager(new LinearLayoutManager(this));
        binding.recyclerInvencoes.setAdapter(adapter);
    }

    // Troca entre lista (LinearLayoutManager) e grade (GridLayoutManager)
    private void alternarLayout() {
        emGrade = !emGrade;
        if (emGrade) {
            binding.recyclerInvencoes.setLayoutManager(new GridLayoutManager(this, COLUNAS_GRADE));
            binding.buttonAlternarLayout.setText(R.string.botao_ver_lista);
        } else {
            binding.recyclerInvencoes.setLayoutManager(new LinearLayoutManager(this));
            binding.buttonAlternarLayout.setText(R.string.botao_ver_grade);
        }
        // Microanimação: a lista reaparece suavemente
        binding.recyclerInvencoes.setAlpha(0f);
        binding.recyclerInvencoes.animate().alpha(1f).setDuration(DURACAO_ANIMACAO).start();
    }

    private void cadastrarInvencao() {
        String nome = binding.editNomeInvencao.getText().toString().trim();
        String inventor = binding.editNomeInventor.getText().toString().trim();
        int posicaoCategoria = binding.spinnerCategoria.getSelectedItemPosition();

        if (nome.length() < MIN_CARACTERES) {
            binding.editNomeInvencao.setError(getString(R.string.erro_nome_invencao));
            binding.editNomeInvencao.requestFocus();
            return;
        }
        if (inventor.length() < MIN_CARACTERES) {
            binding.editNomeInventor.setError(getString(R.string.erro_nome_inventor));
            binding.editNomeInventor.requestFocus();
            return;
        }
        if (posicaoCategoria == 0) { // posição 0 = "Selecione a categoria"
            Toast.makeText(this, R.string.erro_categoria, Toast.LENGTH_SHORT).show();
            return;
        }

        String categoria = binding.spinnerCategoria.getSelectedItem().toString();
        String descricao = getString(R.string.descricao_padrao, inventor);

        // Invenção do usuário usa a imagem padrão
        Invencao nova = new Invencao(nome, inventor, categoria, descricao,
                R.drawable.img_invencao_padrao);
        invencoes.add(nova);

        int posicao = invencoes.size() - 1;
        adapter.notifyItemInserted(posicao);
        binding.recyclerInvencoes.scrollToPosition(posicao);

        Toast.makeText(this, getString(R.string.msg_cadastrada, nome), Toast.LENGTH_SHORT).show();
        limparFormulario();
        atualizarQuantidade();
    }

    private void limparFormulario() {
        binding.editNomeInvencao.setText("");
        binding.editNomeInventor.setText("");
        binding.spinnerCategoria.setSelection(0);
        binding.editNomeInvencao.clearFocus();
        binding.editNomeInventor.clearFocus();
    }

    // Clique simples: abre a ficha com Intent explícita
    private void abrirFicha(Invencao invencao) {
        Intent intent = new Intent(this, FichaActivity.class);
        intent.putExtra(Extras.EXTRA_NOME, invencao.getNome());
        intent.putExtra(Extras.EXTRA_INVENTOR, invencao.getInventor());
        intent.putExtra(Extras.EXTRA_CATEGORIA, invencao.getCategoria());
        intent.putExtra(Extras.EXTRA_DESCRICAO, invencao.getDescricao());
        intent.putExtra(Extras.EXTRA_IMAGEM, invencao.getImagemResId());
        startActivity(intent);
    }

    // Clique longo: pede confirmação antes de remover
    private void confirmarRemocao(Invencao invencao) {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.titulo_remover)
                .setMessage(getString(R.string.mensagem_remover, invencao.getNome()))
                .setPositiveButton(R.string.botao_remover, (dialog, which) -> removerInvencao(invencao))
                .setNegativeButton(R.string.botao_cancelar, null)
                .show();
    }

    private void removerInvencao(Invencao invencao) {
        int posicao = invencoes.indexOf(invencao);
        if (posicao == -1) return;

        invencoes.remove(posicao);
        adapter.notifyItemRemoved(posicao);

        Toast.makeText(this, getString(R.string.msg_removida, invencao.getNome()),
                Toast.LENGTH_SHORT).show();
        atualizarQuantidade();
    }

    private void atualizarQuantidade() {
        binding.textQuantidade.setText(getString(R.string.formato_quantidade, invencoes.size()));
    }

    // Recebe o retorno da Câmara de Testes (CLEAR_TOP + SINGLE_TOP)
    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        Log.d(Extras.TAG_CICLO, "MainActivity: onNewIntent");
        setIntent(intent);

        String nome = intent.getStringExtra(Extras.EXTRA_RESULTADO_NOME);
        String resultado = intent.getStringExtra(Extras.EXTRA_RESULTADO);
        if (nome != null && resultado != null) {
            binding.textUltimoTeste.setText(getString(R.string.formato_ultimo_teste, nome, resultado));
            binding.textUltimoTeste.setVisibility(View.VISIBLE);
            binding.textUltimoTeste.setAlpha(0f);
            binding.textUltimoTeste.animate().alpha(1f).setDuration(DURACAO_ANIMACAO).start();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        Log.d(Extras.TAG_CICLO, "MainActivity: onResume");
        atualizarQuantidade(); // quantidade atual de protótipos
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(Extras.TAG_CICLO, "MainActivity: onStop");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(Extras.TAG_CICLO, "MainActivity: onDestroy");
    }
}
