package by.bycha.raspisanka.Adapter;

import android.app.Fragment;
import android.content.Context;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import by.bycha.raspisanka.Containers.Week;
import by.bycha.raspisanka.DTO.JSONtoArrays;


public class TabsAdapter extends FragmentPagerAdapter {
    private Map<Integer , android.support.v4.app.Fragment> tabs;
    private Context context ;
    private final String[] title = {"над" , "текущее" , "под"} ;
    private ArrayList<Week> data;

    public TabsAdapter(Context context, FragmentManager fm, JSONObject basejson) {
        super(fm);
        this.context = context ;
        try {
            data = JSONtoArrays.convertData(basejson);
        } catch (JSONException e) {
            e.printStackTrace();
        }
        initTabs();
    }


    @Override
    public CharSequence getPageTitle(int position) {
        return title[position];
    }

    @Override
    public android.support.v4.app.Fragment getItem(int position) {
        return tabs.get(position);
    }

    @Override
    public int getCount() {
        return tabs.size();
    }

    private void initTabs() {
        tabs = new HashMap<>();
        tabs.put(0 , ScheduleFragment.getInstance(context , data.get(0)));
        tabs.put(1 , ScheduleFragment.getInstance(context , data.get(1)));
        tabs.put(2 , ScheduleFragment.getInstance(context , data.get(2)));
    }
}
