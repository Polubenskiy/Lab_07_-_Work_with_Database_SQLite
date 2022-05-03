package com.example.workwithdatabasesqlite;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity2 extends AppCompatActivity
{

    EditText textViewContent;
    int noteId;
    String noteText;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main2);

        textViewContent = findViewById(R.id.textViewContent);

        Intent i = getIntent();
        noteId = i.getIntExtra("note-id", 0);
        noteText = i.getStringExtra("note-txt");

        textViewContent.setText(noteText);
        textViewContent.setSelection(textViewContent.getText().length());
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        //return super.onCreateOptionsMenu(menu);
        getMenuInflater().inflate(R.menu.menu2, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item)
    {
        int id = item.getItemId();

        switch(id)
        {
            case R.id.item_save:
            {
                //get text from text box, modify note, show toast "note saved" adn exit activity
                String noteTxt = textViewContent.getText().toString();
                G.notes.AlterNote(noteId, noteTxt);
                Toast toast = Toast.makeText(getApplicationContext(),
                        "Note Saved",
                        Toast.LENGTH_SHORT);
                toast.show();
                finish();
                break;
            }
            case R.id.item_delete:
            {
                AlertDialog.Builder dialog = new AlertDialog.Builder(this);
                dialog.setTitle("Note Deleting");
                dialog.setCancelable(true);
                dialog.setMessage("You sure you want to delete this note?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        G.notes.DeleteNote(noteId);

                        Toast toast = Toast.makeText(getApplicationContext(),
                                "Note Deleted",
                                Toast.LENGTH_SHORT);
                        toast.show();
                        finish();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener()
                {
                    @Override
                    public void onClick(DialogInterface dialog, int which)
                    {
                        dialog.cancel();
                        return;
                    }
                });

                dialog.show();
            }
        }
        return super.onOptionsItemSelected(item);
    }
}