package gr.android.cineflix.ui.detailsScreen;

import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import gr.android.cineflix.R;
import gr.android.cineflix.databinding.ItemMovieBinding;
import gr.android.cineflix.databinding.ItemSimilarMovieBinding;
import gr.android.cineflix.domain.models.MovieUi;

public class SimilarMovieListAdapter extends ListAdapter<MovieUi, SimilarMovieListAdapter.MovieViewHolder> {

    private OnItemClickListener itemClickListener;

    protected SimilarMovieListAdapter(OnItemClickListener itemClickListener) {
        super(new DiffCallback());
        this.itemClickListener = itemClickListener;
    }

    @NonNull
    @Override
    public MovieViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        ItemSimilarMovieBinding binding = ItemSimilarMovieBinding.inflate(LayoutInflater.from(parent.getContext()), parent, false);
        return new MovieViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull MovieViewHolder holder, int position) {
        MovieUi movie = getItem(position);
        holder.bind(movie);
    }

    class MovieViewHolder extends RecyclerView.ViewHolder {

        private final ItemSimilarMovieBinding binding;

        MovieViewHolder(ItemSimilarMovieBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(MovieUi movie) {
            Glide.with(binding.moviePoster.getContext())
                    .load("https://image.tmdb.org/t/p/w500" + movie.getPosterPath())
                    .apply(new RequestOptions().placeholder(R.drawable.loading))
                    .into(binding.moviePoster);

            binding.getRoot().setOnClickListener(view -> {
                itemClickListener.onItemClick(movie.getId(), movie.isFavorite());
            });
        }
    }

    static class DiffCallback extends DiffUtil.ItemCallback<MovieUi> {
        @Override
        public boolean areItemsTheSame(@NonNull MovieUi oldItem, @NonNull MovieUi newItem) {
            // Check if items have the same ID
            return oldItem.getId() == newItem.getId() && oldItem.isFavorite() == newItem.isFavorite();
        }

        @Override
        public boolean areContentsTheSame(@NonNull MovieUi oldItem, @NonNull MovieUi newItem) {
            // Check if all properties (except ID) are the same
            return oldItem.getId() == newItem.getId() && oldItem.isFavorite() == newItem.isFavorite();
        }
    }

    ;

    public interface OnItemClickListener {
        void onItemClick(int movieId, boolean isFavorite);
    }
}

