# Libra - Application de Gestion de Bibliothèque Personnelle

![Logo Libra](app/src/main/res/drawable/ic_books_stack.xml)

## Présentation du Projet

Libra est une application Android moderne permettant aux utilisateurs de gérer leur bibliothèque personnelle. Elle offre une interface utilisateur élégante et intuitive pour cataloguer, rechercher et organiser vos livres.

## Fonctionnalités

- **Recherche rapide** : Barre de recherche avec design moderne et coins arrondis
- **Affichage des livres** : Liste de livres avec cartes arrondies et indicateurs visuels
- **Ajout de livres** : Bouton flottant (FAB) pour ajouter facilement de nouveaux livres
- **Interface moderne** : Design épuré avec palette de couleurs indigo et blanc
- **Écran de démarrage** : Splash screen accueillant avec animation

## Structure du Projet

### Architecture

L'application suit l'architecture MVVM (Model-View-ViewModel) recommandée par Google pour les applications Android modernes :

- **Model** : Classes de données représentant les livres et autres entités
- **View** : Layouts XML et fragments pour l'interface utilisateur
- **ViewModel** : Classes gérant la logique métier et la communication avec le modèle

### Principaux Composants

#### Activités
- `MainActivity` : Activité principale avec la liste des livres et la barre de recherche
- `SplashActivity` : Écran de démarrage avec animation
- `BookDetailActivity` : Affichage détaillé d'un livre
- `AddBookActivity` : Formulaire d'ajout/modification de livre

#### Layouts
- `activity_main.xml` : Layout principal avec RecyclerView et barre de recherche
- `activity_splash.xml` : Écran de démarrage avec logo et texte de bienvenue
- `item_book.xml` : Layout pour chaque carte de livre dans la liste

#### Ressources
- `colors.xml` : Définition de la palette de couleurs (indigo, gris, blanc)
- `styles.xml` : Styles pour les composants UI (cartes, textes, boutons)
- `drawable/` : Icônes et images vectorielles
- `mipmap/` : Icônes de l'application

## Design et Style

### Palette de Couleurs

- **Couleur Primaire** : Indigo (`#4F4FD9`)
- **Couleur Primaire Foncée** : Indigo foncé (`#3838A3`)
- **Couleur Primaire Claire** : Indigo clair (`#8A8AE5`)
- **Couleur d'Accent** : Vert (`#4CAF50`)
- **Couleur d'Action** : Rouge (`#FF5252`)
- **Fond** : Gris très clair (`#F5F5F5`)
- **Surface** : Blanc (`#FFFFFF`)
- **Texte Principal** : Noir (`#212121`)
- **Texte Secondaire** : Gris (`#757575`)

### Composants UI Personnalisés

#### Barre de Recherche
- Coins très arrondis (28dp)
- Fond blanc avec ombre portée
- Placeholder en noir
- Séparation de la Toolbar pour un meilleur contrôle visuel

#### Cartes de Livres
- `MaterialCardView` avec coins arrondis (24dp)
- Indicateur vertical coloré
- Affichage de l'année dans une pastille arrondie
- Ombre légère pour effet de profondeur

#### Bouton d'Ajout (FAB)
- Couleur indigo primaire
- Icône personnalisée (symbole plus blanc)
- Élévation pour effet de profondeur

#### Icône de l'Application
- Design vectoriel représentant une pile de livres
- Fond indigo unifié
- Compatible avec les icônes adaptatives d'Android

## Technologies Utilisées

- **Langage** : Java/Kotlin
- **SDK Minimum** : Android 5.0 (API 21)
- **SDK Cible** : Android 13 (API 33)
- **Bibliothèques** :
  - AndroidX Core et AppCompat
  - Material Components pour Android
  - RecyclerView et CardView
  - ConstraintLayout
  - Room (pour la persistance des données)
  - Lifecycle Components (ViewModel, LiveData)

## Installation et Configuration

### Prérequis
- Android Studio 4.0+
- JDK 8+
- SDK Android (API 21+)

### Étapes d'Installation
1. Cloner le dépôt : `git clone [URL_DU_REPO]`
2. Ouvrir le projet dans Android Studio
3. Synchroniser avec Gradle
4. Exécuter sur un émulateur ou appareil physique

## Captures d'Écran

*[Insérer des captures d'écran ici]*

## Améliorations Futures

- Ajout de catégories et de tags pour les livres
- Intégration avec des API de livres (Google Books, etc.)
- Mode hors ligne avec synchronisation
- Partage de collections
- Statistiques de lecture
- Mode sombre

## Auteur

*[Votre nom/équipe]*

## Licence

*[Informations de licence]*
