package ba.sum.fpmoz.wineshop.Interface;

import android.view.View;

public interface ItemClickListener  {
    void onClick (View view, int position, boolean isLongClick);

    void onItemClickListener(View v, int adapterPosition);

    void onLongItemClickListener(View v, int adapterPosition);


}
