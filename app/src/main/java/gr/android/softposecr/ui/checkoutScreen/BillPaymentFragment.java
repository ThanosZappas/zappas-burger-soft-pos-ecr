package gr.android.softposecr.ui.checkoutScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import gr.android.softposecr.databinding.FragmentBillPaymentBinding;
import gr.android.softposecr.transactions.SaleActivity;

public class BillPaymentFragment extends Fragment {
    private FragmentBillPaymentBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentBillPaymentBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backArrowButton.setOnClickListener(v -> {
            Navigation.findNavController(v).navigateUp();});

        binding.checkoutButton.setOnClickListener(v -> startBillPayment());
    }

    private void startBillPayment() {
        String amount = binding.billAmountEditText.getText().toString();
        Bundle bundle = new Bundle();
        bundle.putString("TRANSACTION_TYPE", "BILL_PAYMENT");
        bundle.putString("AMOUNT", amount);
        Intent intent = new Intent(requireActivity(), SaleActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}