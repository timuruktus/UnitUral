package trelico.ru.unitUral.models.viewObjects;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import trelico.ru.unitUral.models.interfaces.RecyclerItem;

public class LoadMoreViewHolder  extends RecyclerView.ViewHolder implements RecyclerItem{


    public LoadMoreViewHolder(@NonNull View itemView){
        super(itemView);
    }
}
