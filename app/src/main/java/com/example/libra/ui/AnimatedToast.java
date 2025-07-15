package com.example.libra.ui;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.TextView;
import android.widget.Toast;

import androidx.cardview.widget.CardView;

import com.example.libra.R;

/**
 * Classe utilitaire pour afficher des toasts animés et stylisés.
 * Cette classe permet de créer des toasts avec une apparence moderne et des animations.
 */
public class AnimatedToast {

    /**
     * Affiche un toast animé avec un style moderne.
     *
     * @param context Le contexte de l'application
     * @param message Le message à afficher
     * @param duration La durée d'affichage (Toast.LENGTH_SHORT ou Toast.LENGTH_LONG)
     */
    public static void show(Context context, String message, int duration) {
        Toast toast = new Toast(context);
        
        // Inflater la vue personnalisée
        View view = LayoutInflater.from(context).inflate(R.layout.toast_custom, null);
        
        // Configurer le message
        TextView textView = view.findViewById(R.id.toast_text);
        textView.setText(message);
        
        // Appliquer une animation à la CardView
        CardView cardView = view.findViewById(R.id.toast_card);
        Animation animation = AnimationUtils.loadAnimation(context, R.anim.toast_animation);
        cardView.startAnimation(animation);
        
        // Configurer le toast
        toast.setView(view);
        toast.setDuration(duration);
        toast.setGravity(Gravity.BOTTOM | Gravity.CENTER_HORIZONTAL, 0, 100);
        
        toast.show();
    }
    
    /**
     * Surcharge pour afficher un toast avec une ressource string.
     *
     * @param context Le contexte de l'application
     * @param stringResId L'ID de la ressource string
     * @param duration La durée d'affichage
     */
    public static void show(Context context, int stringResId, int duration) {
        show(context, context.getString(stringResId), duration);
    }
}
