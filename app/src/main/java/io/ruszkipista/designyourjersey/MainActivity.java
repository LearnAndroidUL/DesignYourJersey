package io.ruszkipista.designyourjersey;

import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mJerseyNameTextView, mJerseyNumberTextView;
    private ImageView mJerseyImageView;
    private Jersey mJersey;
    private int[] jerseyImageResourceIds = {R.drawable.jersey_green,R.drawable.jersey_purple,
                                    R.drawable.jersey_blue,R.drawable.jersey_red};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

       FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });

//      grab text views of item details
        mJerseyNameTextView = findViewById(R.id.jersey_name);
        mJerseyNumberTextView = findViewById(R.id.jersey_number);
        mJerseyImageView = findViewById(R.id.jersey_image);

        mJersey = new Jersey(getString(R.string.jersey_name_default), Integer.parseInt(getString(R.string.jersey_number_default)), jerseyImageResourceIds[0]);
        showJersey();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem menuItem) {
        // Handle action bar (options) item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (menuItem.getItemId()){
            case R.id.action_options_edit:
                editJersey();
                return true;

            case R.id.action_options_settings:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                return true;
        };
        return super.onOptionsItemSelected(menuItem);
    }

    private void editJersey() {

    }

    private void showJersey(){
        mJerseyNameTextView.setText(mJersey.getName());
        mJerseyNumberTextView.setText(Integer.toString(mJersey.getNumber()));
        mJerseyImageView.setImageResource(mJersey.getImageResourceId());
    }
}
