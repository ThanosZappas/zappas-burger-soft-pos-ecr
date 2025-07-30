package gr.android.softposecr.ui.homeScreen;

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
import gr.android.softposecr.databinding.FragmentItemListBinding;
import gr.android.softposecr.domain.models.Item;

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
        viewModel = new ViewModelProvider(this).get(ItemViewModel.class);
        setupRecyclerView();
        updateUI();
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new ItemListAdapter(this);
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setItemAnimator(null);
        viewModel.getItems().observe(getViewLifecycleOwner(), items -> {
            adapter.submitList(items);
        });
    }

    private void updateUI() {
        // Update cart total when quantities change
        viewModel.getCartTotal().observe(getViewLifecycleOwner(), total -> {
            binding.cartTotalText.setText(String.format(Locale.getDefault(), "Total: %.2f€", total));
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    @Override
    public void onItemClick(String itemTitle) {
        // Handle item click
        Navigation.findNavController(binding.getRoot()).navigateUp();
    }

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
