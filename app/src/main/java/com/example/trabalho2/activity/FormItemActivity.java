package com.example.trabalho2.activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.trabalho2.R;
import com.example.trabalho2.model.Item;
import com.example.trabalho2.model.ItemDAO;
import com.example.trabalho2.model.Ratings;
import com.example.trabalho2.model.RatingsDAO;

public class FormItemActivity extends AppCompatActivity {

    private Item item;
    private EditText name;
    private EditText description;
    private EditText totalPoints;
    private ItemDAO itemDAO;
    private RatingsDAO ratingsDAO;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_form_item);

        itemDAO = new ItemDAO(this);
        ratingsDAO = new RatingsDAO(this);
        name = findViewById(R.id.editName);
        description = findViewById(R.id.editDescription);
        totalPoints = findViewById(R.id.editTotalPoints);

        Button deleteButton = (Button) findViewById(R.id.delete);
        deleteButton.setVisibility(View.GONE);

        Intent it = getIntent();

        if(it.hasExtra("item")){
            item = (Item) it.getSerializableExtra("item");
            name.setText(item.getName());
            description.setText(item.getDescription());

            //Ratings r = ratingsDAO.GetItemRatings(item.getId());
            //totalPoints.setText(r.getTotalPoints().toString());
            deleteButton.setVisibility(View.VISIBLE);
        }
    }

    public void Register(){
        if(item == null){
            item.setName(name.getText().toString());
            item.setDescription(description.getText().toString());

            itemDAO.InsertItem(item);
            Toast.makeText(this, "Item inserido!", Toast.LENGTH_SHORT).show();
        }
        else{
            item.setName(name.getText().toString());
            item.setDescription(description.getText().toString());

            itemDAO.UpdateItem(item);
            Toast.makeText(this, "Item atualizado!", Toast.LENGTH_SHORT).show();
        }

        finish();
    }

    public void DeleteItem(){
        AlertDialog dialog = new AlertDialog.Builder(this)
                .setTitle("Atenção!")
                .setMessage("Deseja excluir o item?")
                .setNegativeButton("Não",null)
                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        itemDAO.DeleteItem(item);
                        finish();
                    }
                }).create();

        dialog.show();
    }
}
