package com.thedev.rajaratacommunity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.skydoves.elasticviews.ElasticImageView;
import com.thedev.rajaratacommunity.Adapters.NewsAdapter;
import com.thedev.rajaratacommunity.Models.NewsData;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;

public class AllNewsScreen extends AppCompatActivity {

    RecyclerView AllNewsRview;
    ElasticImageView back;

    String latestnewlink;
    String mainurl="http://www.rjt.ac.lk/news/";
    ArrayList<NewsData> news=new ArrayList<NewsData>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_news_screen);

        AllNewsRview=findViewById(R.id.AllNewsRview);
        back=findViewById(R.id.back);
        AllNewsRview.setHasFixedSize(true);
        AllNewsRview.setLayoutManager(new LinearLayoutManager(this));


        back.setOnClickListener(view -> {
            this.finish();
        });

        getNews();
    }

    private void getNews() {
        RequestQueue queue= Volley.newRequestQueue(AllNewsScreen.this);

        StringRequest request=new StringRequest(Request.Method.GET, mainurl, response -> {
            Document doc= Jsoup.parse(response);
            //Elements data = doc.select("span.art-postheader");
            Elements datat=doc.select("div[class=row align-items-center no-gutter blog-item rs-blog-grid1]");


            for (int i=0;i<datat.size();i++){
                Elements data=datat.eq(i);

                String imglink=data.select("div[class=col-md-6]").eq(0).select("a").eq(0).select("img").attr("src");
                String title=data.select("div[class=col-md-6]").eq(1).select("a").eq(0).text();
                //title dd
                String tdate=data.select("div[class=col-md-6]").eq(1).select("div[class=blog-content]").select("ul").text();
                Elements el=doc.select("div.art-post-inner").select("a");
                latestnewlink=el.eq(i).attr("href");
                String link=data.select("div[class=col-md-6]").eq(0).select("a").attr("href");
                String tbody="t body";
                news.add(new NewsData(title,tdate,imglink,link));

            }

            NewsAdapter adapter=new NewsAdapter(AllNewsScreen.this,news);
            AllNewsRview.setAdapter(adapter);





        }, error -> Toast.makeText(AllNewsScreen.this, ""+error.getMessage(), Toast.LENGTH_SHORT).show());
        queue.add(request);
    }
}