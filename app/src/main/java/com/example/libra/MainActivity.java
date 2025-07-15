package com.example.libra;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libra.adapter.BookAdapter;
import com.example.libra.model.Book;
import com.example.libra.ui.SwipeToDeleteCallback;
import com.example.libra.viewmodel.BookViewModel;
import com.google.android.material.dialog.MaterialAlertDialogBuilder;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputLayout;

import java.util.Calendar;
import java.util.Objects;

/**
 * Activité principale de l'application Libra.
 * Cette activité affiche la liste des livres et gère les interactions utilisateur.
 */
public class MainActivity extends AppCompatActivity implements SwipeToDeleteCallback.SwipeToDeleteListener {

    private BookViewModel bookViewModel;
    private BookAdapter adapter;
    private TextView textViewEmpty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Configuration de la Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Initialisation des vues
        textViewEmpty = findViewById(R.id.text_view_empty);
        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        FloatingActionButton fabAddBook = findViewById(R.id.fab_add_book);
        EditText searchEditText = findViewById(R.id.search_edit_text);

        // Configuration du RecyclerView
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setHasFixedSize(true);

        // Configuration de l'adaptateur
        adapter = new BookAdapter();
        recyclerView.setAdapter(adapter);

        // Initialisation du ViewModel
        bookViewModel = new ViewModelProvider(this).get(BookViewModel.class);

        // Observer les changements de données
        bookViewModel.getAllBooks().observe(this, books -> {
            adapter.submitList(books);
            if (books.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                textViewEmpty.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                textViewEmpty.setVisibility(View.GONE);
            }
        });

        // Configuration du swipe-to-delete
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(
                new SwipeToDeleteCallback(this, this));
        itemTouchHelper.attachToRecyclerView(recyclerView);

        // Écouteur pour le bouton d'ajout
        fabAddBook.setOnClickListener(v -> showAddEditBookDialog(null));

        // Écouteur pour les clics sur les éléments de la liste
        adapter.setOnItemClickListener(book -> showAddEditBookDialog(book));

        // Configuration de la recherche en temps réel
        searchEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Non utilisé
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                // Mettre à jour la recherche lorsque le texte change
                bookViewModel.setSearchQuery(s.toString());
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Non utilisé
            }
        });

        // Observer les résultats de recherche
        bookViewModel.getSearchResults().observe(this, books -> {
            adapter.submitList(books);
            if (books.isEmpty()) {
                recyclerView.setVisibility(View.GONE);
                textViewEmpty.setVisibility(View.VISIBLE);
            } else {
                recyclerView.setVisibility(View.VISIBLE);
                textViewEmpty.setVisibility(View.GONE);
            }
        });
    }

    /**
     * Afficher le dialogue d'ajout/modification de livre.
     *
     * @param book Le livre à modifier, ou null pour un nouveau livre
     */
    private void showAddEditBookDialog(Book book) {
        // Créer le dialogue avec Material Design
        MaterialAlertDialogBuilder builder = new MaterialAlertDialogBuilder(this);
        
        // Définir le titre en fonction de l'opération (ajout ou modification)
        builder.setTitle(book == null ? R.string.add_book : R.string.edit_book);
        
        // Inflater le layout du dialogue
        View view = LayoutInflater.from(this).inflate(R.layout.dialog_add_edit_book, null);
        builder.setView(view);

        // Récupérer les vues du dialogue
        TextInputLayout textInputTitle = view.findViewById(R.id.text_input_title);
        TextInputLayout textInputAuthor = view.findViewById(R.id.text_input_author);
        TextInputLayout textInputYear = view.findViewById(R.id.text_input_year);
        
        EditText editTextTitle = view.findViewById(R.id.edit_text_title);
        EditText editTextAuthor = view.findViewById(R.id.edit_text_author);
        EditText editTextYear = view.findViewById(R.id.edit_text_year);

        // Pré-remplir les champs si c'est une modification
        if (book != null) {
            editTextTitle.setText(book.getTitle());
            editTextAuthor.setText(book.getAuthor());
            editTextYear.setText(String.valueOf(book.getYear()));
        }

        // Créer le dialogue
        AlertDialog dialog = builder.create();
        
        // Configurer les boutons
        view.findViewById(R.id.button_cancel).setOnClickListener(v -> dialog.dismiss());
        
        view.findViewById(R.id.button_save).setOnClickListener(v -> {
            // Récupérer les valeurs des champs
            String title = Objects.requireNonNull(editTextTitle.getText()).toString().trim();
            String author = Objects.requireNonNull(editTextAuthor.getText()).toString().trim();
            String yearStr = Objects.requireNonNull(editTextYear.getText()).toString().trim();

            // Valider les champs
            boolean isValid = true;

            if (title.isEmpty()) {
                textInputTitle.setError(getString(R.string.title_required));
                isValid = false;
            } else {
                textInputTitle.setError(null);
            }

            if (author.isEmpty()) {
                textInputAuthor.setError(getString(R.string.author_required));
                isValid = false;
            } else {
                textInputAuthor.setError(null);
            }

            int year = 0;
            if (yearStr.isEmpty()) {
                textInputYear.setError(getString(R.string.year_required));
                isValid = false;
            } else {
                try {
                    year = Integer.parseInt(yearStr);
                    int currentYear = Calendar.getInstance().get(Calendar.YEAR);
                    if (year <= 0 || year > currentYear) {
                        textInputYear.setError(getString(R.string.year_invalid));
                        isValid = false;
                    } else {
                        textInputYear.setError(null);
                    }
                } catch (NumberFormatException e) {
                    textInputYear.setError(getString(R.string.year_invalid));
                    isValid = false;
                }
            }

            // Si tous les champs sont valides, enregistrer le livre
            if (isValid) {
                if (book == null) {
                    // Créer un nouveau livre
                    Book newBook = new Book(title, author, year);
                    bookViewModel.insert(newBook);
                    Toast.makeText(MainActivity.this, R.string.book_saved, Toast.LENGTH_SHORT).show();
                } else {
                    // Mettre à jour le livre existant
                    Book updatedBook = new Book(title, author, year);
                    updatedBook.setId(book.getId());
                    bookViewModel.update(updatedBook);
                    Toast.makeText(MainActivity.this, R.string.book_saved, Toast.LENGTH_SHORT).show();
                }
                dialog.dismiss();
            }
        });

        dialog.show();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflater le menu
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        // Gérer les clics sur les éléments du menu
        if (item.getItemId() == R.id.action_delete_all) {
            showDeleteAllConfirmationDialog();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Afficher le dialogue de confirmation pour supprimer tous les livres.
     */
    private void showDeleteAllConfirmationDialog() {
        new MaterialAlertDialogBuilder(this)
                .setTitle(R.string.delete_all_books)
                .setMessage(R.string.confirm_delete_all)
                .setPositiveButton(android.R.string.yes, (dialog, which) -> {
                    bookViewModel.deleteAll();
                    Toast.makeText(this, R.string.all_books_deleted, Toast.LENGTH_SHORT).show();
                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    @Override
    public void onSwiped(int position) {
        // Supprimer le livre lorsqu'il est swipé
        Book book = adapter.getBookAt(position);
        bookViewModel.delete(book);
        Toast.makeText(this, R.string.book_deleted, Toast.LENGTH_SHORT).show();
    }
}