package com.example.libra.ui;

import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.view.MotionEvent;
import android.view.View;
import android.view.animation.AccelerateDecelerateInterpolator;

/**
 * Classe utilitaire pour ajouter des animations aux boutons et autres éléments interactifs.
 * Cette classe permet d'ajouter facilement des effets de pression (scale down) et de relâchement (scale up)
 * pour améliorer l'expérience utilisateur.
 */
public class ButtonAnimator {

    private static final float SCALE_DOWN_VALUE = 0.95f;
    private static final float SCALE_NORMAL_VALUE = 1.0f;
    private static final int ANIMATION_DURATION = 150;

    /**
     * Ajoute un effet d'animation de pression à une vue.
     * 
     * @param view La vue à animer
     */
    public static void addPressAnimation(View view) {
        view.setOnTouchListener((v, event) -> {
            switch (event.getAction()) {
                case MotionEvent.ACTION_DOWN:
                    animateButtonScale(v, SCALE_DOWN_VALUE);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    animateButtonScale(v, SCALE_NORMAL_VALUE);
                    break;
            }
            // Permet à l'événement onClick de se déclencher normalement
            return false;
        });
    }

    /**
     * Anime la mise à l'échelle d'une vue.
     * 
     * @param view La vue à animer
     * @param scale La valeur d'échelle cible
     */
    private static void animateButtonScale(View view, float scale) {
        ObjectAnimator scaleX = ObjectAnimator.ofFloat(view, "scaleX", scale);
        ObjectAnimator scaleY = ObjectAnimator.ofFloat(view, "scaleY", scale);
        
        AnimatorSet animatorSet = new AnimatorSet();
        animatorSet.playTogether(scaleX, scaleY);
        animatorSet.setDuration(ANIMATION_DURATION);
        animatorSet.setInterpolator(new AccelerateDecelerateInterpolator());
        animatorSet.start();
    }
}
