package info.androidhive.cardview;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioGroup;

import java.util.Random;

public class Upload extends AppCompatActivity {

    EditText editText3,editText4,editText6,editText7,editText8;
    RadioGroup category;
    String category_text;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_des);
        editText3=(EditText)findViewById(R.id.editText3);
        editText4=(EditText)findViewById(R.id.editText4);
        editText6=(EditText)findViewById(R.id.editText6);
        editText7=(EditText)findViewById(R.id.editText7);
        editText8=(EditText)findViewById(R.id.editText8);
        category=(RadioGroup)findViewById(R.id.category_RG);
        category.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId){
                    case R.id.rb_electronics:
                        category_text="Electronics";
                        break;
                    case R.id.rb_smartphone:
                        category_text="SmartPhone";
                        break;
                }
            }
        });
    }
    public void uploadToServer(View view){
        Random random=new Random();
        String itemID=String.valueOf(random.nextInt());
        String username=editText3.getText().toString().replaceAll("'","''");
        String price=editText4.getText().toString();
        String description=editText6.getText().toString().replaceAll("'","''");
        String location=editText7.getText().toString().replaceAll("'","''");
        String mobile=editText8.getText().toString();

        if(!username.isEmpty()&&!description.isEmpty()&&
                !price.isEmpty()&&!location.isEmpty()
                &&!mobile.isEmpty()&&!category_text.isEmpty()){
            SeasonAndEpisode episode=new SeasonAndEpisode(1,Upload.this,itemID,username,description,mobile,price,category_text,location);
            episode.execute();
        }
    }
}
