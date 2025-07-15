package com.example.libra.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Transformations;

import com.example.libra.model.Book;
import com.example.libra.repository.BookRepository;

import java.util.List;

/**
 * ViewModel pour gérer les données des livres et les exposer à l'interface utilisateur.
 * Cette classe étend AndroidViewModel pour avoir accès au contexte de l'application.
 */
public class BookViewModel extends AndroidViewModel {

    private final BookRepository repository;
    private final LiveData<List<Book>> allBooks;
    private final MutableLiveData<String> searchQuery = new MutableLiveData<>();
    private final LiveData<List<Book>> searchResults;

    /**
     * Constructeur du ViewModel.
     *
     * @param application L'application Android
     */
    public BookViewModel(@NonNull Application application) {
        super(application);
        repository = new BookRepository(application);
        allBooks = repository.getAllBooks();
        
        // Transformation pour la recherche de livres
        searchResults = Transformations.switchMap(searchQuery, query -> {
            if (query == null || query.isEmpty()) {
                return allBooks;
            } else {
                return repository.searchBooks(query);
            }
        });
    }

    /**
     * Récupérer tous les livres.
     *
     * @return LiveData contenant la liste de tous les livres
     */
    public LiveData<List<Book>> getAllBooks() {
        return allBooks;
    }

    /**
     * Récupérer les résultats de recherche.
     *
     * @return LiveData contenant la liste des livres correspondant à la recherche
     */
    public LiveData<List<Book>> getSearchResults() {
        return searchResults;
    }

    /**
     * Définir la requête de recherche.
     *
     * @param query La chaîne de recherche
     */
    public void setSearchQuery(String query) {
        searchQuery.setValue(query);
    }

    /**
     * Insérer un nouveau livre.
     *
     * @param book Le livre à insérer
     */
    public void insert(Book book) {
        repository.insert(book);
    }

    /**
     * Mettre à jour un livre existant.
     *
     * @param book Le livre à mettre à jour
     */
    public void update(Book book) {
        repository.update(book);
    }

    /**
     * Supprimer un livre.
     *
     * @param book Le livre à supprimer
     */
    public void delete(Book book) {
        repository.delete(book);
    }

    /**
     * Supprimer tous les livres.
     */
    public void deleteAll() {
        repository.deleteAll();
    }
}
