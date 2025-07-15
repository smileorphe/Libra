package com.example.libra.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.DiffUtil;
import androidx.recyclerview.widget.ListAdapter;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libra.R;
import com.example.libra.model.Book;

/**
 * Adaptateur pour afficher les livres dans un RecyclerView.
 * Utilise ListAdapter avec DiffUtil pour des mises à jour efficaces.
 */
public class BookAdapter extends ListAdapter<Book, BookAdapter.BookViewHolder> {

    private OnItemClickListener listener;

    /**
     * Constructeur de l'adaptateur.
     */
    public BookAdapter() {
        super(DIFF_CALLBACK);
    }

    /**
     * DiffUtil.ItemCallback pour comparer les livres et optimiser les mises à jour.
     */
    private static final DiffUtil.ItemCallback<Book> DIFF_CALLBACK = new DiffUtil.ItemCallback<Book>() {
        @Override
        public boolean areItemsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getId() == newItem.getId();
        }

        @Override
        public boolean areContentsTheSame(@NonNull Book oldItem, @NonNull Book newItem) {
            return oldItem.getTitle().equals(newItem.getTitle()) &&
                   oldItem.getAuthor().equals(newItem.getAuthor()) &&
                   oldItem.getYear() == newItem.getYear();
        }
    };

    @NonNull
    @Override
    public BookViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_book, parent, false);
        return new BookViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull BookViewHolder holder, int position) {
        Book currentBook = getItem(position);
        holder.bind(currentBook);
    }

    /**
     * Récupérer un livre à une position donnée.
     *
     * @param position La position du livre
     * @return Le livre à la position donnée
     */
    public Book getBookAt(int position) {
        return getItem(position);
    }

    /**
     * ViewHolder pour les éléments de la liste.
     */
    class BookViewHolder extends RecyclerView.ViewHolder {
        private final TextView textViewTitle;
        private final TextView textViewAuthor;
        private final TextView textViewYear;

        BookViewHolder(@NonNull View itemView) {
            super(itemView);
            textViewTitle = itemView.findViewById(R.id.text_view_title);
            textViewAuthor = itemView.findViewById(R.id.text_view_author);
            textViewYear = itemView.findViewById(R.id.text_view_year);

            // Définir le listener de clic sur l'élément
            itemView.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (listener != null && position != RecyclerView.NO_POSITION) {
                    listener.onItemClick(getItem(position));
                }
            });
        }

        /**
         * Lier les données du livre aux vues.
         *
         * @param book Le livre à afficher
         */
        void bind(Book book) {
            textViewTitle.setText(book.getTitle());
            textViewAuthor.setText(book.getAuthor());
            textViewYear.setText(String.valueOf(book.getYear()));
        }
    }

    /**
     * Interface pour gérer les clics sur les éléments.
     */
    public interface OnItemClickListener {
        void onItemClick(Book book);
    }

    /**
     * Définir le listener de clic sur les éléments.
     *
     * @param listener Le listener à définir
     */
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }
}
