package gr.android.softposecr.ui.homeScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Locale;

import gr.android.softposecr.R;
import gr.android.softposecr.databinding.ItemBinding;
import gr.android.softposecr.domain.models.Item;

public class ItemListAdapter extends ListAdapter<Item, ItemListAdapter.ItemViewHolder> {

    private final OnItemActionListener listener;
    private final ItemViewModel viewModel;

    public ItemListAdapter(OnItemActionListener listener, ItemViewModel viewModel) {
        super(new DiffCallback());
        this.listener = listener;
        this.viewModel = viewModel;
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
        holder.itemView.setOnClickListener(v -> {
            Bundle args = new Bundle();
            args.putString("ITEM_TITLE", item.getTitle());
            args.putString("ITEM_DESCRIPTION", item.getOverview());
            args.putInt("ITEM_POSTER", item.getPosterPath());
            args.putFloat("ITEM_PRICE", (float) item.getPrice());
            Navigation.findNavController(v).navigate(R.id.action_itemListFragment_to_itemDetailsFragment, args);
        });

        // Initialize counter with current quantity
        int currentQuantity = viewModel.getItemQuantity(item.getTitle());
        holder.binding.itemCounter.setText(String.valueOf(currentQuantity));

        holder.binding.plusButton.setOnClickListener(v -> {
            listener.onPlusClick(item.getTitle());
            int count = Integer.parseInt(holder.binding.itemCounter.getText().toString());
            holder.binding.itemCounter.setText(String.valueOf(count + 1));
        });
        holder.binding.minusButton.setOnClickListener(v -> {
            listener.onMinusClick(item.getTitle());
            int count = Integer.parseInt(holder.binding.itemCounter.getText().toString());
            if (count > 0) {
                holder.binding.itemCounter.setText(String.valueOf(count - 1));
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
