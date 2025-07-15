package com.example.libra.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.example.libra.dao.BookDao;
import com.example.libra.model.Book;

/**
 * Base de données Room pour l'application Libra.
 * Cette classe est annotée avec @Database pour définir les entités et la version.
 */
@Database(entities = {Book.class}, version = 1, exportSchema = false)
public abstract class BookDatabase extends RoomDatabase {

    // DAO pour les opérations sur les livres
    public abstract BookDao bookDao();

    // Instance unique de la base de données (Singleton)
    private static volatile BookDatabase INSTANCE;

    /**
     * Obtenir l'instance unique de la base de données.
     * Si l'instance n'existe pas, elle est créée.
     *
     * @param context Le contexte de l'application
     * @return L'instance unique de la base de données
     */
    public static BookDatabase getInstance(Context context) {
        if (INSTANCE == null) {
            synchronized (BookDatabase.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(
                            context.getApplicationContext(),
                            BookDatabase.class,
                            "libra_database")
                            .fallbackToDestructiveMigration() // En cas de changement de version, la base est recréée
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}
