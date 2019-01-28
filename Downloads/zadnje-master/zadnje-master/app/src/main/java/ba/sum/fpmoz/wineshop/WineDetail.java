package ba.sum.fpmoz.wineshop;

import android.media.Image;
import android.support.annotation.NonNull;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.squareup.picasso.Picasso;

import ba.sum.fpmoz.wineshop.model.Wine;

public class WineDetail extends AppCompatActivity {

    TextView wine_title, wine_cijena, wine_opis;
    ImageView wine_image;
    CollapsingToolbarLayout collapsingToolbarLayout;
    FloatingActionButton btnCart;

    String vinaId="";
    FirebaseDatabase database;
    DatabaseReference vina;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_wine_detail);

        database = FirebaseDatabase.getInstance();
        vina = database.getReference("vina");

        btnCart=(FloatingActionButton)findViewById(R.id.nav_kosarica);

        wine_opis=(TextView)findViewById(R.id.wine_opis);
        wine_title=(TextView)findViewById(R.id.wine_title);
        wine_cijena=(TextView)findViewById(R.id.wine_cijena);
        wine_image=(ImageView)findViewById(R.id.wine_image);

        collapsingToolbarLayout =(CollapsingToolbarLayout)findViewById(R.id.collapsing);
        collapsingToolbarLayout.setExpandedTitleTextAppearance(R.style.ExpandedAppbar);
        collapsingToolbarLayout.setCollapsedTitleTextAppearance(R.style.CollapsedAppbar);

        if (getIntent() !=null)
                vinaId=getIntent().getStringExtra("VinaId");
        if (!vinaId.isEmpty()){
            getDetalWine(vinaId);
        }

    }

    private void getDetalWine(String vinaId) {
        vina.child(vinaId).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Wine wine = dataSnapshot.getValue(Wine.class);

                Picasso.get().load(wine.getSlika())
                        .into(wine_image);
                collapsingToolbarLayout.setTitle(wine.getNaziv());
                wine_cijena.setText(wine.getCijena());
                wine_title.setText(wine.getNaziv());
                wine_opis.setText(wine.getOpis());
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
