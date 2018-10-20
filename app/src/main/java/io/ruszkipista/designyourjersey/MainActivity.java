package io.ruszkipista.designyourjersey;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    private TextView mJerseyNameTextView, mJerseyNumberTextView;
    private ImageView mJerseyImageView;
    private int mJerseyColorResourceId = 0;
    private int mButtonColorResourceId;
    private Jersey mJersey;
    private int[] jerseyImageResourceIds = {R.drawable.jersey_green,R.drawable.jersey_purple,
                                    R.drawable.jersey_blue,R.drawable.jersey_red};

//  constants for Persistence:
    private final static String PREFS = "PREFS";
    private static final String KEY_JERSEY_NAME = "KEY_JERSEY_NAME";
    private static final String KEY_JERSEY_NUMBER = "KEY_JERSEY_NUMBER";
    private static final String KEY_JERSEY_COLOR = "KEY_JERSEY_COLOR";

//  execution starts here
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
               showDialogEditJersey();
            }
        });

//      grab text views of item details
        mJerseyNameTextView = findViewById(R.id.jersey_name);
        mJerseyNumberTextView = findViewById(R.id.jersey_number);
        mJerseyImageView = findViewById(R.id.jersey_image);
        mJersey = new Jersey();
//      read application preferences for stored Jersey attributes
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        mJersey.setName(prefs.getString(KEY_JERSEY_NAME,getString(R.string.jersey_name_default)));
        mJersey.setNumber(prefs.getInt(KEY_JERSEY_NUMBER,Integer.parseInt(getString(R.string.jersey_number_default))));
        mJersey.setPictureResourceId(prefs.getInt(KEY_JERSEY_COLOR,Integer.parseInt(getString(R.string.jersey_number_default))));
        showJersey();
    }

//  before object gets destroyed, save Jersey attributes
    @Override
    protected void onPause() {
        super.onPause();
//      start application preferences editor
        SharedPreferences prefs = getSharedPreferences(PREFS, MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
//      put attributes into the editor
        editor.putString(KEY_JERSEY_NAME, mJersey.getName());
        editor.putInt(KEY_JERSEY_NUMBER, mJersey.getNumber());
        editor.putInt(KEY_JERSEY_COLOR, mJersey.getImageResourceId());
//      save editor content
        editor.commit();
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
            case R.id.action_options_reset:
                showDialogReset();
                return true;

            case R.id.action_options_edit:
                showDialogEditJersey();
                return true;

            case R.id.action_options_settings:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                return true;
        };
        return super.onOptionsItemSelected(menuItem);
    }

    private void showDialogReset() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle(R.string.action_reset);
        builder.setMessage(R.string.confirmation_dialog_reset);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                resetJersey();
            }
        });
        builder.setNegativeButton(android.R.string.cancel,null);
        builder.create().show();
    }

    private void showDialogEditJersey() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_editjersey,null,false);
        builder.setView(view);

//      grab view handles
        final EditText nameEditTextView = view.findViewById(R.id.jersey_name_dialog);
        final EditText numberEditTextView = view.findViewById(R.id.jersey_number_dialog);
        final ImageButton colorEditImageButtonView = view.findViewById(R.id.jersey_color_dialog);

//      set view parts with current attribute values
        mButtonColorResourceId = mJerseyColorResourceId;
        nameEditTextView.setText(mJersey.getName());
        numberEditTextView.setText(Integer.toString(mJersey.getNumber()));
//      set button color
        colorEditImageButtonView.setImageResource(jerseyImageResourceIds[mButtonColorResourceId]);
//      setup color button click listener
        colorEditImageButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              circular increment of image resource index
                mButtonColorResourceId = (mButtonColorResourceId + 1) % jerseyImageResourceIds.length;
                colorEditImageButtonView.setImageResource(jerseyImageResourceIds[mButtonColorResourceId]);
            }
        });

//      set dialog Title
        builder.setTitle(R.string.jersey_title_dialog);
//      set OK button click listener
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//              read dialog new attribute values and set on Jersey
                mJersey.setName(nameEditTextView.getText().toString());
//              read new number, handle conversion exception
                int numberEntered;
                try {numberEntered = Integer.parseInt(numberEditTextView.getText().toString());}
                catch (NumberFormatException e) {numberEntered = 0;}
                mJersey.setNumber(numberEntered);
//              get new image resource pointer (color)
                mJerseyColorResourceId = mButtonColorResourceId;
//              update image resource pointer (color)
                mJersey.setPictureResourceId(jerseyImageResourceIds[mJerseyColorResourceId]);

                showJersey();
            }
        });
//      set CANCEL button click listener
        builder.setNegativeButton(android.R.string.cancel,null);
//      show dialog
        builder.create().show();
    }

    private void initJersey() {
        mJersey.setName(getString(R.string.jersey_name_default));
        mJersey.setNumber(Integer.parseInt(getString(R.string.jersey_number_default)));
        mJerseyColorResourceId = 0;
        mJersey.setPictureResourceId(jerseyImageResourceIds[mJerseyColorResourceId]);
    }

    private void resetJersey() {
        final Jersey mSavedJersey = new Jersey();
        mSavedJersey.copy(mJersey);
        initJersey();
        showJersey();

        Snackbar snackbar = Snackbar.make(findViewById(R.id.coordinator_layout),R.string.confirmation_snack_reset,Snackbar.LENGTH_LONG);
        snackbar.setAction(R.string.action_undo, new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//              restore saved attributes
                mJersey.copy(mSavedJersey);
                showJersey();
                Snackbar.make(findViewById(R.id.coordinator_layout),R.string.confirmation_snack_restored,Snackbar.LENGTH_LONG).show();
            }
        });
        snackbar.show();
    }

    private void showJersey(){
        mJerseyNameTextView.setText(mJersey.getName());
        mJerseyNumberTextView.setText(Integer.toString(mJersey.getNumber()));
        mJerseyImageView.setImageResource(mJersey.getImageResourceId());
    }
}
