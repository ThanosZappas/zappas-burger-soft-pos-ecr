package gr.android.softposecr.ui.orderScreen;

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
import gr.android.softposecr.R;
import gr.android.softposecr.databinding.FragmentItemListBinding;

// implements ItemListAdapter.OnItemClickListener {
@AndroidEntryPoint
public class ItemListFragment extends Fragment implements ItemListAdapter.OnItemActionListener {

    private FragmentItemListBinding binding;

    private ItemListAdapter adapter;

    private ItemViewModel viewModel;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemListBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        setupRecyclerView();
        updateUI();
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ItemListAdapter(this, viewModel);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setItemAnimator(null);

        // Set up search functionality
        binding.search.setOnQueryTextListener(new androidx.appcompat.widget.SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                viewModel.filterItems(newText);
                return true;
            }
        });

        viewModel.getFilteredItems().observe(getViewLifecycleOwner(), items -> {
            adapter.submitList(items);
        });
    }

    private void updateUI() {
        // Update cart total when quantities change
        viewModel.getCartTotal().observe(getViewLifecycleOwner(), total -> {
            binding.cartTotalText.setText(String.format(Locale.getDefault(), "Cart: %.2f€", total));
            if( total == 0.0f) {
                binding.cartTotalText.setVisibility(View.GONE);
            } else {
                binding.cartTotalText.setVisibility(View.VISIBLE);
            }
        });
        binding.cartTotalText.setOnClickListener(view -> {
            // Navigate to CartFragment when cart total is clicked
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_itemListFragment_to_cartFragment);
        });
        binding.backArrowButton.setOnClickListener(v -> {
            // Navigate back to the previous screen
            Navigation.findNavController(binding.getRoot()).navigateUp();
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(String itemTitle) {}

    @Override
    public void onPlusClick(String itemTitle) {
        // Handle plus button click
        viewModel.incrementItemQuantity(itemTitle);
    }

    @Override
    public void onMinusClick(String itemTitle) {
        // Handle minus button click
        viewModel.decrementItemQuantity(itemTitle);
    }
}
