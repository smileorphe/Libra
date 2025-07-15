package com.example.libra.model;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

/**
 * Entité représentant un livre dans la bibliothèque.
 * Cette classe est annotée avec @Entity pour Room, ce qui permet
 * de créer une table correspondante dans la base de données.
 */
@Entity(tableName = "books")
public class Book {

    @PrimaryKey(autoGenerate = true)
    private int id;

    private String title;
    private String author;
    private int year;

    /**
     * Constructeur pour créer un nouveau livre.
     *
     * @param title  Le titre du livre
     * @param author L'auteur du livre
     * @param year   L'année de publication du livre
     */
    public Book(String title, String author, int year) {
        this.title = title;
        this.author = author;
        this.year = year;
    }

    /**
     * Getters et setters pour accéder aux propriétés du livre
     */
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }

    /**
     * Méthode toString pour l'affichage du livre
     */
    @Override
    public String toString() {
        return "Book{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", author='" + author + '\'' +
                ", year=" + year +
                '}';
    }
}
