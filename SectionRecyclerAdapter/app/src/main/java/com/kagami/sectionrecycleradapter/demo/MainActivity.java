package com.kagami.sectionrecycleradapter.demo;

import android.content.Context;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.kagami.sectionrecycleradapter.SectionRecyclerAdapter;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private List<List<String>> dataSet;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupDataSet();
        recyclerView=(RecyclerView)findViewById(R.id.recycler);
        recyclerView.setAdapter(new MyAdapter(this,2));

    }

    private void setupDataSet(){
        dataSet=new ArrayList<>();
        for(int i=0;i<100;i++){
            List<String> list=new ArrayList<>();
            int count=(int)(Math.random()*10);
            //int count=2;
            for(int j=0;j<count;j++){
                list.add(String.format("item:%d-%d",i,j));
            }
            dataSet.add(list);
        }
    }

    class MyAdapter extends SectionRecyclerAdapter<HeaderHolder,ItemHolder>{

        public MyAdapter(Context context, int columns) {
            super(context, columns);
        }

        @Override
        public int getSectionCount() {
            return dataSet.size();
        }

        @Override
        public int getItemCountInSection(int section) {
            return dataSet.get(section).size();
        }

        @Override
        public void onBindSectionViewHolder(HeaderHolder holder, int section) {
            holder.textView.setText(String.format("section:%d   size:%d",section,dataSet.get(section).size()));
        }

        @Override
        public void onBindItemViewHolder(ItemHolder holder, int section, int item) {
            holder.textView.setText(dataSet.get(section).get(item));
        }

        @Override
        public HeaderHolder onCreateSectionViewHolder(ViewGroup parent) {
            TextView tv=new TextView(MainActivity.this);
            return new HeaderHolder(tv);
        }

        @Override
        public ItemHolder onCreateItemViewHolder(ViewGroup parent) {
            TextView tv=new TextView(MainActivity.this);
            return new ItemHolder(tv);
        }
    }



    class HeaderHolder extends RecyclerView.ViewHolder{
        public final TextView textView;

        public HeaderHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView;
            textView.setBackgroundColor(Color.GRAY);
        }
    }
    class ItemHolder extends RecyclerView.ViewHolder{
        public final TextView textView;
        public ItemHolder(View itemView) {
            super(itemView);
            textView=(TextView)itemView;
            textView.setBackgroundColor(Color.GREEN);
        }
    }
}
