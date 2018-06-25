package mm.kb.com.mondaymorning;

import android.app.Activity;
import android.content.Context;
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
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import org.w3c.dom.Text;

import java.util.Arrays;
import java.util.List;


public class RVAdapter extends RecyclerView.Adapter<RVAdapter.PersonViewHolder> {
    Context context;
    private String[] mTitleArray;
    private List<AllNewsData> list;

    public RVAdapter(Context contxt, List<AllNewsData>listItems) {
        context=contxt;
        list=listItems;
    }

    public static class PersonViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        TextView subjectCode;
        TextView authors;
        TextView date;
        public ImageView article_card_image;

        PersonViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.card_view);
            subjectCode = (TextView)itemView.findViewById(R.id.tv_text);
            authors=(TextView)itemView.findViewById(R.id.tv_blah);
            date=(TextView)itemView.findViewById(R.id.tv_blah2);
            article_card_image=(ImageView)itemView.findViewById((R.id.iv_image));
        }
    }
    String title;
    List<String> feature;
    List<String> authorList;
    List<String> dateList;

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
        AllNewsData data=list.get(i);
        personViewHolder.subjectCode.setText(data.getTitle());
        personViewHolder.authors.setText(data.getByLine());
        personViewHolder.date.setText(data.getDateLine());
        Glide.with(context).load(data.getImg_url())
                .thumbnail(0.25f)
                .apply(new RequestOptions().centerCrop())
                .into(personViewHolder.article_card_image);
    }


    @Override
    public int getItemCount() {
        return list.size();
    }
}