package gr.android.softposecr.ui.checkoutScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import java.util.Locale;

import gr.android.softposecr.R;
import gr.android.softposecr.databinding.FragmentEreceiptBinding;
import gr.android.softposecr.transactions.SaleActivity;
import gr.android.softposecr.ui.homeScreen.ItemViewModel;

public class EReceiptFragment extends Fragment {
    private FragmentEreceiptBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentEreceiptBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        setupViews();
        setupClickListeners();
    }

    private void setupViews() {
        // Set hints color for EditTexts
        binding.emailEditText.setHintTextColor(requireContext().getColor(R.color.icon_text));
        binding.phoneEditText.setHintTextColor(requireContext().getColor(R.color.icon_text));

        // Set input types
        binding.emailEditText.setInputType(android.text.InputType.TYPE_TEXT_VARIATION_EMAIL_ADDRESS);
        binding.phoneEditText.setInputType(android.text.InputType.TYPE_CLASS_PHONE);
    }


    private void setupClickListeners() {


        binding.backArrowButton.setOnClickListener(v -> {
            // Navigate back
            Navigation.findNavController(v).navigateUp();
        });

        binding.skipButton.setOnClickListener(view -> {
            Toast.makeText(requireContext(), "Proceeding to Checkout...", Toast.LENGTH_SHORT).show();
            proceedToCheckout();
        });

        binding.continueButton.setOnClickListener(v -> {
            String email = binding.emailEditText.getText().toString().trim();
            String phoneNumber = binding.phoneEditText.getText().toString().trim();
            if (validateInputs()) {
                Toast.makeText(requireContext(), "Proceeding to Checkout...", Toast.LENGTH_SHORT).show();
                proceedToCheckout();
            }
        });
    }


    private boolean validateInputs() {
        boolean isEmailValid;
        boolean isPhoneNumberValid;
        String email;
        String phoneNumber;

        phoneNumber = binding.phoneEditText.getText().toString().trim();
        if (!android.util.Patterns.PHONE.matcher(phoneNumber).matches() && !phoneNumber.isEmpty()) {
            binding.phoneEditText.setError("Please enter a valid phone number");
            isPhoneNumberValid = false;
        } else {
            isPhoneNumberValid = true;
        }

        email = binding.emailEditText.getText().toString().trim();
        if (!android.util.Patterns.EMAIL_ADDRESS.matcher(email).matches() && !email.isEmpty()) {
            binding.emailEditText.setError("Please enter a valid email address");
            isEmailValid = false;
        } else isEmailValid = true;

        return isEmailValid && isPhoneNumberValid || isEmailValid && phoneNumber.isEmpty() || isPhoneNumberValid && email.isEmpty() || email.isEmpty() && phoneNumber.isEmpty();
    }


    private void proceedToCheckout() {
        Toast.makeText(requireContext(), "Proceeding to Checkout...", Toast.LENGTH_SHORT).show();
        Bundle bundle = new Bundle();
        String amount = getArguments().getString("AMOUNT", "1");
        String tip = "0"; // Default tip if not provided
        String installments = "0"; // Default installments if not provided
        String currency = "EUR"; // Default currency if not provided
        String uid = "123456789"; // Default UID if not provided
        String email = binding.emailEditText.getText().toString().trim();
        String phoneNumber = binding.phoneEditText.getText().toString().trim();

        bundle.putString("TRANSACTION_TYPE", "SALE");
        bundle.putString("AMOUNT", amount);
        bundle.putString("TIP", tip);
        bundle.putString("INSTALLMENTS", installments);
        bundle.putString("CURRENCY", currency);
        bundle.putString("EMAIL", email);
        bundle.putString("PHONE_NUMBER", phoneNumber);
        bundle.putString("UID", uid);
        performSale(bundle);
    }

    private void performSale(Bundle bundle) {
        Intent intent = new Intent(requireContext(), SaleActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
