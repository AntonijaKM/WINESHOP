package ba.sum.fpmoz.wineshop;

import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.firebase.ui.database.FirebaseRecyclerOptions;
import com.firebase.ui.database.SnapshotParser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.squareup.picasso.Picasso;

import ba.sum.fpmoz.wineshop.Interface.ItemClickListener;
import ba.sum.fpmoz.wineshop.holders.WineViewHolder;
import ba.sum.fpmoz.wineshop.model.Wine;

public class PopisVina extends AppCompatActivity {

    RecyclerView recycler_View;
    RecyclerView.LayoutManager layoutManager;

    FirebaseDatabase database;
    DatabaseReference popisVina;

    String vinaId = "";
    FirebaseRecyclerAdapter<Wine, WineViewHolder> adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_popis_vina);

        database = FirebaseDatabase.getInstance();
        popisVina = database.getReference("Vina");

        recycler_View = (RecyclerView) findViewById(R.id.recycler_View);
        recycler_View.setHasFixedSize(true);
        layoutManager = new LinearLayoutManager(this);
        recycler_View.setLayoutManager(layoutManager);

        if (getIntent() != null)
            vinaId = getIntent().getStringExtra("VinaId");
        if (!vinaId.isEmpty() && vinaId != null) {
            loadPopisVina(vinaId);
        }
    }

    private void loadPopisVina(String vinaId) {
        FirebaseRecyclerOptions<Wine> options = new FirebaseRecyclerOptions.Builder<Wine>().setQuery(
                popisVina.child("vina"), new SnapshotParser<Wine>() {
                    @NonNull
                    @Override
                    public Wine parseSnapshot(@NonNull DataSnapshot snapshot) {
                        return snapshot.getValue(Wine.class);
                    }
                }
        ).build();
        adapter = new FirebaseRecyclerAdapter<Wine, WineViewHolder>(options) {
            @NonNull
            @Override
            public WineViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
                return null;
            }

            @Override
            protected void onBindViewHolder(@NonNull WineViewHolder holder, int position, @NonNull Wine model) {
                holder.naziv.setText(model.getNaziv());
                Picasso.get().load(model.getSlika())
                        .into(holder.slika);

                final Wine local = model;
                holder.setItemClickListener(new ItemClickListener() {
                    @Override
                    public void onClick(View view, int position, boolean isLongClick) {
                        Intent wineDetail = new Intent(PopisVina.this, WineDetail.class);
                        wineDetail.putExtra("VinaId", adapter.getRef(position).getKey());
                        startActivity(wineDetail);
                    }
                });

            }


        };
        recycler_View.setAdapter(adapter);
    }
}
