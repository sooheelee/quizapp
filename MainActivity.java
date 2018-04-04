package com.example.android.quizapp;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import static com.example.android.quizapp.R.layout;

/**
 * App quizzes on geography questions and grades answers.
 */
public class MainActivity extends AppCompatActivity {

    /**
     * Creates new instance.
     *
     * @param savedInstanceState Saves the state of selections
     */
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main);
        scrollFlags();
        onFlagClickListener();
    }

    /**
     * Activates one-line TextView at top into scrolling marquee.
     */
    private void scrollFlags() {
        TextView tv = findViewById(R.id.scrolling_flags);
        tv.setSelected(true);
        tv.setEllipsize(TextUtils.TruncateAt.MARQUEE);
        tv.setSingleLine(true);
    }

    /**
     * Creates menu.
     *
     * @param menu has reset option.
     * @return true upon inflation
     */
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        return true;
    }

    /**
     * Acts on menu item selection.
     *
     * @param item RESET clears quiz answers
     * @return boolean of item
     */
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.reset) {
            setContentView(layout.activity_main);
            scrollFlags();
        }
        return super.onOptionsItemSelected(item);
    }

    /**
     * Listens for click on scrolling flag marquee.
     * Upon click opens web browser to website with flag emoji codes by country.
     */
    public void onFlagClickListener() {
        TextView scrollingFlags = findViewById(R.id.scrolling_flags);
        scrollingFlags.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse("https://emojipedia.org/flags/"));
                startActivity(intent);
            }
        });
    }

    /**
     * Button click grades each quiz question and displays number of correct answers.
     *
     * @param view button click
     */
    public void scoreQuiz(View view) {
        int numberCorrect = 0;
        int numberOfQuestions = 7;

        numberCorrect = numberCorrect + getRadioAnswer(R.id.q01, "Hawaii");
        numberCorrect = numberCorrect + getEditTextAnswer(R.id.q02_text_field, "Canada");

        int[] q03 = {R.id.q03_a, R.id.q03_b, R.id.q03_c, R.id.q03_d, R.id.q03_e};
        boolean[] q03_answers = {true, false, false, true, true};
        numberCorrect = numberCorrect + getCheckBoxAnswer(q03, q03_answers);

        int[] q04 = {R.id.q04_a, R.id.q04_b, R.id.q04_c, R.id.q04_d, R.id.q04_e};
        String[] q04_answers = {"5", "1", "4", "3", "2"};
        numberCorrect = numberCorrect + getRankingAnswer(q04, q04_answers);

        numberCorrect = numberCorrect + getRadioAnswer(R.id.q05, "Maine");
        numberCorrect = numberCorrect + getEditTextAnswer(R.id.q06_text_field, "Egypt");

        int[] q07 = {R.id.q07_a, R.id.q07_b, R.id.q07_c, R.id.q07_d, R.id.q07_e};
        String[] q07_answers = {"5", "1", "2", "4", "3"};
        numberCorrect = numberCorrect + getRankingAnswer(q07, q07_answers);

        String grade_message = numberCorrect + " " + getString(R.string.out_of) + " " + numberOfQuestions + " " + getString(R.string.correct);
        if (numberCorrect == 7) {
            grade_message = grade_message + getString(R.string.excellent);
        }
        toast(grade_message);
    }

    /**
     * For ranking question type, matches array of viewId items to array of answers.
     * Returns one point for correct answer and zero for incorrect answer.
     *
     * @param viewId       Array of viewIds for each item to be ranked
     * @param answerString Array of strings for each correct answer in matching order
     * @return Integer point towards score
     */
    private int getRankingAnswer(int[] viewId, String[] answerString) {
        int point = 0;
        if (whatIsRankAsString(viewId[0]).equals(answerString[0])
                && whatIsRankAsString(viewId[1]).equals(answerString[1])
                && whatIsRankAsString(viewId[2]).equals(answerString[2])
                && whatIsRankAsString(viewId[3]).equals(answerString[3])
                && whatIsRankAsString(viewId[4]).equals(answerString[4])) {
            point = 1;
        }
        return point;
    }

    /**
     * Obtains string of viewId item rank.
     *
     * @param viewId A viewId of an item to be ranked
     * @return String corresponding to the item
     */
    public String whatIsRankAsString(int viewId) {
        EditText editText = findViewById(viewId);
        return editText.getText().toString();
    }

    /**
     * Scores multiple-answer checkbox question.
     *
     * @param viewId        Array of viewIds for each given multiple-choice answer
     * @param answerBoolean Array of booleans, true or false, for each answer in matching order
     * @return integer point for correct answer
     */
    public int getCheckBoxAnswer(int[] viewId, boolean[] answerBoolean) {
        int point = 0;
        if (isCheckedCheckBox(viewId[0]) == (answerBoolean[0])
                && isCheckedCheckBox(viewId[1]) == (answerBoolean[1])
                && isCheckedCheckBox(viewId[2]) == (answerBoolean[2])
                && (isCheckedCheckBox(viewId[3]) == (answerBoolean[3]))
                && isCheckedCheckBox(viewId[4]) == (answerBoolean[4])) {
            point = 1;
        }
        return point;
    }

    /**
     * Returns true or false whether checkbox is checked.
     *
     * @param viewId of CheckBox view
     * @return boolean of whether CheckBox is checked
     */
    public boolean isCheckedCheckBox(int viewId) {
        CheckBox checkBoxStatus = findViewById(viewId);
        return checkBoxStatus.isChecked();
    }

    /**
     * Scores single answer multiple-choice question.
     * Returns one point for correct answer and zero for incorrect answer.
     *
     * @param viewId of RadioGroup consisting of RadioButtons
     * @param answer string of correct answer
     * @return integer point for correct answer
     */
    public int getRadioAnswer(int viewId, final String answer) {
        int point = 0;
        RadioGroup radioGroup = findViewById(viewId);
        int selectedRadioButtonID = radioGroup.getCheckedRadioButtonId();
        if (selectedRadioButtonID == -1) {
            return point;
        } else {
            RadioButton radioButton = radioGroup.findViewById(selectedRadioButtonID);
            String radioText = radioButton.getText().toString();
            if (radioText.equalsIgnoreCase(answer)) {
                point = 1;
            }
        }
        return point;
    }

    /**
     * Scores answer typed into textbox.
     * Returns one point for correct answer and zero for incorrect answer.
     *
     * @param viewId of EditText view
     * @param answer string of correct answer
     * @return integer point for correct answer
     */
    private int getEditTextAnswer(int viewId, String answer) {
        int point = 0;
        EditText editText = findViewById(viewId);
        editText.setCursorVisible(true);
        final String answerText = editText.getText().toString();
        if (answerText.equals(answer)) {
            point = 1;
        }
        return point;
    }

    /**
     * Displays long-lasting toast message to screen.
     *
     * @param text string to display
     */
    private void toast(String text) {
        Context context = getApplicationContext();
        int duration = Toast.LENGTH_LONG;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}

