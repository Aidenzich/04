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
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {
    private String answer;
    private int dig = 3;
    private EditText input;
    private TextView log;
    private int counter; //不用賦予初值，其值為0
    private long lastTime = 0;
    //private String[] three = new String[3] ;  //string的宣告方式要有new



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
        AlertDialog alertDialog = new AlertDialog.Builder(this)
                .setMessage("Exit?")
                .setCancelable(false)
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        finish();
                    }
                })
                .setNegativeButton("Cancel",null)
                .create();
        alertDialog.show();
    }
    @Override
    protected  void  onDestroy(){
        super.onDestroy();
    }
    @Override
    public  void finish(){
        super.finish();
        Log.v("az","finish");
    }
   /*
    @Override
    public  void onBackPressed(){
        super.onBackPressed();
        Toast.makeText(this, "back one more", Toast.LENGTH_SHORT).show();
        Log.v("az","onBackPress");
    }
   */
   @Override
   public  void onBackPressed(){
       if (System.currentTimeMillis() - lastTime > 3*1000){
        lastTime = System.currentTimeMillis();
           Toast.makeText(this, "back one more", Toast.LENGTH_SHORT).show();
       }else{
           super.onBackPressed();
       }
   }

    int temp;
    public void setting(View view) {
      // three[0] ="1"; three[1] ="2" ; three[2] ="3"; //不可為空值
        String[] tree = {"3","4","5","6"};
       AlertDialog alertDialog = new AlertDialog.Builder(this)
               .setTitle("Select Game Mode")
               .setItems(tree, new DialogInterface.OnClickListener(){
                   @Override
                   public  void  onClick(DialogInterface dialog, int which){
                   Log.v("az","which = "+ which);
                   }
               })

               .setSingleChoiceItems(tree, dig-3, new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Log.v("az","which1="+which);
                       temp = which;

                   }
               })

               .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                   @Override
                   public void onClick(DialogInterface dialog, int which) {
                       Log.v("az","which2"+which);
                       dig = temp + 3;
                       newGame(null);

                   }
               })

               .create();
       alertDialog.show();
    }

    public void newGame(View view) {

        counter = 0;
        input.setText("");
        log.setText("");
        answer = createAnswer(dig);
        Log.v("az","new game");
        Log.v("az", answer);
    }

    public void guess(View view) {
        counter++;
        String strInput = input.getText().toString();
        if (!isRightNumber(strInput)){
            AlertDialog alertDialog = new AlertDialog.Builder(this)
                    .setMessage("請輸入正確數字")
                    .create();
            alertDialog.show();
            return;
        }
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

    private boolean isRightNumber(String g){
        return g.matches("^[0-9]{"+dig+"}$");
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

