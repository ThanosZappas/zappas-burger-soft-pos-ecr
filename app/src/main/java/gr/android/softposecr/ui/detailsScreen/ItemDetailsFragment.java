package gr.android.softposecr.ui.detailsScreen;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.Navigation;

import java.util.Locale;

import gr.android.softposecr.databinding.FragmentItemDetailsBinding;
import gr.android.softposecr.ui.homeScreen.ItemViewModel;

public class ItemDetailsFragment extends Fragment {
    private ItemViewModel viewModel;
    private FragmentItemDetailsBinding binding;
    private String currentNotes = "";
    private String currentItemTitle = "";

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentItemDetailsBinding.inflate(inflater, container, false);
        viewModel = new ViewModelProvider(requireActivity()).get(ItemViewModel.class);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        setupObservers();
        updateUI();
    }

    private void setupObservers() {
        // Observe cart changes to keep counter updated
        viewModel.getCartItems().observe(getViewLifecycleOwner(), items -> {
            updateCounter();
        });
    }

    private void setupViews() {
        binding.backArrowButton.setOnClickListener(v ->
                Navigation.findNavController(binding.getRoot()).navigateUp());

        // Setup counter buttons
        binding.plusButton.setOnClickListener(v -> {
            if (!currentItemTitle.isEmpty()) {
                viewModel.incrementItemQuantity(currentItemTitle);
                updateCounter();
            }
        });

        binding.minusButton.setOnClickListener(v -> {
            if (!currentItemTitle.isEmpty()) {
                viewModel.decrementItemQuantity(currentItemTitle);
                updateCounter();
            }
        });

        // Setup submit button
        binding.submitNotes.setOnClickListener(v -> {
            String notes = binding.itemNotes.getText().toString();
            if (!notes.equals("Notes..") && !notes.isEmpty()) {
                // Here you can handle the notes submission
                // For example, save to ViewModel or database
                binding.itemNotes.clearFocus();
            }
        });

        // Setup notes EditText
        binding.itemNotes.setOnFocusChangeListener((v, hasFocus) -> {
            if (hasFocus && binding.itemNotes.getText().toString().equals("Notes..")) {
                binding.itemNotes.setText("");
            } else if (!hasFocus && binding.itemNotes.getText().toString().isEmpty()) {
                binding.itemNotes.setHint("Notes..");
            }
        });

        binding.itemNotes.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().equals("Notes..")) {
                    currentNotes = s.toString();
                }
            }
        });
    }

    private void updateCounter() {
        if (!currentItemTitle.isEmpty()) {
            int quantity = viewModel.getItemQuantity(currentItemTitle);
            binding.itemCounter.setText(String.valueOf(quantity));
        }
    }

    private void updateUI() {
        if (getArguments() != null) {
            String itemTitle = getArguments().getString("ITEM_TITLE", "");
            String itemDescription = getArguments().getString("ITEM_DESCRIPTION", "");
            int itemPoster = getArguments().getInt("ITEM_POSTER", 0);
            double itemPrice = getArguments().getFloat("ITEM_PRICE", 0.00f);

            currentItemTitle = itemTitle;
            binding.itemTitle.setText(itemTitle);
            binding.itemDescription.setText(itemDescription);
            binding.itemPoster.setImageResource(itemPoster);
            binding.itemPrice.setText(String.format(Locale.getDefault(), "%.2f€", itemPrice));

            // Initialize counter with current quantity
            updateCounter();
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
