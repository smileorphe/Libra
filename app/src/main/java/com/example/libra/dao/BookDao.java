package com.example.libra.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;
import androidx.room.Update;

import com.example.libra.model.Book;

import java.util.List;

/**
 * Interface DAO (Data Access Object) pour les opérations CRUD sur les livres.
 * Cette interface est annotée avec @Dao pour Room.
 */
@Dao
public interface BookDao {

    /**
     * Insérer un nouveau livre dans la base de données.
     * Si un livre avec le même ID existe déjà, il sera remplacé.
     *
     * @param book Le livre à insérer
     * @return L'ID du livre inséré
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    long insert(Book book);

    /**
     * Insérer plusieurs livres dans la base de données.
     *
     * @param books Liste de livres à insérer
     * @return Les IDs des livres insérés
     */
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    List<Long> insertAll(List<Book> books);

    /**
     * Mettre à jour un livre existant.
     *
     * @param book Le livre à mettre à jour
     * @return Le nombre de livres mis à jour (0 ou 1)
     */
    @Update
    int update(Book book);

    /**
     * Supprimer un livre de la base de données.
     *
     * @param book Le livre à supprimer
     * @return Le nombre de livres supprimés (0 ou 1)
     */
    @Delete
    int delete(Book book);

    /**
     * Supprimer tous les livres de la base de données.
     *
     * @return Le nombre de livres supprimés
     */
    @Query("DELETE FROM books")
    int deleteAll();

    /**
     * Récupérer un livre par son ID.
     *
     * @param id L'ID du livre à récupérer
     * @return Le livre correspondant à l'ID, ou null s'il n'existe pas
     */
    @Query("SELECT * FROM books WHERE id = :id")
    Book getBookById(int id);

    /**
     * Récupérer tous les livres de la base de données.
     * Le résultat est encapsulé dans un LiveData pour observer les changements.
     *
     * @return LiveData contenant la liste de tous les livres
     */
    @Query("SELECT * FROM books ORDER BY title ASC")
    LiveData<List<Book>> getAllBooks();

    /**
     * Rechercher des livres par titre ou auteur.
     * Cette méthode permet de rechercher des livres dont le titre ou l'auteur
     * contient la chaîne de recherche.
     *
     * @param searchQuery La chaîne de recherche
     * @return LiveData contenant la liste des livres correspondants
     */
    @Query("SELECT * FROM books WHERE title LIKE '%' || :searchQuery || '%' OR author LIKE '%' || :searchQuery || '%' ORDER BY title ASC")
    LiveData<List<Book>> searchBooks(String searchQuery);
}
