package io.ruszkipista.designyourjersey;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.provider.Settings;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.support.v7.app.AlertDialog;
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

        mJersey = new Jersey(getString(R.string.jersey_name_default),
                            Integer.parseInt(getString(R.string.jersey_number_default)),
                            jerseyImageResourceIds[mJerseyColorResourceId]);
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
                showDialogEditJersey();
                return true;

            case R.id.action_options_settings:
                startActivity(new Intent(Settings.ACTION_SETTINGS));
                return true;
        };
        return super.onOptionsItemSelected(menuItem);
    }

    private void showDialogEditJersey() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.dialog_editjersey,null,false);
        builder.setView(view);

//      grab view handles
        final EditText nameEditTextView = view.findViewById(R.id.jersey_name_dialog);
        final EditText numberEditTextView = view.findViewById(R.id.jersey_number_dialog);
        final ImageButton colorEditImageButtonView = view.findViewById(R.id.jersey_image_dialog);

//      set view parts with current attribute values
        mButtonColorResourceId = mJerseyColorResourceId;
        nameEditTextView.setText(mJersey.getName());
        numberEditTextView.setText(Integer.toString(mJersey.getNumber()));
        colorEditImageButtonView.setImageResource(jerseyImageResourceIds[mButtonColorResourceId]);
        colorEditImageButtonView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mButtonColorResourceId = (mButtonColorResourceId + 1) % 4;
                colorEditImageButtonView.setImageResource(jerseyImageResourceIds[mButtonColorResourceId]);
            }
        });

//      set dialog Title
        builder.setTitle(R.string.jersey_title_dialog);
        builder.setPositiveButton(android.R.string.ok, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
//              read dialog new attribute values and set on Jersey
                mJersey.setName(nameEditTextView.getText().toString());
                mJersey.setNumber(Integer.parseInt(numberEditTextView.getText().toString()));
                mJerseyColorResourceId = mButtonColorResourceId;
                mJersey.setPictureResourceId(jerseyImageResourceIds[mJerseyColorResourceId]);
                showJersey();
            }
        });
        builder.setNegativeButton(android.R.string.cancel,null);
        builder.create().show();
    }
    private void showJersey(){
        mJerseyNameTextView.setText(mJersey.getName());
        mJerseyNumberTextView.setText(Integer.toString(mJersey.getNumber()));
        mJerseyImageView.setImageResource(mJersey.getImageResourceId());
    }
}
