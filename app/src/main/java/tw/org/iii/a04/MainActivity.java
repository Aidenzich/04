package tw.org.iii.a04;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import java.util.LinkedList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Collection;
import android.util.Log;

public class MainActivity extends AppCompatActivity {
    private String answer;
    private int dig = 3;
    private EditText input;
    private TextView log;
    private int counter; //不用賦予初值，其值為0
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        input = findViewById(R.id.input);
        log = findViewById(R.id.log);
        answer = createAnswer(dig);
        Log.v("az", answer);
    }

    //  Create an answer
    private String createAnswer(int dig){
        LinkedList<Integer> list = new LinkedList<>();
        for (int i=0; i<10; i++) list.add(i);
        Collections.shuffle(list);

        StringBuffer sb = new StringBuffer();
        for (int i=0; i<dig; i++){
            sb.append(list.get(i));
        }

        return sb.toString();
    }

    public void exit(View view) {

    }

    public void setting(View view) {
    }

    public void newGame(View view) {
        Log.v("az","new game");
    }

    public void guess(View view) {
        counter++;
        String strInput = input.getText().toString();
        String result = checkAB(strInput);
        log.append(strInput + " => " + result + "\n");
        if(result.equals(dig+"A0B")){
            //Winner
            showDialog(true);
        }else if (counter == 3){
            //Loser838
            showDialog(false);
        }
        input.setText("");
    }
    private void showDialog(boolean isWinner){
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setTitle( isWinner?"WINNER":"Loser" )
                .setMessage(isWinner?"Nice":"Bad"+answer)
                .setCancelable(false)
                .setPositiveButton("ok", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    newGame(null);
                    }
                })
                .create();

        alertDialog.show();
        /*builder.setTitle("Title")
                        .setMessage("");
        builder.setMessage("");
        alertDialog = builder.create();

        alertDialog.show();*/
    }

    private String checkAB(String guess){
        int a, b; a = b = 0;
        for (int i=0; i<guess.length(); i++){
            if (guess.charAt(i) == answer.charAt(i) ){
                a++;
            }else if (answer.indexOf(guess.charAt(i)) >= 0){
                b++;
            }
        }
        return a + "A" + b + "B";
    }
}

    /*
    private String createAnswer(int dig){
        HashSet<Integer> set = new HashSet<>();
         while(set.size()<dig){
             set.add((int)(Math.random()*10));
         }
         StringBuffer sb = new StringBuffer();
          for(Integer i : set) {
            sb.append(i);
        }
         Log.v("az",sb.toString());
         return sb.toString();
    }
     */

