package gr.android.cineflix.ui.detailsScreen;

import android.content.Intent;
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

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;
import java.util.stream.Collectors;

import dagger.hilt.android.AndroidEntryPoint;
import gr.android.cineflix.R;
import gr.android.cineflix.common.Helper;
import gr.android.cineflix.databinding.FragmentDetailsBinding;
import gr.android.cineflix.domain.models.CastUi;
import gr.android.cineflix.domain.models.GenreUi;
import gr.android.cineflix.domain.models.MovieDetailsWithReviewsUi;
import gr.android.cineflix.domain.models.ReviewsUi;
import gr.android.cineflix.ui.homeScreen.MovieViewModel;

@AndroidEntryPoint
public class DetailsFragment extends Fragment {

    private FragmentDetailsBinding binding;
    private DetailsViewModel detailsViewModel;
    private SimilarMovieListAdapter adapter;
    private MovieViewModel viewModel;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        super.onCreateView(inflater, container, savedInstanceState);
        binding = FragmentDetailsBinding.inflate(inflater);
        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        detailsViewModel = new ViewModelProvider(this).get(DetailsViewModel.class);
        viewModel = new ViewModelProvider(this).get(MovieViewModel.class);

        setupFixedUI();

        boolean isFavorite = false;

        // Fetch movie details and reviews
        if (getArguments() != null) {
            int movieId = getArguments().getInt("MOVIE_ID");
            isFavorite = getArguments().getBoolean("FAVORITE");
            if (movieId != -1) {
                detailsViewModel.fetchMovieDetailsWithReviews(movieId);
                detailsViewModel.fetchSimilarMovies(movieId);
            }
        }
        setupRecyclerView();
        initObservers(isFavorite);
    }

    private void setupFixedUI() {
        binding.backArrowButton.setOnClickListener(v -> {
            Navigation.findNavController(binding.getRoot()).navigateUp();
        });
    }

    private void initObservers(boolean isFavorite) {
        detailsViewModel.movieDetailsLiveData.observe(getViewLifecycleOwner(), movieDetailsWithReviewsUi -> {
            if (movieDetailsWithReviewsUi != null) {
                // Update UI with the movie details and reviews
                updateUI(movieDetailsWithReviewsUi, isFavorite);
            }
        });

        detailsViewModel.getSimilarMovies().observe(getViewLifecycleOwner(), movies -> {
            adapter.submitList(movies.subList(0, 6));
        });
    }

    private void updateUI(MovieDetailsWithReviewsUi movieDetailsWithReviewsUi, boolean isFavorite) {
        setupMoviePoster(movieDetailsWithReviewsUi.getMovieDetails().getBackdropPath());
        setupShareButton(movieDetailsWithReviewsUi);
        binding.movieTitle.setText(movieDetailsWithReviewsUi.getMovieDetails().getTitle());
        setupGenres(movieDetailsWithReviewsUi.getMovieDetails().getGenres());
        if (isFavorite == true) {
            binding.favoriteIcon.setImageResource(R.drawable.ic_favorite_selected);
        }
        binding.movieReleaseDate.setText(movieDetailsWithReviewsUi.getMovieDetails().getReleaseDate());
        float rating = (float) movieDetailsWithReviewsUi.getMovieDetails().getVoteAverage() / 2;
        binding.movieRating.setRating(rating);
        binding.movieRuntime.setText(Helper.formatRuntime(movieDetailsWithReviewsUi.getMovieDetails().getRuntime()));
        binding.movieDescription.setText(movieDetailsWithReviewsUi.getMovieDetails().getOverview());
        setupMovieCast(movieDetailsWithReviewsUi.getCastList());
        setupMovieReviews(movieDetailsWithReviewsUi);
    }


    private void setupMovieCast(List<CastUi> cast) {
        String castNamesText = cast.stream()
                .map(CastUi::getName)
                .collect(Collectors.joining(", "));
        binding.movieCast.setText(castNamesText);
    }

    private void setupMovieReviews(MovieDetailsWithReviewsUi movieDetailsWithReviewsUi) {
        List<ReviewsUi> reviews = movieDetailsWithReviewsUi.getReviews();

        if (reviews.isEmpty()) {
            binding.reviewsLayout.setVisibility(View.GONE);
            return;
        }
        binding.reviewsLayout.setVisibility(View.VISIBLE);
        binding.reviewsTitle.setText("Reviews");

        switch (reviews.size()) {
            case 1:
                binding.firstReviewer.setText(reviews.get(0).getAuthor());
                binding.firstReview.setText(reviews.get(0).getContent());
                binding.secondReviewer.setVisibility(View.GONE);
                binding.secondReview.setVisibility(View.GONE);
                binding.thirdReviewer.setVisibility(View.GONE);
                binding.thirdReview.setVisibility(View.GONE);
                break;
            case 2:
                binding.firstReviewer.setText(reviews.get(0).getAuthor());
                binding.firstReview.setText(reviews.get(0).getContent());
                binding.secondReviewer.setText(reviews.get(1).getAuthor());
                binding.secondReview.setText(reviews.get(1).getContent());
                binding.thirdReviewer.setVisibility(View.GONE);
                binding.thirdReview.setVisibility(View.GONE);
                break;
            default:
                binding.firstReviewer.setText(reviews.get(0).getAuthor());
                binding.firstReview.setText(reviews.get(0).getContent());
                binding.secondReviewer.setText(reviews.get(1).getAuthor());
                binding.secondReview.setText(reviews.get(1).getContent());
                binding.thirdReviewer.setText(reviews.get(2).getAuthor());
                binding.thirdReview.setText(reviews.get(2).getContent());
                break;
        }
    }

    private void setupRecyclerView() {
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.HORIZONTAL, false);
        binding.similarMoviesRecyclerView.setLayoutManager(layoutManager);

        adapter = new SimilarMovieListAdapter(
                (movieId, isFavorite) -> {
                    Bundle bundle = new Bundle();
                    bundle.putInt("MOVIE_ID", movieId);
                    bundle.putBoolean("FAVORITE", isFavorite);
                    Navigation.findNavController(binding.getRoot()).navigate(R.id.action_detailsFragment_self, bundle);
                }
        );

        binding.similarMoviesRecyclerView.setAdapter(adapter);
        binding.similarMoviesRecyclerView.setItemAnimator(null);
    }


    private void setupGenres(List<GenreUi> genres) {
        String genresText = genres.stream()
                .map(GenreUi::getName)  // Assuming GenreUi has a getName() method
                .collect(Collectors.joining(", "));
        binding.movieGenre.setText(genresText);
    }

    private void setupMoviePoster(String backdropPath) {
        // Load movie poster using Glide
        String fullImageUrl = "https://image.tmdb.org/t/p/w500" + backdropPath;

        Glide.with(binding.moviePoster.getContext())
                .load(fullImageUrl)
                .apply(new RequestOptions().placeholder(R.drawable.loading))
                .into(binding.moviePoster);
    }

    private void setupShareButton(MovieDetailsWithReviewsUi movieDetailsWithReviewsUi) {
        String movieHomepage = movieDetailsWithReviewsUi.getMovieDetails().getHomepage();
        if (movieHomepage != null && !movieHomepage.isEmpty()) {
            binding.shareButton.setVisibility(View.VISIBLE);
            binding.shareButton.setOnClickListener(v -> {
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_TEXT, movieHomepage);
                startActivity(Intent.createChooser(shareIntent, "Share Movie Homepage"));
            });
        } else {
            binding.shareButton.setVisibility(View.GONE);
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
        adapter = null;
    }
}
