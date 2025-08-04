package gr.android.softposecr.ui.otherScreen;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import gr.android.softposecr.R;
import gr.android.softposecr.databinding.FragmentOtherBinding;
import gr.android.softposecr.transactions.TransactionsActivity;

public class OtherFragment extends Fragment {
    private FragmentOtherBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentOtherBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupClickListeners();
    }

    private void setupClickListeners() {
        binding.backArrowButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigateUp());

        binding.billPaymentButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        R.id.action_otherFragment_to_billPaymentFragment));

        binding.refundButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        R.id.action_otherFragment_to_refundFragment));

        binding.voidButton.setOnClickListener(v ->
                Navigation.findNavController(v).navigate(
                        R.id.action_otherFragment_to_voidFragment));

        binding.closeBatchButton.setOnClickListener(v ->
                startCloseBatch()
                );
    }

    private void startCloseBatch() {
        Bundle bundle = new Bundle();
        bundle.putString("TRANSACTION_TYPE", "CLOSE_BATCH");
        Intent intent = new Intent(requireActivity(), TransactionsActivity.class);
        intent.putExtras(bundle);
        startActivity(intent);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}