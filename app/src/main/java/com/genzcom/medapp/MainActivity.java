package com.genzcom.medapp;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;


public class MainActivity extends Activity {

    EditText edt_loc;
    Button btn_find;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edt_loc = (EditText)findViewById(R.id.edt_location);
        btn_find = (Button)findViewById(R.id.btn_find_doc);



        btn_find.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edt_loc.getText().length()==0)
                    return;
                else
                {
                    Intent intent = new Intent(MainActivity.this,ListDocAct.class);
                    intent.putExtra("location",edt_loc.getText().toString());
                    startActivity(intent);
                }
            }
        });
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
}
