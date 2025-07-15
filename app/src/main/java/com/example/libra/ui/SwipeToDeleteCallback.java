package com.example.libra.ui;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.PorterDuff;
import android.graphics.PorterDuffXfermode;
import android.graphics.drawable.ColorDrawable;
import android.graphics.drawable.Drawable;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.RecyclerView;

import com.example.libra.R;

/**
 * Classe d'aide pour gérer le swipe-to-delete sur le RecyclerView.
 * Cette classe étend ItemTouchHelper.SimpleCallback pour personnaliser
 * le comportement du swipe et son apparence.
 */
public class SwipeToDeleteCallback extends ItemTouchHelper.SimpleCallback {

    private final SwipeToDeleteListener listener;
    private final ColorDrawable background;
    private final Drawable deleteIcon;
    private final int iconMargin;
    private final Paint clearPaint;

    /**
     * Constructeur de la classe SwipeToDeleteCallback.
     *
     * @param context  Le contexte de l'application
     * @param listener L'écouteur pour les événements de swipe
     */
    public SwipeToDeleteCallback(Context context, SwipeToDeleteListener listener) {
        super(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT);
        this.listener = listener;
        
        // Configuration de l'arrière-plan
        background = new ColorDrawable(Color.parseColor("#F44336")); // Rouge
        
        // Configuration de l'icône de suppression
        deleteIcon = ContextCompat.getDrawable(context, android.R.drawable.ic_menu_delete);
        iconMargin = (int) context.getResources().getDimension(R.dimen.icon_margin);
        
        // Configuration du Paint pour effacer
        clearPaint = new Paint();
        clearPaint.setXfermode(new PorterDuffXfermode(PorterDuff.Mode.CLEAR));
    }

    @Override
    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
        // Ne pas permettre le déplacement des éléments
        return false;
    }

    @Override
    public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int direction) {
        // Appeler le listener lorsqu'un élément est swipé
        int position = viewHolder.getAdapterPosition();
        listener.onSwiped(position);
    }

    @Override
    public void onChildDraw(@NonNull Canvas c, @NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
        super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);

        View itemView = viewHolder.itemView;
        int itemHeight = itemView.getBottom() - itemView.getTop();
        boolean isCanceled = dX == 0f && !isCurrentlyActive;

        if (isCanceled) {
            // Effacer le canvas si l'action est annulée
            c.drawRect(itemView.getRight() + dX, itemView.getTop(), itemView.getRight(), itemView.getBottom(), clearPaint);
            return;
        }

        // Dessiner l'arrière-plan
        background.setBounds(
                itemView.getRight() + (int) dX,
                itemView.getTop(),
                itemView.getRight(),
                itemView.getBottom()
        );
        background.draw(c);

        // Dessiner l'icône de suppression
        if (deleteIcon != null) {
            int iconTop = itemView.getTop() + (itemHeight - deleteIcon.getIntrinsicHeight()) / 2;
            int iconBottom = iconTop + deleteIcon.getIntrinsicHeight();

            if (dX < 0) { // Swipe vers la gauche
                int iconLeft = itemView.getRight() - iconMargin - deleteIcon.getIntrinsicWidth();
                int iconRight = itemView.getRight() - iconMargin;
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            } else { // Swipe vers la droite
                int iconLeft = itemView.getLeft() + iconMargin;
                int iconRight = itemView.getLeft() + iconMargin + deleteIcon.getIntrinsicWidth();
                deleteIcon.setBounds(iconLeft, iconTop, iconRight, iconBottom);
            }
            deleteIcon.draw(c);
        }
    }

    /**
     * Interface pour écouter les événements de swipe.
     */
    public interface SwipeToDeleteListener {
        void onSwiped(int position);
    }
}
