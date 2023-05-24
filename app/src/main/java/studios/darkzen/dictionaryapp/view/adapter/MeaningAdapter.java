package studios.darkzen.dictionaryapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.R;

import java.util.List;

import studios.darkzen.dictionaryapp.service.model.Meanings;
import studios.darkzen.dictionaryapp.view.viewholders.MeaningsViewholder;

public class MeaningAdapter extends RecyclerView.Adapter<MeaningsViewholder> {
    private Context context;
    private List<Meanings> meaningsList;

    public MeaningAdapter(Context context, List<Meanings> meaningsList) {
        this.context = context;
        this.meaningsList = meaningsList;
    }

    @NonNull
    @Override
    public MeaningsViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new MeaningsViewholder(LayoutInflater.from(context).inflate(R.layout.meaning_list, parent ,false));
    }

    @Override
    public void onBindViewHolder(@NonNull MeaningsViewholder holder, int position) {
        holder.tvPartsofSpeach.setText(meaningsList.get(position).getPartOfSpeech());
        holder.rvMeaningDefinition.setHasFixedSize(true);
        holder.rvMeaningDefinition.setLayoutManager(new GridLayoutManager(context,1));
        DefinitionAdapter definitionAdapter=new DefinitionAdapter(context,meaningsList.get(position).getDefinitions());
        holder.rvMeaningDefinition.setAdapter(definitionAdapter);
    }

    @Override
    public int getItemCount() {
        return meaningsList.size();
    }
}
