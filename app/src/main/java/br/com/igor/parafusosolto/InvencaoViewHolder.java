package br.com.igor.parafusosolto;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import br.com.igor.parafusosolto.databinding.ItemInvencaoBinding;

// ViewHolder: guarda as Views de um cartão
public class InvencaoViewHolder extends RecyclerView.ViewHolder {

    private final ItemInvencaoBinding binding;

    public InvencaoViewHolder(@NonNull ItemInvencaoBinding binding) {
        super(binding.getRoot());
        this.binding = binding;
    }

    // Preenche o cartão com a invenção
    public void bind(Invencao invencao,
                     InvencaoAdapter.OnInvencaoClickListener clickListener,
                     InvencaoAdapter.OnInvencaoLongClickListener longClickListener) {
        binding.imageInvencao.setImageResource(invencao.getImagemResId());
        binding.textNomeInvencao.setText(invencao.getNome());
        binding.textCategoriaInvencao.setText(invencao.getCategoria());

        // Os eventos enviam o objeto, não a posição
        binding.getRoot().setOnClickListener(view -> clickListener.onInvencaoClick(invencao));
        binding.getRoot().setOnLongClickListener(view -> {
            longClickListener.onInvencaoLongClick(invencao);
            return true;
        });
    }
}
