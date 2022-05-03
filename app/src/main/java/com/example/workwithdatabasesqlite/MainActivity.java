package com.example.workwithdatabasesqlite;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity
{

    ListView listViewCtl;
    ArrayList<Mynote> list = new ArrayList<Mynote>();
    ArrayAdapter<Mynote> adp;

    Context ctx;

    void update_list()
    {
        list.clear();
        G.notes.getAllNotes(list);
        adp.notifyDataSetChanged();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data)
    {
        update_list();
        super.onActivityResult(requestCode, resultCode, data);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ctx = this;

        G.notes = new Database(this, "notes.db", null, 1);
        listViewCtl = findViewById(R.id.lst_note);
        adp = new ArrayAdapter<Mynote>(this, android.R.layout.simple_list_item_1, list);
        listViewCtl.setAdapter(adp);

        listViewCtl.setOnItemClickListener((parent, view, position, id) ->
        {
            Mynote n = adp.getItem(position);
            Intent i = new Intent(ctx, MainActivity2.class);
            i.putExtra("note-id", n.id);
            i.putExtra("note-txt",n.txt);

            startActivityForResult(i,1);
        });

        update_list();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu1, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        switch (id)
        {
            case R.id.item_save:
                {
                int nid = G.notes.getMaxId() + 1;
                G.notes.addNote(nid, "Hello, world!");
                update_list();
                return true;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}