package com.example.myapplication;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.text.method.ScrollingMovementMethod;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;
import com.google.gson.Gson;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by AMANULLAH on 07-Feb-18.
 */

public class DetailActivity extends AppCompatActivity implements ContactsAdapter.ContactsAdapterListener {

    Context context=this;
    String rolls;
    ImageView imageView;
    ImageView imageView2;
    TextView tx1,tx2,tx3,tx4,tx5,tx6,tx7,tx8,tx9,tx10,tx11,tx12,tx13;
    ImageView circleImageView;
    Upload2 user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main4);

        user = new Gson().fromJson(getIntent().getStringExtra("myjson"), Upload2.class);

        circleImageView= (ImageView) findViewById(R.id.img1);
        imageView= (ImageView) findViewById(R.id.background);
        imageView2= (ImageView) findViewById(R.id.background2);
        tx1= (TextView) findViewById(R.id.textView1);
        tx2= (TextView) findViewById(R.id.textView2);
        tx3= (TextView) findViewById(R.id.text_phone);
        tx4= (TextView) findViewById(R.id.text_fb);
        tx5= (TextView) findViewById(R.id.text_work);
        tx9= (TextView) findViewById(R.id.text_work3);
        tx6= (TextView) findViewById(R.id.text_email);
        tx7= (TextView) findViewById(R.id.text_blood);
        tx8= (TextView) findViewById(R.id.text_b);
        tx10= (TextView) findViewById(R.id.text_ps);
        tx11= (TextView) findViewById(R.id.text_pw);
        tx12= (TextView) findViewById(R.id.text_in);

        Glide.with(context)
                .load(user.getmImageUrl())
                .apply(RequestOptions.circleCropTransform())
                .into(circleImageView);
        Glide.with(context)
                .load(user.getmImageUrl())
                .into(imageView);
        Glide.with(context)
                .load(user.getmImageUrl())
                .into(imageView2);
        imageView2.setRotationX(180);

        tx1.setText(user.getUserName());
        tx2.setText("");
        tx3.setText(user.getPhone());
        tx4.setText(user.getFb());
        tx4.setMovementMethod(new ScrollingMovementMethod());
        tx5.setText(user.getHome());
        tx9.setText(user.getWork());
        tx6.setText(user.getUserEmail());
        tx7.setText(user.getBlood());
        tx8.setText(user.getBirth());
        tx10.setText(user.getPresentStudy());
        tx11.setText(user.getPresentWork());
        tx12.setText(user.getLinkin());

    }


    public void call1(View view) {
        String uri = "tel:"+tx3.getText().toString();
        Intent dialIntent = new Intent(Intent.ACTION_DIAL, Uri.parse(uri));
        try {
            startActivity(dialIntent);
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DetailActivity.this, "CALL faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public void msg1(View view) {
        Intent smsIntent = new Intent(Intent.ACTION_VIEW);
        smsIntent.setData(Uri.parse("smsto:"));
        smsIntent.setType("vnd.android-dir/mms-sms");
        String num = tx3.getText().toString();
        smsIntent.putExtra("address"  , num);
        try {
            startActivity(smsIntent);
            finish();
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DetailActivity.this, "SMS faild, please try again later.", Toast.LENGTH_SHORT).show();
        }
    }

    public void email(View view) {
        final Intent emailIntent = new Intent(Intent.ACTION_SEND);
        emailIntent.setType("text/plain");
        emailIntent.putExtra(Intent.EXTRA_EMAIL, new String[]{tx6.getText().toString()});
        try {
            startActivity(Intent.createChooser(emailIntent, "Send email using..."));
        } catch (android.content.ActivityNotFoundException ex) {
            Toast.makeText(DetailActivity.this, "No email clients installed.", Toast.LENGTH_SHORT).show();
        }
    }

    public void map1(View view) {
        String uri="http://maps.google.co.in/maps?q="+user.getHome()+", bangladesh";
        Intent map=new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        map.setPackage("com.google.android.apps.maps");
        try {
            startActivity(map);
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(DetailActivity.this, "map not open", Toast.LENGTH_SHORT).show();
        }
    }
    public void map2(View view) {
        String uri="http://maps.google.co.in/maps?q="+user.getWork()+", bangladesh";
        Intent map=new Intent(Intent.ACTION_VIEW, Uri.parse(uri));
        map.setPackage("com.google.android.apps.maps");
        try {
            startActivity(map);
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(DetailActivity.this, "map not open", Toast.LENGTH_SHORT).show();
        }
    }

    public void direction(View view) {
        Toast.makeText(DetailActivity.this, "direction", Toast.LENGTH_SHORT).show();
    }

    public void facebook(View view) {
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = getFacebookPageURL(this,user.getFb());
        facebookIntent.setData(Uri.parse(facebookUrl));
        try {
            startActivity(facebookIntent);
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(DetailActivity.this, "fb not open", Toast.LENGTH_SHORT).show();
        }

    }

    public String getFacebookPageURL(Context context,String url) {
        PackageManager packageManager = context.getPackageManager();
        try {
            int versionCode = packageManager.getPackageInfo("com.facebook.katana", 0).versionCode;
            if (versionCode >= 3002850) { //newer versions of fb app
                return "fb://facewebmodal/f?href=" +url;
            } else { //older versions of fb app
                return "fb://page/" + url;
            }
        } catch (PackageManager.NameNotFoundException e) {
            return url;
        }
    }


    public void in(View view){
        Intent facebookIntent = new Intent(Intent.ACTION_VIEW);
        String facebookUrl = user.getLinkin();
        facebookIntent.setData(Uri.parse(facebookUrl));
        try {
            startActivity(facebookIntent);
        }catch (android.content.ActivityNotFoundException ex){
            Toast.makeText(DetailActivity.this, "Linkedin not open", Toast.LENGTH_SHORT).show();
        }

    }

    @Override
    public void onContactSelected(Upload2 contact) {
        Toast.makeText(getApplicationContext(), "Selected: " + contact.getUserName() + ", " + contact.getPhone(), Toast.LENGTH_LONG).show();
    }
}
