package cmpe277.lab2minesweeping;

import android.app.ActionBar;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AbsListView;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.Random;

/**
 * Created by yunlongxu on 2/29/16.
 */
public class GameActivity extends Activity {
    private TextView mineCount;
    private TextView timer;
    private TableLayout mineField;
    private Field[][] field;
    private int fieldDimension = 29;
    private int fieldPadding = 29;
    private int numOfRowInField = 12;
    private int numOfColInField = 8;
    private int totalBomb = 30;
    private ImageButton clickStart;
    private int availableBomb;
    private boolean gameOver;
    private boolean checkBombPlant;
    private ImageButton btn_restart;
    private ImageButton btn_exit;
    private boolean isRotate;

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putSerializable("field", field);
        outState.putSerializable("fieldDimension", fieldDimension);
        outState.putSerializable("fieldPadding", fieldPadding);
        outState.putSerializable("numOfRowInField", numOfRowInField);
        outState.putSerializable("numOfColInField", numOfColInField);
        outState.putSerializable("totalBomb", totalBomb);
        outState.putSerializable("availableBomb", availableBomb);
        outState.putSerializable("gameOver", gameOver);
        outState.putSerializable("checkBombPlant", checkBombPlant);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_game);
        int orient = getResources().getConfiguration().orientation;
        if (orient == ActivityInfo.SCREEN_ORIENTATION_PORTRAIT) {
            this.fieldDimension = 40;
        } else {
            this.fieldDimension = 29;
        }
        if (savedInstanceState != null) {
            field = (Field[][]) savedInstanceState.getSerializable("field");
            fieldDimension = (int) savedInstanceState.getSerializable("fieldDimension");
            fieldPadding = (int) savedInstanceState.getSerializable("fieldPadding");
            numOfRowInField = (int) savedInstanceState.getSerializable("numOfRowInField");
            numOfColInField = (int) savedInstanceState.getSerializable("numOfColInField");
            totalBomb = (int) savedInstanceState.getSerializable("totalBomb");
            availableBomb = (int) savedInstanceState.getSerializable("availableBomb");
            gameOver = (boolean) savedInstanceState.getSerializable("gameOver");
            checkBombPlant = (boolean) savedInstanceState.getSerializable("checkBombPlant");
        }
        mineCount = (TextView)findViewById(R.id.MineCount);
        timer = (TextView)findViewById(R.id.Timer);
        Typeface customFont = Typeface.createFromAsset(getAssets(), "fonts/lcd2mono.ttf");
        mineCount.setTypeface(customFont);
        timer.setTypeface(customFont);
        clickStart = (ImageButton)findViewById(R.id.Smiley);
        clickStart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endExistingGame();
                startNewGame();
            }
        });
        mineField = (TableLayout)findViewById(R.id.MineField);
        btn_restart = (ImageButton)findViewById(R.id.btn_restart);
        btn_restart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                endExistingGame();
                startNewGame();
            }
        });
        btn_exit = (ImageButton)findViewById(R.id.btn_exit);
        btn_exit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        if (field != null) {
            createBoard();
            showBoard();
            isRotate = true;
        } else {
            showDialog("Click the Smile Face to Start Game", 2000, true, false);
        }
    }
    public void startNewGame() {
        field = new Field[numOfRowInField + 2][numOfColInField + 2];
        createBoard();
        showBoard();
        availableBomb = totalBomb;
        gameOver = false;
    }
    public void createBoard() {
        for (int row = 0; row < numOfRowInField + 2; row++) {
            for (int col = 0; col < numOfColInField + 2; col++) {
                if (isRotate) {
                    Field temp = field[row][col];
                    field[row][col] = new Field(this);
                    field[row][col].setting(temp.isInvisible(), temp.isBomb(), temp.isClickable(), temp.getNumOfMinesAround());
                    isRotate = false;
                } else {
                    field[row][col] = new Field(this);
                    field[row][col].setDefault();
                }
                final int curRow = row;
                final int curCol = col;
                field[row][col].setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!checkBombPlant) {
                            checkBombPlant = true;
                            plantBomb(curRow, curCol);
                        }
                        makeAreaVisiable(curRow, curCol);
                        if (field[curRow][curCol].isBomb()) {
                            gameFinish(curRow, curCol);
                        }
                        if (checkWin()) {
                            gameWin();
                        }
                    }
                });
            }
        }
    }

    public void showBoard() {
        for (int row = 1; row < numOfRowInField + 1; row++) {
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(new TableRow.LayoutParams((fieldDimension + 2 * fieldPadding) * numOfColInField, fieldDimension + 2 * fieldPadding));
            for (int col = 1; col < numOfColInField + 1; col++) {
                field[row][col].setLayoutParams(new TableRow.LayoutParams(fieldDimension + 2 * fieldPadding, fieldDimension + 2 * fieldPadding));
                field[row][col].setPadding(fieldPadding, fieldPadding, fieldPadding, fieldPadding);
                tableRow.addView(field[row][col]);
            }
            mineField.addView(tableRow, new TableLayout.LayoutParams((fieldDimension + 2 * fieldPadding) * numOfColInField, fieldDimension + 2 * fieldPadding));
        }
    }

    public void endExistingGame() {
        timer.setText("000");
        mineCount.setText("000");
        clickStart.setBackgroundResource(R.drawable.smile);

        mineField.removeAllViews();
        checkBombPlant = false;
        availableBomb = 0;
    }

    public void plantBomb(int curRow, int curCol) {
        Random rand = new Random();
        int bombRow, bombCol;
        for (int row = 0; row < totalBomb; row++) {
            bombRow = rand.nextInt(numOfRowInField);
            bombCol = rand.nextInt(numOfColInField);
            if (bombRow + 1 != curCol || bombCol + 1 != curRow) {
                if (field[bombRow + 1][bombCol + 1].isBomb()) {
                    row--;
                }
                field[bombRow + 1][bombCol + 1].plantBomb();
            }
            else {
                row--;
            }
        }

        int bombAround;

        for (int row = 0; row < numOfRowInField + 2; row++) {
            for (int col = 0; col < numOfColInField + 2; col++) {
                bombAround = 0;
                if ((row != 0) && (row != (numOfRowInField + 1)) && (col != 0) && (col != (numOfColInField + 1))) {
                    for (int preRow = -1; preRow < 2; preRow++) {
                        for (int preCol = -1; preCol < 2; preCol++) {
                            if (field[row + preRow][col + preCol].isBomb()) {
                                bombAround++;
                            }
                        }
                    }
                    field[row][col].setNumOfMinesAround(bombAround);
                } else {
                    field[row][col].setNumOfMinesAround(9);
                    field[row][col].setVisiable();
                }
            }
        }
    }

    public void makeAreaVisiable(int curRow, int curCol) {
        if (field[curRow][curCol].isBomb()) {
            return;
        }
        field[curRow][curCol].setVisiable();
        if (field[curRow][curCol].getNumOfMinesAround() != 0) {
            return;
        }
        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                if (field[curRow + row - 1][curCol + col - 1].isInvisible() && (curRow + row - 1 > 0)
                        && (curCol + col - 1 > 0) && (curRow + row - 1 < numOfRowInField + 1)
                        && (curCol + col - 1 < numOfColInField + 1)) {
                    makeAreaVisiable(curRow + row - 1, curRow + col - 1);
                }
            }
        }
        return;
    }

    public boolean checkWin() {
        for (int row = 1; row < numOfRowInField + 1; row++) {
            for(int col = 1; col < numOfColInField + 1; col++) {
                if (!field[row][col].isBomb() && field[row][col].isInvisible()) {
                    return false;
                }
            }
        }
        return true;
    }

    public void updateNumOfBomb() {
        if (availableBomb < 0) {
            mineCount.setText(Integer.toString(availableBomb));
        } else if (availableBomb < 10) {
            mineCount.setText("00" + Integer.toString(availableBomb));
        } else if (availableBomb < 100) {
            mineCount.setText("0" + Integer.toString(availableBomb));
        } else {
            mineCount.setText(Integer.toString(availableBomb));
        }
    }

    public void gameWin() {
        gameOver = true;
        availableBomb = 0;
        clickStart.setBackgroundResource(R.drawable.cool);
        updateNumOfBomb();

        for (int row = 1; row < numOfRowInField + 1; row++) {
            for (int col = 1; col < numOfColInField + 1; col++) {
                field[row][col].setClickable(false);
                if (field[row][col].isBomb()) {
                    field[row][col].setField(false);
                }
            }
        }

        showDialog("You won the Game!!!", 1000, false, true);
    }

    public void gameFinish(int curRow, int curCol) {
        gameOver = true;
        clickStart.setBackgroundResource(R.drawable.sad);
        for (int row = 1; row < numOfRowInField + 1; row++) {
            for (int col = 1; col < numOfColInField + 1; col++) {
                field[row][col].setField(false);
                if (field[row][col].isBomb()) {
                    field[row][col].setBombField(false);
                }
            }
        }
        field[curRow][curCol].touchBomb();
        showDialog("Sorry, You lose this Game", 1000, false, false);
    }

    public void showDialog(String message, int milliseconds, boolean smile, boolean cool) {
        Toast dialog = Toast.makeText(getApplicationContext(), message, Toast.LENGTH_LONG);
        dialog.setGravity(Gravity.CENTER, 0, 0);
        LinearLayout dialogView = (LinearLayout)dialog.getView();
        ImageView smileImage = new ImageView(getApplicationContext());
        if (smile) {
            smileImage.setImageResource(R.drawable.smile);
        } else if (cool) {
            smileImage.setImageResource(R.drawable.cool);
        } else {
            smileImage.setImageResource(R.drawable.sad);
        }
        dialogView.addView(smileImage, 0);
        dialog.setDuration(milliseconds);
        dialog.show();
    }
}
