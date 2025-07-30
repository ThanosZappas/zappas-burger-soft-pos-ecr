package gr.android.softposecr.ui.checkoutScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;
import androidx.recyclerview.widget.LinearLayoutManager;
import java.util.Locale;
import dagger.hilt.android.AndroidEntryPoint;
import gr.android.softposecr.databinding.FragmentCartBinding;
import gr.android.softposecr.domain.models.Item;
import gr.android.softposecr.ui.homeScreen.ItemViewModel;

@AndroidEntryPoint
public class CartFragment extends Fragment implements CartAdapter.CartItemActionListener {

    private FragmentCartBinding binding;
    private ItemViewModel viewModel;
    private CartAdapter adapter;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentCartBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        setupRecyclerView();
        viewModel.updateCartItemsAndTotal(); // Force an initial update
        setupObservers();
        setupUI();
    }

    private void setupRecyclerView() {
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CartAdapter(this, viewModel.getItemQuantities());
        binding.cartRecyclerView.setAdapter(adapter);
        System.out.println("Cart quantities at setup: " + viewModel.getItemQuantities());
    }

    private void setupObservers() {
        // Observe cart items
        viewModel.getCartItems().observe(getViewLifecycleOwner(), items -> {
            System.out.println("Cart items updated: " + items.size() + " items");
            if (items.isEmpty()) {
                System.out.println("Cart is empty!");
            } else {
                for (Item item : items) {
                    System.out.println("Cart item: " + item.getTitle() + " - Quantity: " + viewModel.getItemQuantity(item));
                }
            }
            adapter.submitList(items);
        });

        // Observe total amount
        viewModel.getCartTotal().observe(getViewLifecycleOwner(), total -> {
            System.out.println("Cart total updated: " + total);
            binding.totalAmount.setText(String.format(Locale.getDefault(), "%.2f€", total));
        });
    }

    private void setupUI() {
        binding.checkoutButton.setOnClickListener(v -> {
            // Navigation to checkout will be handled by you
        });
        binding.backArrowButton.setOnClickListener(v-> {
            Navigation.findNavController(v).navigateUp();
        });
    }

    @Override
    public void onPlusClick(String itemTitle) {
        viewModel.incrementItemQuantity(itemTitle);
    }

    @Override
    public void onMinusClick(String itemTitle) {
        viewModel.decrementItemQuantity(itemTitle);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}