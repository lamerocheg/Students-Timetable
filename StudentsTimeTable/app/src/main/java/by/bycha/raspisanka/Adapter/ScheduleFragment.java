package by.bycha.raspisanka.Adapter;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import by.bycha.raspisanka.Containers.Week;
import by.bycha.raspisanka.R;

public class ScheduleFragment extends Fragment {
    private static final int LAYOUT = R.layout.shedule_fragment;
    private View view;
    private Context context;
    private String title;
    private Week week;
    public static ScheduleFragment getInstance(Context context , Week week){
        Bundle args = new Bundle();
        ScheduleFragment fragment = new ScheduleFragment();
        fragment.setArguments(args);
        fragment.setContext(context);
        fragment.setWeek(week);
        return fragment;
    }

    public void setContext(Context context) {
        this.context = context;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        view = inflater.inflate(LAYOUT , container , false);

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(context));
        recyclerView.setAdapter(new by.bycha.raspisanka.Adapter.ListAdapter(week));
        return this.view;
    }


    public void setWeek(Week week) {
        this.week = week;
    }
}