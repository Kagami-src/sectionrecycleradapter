package com.kagami.sectionrecycleradapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.ViewGroup;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by kagami on 16/4/18.
 */
abstract public class SectionRecyclerAdapter<SV extends RecyclerView.ViewHolder,CV extends RecyclerView.ViewHolder> extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private final static int TYPE_SECTION=1,TYPE_ITEM=2;


    private List<IntRange> ranges=new ArrayList<>();
    private GridLayoutManager gridLayoutManager;
    public SectionRecyclerAdapter(Context context,int columns){
        super();
        gridLayoutManager=new GridLayoutManager(context,columns);
        gridLayoutManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
            @Override
            public int getSpanSize(int position) {
                int type=getItemViewType(position);
                if(type==TYPE_SECTION){
                    return gridLayoutManager.getSpanCount();
                }
                return 1;
            }
        });

    }
    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
        recyclerView.setLayoutManager(gridLayoutManager);
    }
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        if(viewType==TYPE_SECTION)
            return onCreateSectionViewHolder(parent);
        return onCreateItemViewHolder(parent);
    }

    @Override
    public int getItemViewType(int position) {
        for(IntRange range : ranges){
            if(range.contains(position)){
                if(position==range.start){
                    return TYPE_SECTION;
                }
                return TYPE_ITEM;
            }
        }
        return TYPE_ITEM;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        for(int i=0;i<ranges.size();i++){
            IntRange range=ranges.get(i);
            if(range.contains(position)){
                if(range.start==position){
                    onBindSectionViewHolder((SV)holder,i);
                }else{
                    onBindItemViewHolder((CV)holder,i,position-range.start-1);
                }
            }
        }
    }

    @Override
    public int getItemCount() {
        ranges.clear();
        int count=0;
        int sectionCount=getSectionCount();
        for(int i=0;i<sectionCount;i++) {
            int newcount=1 + count + getItemCountInSection(i);
            ranges.add(new IntRange(count,newcount));
            count = newcount;
        }
        return count;
    }

    abstract public int getSectionCount();
    abstract public int getItemCountInSection(int section);
    abstract public void onBindSectionViewHolder(SV holder, int section);
    abstract public void onBindItemViewHolder(CV holder, int section,int item);
    abstract public SV onCreateSectionViewHolder(ViewGroup parent);
    abstract public CV onCreateItemViewHolder(ViewGroup parent);


    class IntRange{
        public final int start,end;
        public IntRange(int start,int end){
            this.start=start;
            this.end=end;
        }
        public boolean contains(int i){
            if(i>=start && i<end)
                return true;
            return false;
        }

    }
}
