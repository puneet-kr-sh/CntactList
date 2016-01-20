package com.rapidsofttechnologies.sqlitedatabase;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;


public class MainActivity extends Activity {
/*    private static final int REQUEST_CODE_INSERT_CONTACT=1;
    private static final int REQUEST_CODE_UPDATE_CONTACT=2;
    private static final int REQUEST_CODE_DELETE_CONTACT=3;*/
    private ListView obj;
    DBHelper mydb;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mydb =  new DBHelper(this);
        final ArrayList<Contact> contactList = mydb.getAllContacts();

        if(contactList.size()==0)
        {
            RelativeLayout rv = (RelativeLayout)findViewById(R.id.rv);
            TextView noContactText = new TextView(MainActivity.this);
            noContactText.setText("Currently there is No Contact");
            noContactText.setTextSize(20);
            noContactText.setTextColor(Color.RED);
            noContactText.setGravity(View.TEXT_ALIGNMENT_CENTER);
            RelativeLayout.LayoutParams params = new RelativeLayout.LayoutParams
                    ( RelativeLayout.LayoutParams.WRAP_CONTENT,RelativeLayout.LayoutParams.WRAP_CONTENT);
            params.topMargin=100;
            params.leftMargin=50;
            params.addRule(RelativeLayout.BELOW,R.id.imageView);
            noContactText.setLayoutParams(params);
            rv.addView(noContactText);
        }
        else
        {
            final ArrayList<String> nameList = new ArrayList<>();
            for(int i =0; i<contactList.size();i++)
            {
                nameList.add(contactList.get(i).getName());
            }
            ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1,nameList);
            obj = (ListView)findViewById(R.id.listView1);
            obj.setAdapter(arrayAdapter);
            obj.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                    Intent intent = new Intent(MainActivity.this,DisplayContact.class);
                    intent.putExtra("index", i);
                    intent.putExtra("viewOnly_Flag", true);
                    intent.putExtra("contact", contactList.get(i));
                    startActivity(intent);
                    finish();
                }
            });
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        super.onOptionsItemSelected(item);

        switch (item.getItemId())
        {
            case R.id.addItem:
                Intent intent = new Intent(MainActivity.this,DisplayContact.class);
                startActivity(intent);
                finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

/*   public boolean onKeyDown(int keycode, KeyEvent event) {
        if (keycode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
        }
        return super.onKeyDown(keycode, event);
    }*/
}
