package by.bycha.raspisanka.DTO;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;

import by.bycha.raspisanka.Containers.Week;


public class JSONtoArrays {
    static public ArrayList<Week> convertData(JSONObject data) throws JSONException {
        ArrayList<Week> result = new ArrayList<Week>(3);
        result.add(0 , new Week());
        result.add(0 , new Week());
        result.add(2 , new Week());
        for (int  i = 0 ; i <  data.getJSONObject("a").getInt("days_number") ; i++)
            (result.get(0)).addDay(data , "a" , i);
        for (int  i = 0 ; i <  data.getJSONObject("b").getInt("days_number") ; i++)
            (result.get(2)).addDay(data , "b" , i);
//--------------------------------------------------------------------------------------------
        GregorianCalendar today = new GregorianCalendar();
        int currWeek = today.get(Calendar.WEEK_OF_YEAR) % 2;
        int firstDay = today.get(Calendar.DAY_OF_WEEK) - 1;
        int time = today.get(Calendar.HOUR_OF_DAY)*60 + today.get(Calendar.MINUTE);
        int last = data.getJSONObject(currWeek > 0 ? "a" : "b").getInt("lections_number");
        String lastTime = data.getJSONObject(currWeek > 0 ? "a" : "b").getJSONObject("times").getJSONArray("end").getString(last-1);
        String[] times = lastTime.split(":");
        int lastInt = Integer.valueOf(times[0]) *60 + Integer.valueOf(times[1]);
        if  (time > lastInt) {
             if (firstDay == 6 ){
                 currWeek = currWeek == 0 ? 1 : 0;
                 firstDay = 0 ;
             } else firstDay++;
        }
        for (int  i = firstDay ; i < data.getJSONObject(currWeek > 0 ? "a" : "b").getInt("days_number") ; i++ )
        {
            (result.get(1)).addDay(data , currWeek > 0 ? "a" : "b" , i);
        }

        for (int  i = 0 ; i < firstDay ; i++ )
        {
            (result.get(1)).addDay(data , currWeek < 1 ? "a" : "b" , i);
        }
        return result;
    }
}
