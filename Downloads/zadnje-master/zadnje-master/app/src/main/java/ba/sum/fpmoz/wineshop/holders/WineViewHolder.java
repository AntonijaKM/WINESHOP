package ba.sum.fpmoz.wineshop.holders;

import android.support.annotation.NonNull;
import android.support.v7.view.menu.MenuView;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.squareup.picasso.Picasso;

import ba.sum.fpmoz.wineshop.Interface.ItemClickListener;
import ba.sum.fpmoz.wineshop.R;
import ba.sum.fpmoz.wineshop.model.Wine;

public class WineViewHolder extends RecyclerView.ViewHolder  {
    View mView;
    TextView naziv, opis, cijena;
    ImageView slika;


    public WineViewHolder(@NonNull View itemView) {
        super(itemView);
        mView = itemView;
        naziv = (TextView) itemView.findViewById(R.id.wine_title);
        cijena = (TextView) itemView.findViewById(R.id.wine_cijena);
        opis = (TextView) itemView.findViewById(R.id.wine_opis);
        slika = (ImageView) itemView.findViewById(R.id.wine_image);

        mView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clickListener.onItemClickListener(v, getAdapterPosition());
            }
        });

    }

    public void setWine(Wine wine) {
        naziv.setText(wine.naziv);
        opis.setText(wine.opis);
        cijena.setText(wine.cijena);
        Picasso.get().load(wine.slika).into(slika);
    }

    private ItemClickListener clickListener;

    public void setOnItemClickListener(ItemClickListener ClickListener) {
        this.clickListener = clickListener;
    }

    public interface ClickListener {
        public void onItemClick(View v, int position);
    }

    public interface ItemClickListener {
        public void onItemClickListener(View v, int position);

    }}