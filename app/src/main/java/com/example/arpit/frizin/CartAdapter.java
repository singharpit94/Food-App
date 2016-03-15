package com.example.arpit.frizin;

import android.app.Dialog;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.toolbox.ImageLoader;
import com.android.volley.toolbox.NetworkImageView;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicNameValuePair;
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
 * Created by arpit on 13/3/16.
 */
public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {
    List<CartProduct> list;
    private ImageLoader imageLoader;
    private Context context;

    String s=null;
    String user="arpitsinghnitd@gmail.com";
    int pid=0;

    public CartAdapter(List<CartProduct> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cart_product, parent, false);
        ViewHolder viewHolder = new ViewHolder(view);
        return viewHolder;
    }
    @Override
    public void onBindViewHolder(final ViewHolder holder, final int position) {
        CartProduct  stationary = list.get(position);
        //Loading image from url
        int x;
        final String name=stationary.getName();
        final Long price=stationary.getPrice();
        final Long[] quantity = new Long[list.size()];
        final String desc=stationary.getDesc();
        final int qty=stationary.getQty();

        final int id=stationary.getId();
        holder.fun(id);
        Log.d("OnBind", "calling on bind");
        imageLoader = Singleton.getInstance().getImageLoader();
        imageLoader.get(stationary.getImgUrl(), ImageLoader.getImageListener(holder.imageView, R.mipmap.ic_launcher, R.drawable.abc_btn_check_material));
        holder.textViewName.setText(stationary.getName());
        holder.textViewPrice.setText(stationary.getPrice().toString());
        holder.textViewQty.setText(Integer.toString(stationary.getQty()));
        holder.remove.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                removeAt(position,id);

            }
        });

        holder.imageView.setImageUrl(stationary.getImgUrl(), imageLoader);









    }
    @Override
    public int getItemCount() {
        return list.size();
    }
    public void removeAt(int p,int id) {
        list.remove(p);
        //  Config.cartlist.remove(p);
       // notifyItemRemoved(p);
        //notifyItemRangeChanged(p, list.size());
        pid=id;
        Toast.makeText(context,Integer.toString(id),Toast.LENGTH_LONG).show();
        Add a=new Add();
        a.execute(id);
        notifyItemRemoved(p);
        notifyItemRangeChanged(p, list.size());

    }


    class ViewHolder extends RecyclerView.ViewHolder    {
        public NetworkImageView imageView;
        public TextView textViewName;

        public TextView textViewPrice;
        public TextView textViewQty;
        public Button remove;

        private ItemClickListener clickListener;
        public int id;


        //Initializing Views
        public ViewHolder(View itemView) {
            super(itemView);
            context=itemView.getContext();
            imageView = (NetworkImageView) itemView.findViewById(R.id.imageViewHero);
            textViewName = (TextView) itemView.findViewById(R.id.name);
            textViewQty=(TextView)itemView.findViewById(R.id.qty);
            remove=(Button)itemView.findViewById(R.id.remove);
            textViewPrice = (TextView) itemView.findViewById(R.id.price);


        }


        void  fun(int id)
        {
            this.id=id;
            //Toast.makeText(context,id,Toast.LENGTH_LONG).show();

        }

    }
    public class Add extends AsyncTask<Integer, String, String> {

        private Dialog loadingDialog;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
           // loadingDialog = ProgressDialog.show(CartAdapter.this, "Please wait", "Registering...");
        }
        @Override
        protected String doInBackground(Integer... params) {
            InputStream is = null;
            int proid=params[0];
            // Setting the name Value Pairs.
            List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
            // Adding the string variables inside the namevaluepairs
            nameValuePairs.add(new BasicNameValuePair("product_id",Integer.toString(proid)));
            nameValuePairs.add(new BasicNameValuePair("user",user));
            //nameValuePairs.add(new BasicNameValuePair("qty",Integer.toString(quantity)));
            String result =null;
            // Setting up the connection inside the try and catch block.
            try {
                //Setting up the default HttpClient
                HttpClient httpClient = new DefaultHttpClient();

                //Setting up the http post method and passing the url in case
                //of online database and the ip address in case of local database.
                //And the php files which serves as the link between the android app
                //and mysql database.
                HttpPost httpPost = new HttpPost("http://172.16.41.13/frizin/remove_cart.php");

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
            {
                Toast.makeText(context, "Singh", Toast.LENGTH_LONG).show();

            }
            else {
                s = result.trim();

                //loadingDialog.dismiss();

                Toast.makeText(context, s, Toast.LENGTH_LONG).show();
              //  parseData();
            }





        }
    }








}
