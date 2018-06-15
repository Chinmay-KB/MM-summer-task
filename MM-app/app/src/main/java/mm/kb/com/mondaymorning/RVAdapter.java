package mm.kb.com.mondaymorning;

import android.app.Activity;
import android.support.design.widget.Snackbar;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.content.Intent;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
    public RVAdapter(List<String> namesList) {
        feature=namesList;
        title="Something";
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView subjectCode;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            subjectCode = (TextView)itemView.findViewById(R.id.tv_text);
        }
    }
    String title;
    List<String> feature;

    RVAdapter(ArticleActivity interimActivity, List<String> feature){
        this.feature = feature;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public PersonViewHolder onCreateViewHolder(final ViewGroup viewGroup, int i) {
        View v = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.card_view, viewGroup, false);
        final PersonViewHolder pvh = new PersonViewHolder(v);

        v.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                int index=pvh.getAdapterPosition();
                Intent in=new Intent(viewGroup.getContext(),ArticleActivity.class);
                String item=feature.get(index);
                String article_title=feature.get(index);
                in.putExtra("title",article_title);
                title=article_title;
                TextView tv=(TextView)v.findViewById(R.id.tv_text);
                ActivityOptionsCompat options=ActivityOptionsCompat.makeSceneTransitionAnimation((Activity) viewGroup.getContext(),tv,"animating");
                LocalBroadcastManager.getInstance(viewGroup.getContext()).sendBroadcast(in);
                viewGroup.getContext().startActivity(in);

            }
        });
        return pvh;
    }

    @Override
    public void onBindViewHolder(PersonViewHolder personViewHolder, int i) {
        personViewHolder.subjectCode.setText(feature.get(i));
    }


    @Override
    public int getItemCount() {
        return feature.size();
    }
}