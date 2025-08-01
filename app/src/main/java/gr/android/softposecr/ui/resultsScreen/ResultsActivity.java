package gr.android.softposecr.ui.resultsScreen;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import android.content.Intent;

import gr.android.softposecr.R;
import gr.android.softposecr.ui.MainActivity;

import gr.android.softposecr.databinding.ActivityResultsBinding;

public class ResultsActivity extends AppCompatActivity {
    private ActivityResultsBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityResultsBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        handleTransactionResult();
        setupClickListeners();
    }

    private void handleTransactionResult() {
        Uri data = getIntent().getData();
        if (data != null) {
            String status = data.getQueryParameter("status");
            Log.d("ResultsActivity", "Status: " + status);
            boolean isSuccess = "success".equalsIgnoreCase(status);

            // Update UI based on transaction status
            binding.transactionImage.setImageResource(isSuccess ?
                    R.drawable.check_mark_96 : R.drawable.cancel_96);

            binding.transactionStatus.setText(isSuccess ?
                    "TRANSACTION SUCCESSFUL" : "TRANSACTION UNSUCCESSFUL");
        }
    }

    private void setupClickListeners() {
        binding.backArrowButton.setOnClickListener(v -> goToHomePage());

        binding.continueButton.setOnClickListener(v -> goToHomePage());
    }

    private void goToHomePage() {
        Intent intent = new Intent(this, MainActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        binding = null;
    }
}