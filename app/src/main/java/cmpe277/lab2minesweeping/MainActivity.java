package cmpe277.lab2minesweeping;

import android.app.Activity;
import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageButton;

/**
 * Created by yunlongxu on 2/28/16.
 */
public class MainActivity extends Activity {
    private ImageButton exitButton;
    private ImageButton startButton;
    private String[] levelNames = {"Default", "Coming Soon"};

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("levelNames", levelNames);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //requestWindowFeature(Window.FEATURE_NO_TITLE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_main);

        int orient = getResources().getConfiguration().orientation;
        if (savedInstanceState != null) {
            levelNames = (String[]) savedInstanceState.getSerializable("levelNames");
        }

        exitButton = (ImageButton)findViewById(R.id.button_exit);
        exitButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        startButton = (ImageButton)findViewById(R.id.button_start);
        startButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                Intent intent = new Intent();
//                intent.setClass(MainActivity.this, GameActivity.class);
//                MainActivity.this.startActivity(intent);
                AlertDialog.Builder builder = new AlertDialog.Builder(MainActivity.this);

                builder.setTitle("Choose a level").setSingleChoiceItems(levelNames, 0, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        startGame(which);
                    }
                });
                AlertDialog alertDialog = builder.create();
                alertDialog.show();
            }
        });
    }
    public void startGame(int chosenLevel) {
        Intent intent = new Intent();
        intent.setClass(MainActivity.this, GameActivity.class);
        intent.putExtra("level", chosenLevel);
        MainActivity.this.startActivity(intent);
    }
}
