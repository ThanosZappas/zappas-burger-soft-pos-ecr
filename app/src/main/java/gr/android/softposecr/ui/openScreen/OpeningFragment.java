package gr.android.softposecr.ui.openScreen;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;
import gr.android.softposecr.R;
import gr.android.softposecr.databinding.FragmentOpeningBinding;

public class OpeningFragment extends Fragment {
    FragmentOpeningBinding binding;
    private DrawerLayout drawerLayout;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentOpeningBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        drawerLayout = binding.drawerLayout;

        // Set up existing place order button navigation
        binding.placeOrderButton.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigate(R.id.action_openingFragment_to_itemListFragment);
        });

        // Set up the menu button click listener
        binding.menuButton.setOnClickListener(v -> drawerLayout.openDrawer(GravityCompat.START));

        // Set up navigation view item selection
        binding.navigationView.setNavigationItemSelectedListener(menuItem -> {
            int id = menuItem.getItemId();
            if (id == R.id.nav_home) {
                // Already on home screen
            } else if (id == R.id.nav_orders) {
                Navigation.findNavController(binding.getRoot()).navigate(R.id.action_openingFragment_to_itemListFragment);
            } else if (id == R.id.nav_settings) {
                // Handle settings navigation
            } else if (id == R.id.nav_about) {
                // Handle about navigation
            } else if (id == R.id.nav_help) {
                // Handle help navigation
            }
            drawerLayout.closeDrawer(GravityCompat.START);
            return true;
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}
