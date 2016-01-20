package com.rapidsofttechnologies.sqlitedatabase;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


public class DisplayContact extends Activity {
    EditText name;
    EditText phone;
    EditText email;
    EditText city;
    int id_To_Update = 0;
    DBHelper mydb = new DBHelper(this);

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_display_contact);
        name = (EditText) findViewById(R.id.editTextName);
        phone = (EditText) findViewById(R.id.editTextPhone);
        email = (EditText) findViewById(R.id.editTextEmail);
        city = (EditText) findViewById(R.id.editTextCity);
        Intent display_intent = getIntent();
        if(display_intent.hasExtra("viewOnly_Flag"))
        {
            int index = display_intent.getIntExtra("index",0);
            Contact contact= (Contact)display_intent.getSerializableExtra("contact");
            String nm = contact.getName();
            String ph = contact.getPhone();
            String eml= contact.getEmail();
            String cty = contact.getCity();
            id_To_Update = contact.getId();
            Button b = (Button) findViewById(R.id.button1);
            b.setVisibility(View.INVISIBLE);

            name.setText(nm);
            name.setFocusable(false);
            name.setClickable(false);

            phone.setText(ph);
            phone.setFocusable(false);
            phone.setClickable(false);

            email.setText(eml);
            email.setFocusable(false);
            email.setClickable(false);

            city.setText(cty);
            city.setFocusable(false);
            city.setClickable(false);
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if(getIntent().hasExtra("viewOnly_Flag")){
            getMenuInflater().inflate(R.menu.menu_display_contact,menu);
            return true;
        }
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.Edit_Contact:
                Button b = (Button)findViewById(R.id.button1);
                b.setVisibility(View.VISIBLE);
                name.setEnabled(true);
                name.setFocusableInTouchMode(true);
                name.setClickable(true);

                phone.setEnabled(true);
                phone.setClickable(true);
                phone.setFocusableInTouchMode(true);

                email.setEnabled(true);
                email.setFocusableInTouchMode(true);
                email.setClickable(true);

                city.setEnabled(true);
                city.setClickable(true);
                city.setFocusableInTouchMode(true);

                return true;

            case R.id.Delete_Contact:
                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setMessage(R.string.deleteContact)
                        .setPositiveButton(R.string.yes, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                if(mydb.deleteContact(id_To_Update)) {
                                    Toast.makeText(getApplicationContext(), "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                    Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                            }
                        })
                        .setNegativeButton(R.string.no, new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                                startActivity(intent);
                                finish();
                            }
                        });
                AlertDialog ad = builder.create();
                ad.setTitle("Are you Sure ?");
                ad.show();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void run(View view)
    {

        if(getIntent().hasExtra("viewOnly_Flag"))
        {
            if (mydb.updateContact(id_To_Update, name.getText().toString(), phone.getText().toString(), email.getText().toString(), city.getText().toString()))
            {
                Toast.makeText(getApplicationContext(), "contact Updated", Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);
                startActivity(intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext(), "Not Updated", Toast.LENGTH_SHORT).show();
            }
        }
        else
        {
                if(mydb.insertContact(name.getText().toString(),phone.getText().toString(),email.getText().toString(),city.getText().toString()))
                {
                    Toast.makeText(getApplicationContext(),"New Contact Inserted.", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    Toast.makeText(getApplicationContext(),"No Insertion", Toast.LENGTH_SHORT).show();
                }

            Intent intent = new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
            finish();
        }
    }
}
