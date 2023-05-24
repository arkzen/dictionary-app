package studios.darkzen.dictionaryapp.view.viewholders;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.R;


public class DefinitionViewholder extends RecyclerView.ViewHolder {
    public TextView tvMeaning, tvExample, tvSynonyms, synTextItem;
    public ImageView bulletitem;

    public DefinitionViewholder(@NonNull View itemView) {
        super(itemView);
        bulletitem = itemView.findViewById(R.id.bulletitem);
        tvMeaning = itemView.findViewById(R.id.tvMeaning);
        tvExample = itemView.findViewById(R.id.tvExample);
        tvSynonyms = itemView.findViewById(R.id.tvSynonyms);
        synTextItem = itemView.findViewById(R.id.synTextItem);
    }
}
