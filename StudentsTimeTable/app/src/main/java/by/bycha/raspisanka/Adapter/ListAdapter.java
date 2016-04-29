package by.bycha.raspisanka.Adapter;

import android.support.v7.widget.CardView;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import by.bycha.raspisanka.Containers.Week;
import by.bycha.raspisanka.R;


public class ListAdapter extends RecyclerView.Adapter<ListAdapter.ListHolder> {
    public Week week ;

    public ListAdapter(Week week) {
        this.week = week;
    }

    @Override
    public ListHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.cardview_fragment, parent, false);
        return new ListHolder(view);
    }

    @Override
    public void onBindViewHolder(ListHolder holder, int position) {
        holder.linearLayout.removeAllViews();
        LinearLayout titleDay = (LinearLayout) LayoutInflater.from(holder.linearLayout.getContext()).inflate(R.layout.title_fragment, holder.linearLayout, false);
        TextView title = (TextView) titleDay.findViewById(R.id.titleDay);
        title.setText(week.days.get(position).title);
        holder.linearLayout.addView(titleDay);
        for (int i = 0 ; i < week.days.get(position).lection.size() ; i++){
            LinearLayout lectionLayout = (LinearLayout) LayoutInflater.from(holder.linearLayout.getContext())
                    .inflate(R.layout.fragment_lection_item, holder.linearLayout, false);
            TextView start = (TextView) lectionLayout.findViewById(R.id.start);
            TextView stop = (TextView) lectionLayout.findViewById(R.id.stop);
            TextView subject = (TextView) lectionLayout.findViewById(R.id.subject);
            TextView about = (TextView) lectionLayout.findViewById(R.id.about);
            TextView classroom = (TextView) lectionLayout.findViewById(R.id.classroom);
            start.setText(week.days.get(position).starts.get(i));
            stop.setText(week.days.get(position).stops.get(i));
            about.setText(week.days.get(position).lectors.get(i));
            subject.setText(week.days.get(position).lection.get(i));
            classroom.setText(week.days.get(position).classrooms.get(i));
            holder.linearLayout.addView(lectionLayout);
        }
    }



    @Override
    public int getItemCount() {
        return week.days.size();
    }

    public static class ListHolder extends RecyclerView.ViewHolder {
        CardView cardView;
        LinearLayout linearLayout;

        public ListHolder(View itemView) {
            super(itemView);
            cardView = (CardView) itemView.findViewById(R.id.cardView);
            linearLayout = (LinearLayout) itemView.findViewById(R.id.scheduleDayLayout);
        }
    }
}
