package gr.android.softposecr.ui.homeScreen;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Locale;
import gr.android.softposecr.databinding.ItemBinding;
import gr.android.softposecr.domain.models.Item;

public class ItemListAdapter extends ListAdapter<Item, ItemListAdapter.ItemViewHolder> {

    private final OnItemActionListener listener;

    public ItemListAdapter(OnItemActionListener listener) {
        super(new DiffCallback());
        this.listener = listener;
    }

    @NonNull
    @Override
    public ItemViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemBinding binding = ItemBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new ItemViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull ItemViewHolder holder, int position) {
        Item item = getItem(position);
        holder.bind(item);
        holder.itemView.setOnClickListener(v -> listener.onItemClick(item.getTitle()));
        holder.binding.plusButton.setOnClickListener(v -> {
            listener.onPlusClick(item.getTitle());
            int currentCount = Integer.parseInt(holder.binding.itemCounter.getText().toString());
            holder.binding.itemCounter.setText(String.valueOf(currentCount + 1));
        });
        holder.binding.minusButton.setOnClickListener(v -> {
            listener.onMinusClick(item.getTitle());
            int currentCount = Integer.parseInt(holder.binding.itemCounter.getText().toString());
            if (currentCount > 0) {
                holder.binding.itemCounter.setText(String.valueOf(currentCount - 1));
            }
        });
    }

    static class ItemViewHolder extends RecyclerView.ViewHolder {
        private final ItemBinding binding;

        ItemViewHolder(ItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Item item) {
            binding.itemTitle.setText(item.getTitle());
            binding.itemPrice.setText(String.format(Locale.getDefault(), "%.2f€", item.getPrice()));
            binding.itemDescription.setText(item.getOverview());
            binding.itemPoster.setImageResource(item.getPosterPath());
            binding.itemCounter.setText("0"); // Reset counter when binding
        }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<Item> {
        @Override
        public boolean areItemsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getTitle().equals(newItem.getTitle());
        }

        @Override
        public boolean areContentsTheSame(@NonNull Item oldItem, @NonNull Item newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                   oldItem.getOverview().equals(newItem.getOverview()) &&
                   oldItem.getPosterPath() == newItem.getPosterPath() &&
                   oldItem.getPrice() == newItem.getPrice();
        }
    }


    public interface OnItemActionListener {
        void onItemClick(String itemTitle);
        void onPlusClick(String itemTitle);
        void onMinusClick(String itemTitle);
    }
}
