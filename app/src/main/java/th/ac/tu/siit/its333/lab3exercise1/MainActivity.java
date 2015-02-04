package th.ac.tu.siit.its333.lab3exercise1;

import android.content.Intent;
import android.support.v7.app.ActionBarActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;


public class MainActivity extends ActionBarActivity {

    int cr = 0;         // Credits
    double gp = 0.0;    // Grade points
    double gpa = 0.0;   // Grade point average

    List<String> listCodes;
    List<Integer> listCredits;
    List<Double> listGrades;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        listCodes = new ArrayList<String>();
        listCredits = new ArrayList<Integer>();
        listGrades = new ArrayList<Double>();

        //Use listCodes.add("ITS333"); to add a new course code
        //Use listCodes.size() to refer to the number of courses in the list
    }

    public void AddClicked(View v) {
        Intent i = new Intent(this,CourseActivity.class);
        startActivityForResult(i,12);
    }

    public void ShowClicked(View v){
        Intent in = new Intent(this,CourseListActivity.class);
        String s = "List Courses\n";

        for(int i=0;i<listCodes.size();i++){

            String grade = new String();
            if(listGrades.get(i) == 4.0)
                grade = "A";
            if(listGrades.get(i) == 3.5)
                grade = "B+";
            if(listGrades.get(i) == 3.0)
                grade = "B";
            if(listGrades.get(i) == 2.5)
                grade = "C+";
            if(listGrades.get(i) == 2.0)
                grade = "C";
            if(listGrades.get(i) == 1.5)
                grade = "D+";
            if(listGrades.get(i) == 1.0)
                grade = "D";
            if(listGrades.get(i) == 0.0)
                grade = "F";

            String credit = Integer.toString(listCredits.get(i));

            String total = listCodes.get(i)+" ("+credit+" credits) = "+grade+"\n";
            s = s+total;

            in.putExtra("list",s);
        }
        startActivity(in);
    }

    public void calculateGPA(){
        cr = 0;
        gp = 0.0;
        gpa = 0.0;

        for(int i = 0 ; i<listCredits.size() ; i++){
            cr+=listCredits.get(i);
            double gptemp = listCredits.get(i) * listGrades.get(i);
            gp+=gptemp;
        }

        gpa = gp/cr;

        TextView tvgp = (TextView)findViewById(R.id.tvGP);
        TextView tvcr = (TextView)findViewById(R.id.tvCR);
        TextView tvgpa = (TextView)findViewById(R.id.tvGPA);

        tvgp.setText(Double.toString(gp));
        tvcr.setText(Integer.toString(cr));
        String gpa2 = String.format("%.2f",gpa);
        if(gpa != Double.NaN)
            tvgpa.setText(gpa2);
        else
            tvgpa.setText("0.0");
    }

    public void Reset(View v){
        cr = 0;
        gp = 0.0;
        gpa = 0.0;

        listCodes = new ArrayList<String>();
        listCredits = new ArrayList<Integer>();
        listGrades = new ArrayList<Double>();

        TextView tvgp = (TextView)findViewById(R.id.tvGP);
        TextView tvcr = (TextView)findViewById(R.id.tvCR);
        TextView tvgpa = (TextView)findViewById(R.id.tvGPA);

        tvgp.setText(Double.toString(gp));
        tvcr.setText(Integer.toString(cr));
        tvgpa.setText(Double.toString(gpa));

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Values from child activity

        if(requestCode == 12){
            if(resultCode == RESULT_OK){
                String coursecode = data.getStringExtra("coursecode");
                Integer credit = data.getIntExtra("credit",0);
                Double grade = data.getDoubleExtra("grade",-1);

                listCodes.add(coursecode);
                listCredits.add(credit);
                listGrades.add(grade);

                calculateGPA();
            }
        }
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
