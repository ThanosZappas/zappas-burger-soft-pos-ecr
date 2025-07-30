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
        setupObservers();
        setupCheckoutButton();
    }

    private void setupRecyclerView() {
        binding.cartRecyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new CartAdapter(this, viewModel.getItemQuantities());
        binding.cartRecyclerView.setAdapter(adapter);
    }

    private void setupObservers() {
        // Observe cart items
        viewModel.getCartItems().observe(getViewLifecycleOwner(), items -> {
            adapter.submitList(items);
        });

        // Observe total amount
        viewModel.getCartTotal().observe(getViewLifecycleOwner(), total -> {
            binding.totalAmount.setText(String.format(Locale.getDefault(), "%.2f€", total));
        });
    }

    private void setupCheckoutButton() {
        binding.checkoutButton.setOnClickListener(v -> {
            // Navigation to checkout will be handled by you
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
