package studios.darkzen.dictionaryapp.view.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.dictionaryapp.R;

import java.util.List;

import studios.darkzen.dictionaryapp.service.model.Definitions;
import studios.darkzen.dictionaryapp.view.viewholders.DefinitionViewholder;

public class DefinitionAdapter extends RecyclerView.Adapter<DefinitionViewholder> {
    private Context context;
    private List<Definitions> definitionsList;

    public DefinitionAdapter(Context context, List<Definitions> definitionsList) {
        this.context = context;
        this.definitionsList = definitionsList;
    }

    @NonNull
    @Override
    public DefinitionViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new DefinitionViewholder(LayoutInflater.from(context).inflate(R.layout.definition_list,parent,false) );
    }

    @Override
    public void onBindViewHolder(@NonNull DefinitionViewholder holder, int position) {

        holder.tvMeaning.setText(definitionsList.get(position).getDefinition());

        String exampleText = definitionsList.get(position).getExample();
        if (exampleText != null) {
            holder.tvExample.setText("\"" + exampleText + "\"");
        } else {
            holder.tvExample.setText("");
        }


        StringBuilder syn = new StringBuilder();
        List<String> synonyms = definitionsList.get(position).getSynonyms();

        if (synonyms != null && !synonyms.isEmpty()) {
            for (String synonym : synonyms) {
                syn.append(synonym).append(", ");
            }
            syn.setLength(syn.length() - 2);
            holder.synTextItem.setText(syn);
            holder.synTextItem.setVisibility(View.VISIBLE);
            holder.tvSynonyms.setVisibility(View.VISIBLE);
        } else {
            holder.synTextItem.setVisibility(View.GONE);
            holder.tvSynonyms.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return definitionsList.size();
    }
}
