package com.example.libra.ui;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.example.libra.MainActivity;
import com.example.libra.R;

/**
 * Activité d'écran de démarrage qui affiche un logo et des animations
 * avant de rediriger vers l'activité principale.
 */
public class SplashActivity extends AppCompatActivity {

    // Durée de l'écran de démarrage en millisecondes
    private static final long SPLASH_DURATION = 2000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        // Initialiser les animations
        initAnimations();

        // Configurer le délai avant de passer à l'activité principale
        new Handler(Looper.getMainLooper()).postDelayed(this::startMainActivity, SPLASH_DURATION);
    }

    /**
     * Initialise et démarre les animations pour les éléments de l'écran de démarrage
     */
    private void initAnimations() {
        // Récupérer les vues
        ImageView logo = findViewById(R.id.splash_logo);
        TextView title = findViewById(R.id.splash_title);
        ImageView leafBottomLeft = findViewById(R.id.leaf_bottom_left);
        ImageView leafBottomRight = findViewById(R.id.leaf_bottom_right);
        ImageView leafTopRight = findViewById(R.id.leaf_top_right);

        // Charger les animations
        Animation fadeIn = AnimationUtils.loadAnimation(this, R.anim.fade_in);
        Animation slideUp = AnimationUtils.loadAnimation(this, R.anim.slide_up);
        Animation slideFromLeft = AnimationUtils.loadAnimation(this, R.anim.slide_in_left);
        Animation slideFromRight = AnimationUtils.loadAnimation(this, R.anim.slide_in_right);
        Animation slideFromTop = AnimationUtils.loadAnimation(this, R.anim.slide_from_top);

        // Appliquer les animations
        logo.startAnimation(fadeIn);
        title.startAnimation(slideUp);
        leafBottomLeft.startAnimation(slideFromLeft);
        leafBottomRight.startAnimation(slideFromRight);
        leafTopRight.startAnimation(slideFromTop);
    }

    /**
     * Démarre l'activité principale et termine l'écran de démarrage
     */
    private void startMainActivity() {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
        
        // Appliquer une animation de transition
        overridePendingTransition(R.anim.fade_in, R.anim.fade_out);
        
        // Terminer cette activité pour qu'elle ne soit pas accessible via le bouton retour
        finish();
    }

    @Override
    public void onWindowFocusChanged(boolean hasFocus) {
        super.onWindowFocusChanged(hasFocus);
        if (hasFocus) {
            // Mode plein écran immersif pour une meilleure expérience
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                            | View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                            | View.SYSTEM_UI_FLAG_FULLSCREEN
                            | View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }
    }
}
