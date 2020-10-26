package com.example.covid19tracker;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerViewAdapter extends RecyclerView.Adapter<RecyclerViewAdapter.RecyclerViewHolder>{

    private Context context;
    private ArrayList<CoronaItem> CoronaItemArrayList;

    public ArrayList<CoronaItem> getCoronaItemArrayList() {
        return CoronaItemArrayList;
    }

    public void setCoronaItemArrayList(ArrayList<CoronaItem> CoronaItemArrayList) {
        this.CoronaItemArrayList = CoronaItemArrayList;
    }

    public RecyclerViewAdapter(Context context, ArrayList<CoronaItem> CoronaItemArrayList) {
        this.context = context;
        this.CoronaItemArrayList = CoronaItemArrayList;

    }

    @NonNull
    @Override
    public RecyclerViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view= LayoutInflater.from(context).inflate(R.layout.news_items,parent
                ,false);
        return new RecyclerViewHolder(view);


    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerViewHolder holder, int position) {

        CoronaItem citem =CoronaItemArrayList.get(position);
        String state= citem.getState();
        String death= citem.getDeath();
        String recovered= citem.getRecovered();
        String active= citem.getActive();
        String confirmed= citem.getConfirmed();
        String lastUpdt= citem.getLastUpdated();
        String todayDeath= citem.getTodayDeath();
        String todayActive= citem.getTodayActive();
        String todayRecovered= citem.getTodayRecovered();

        holder.state.setText(state);
        holder.death.setText(death);
        holder.recovered.setText(recovered);
        holder.active.setText(active);
        holder.confirmed.setText(confirmed);
        holder.lastUpdate.setText(lastUpdt);
        holder.todayDeath.setText(String.format("(%s)",todayDeath));
        holder.todayRecovered.setText(String.format("(%s)",todayRecovered));
        holder.todayActive.setText(String.format("(%s)",todayActive));

    }

    @Override
    public int getItemCount() {
        try{
            return CoronaItemArrayList.size();
        } catch (Exception ex){
            return 0;
        }

    }

    class RecyclerViewHolder extends RecyclerView.ViewHolder{
        TextView state,death,recovered,active,confirmed,
                lastUpdate,todayDeath,todayActive,todayRecovered;



        public RecyclerViewHolder(@NonNull View itemView) {
            super(itemView);
            death=itemView.findViewById(R.id.death);
            state=itemView.findViewById(R.id.stateName);
            recovered=itemView.findViewById(R.id.recovered);
            active=itemView.findViewById(R.id.active);
            confirmed=itemView.findViewById(R.id.confirmed);
            lastUpdate=itemView.findViewById(R.id.lastUpdated);
            todayDeath=itemView.findViewById(R.id.todayDeath);
            todayActive=itemView.findViewById(R.id.todayActive);
            todayRecovered=itemView.findViewById(R.id.todayRecovered);
        }
    }
}