package studios.darkzen.dictionaryapp.view.viewholders;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.R;

public class MeaningsViewholder extends RecyclerView.ViewHolder {
    public TextView tvPartsofSpeach, meaningtitle;
    public View line;
    public RecyclerView rvMeaningDefinition;

    public MeaningsViewholder(@NonNull View itemView) {
        super(itemView);
        tvPartsofSpeach = itemView.findViewById(R.id.tvPartsofSpeach);
        meaningtitle = itemView.findViewById(R.id.meaningtitle);
        line = itemView.findViewById(R.id.line);
        rvMeaningDefinition = itemView.findViewById(R.id.rvMeaningDefinition);
    }
}
