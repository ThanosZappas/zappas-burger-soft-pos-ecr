package gr.android.softposecr.ui.checkoutScreen;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;
import java.util.Locale;
import gr.android.softposecr.databinding.CartItemBinding;
import gr.android.softposecr.domain.models.Item;

public class CartAdapter extends ListAdapter<Item, CartAdapter.CartViewHolder> {

    public interface CartItemActionListener {
        void onPlusClick(String itemTitle);
        void onMinusClick(String itemTitle);
    }

    private final CartItemActionListener listener;
    private final java.util.Map<String, Integer> itemQuantities;

    public CartAdapter(CartItemActionListener listener, java.util.Map<String, Integer> itemQuantities) {
        super(new DiffCallback());
        this.listener = listener;
        this.itemQuantities = itemQuantities;
    }

    @NonNull
    @Override
    public CartViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        CartItemBinding binding = CartItemBinding.inflate(
            LayoutInflater.from(parent.getContext()), parent, false);
        return new CartViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull CartViewHolder holder, int position) {
        Item item = getItem(position);
        holder.bind(item, itemQuantities.getOrDefault(item.getTitle(), 0));

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

    static class CartViewHolder extends RecyclerView.ViewHolder {
        private final CartItemBinding binding;

        CartViewHolder(CartItemBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(Item item, int quantity) {
            binding.cartItemTitle.setText(item.getTitle());
            binding.cartItemPrice.setText(String.format(Locale.getDefault(), "%.2f€", item.getPrice()));
            binding.itemCounter.setText(String.valueOf(quantity));
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
                   oldItem.getPrice() == newItem.getPrice();
        }
    }
}
