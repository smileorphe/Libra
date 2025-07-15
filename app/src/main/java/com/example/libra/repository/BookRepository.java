package com.example.libra.repository;

import android.app.Application;

import androidx.lifecycle.LiveData;

import com.example.libra.dao.BookDao;
import com.example.libra.database.BookDatabase;
import com.example.libra.model.Book;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Repository pour gérer les opérations de données sur les livres.
 * Cette classe fait le lien entre le ViewModel et la source de données (Room).
 */
public class BookRepository {

    private final BookDao bookDao;
    private final LiveData<List<Book>> allBooks;
    private final ExecutorService executorService;

    /**
     * Constructeur du repository.
     *
     * @param application L'application Android
     */
    public BookRepository(Application application) {
        BookDatabase database = BookDatabase.getInstance(application);
        bookDao = database.bookDao();
        allBooks = bookDao.getAllBooks();
        executorService = Executors.newSingleThreadExecutor();
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
     * Rechercher des livres par titre ou auteur.
     *
     * @param query La chaîne de recherche
     * @return LiveData contenant la liste des livres correspondants
     */
    public LiveData<List<Book>> searchBooks(String query) {
        return bookDao.searchBooks(query);
    }

    /**
     * Insérer un nouveau livre dans la base de données.
     * Cette opération est exécutée sur un thread séparé.
     *
     * @param book Le livre à insérer
     */
    public void insert(Book book) {
        executorService.execute(() -> {
            bookDao.insert(book);
        });
    }

    /**
     * Mettre à jour un livre existant.
     * Cette opération est exécutée sur un thread séparé.
     *
     * @param book Le livre à mettre à jour
     */
    public void update(Book book) {
        executorService.execute(() -> {
            bookDao.update(book);
        });
    }

    /**
     * Supprimer un livre de la base de données.
     * Cette opération est exécutée sur un thread séparé.
     *
     * @param book Le livre à supprimer
     */
    public void delete(Book book) {
        executorService.execute(() -> {
            bookDao.delete(book);
        });
    }

    /**
     * Supprimer tous les livres de la base de données.
     * Cette opération est exécutée sur un thread séparé.
     */
    public void deleteAll() {
        executorService.execute(() -> {
            bookDao.deleteAll();
        });
    }
}
