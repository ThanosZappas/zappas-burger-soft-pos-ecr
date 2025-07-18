package gr.android.cineflix.ui;

import static androidx.navigation.ActivityKt.findNavController;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;
import androidx.navigation.NavController;

import dagger.hilt.android.AndroidEntryPoint;
import gr.android.cineflix.R;
import gr.android.cineflix.databinding.ActivityMainBinding;

@AndroidEntryPoint
public class MainActivity extends AppCompatActivity {

    private NavController navController;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityMainBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
        navController = findNavController(this, R.id.navHostFragment);
    }
}