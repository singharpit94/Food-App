package com.example.arpit.frizin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.app.ActionBar.LayoutParams;
import android.widget.Toast;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by arpit on 26/3/16.
 */
public class OrderDetails extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {


    TextView t1,t2,t3;
    int x;
    private RecyclerView recyclerView;
    private RecyclerView.LayoutManager layoutManager;
    private RecyclerView.Adapter adapter;
    ArrayList<Product> arrayList;
    String s=null;
    String name,phone,address;
    String user="arpitsinghnitd@gmail.com";
    LinearLayout r;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.orderdetails_adapter);
        Intent intent = getIntent();
        if (null != intent) {
            name= intent.getStringExtra("name");
            phone= intent.getStringExtra("phone");
            address= intent.getStringExtra("address");

        }
         t1=(TextView)findViewById(R.id.t1);
        t2=(TextView)findViewById(R.id.t2);
        t3=(TextView)findViewById(R.id.t3);
        t1.setText(name);
        t2.setText(phone);
        t3.setText(address);
        r=(LinearLayout)findViewById(R.id.r1);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);

        toolbar.setTitle("FRIZIN");
        //  setSupportActionBar(toolbar);


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.setDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
         SendMessage s1=new SendMessage();
        s1.execute();
        //fun();
        //adapter=new ProductAdapter(arrayList,this);

        //recyclerView.setAdapter(adapter);
        //recyclerView.setItemAnimator(null);




    }

    @Override
    public void onBackPressed() {

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();


        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    public class SendMessage extends AsyncTask<String, String, String> {

        private Dialog loadingDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            loadingDialog = ProgressDialog.show(OrderDetails.this, "Please wait", "Registering...");
        }
        @Override
        protected String doInBackground(String... params) {
            InputStream is = null;

            // Setting the name Value Pairs.
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            // Adding the string variables inside the namevaluepairs
            nameValuePairs.add(new BasicNameValuePair("user",user));
            nameValuePairs.add(new BasicNameValuePair("name",name));
            nameValuePairs.add(new BasicNameValuePair("phone",phone));
            nameValuePairs.add(new BasicNameValuePair("address",address));
            String result =null;
            // Setting up the connection inside the try and catch block.
            try {
                //Setting up the default HttpClient
                HttpClient httpClient = new DefaultHttpClient();

                //Setting up the http post method and passing the url in case
                //of online database and the ip address in case of local database.
                //And the php files which serves as the link between the android app
                //and mysql database.
                HttpPost httpPost = new HttpPost("http://172.16.41.13/frizin/order.php");

                //Passing the newValuePairs inside the httpPost.
                httpPost.setEntity(new UrlEncodedFormEntity(nameValuePairs));

                //Getting the response
                HttpResponse response = httpClient.execute(httpPost);

                //Setting up the entity
                HttpEntity entity = response.getEntity();


                //Setting up the content inside the input stream reader.
                //Lets define the input stream reader (defined above)
                is = entity.getContent();

                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "UTF-8"), 8);
                StringBuilder sb = new StringBuilder();

                String line = " ";
                while ((line = reader.readLine()) != null)
                {
                    sb.append(line + "\n");
                }
                result = sb.toString();
                s=result;

            }
            catch (ClientProtocolException e) {
                Log.e("ClientProtocol", "Log_tag");
                e.printStackTrace();
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            } catch (IOException e) {
                Log.e("Log_tag", "IO Exception");
                e.printStackTrace();
            }



            return result;
        }



        @Override
        protected void onPostExecute(String result) {
            if(result==null)
            {    loadingDialog.dismiss();
                Toast.makeText(getApplicationContext(), "DOne", Toast.LENGTH_LONG).show();

            }
            else{             s = result.trim();

                loadingDialog.dismiss();

                Toast.makeText(getApplicationContext(),s,Toast.LENGTH_LONG).show();
                parseData();}





        }
    }
    //This method will parse json data
    private void parseData() {
        JSONArray array=null;
        try
        {
            array=new JSONArray(s);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < array.length(); i++) {

            JSONObject json = null;
            try {
                //Getting json
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(
                        LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);

                json = array.getJSONObject(i);
                TextView product = new TextView(this);
                product.setText(json.getString("pname"));
                product.setLayoutParams(params);
                r.addView(product);



            } catch (JSONException e) {
                e.printStackTrace();
            }

        }


    }
    void fun()
    {   long a =49;
        Product stationary= new Product();
        //Adding data to the superhero object
        stationary.setImgUrl("http://172.16.41.13/frizin/01.jpg");
        stationary.setName("Arpit");
        stationary.setPrice(a);
        stationary.setDesc("sffdfddf");
        arrayList.add(stationary);
        // adapter.notifyDataSetChanged();


    }

}
