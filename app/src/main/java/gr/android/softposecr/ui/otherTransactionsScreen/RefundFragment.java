package gr.android.softposecr.ui.otherTransactionsScreen;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import androidx.fragment.app.Fragment;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.navigation.Navigation;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import gr.android.softposecr.databinding.FragmentRefundBinding;
import gr.android.softposecr.transactions.TransactionsActivity;
import gr.android.softposecr.models.Transaction;

public class RefundFragment extends Fragment {
    private FragmentRefundBinding binding;
    private List<Transaction> transactions;
    private Transaction selectedTransaction;
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = FragmentRefundBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        binding.backArrowButton.setOnClickListener(v -> Navigation.findNavController(v).navigateUp());

        binding.secondContinueButton.setOnClickListener(v ->{
            startRefundFromNumber();
        });

        binding.continueButton.setOnClickListener(v -> startRefundFromList());

        setupTransactionSpinner();
    }

    private void setupTransactionSpinner() {
        transactions = createDummyTransactions();

        // Sort transactions by date (newest first)
        Collections.sort(transactions, (t1, t2) -> t2.getDateTime().compareTo(t1.getDateTime()));

        // Create display strings for spinner
        List<String> displayItems = new ArrayList<>();
        for (Transaction t : transactions) {
            displayItems.add(String.format("Trans #%s - €%s - %s",
                    t.getTransactionNumber(), t.getAmount(), t.getDateTime()));
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_spinner_item,
                displayItems
        );
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        binding.transactionSpinner.setAdapter(adapter);

        binding.transactionSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                selectedTransaction = transactions.get(position);
                binding.continueButton.setEnabled(true);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {
                binding.continueButton.setEnabled(false);
            }
        });
    }

    private List<Transaction> createDummyTransactions() {
        List<Transaction> list = new ArrayList<>();
        // Add some transactions from different dates
        LocalDateTime now = LocalDateTime.now();
        String today = now.format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));
        String yesterday = now.minusDays(1).format(DateTimeFormatter.ofPattern("yyyy-MM-dd"));

        // Adding transactions from today with different times
        list.add(new Transaction("1000", "25.50", yesterday + " 09:00"));
        list.add(new Transaction("1001", "25.50", yesterday + " 09:30"));
        list.add(new Transaction("1002", "42.99", yesterday + " 10:15"));
        list.add(new Transaction("1003", "15.75", today + " 11:20"));
        list.add(new Transaction("1004", "89.99", today + " 12:45"));
        list.add(new Transaction("1005", "33.25", today + " 13:30"));

        // Filter to keep only transactions before today
        List<Transaction> filteredTransactions = new ArrayList<>();
        LocalDateTime todayTime = LocalDateTime.now().withHour(0).withMinute(0).withSecond(0).withNano(0);

        for (Transaction t : list) {
            LocalDateTime transactionDate = LocalDateTime.parse(t.getDateTime(), formatter);
            if (transactionDate.isBefore(todayTime)) {
                filteredTransactions.add(t);
            }
        }

        return filteredTransactions;
    }

    private void startRefundFromList() {

        if (selectedTransaction != null) {
            Bundle bundle = new Bundle();
            bundle.putString("TRANSACTION_TYPE", "REFUND");
            bundle.putString("AMOUNT", selectedTransaction.getAmount());
            bundle.putString("TRANSACTION_NUMBER", selectedTransaction.getTransactionNumber());
            Intent intent = new Intent(requireActivity(), TransactionsActivity.class);
            intent.putExtras(bundle);
            startActivity(intent);
        }
    }

    private void startRefundFromNumber() {
        Log.d("RefundFragment", "startRefundFromNumber called");

        if (binding == null) {
            Log.e("RefundFragment", "binding is null");
            return;
        }

        if (binding.transactionNumber == null) {
            Log.e("RefundFragment", "transactionNumber EditText is null");
            return;
        }

        String transactionNumber = binding.transactionNumber.getText().toString().trim();
        Log.d("RefundFragment", "Transaction number entered: " + transactionNumber);

        if (transactions == null) {
            Log.e("RefundFragment", "transactions list is null");
            return;
        }

        if (!transactionNumber.isEmpty()) {
            // Find the transaction with matching number
            Transaction foundTransaction = null;
            for (Transaction t : transactions) {
                Log.d("RefundFragment", "Checking transaction: " + t.getTransactionNumber());
                if (t.getTransactionNumber().equals(transactionNumber)) {
                    foundTransaction = t;
                    Log.d("RefundFragment", "Found matching transaction");
                    break;
                }
            }
            if (foundTransaction != null) {
                Log.d("RefundFragment", "Starting SaleActivity with transaction: " + foundTransaction.getTransactionNumber());
                Bundle bundle = new Bundle();
                bundle.putString("TRANSACTION_TYPE", "REFUND");
                bundle.putString("AMOUNT", foundTransaction.getAmount());
                bundle.putString("TRANSACTION_NUMBER", transactionNumber);
                Intent intent = new Intent(requireActivity(), TransactionsActivity.class);
                intent.putExtras(bundle);
                startActivity(intent);
            } else {
                Log.d("RefundFragment", "Transaction not found: " + transactionNumber);
                binding.transactionNumber.setError("Transaction not found");
            }
        } else {
            Log.d("RefundFragment", "Empty transaction number");
            binding.transactionNumber.setError("Transaction number is required");
        }
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}
