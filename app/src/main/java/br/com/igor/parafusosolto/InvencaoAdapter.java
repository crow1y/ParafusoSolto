package br.com.igor.parafusosolto;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import br.com.igor.parafusosolto.databinding.ItemInvencaoBinding;

// Adapter: liga a lista de invenções aos cartões
public class InvencaoAdapter extends RecyclerView.Adapter<InvencaoViewHolder> {

    // Clique simples: abre a ficha
    public interface OnInvencaoClickListener {
        void onInvencaoClick(Invencao invencao);
    }

    // Clique longo: remove a invenção
    public interface OnInvencaoLongClickListener {
        void onInvencaoLongClick(Invencao invencao);
    }

    private final List<Invencao> invencoes;
    private final OnInvencaoClickListener clickListener;
    private final OnInvencaoLongClickListener longClickListener;

    public InvencaoAdapter(List<Invencao> invencoes,
                           OnInvencaoClickListener clickListener,
                           OnInvencaoLongClickListener longClickListener) {
        this.invencoes = invencoes;
        this.clickListener = clickListener;
        this.longClickListener = longClickListener;
    }

    @NonNull
    @Override
    public InvencaoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemInvencaoBinding binding = ItemInvencaoBinding.inflate(
                LayoutInflater.from(parent.getContext()), parent, false);
        return new InvencaoViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull InvencaoViewHolder holder, int position) {
        holder.bind(invencoes.get(position), clickListener, longClickListener);
    }

    @Override
    public int getItemCount() {
        return invencoes.size();
    }
}
