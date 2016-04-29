package by.bycha.raspisanka.Containers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

public class Day {
    public String title;
    public ArrayList<String> lection;
    public ArrayList<String> starts;
    public ArrayList<String> stops;
    public ArrayList<String> classrooms;
    public ArrayList<String> lectors;

    public Day(String title) {
        this.title = title;
        lection = new ArrayList<>();
        starts = new ArrayList<>();
        stops = new ArrayList<>();
        classrooms = new ArrayList<>();
        lectors = new ArrayList<>();
    }
    public void addLections(JSONObject data, String type ,  int day , int number) throws JSONException {
        if (data.getJSONObject(type).getJSONArray("lections").getJSONArray(day).getString(number).length() >= 2) {
            starts.add(data.getJSONObject(type).getJSONObject("times").getJSONArray("start").getString(number));
            stops.add(data.getJSONObject(type).getJSONObject("times").getJSONArray("end").getString(number));
            lectors.add(data.getJSONObject(type).getJSONArray("lectors").getJSONArray(day).getString(number));
            lection.add(data.getJSONObject(type).getJSONArray("lections").getJSONArray(day).getString(number));
            classrooms.add(data.getJSONObject(type).getJSONArray("places").getJSONArray(day).getString(number));
        }
    }
}
