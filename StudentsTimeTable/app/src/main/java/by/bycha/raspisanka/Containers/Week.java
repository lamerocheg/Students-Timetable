package by.bycha.raspisanka.Containers;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;


public class Week {
    public ArrayList<Day> days;
    static public final String[] TITLES = {"Понедельник" , "Вторник" , "Среда" , "Четверг" , "Пятница" , "Суббота" , "Воскресенье"};
    public Week(ArrayList<Day> days) {
        this.days = days;
    }

    public Week() {
        days = new ArrayList<>();
    }
    public void addDay(JSONObject data, String type , int day) throws JSONException {
        Day tmpDay = new Day(TITLES[day]);
        int length = data.getJSONObject(type).getInt("lections_number") ;
        for (int i = 0 ; i < length ; i++) {
            tmpDay.addLections(data, type, day, i);
        }
        days.add(tmpDay);
    }
    public void addDay(Day day){
        days.add(day);
    }
}
