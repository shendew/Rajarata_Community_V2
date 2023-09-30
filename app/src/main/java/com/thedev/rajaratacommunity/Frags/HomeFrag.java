package com.thedev.rajaratacommunity.Frags;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.denzcoskun.imageslider.ImageSlider;
import com.denzcoskun.imageslider.constants.ScaleTypes;
import com.denzcoskun.imageslider.models.SlideModel;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.thedev.rajaratacommunity.Adapters.EventAdapter;
import com.thedev.rajaratacommunity.Adapters.NewsAdapter;
import com.thedev.rajaratacommunity.Helpers.LoadingDialog;
import com.thedev.rajaratacommunity.Models.Event;
import com.thedev.rajaratacommunity.Models.NewsData;
import com.thedev.rajaratacommunity.R;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.util.ArrayList;



public class HomeFrag extends Fragment {

    ImageSlider imageSlider;

    FirebaseDatabase db;

    DatabaseReference slidersRef, scoreRef,eventRef;

    RelativeLayout hiddebTab;

    TextView matchtitle, teams, score;
    RecyclerView eventRview,newsRview;
    EventAdapter evAdapter;
    String latestnewlink;
    String mainurl="http://www.rjt.ac.lk/news/";
    ArrayList<Event> events=new ArrayList<>();
    ArrayList<SlideModel> imgList=new ArrayList<>();
    ArrayList<NewsData> news=new ArrayList<>();
    LoadingDialog loadingDialog;
    int loadingstts=0;


    public HomeFrag() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View v=inflater.inflate(R.layout.fragment_home, container, false);
        imageSlider=v.findViewById(R.id.image_slider);
        hiddebTab=v.findViewById(R.id.hiddentab);
        matchtitle=v.findViewById(R.id.matchtitle);
        teams=v.findViewById(R.id.teams);
        score=v.findViewById(R.id.score);
        eventRview=v.findViewById(R.id.eventRview);
        newsRview=v.findViewById(R.id.newsRview);
        loadingDialog=new LoadingDialog(getActivity());
        loadingDialog.showDialog();

        LinearLayoutManager lmg=new LinearLayoutManager(getContext());
        lmg.setOrientation(LinearLayoutManager.HORIZONTAL);
        eventRview.setLayoutManager(lmg);

        eventRview.setHasFixedSize(true);
        //newsRview.setHasFixedSize(true);
        newsRview.setLayoutManager(new LinearLayoutManager(getContext()));



        db=FirebaseDatabase.getInstance();
        slidersRef=db.getReference("Sliders");
        scoreRef=db.getReference("HiddenTabs");
        eventRef=db.getReference("Events");



        if (news.isEmpty()){
            getLatestNews();
        }
        if (imgList.isEmpty()){
            loadSliders();
        }
        if (events.isEmpty()){
            loadEvents();
        }

        loadLiveScore();




        return v;
    }

    private void loadEvents() {

        eventRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot ds: snapshot.getChildren()){
                    events.add(ds.getValue(Event.class));
                }
                evAdapter=new EventAdapter(getContext(),events);
                eventRview.setAdapter(evAdapter);
                loadingstts++;
                if (loadingstts==3){
                    loadingDialog.hideDialog();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    private void loadLiveScore() {
        scoreRef.child("MatchTab").addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                String Sscore=snapshot.child("score").getValue().toString();
                String Steams=snapshot.child("teams").getValue().toString();
                String Stitle=snapshot.child("title").getValue().toString();

                matchtitle.setText(Stitle);
                teams.setText(Steams);
                score.setText(Sscore);

                if (Boolean.TRUE.equals(snapshot.child("visibility").getValue(Boolean.class))){
                    hiddebTab.setVisibility(View.VISIBLE);
                }else{
                    hiddebTab.setVisibility(View.GONE);
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(getContext(), ""+error, Toast.LENGTH_SHORT).show();
            }
        });
    }

    private void loadSliders() {
        slidersRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {

                for(DataSnapshot ds:snapshot.getChildren()){
                    String url=ds.child("url").getValue().toString();
                    String title=ds.child("title").getValue().toString();

                    imgList.add(new SlideModel(url,title,ScaleTypes.CENTER_CROP));

                }
                imageSlider.setImageList(imgList,ScaleTypes.CENTER_CROP);
                loadingstts++;
                if (loadingstts==3){
                    loadingDialog.hideDialog();
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void getLatestNews() {

        RequestQueue queue= Volley.newRequestQueue(getContext());

        StringRequest request=new StringRequest(Request.Method.GET, mainurl, response -> {
            Document doc= Jsoup.parse(response);
            //Elements data = doc.select("span.art-postheader");
            Elements datat=doc.select("div[class=row align-items-center no-gutter blog-item rs-blog-grid1]");


            for (int i=0;i<5;i++){
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

            NewsAdapter adapter=new NewsAdapter(getContext(),news);
            newsRview.setAdapter(adapter);
            loadingstts++;
            if (loadingstts==3){
                loadingDialog.hideDialog();
            }




        }, error -> Toast.makeText(getContext(), ""+error.getMessage(), Toast.LENGTH_SHORT).show());
        queue.add(request);

    }
}