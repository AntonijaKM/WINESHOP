package ba.sum.fpmoz.wineshop.holders;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ba.sum.fpmoz.wineshop.Interface.ItemClickListener;
import ba.sum.fpmoz.wineshop.R;

public class WineViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView naziv, opis, cijena;
    public ImageView slika;

    private ItemClickListener itemClickListener;

    public WineViewHolder(@NonNull View itemView) {
        super(itemView);

        naziv=(TextView)itemView.findViewById(R.id.wine_title);
        cijena=(TextView)itemView.findViewById(R.id.wine_cijena);
        opis=(TextView)itemView.findViewById(R.id.wine_opis);
        slika=(ImageView)itemView.findViewById(R.id.wine_image);

        itemView.setOnClickListener(this);


    }

    public void setItemClickListener(ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
    }

    @Override
    public void onClick(View v) {
        itemClickListener.onClick(v, getAdapterPosition(), false);
    }
}