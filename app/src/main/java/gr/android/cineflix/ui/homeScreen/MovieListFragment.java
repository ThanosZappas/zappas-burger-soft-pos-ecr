package gr.android.cineflix.ui.homeScreen;

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

import dagger.hilt.android.AndroidEntryPoint;
import gr.android.cineflix.R;
import gr.android.cineflix.databinding.FragmentMovieBinding;

@AndroidEntryPoint
public class MovieListFragment extends Fragment {

    private MovieViewModel viewModel;
    private MovieListAdapter adapter;

    private FragmentMovieBinding binding;

    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = FragmentMovieBinding.inflate(inflater, container, false);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupRecyclerView();
        setupSwipeRefresh();
        setupViewModel();
    }

    private void setupRecyclerView() {
        binding.recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        adapter = new MovieListAdapter(
                movie -> viewModel.toggleFavorite(movie),
                (movieId, isFavorite) -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("MOVIE_ID", movieId);
                    bundle.putBoolean("FAVORITE", isFavorite);
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_movieListFragment_to_detailsFragment, bundle);
                }
        );
        binding.recyclerView.setAdapter(adapter);
        binding.recyclerView.setItemAnimator(null);
    }

    private void setupSwipeRefresh() {
        binding.swipeRefresh.setOnRefreshListener(() -> {
            viewModel.refreshMovies();
            binding.swipeRefresh.setRefreshing(false);
        });
    }

    private void setupViewModel() {
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);
        viewModel.initWifiMonitoring(requireContext());

        // Observe movies
        viewModel.getMovies().observe(getViewLifecycleOwner(), movies -> {
            adapter.submitList(movies);
        });
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        adapter = null;
    }
}
