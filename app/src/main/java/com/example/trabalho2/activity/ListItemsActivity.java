package com.example.trabalho2.activity;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.ContextMenu;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.SearchView;
import android.widget.Toast;

import com.example.trabalho2.R;
import com.example.trabalho2.model.Datum;
import com.example.trabalho2.model.DatumResponse;
import com.example.trabalho2.model.Item;
import com.example.trabalho2.model.ItemDAO;
import com.example.trabalho2.model.Ratings;
import com.example.trabalho2.model.RatingsDAO;
import com.example.trabalho2.rest.ApiClient;
import com.example.trabalho2.rest.ApiInterface;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ListItemsActivity extends AppCompatActivity {

    private ListView listView;
    private ItemDAO itemDAO;
    private RatingsDAO ratingsDAO;
    private List<Item> items;
    private List<Item> itemsFiltrados = new ArrayList<>();

    private final static String AUTH = "4da03dfaaad739b5538684c194a128ee";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_items);

        if (AUTH.isEmpty()) {
            Toast.makeText(getApplicationContext(), "Please obtain your AUTH from fortnite first!", Toast.LENGTH_LONG).show();
            return;
        }

        final Context _t = this;
        itemDAO = new ItemDAO(this);
        //ratingsDAO = new RatingsDAO(this);
        listView = findViewById(R.id.items_list);

        if(VerifyConnection()){
            Sync(_t);
        }
        else{
            items = itemDAO.GetAllItems();
            itemsFiltrados.addAll(items);

            ArrayAdapter<Item> itemAdapter = new ArrayAdapter<Item>(this, android.R.layout.simple_list_item_1, itemsFiltrados);
            listView.setAdapter(itemAdapter);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    Intent ia = new Intent(ListItemsActivity.this, FormItemActivity.class);

                    final Item selectedItem = itemsFiltrados.get(position);
                    ia.putExtra("item", selectedItem);
                    startActivity(ia);
                }
            });

            Toast.makeText(getApplicationContext(), "Lista atualizada do banco local", Toast.LENGTH_LONG).show();

            //        registerForContextMenu(listView);
        }
    }

    public boolean onCreateOptionsMenu(Menu menu){
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.main_menu, menu);

        SearchView sv = (SearchView) menu.findItem(R.id.app_bar_search).getActionView();

        sv.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                SearchItem(newText);
                return false;
            }
        });

        return true;
    }

    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);

        MenuInflater m = getMenuInflater();
        m.inflate(R.menu.context_menu, menu);
    }

    public void SearchItem(String name){
        itemsFiltrados.clear();

        for (Item i: items){
            if(i.getName().toLowerCase().contains(name.toLowerCase())){
                itemsFiltrados.add(i);
            }
        }

        listView.invalidateViews();
    }

    public void DeleteItem(MenuItem menuItem){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();

        final Item selectedItem = itemsFiltrados.get(menuInfo.position);

        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Deseja excluir o item?")
                .setNegativeButton("Não",null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemsFiltrados.remove(selectedItem);
                        items.remove(selectedItem);
                        itemDAO.DeleteItem(selectedItem);

                        listView.invalidateViews();
                    }
                }).create();

        dialog.show();
    }

    public void EditItem(MenuItem menuItem){
        AdapterView.AdapterContextMenuInfo menuInfo = (AdapterView.AdapterContextMenuInfo) menuItem.getMenuInfo();

        final Item selectedItem = itemsFiltrados.get(menuInfo.position);
        Intent ia = new Intent(this, FormItemActivity.class);
        ia.putExtra("item", selectedItem);
        startActivity(ia);
    }

    public void AddItem(MenuItem menuItem){
        Intent i = new Intent(this, FormItemActivity.class);
        startActivity(i);
    }

    public void Sync(final Context context){
        itemDAO.Restart();
        //ratingsDAO.Restart();

        Toast.makeText(getApplicationContext(), "Aguarde a lista atualizar", Toast.LENGTH_LONG).show();

        ApiInterface apiService = ApiClient.getClient().create(ApiInterface.class);
        Call<DatumResponse> call = apiService.getItems(AUTH);
        call.enqueue(new Callback<DatumResponse>() {
            @Override
            public void onResponse(Call<DatumResponse> call, Response<DatumResponse> response) {
                int statusCode = response.code();
                List<Datum> datums = response.body().getData();

                for (Datum datum : datums) {
                    Item i = new Item();
                    i.setName(datum.getItem().getName());
                    i.setDescription(datum.getItem().getDescription());
                    itemDAO.InsertItem(i);

//                    Ratings r = new Ratings();
//                    r.setTotalPoints(datum.getItem().getRatings().getTotalPoints());
//                    r.setItemId(i.getId());
//                    ratingsDAO.InsertRatings(r, r.getItemId());
                }

                items = itemDAO.GetAllItems();
                itemsFiltrados.addAll(items);

                ArrayAdapter<Item> itemAdapter = new ArrayAdapter<Item>(context, android.R.layout.simple_list_item_1, itemsFiltrados);
                listView.setAdapter(itemAdapter);

                listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                    @Override
                    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                        Intent ia = new Intent(ListItemsActivity.this, FormItemActivity.class);

                        final Item selectedItem = itemsFiltrados.get(position);
                        ia.putExtra("item", selectedItem);
                        startActivity(ia);
                    }
                });

                Toast.makeText(getApplicationContext(), "Lista atualizada da nuvem", Toast.LENGTH_LONG).show();
            }

            @Override
            public void onFailure(Call<DatumResponse> call, Throwable t) {
                // Log error here since request failed
            }
        });
    }

    public void SyncList(MenuItem menuItem){
        if(!VerifyConnection()){
            Toast.makeText(getApplicationContext(), "Sem conexão com a internet", Toast.LENGTH_LONG).show();
            return;
        }

        Sync(this);
    }

    private boolean VerifyConnection(){
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo = cm.getActiveNetworkInfo();

        return networkInfo != null && networkInfo.isConnectedOrConnecting();
    }

    @Override
    public void onResume(){
        super.onResume();

        items = itemDAO.GetAllItems();

        itemsFiltrados.clear();
        itemsFiltrados.addAll(items);
        listView.invalidateViews();
    }
}
