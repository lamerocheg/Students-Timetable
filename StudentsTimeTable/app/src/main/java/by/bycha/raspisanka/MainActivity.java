package by.bycha.raspisanka;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;

import by.bycha.raspisanka.Adapter.TabsAdapter;
import by.bycha.raspisanka.DTO.ServerConnections;

public class MainActivity extends AppCompatActivity {
    private JSONObject basejson;
    private TabsAdapter tabsFragmentAdapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        StartApplication start = new StartApplication();
        start.execute();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_OK){finish();}
        try {
            basejson =  new JSONObject(data.getStringExtra("json"));
            basejson.put("ID" , data.getStringExtra("id"));
            ServerConnections.saveData(openFileOutput("basejson.json" , MODE_PRIVATE) , basejson.toString());
            fillData();
        } catch (NullPointerException |JSONException | IOException e) {
            finish();
        }

    }

    public void goToLoginActivity(){
        Intent intent = new Intent(getApplicationContext() ,LoginActivity.class);
        startActivityForResult(intent, 0);
    }

    public void logoutUser() {
        try {
            ServerConnections.logoutUser(basejson.getString("ID"));
            ServerConnections.saveData(openFileOutput("basejson.json", MODE_PRIVATE), "");
            goToLoginActivity();
        }
        catch (JSONException | IOException e) {e.printStackTrace();}
    }

    public void fillData(){
        setContentView(R.layout.activity_main);
        ViewPager viewPager = (ViewPager) findViewById(R.id.viewPager);
        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabLayout);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolBar);
        toolbar.setTitle(R.string.app_name);
        toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.logout:
                        logoutUser();
                        break;
                }
                return false;
            }
        });
        toolbar.inflateMenu(R.menu.menu);
        if (tabsFragmentAdapter == null) {
            tabsFragmentAdapter = new TabsAdapter(this, getSupportFragmentManager(), basejson);
        }
        viewPager.setAdapter(tabsFragmentAdapter);
        tabLayout.setupWithViewPager(viewPager);
        TabLayout.Tab tab = tabLayout.getTabAt(1);
        if (tab != null) {
            tab.select();
        }
        tabsFragmentAdapter.notifyDataSetChanged();
    }

    class StartApplication extends AsyncTask{
        Boolean avoid = false;
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            setTheme(R.style.DefaultTheme);
            setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            setContentView(R.layout.splash_screen);
        }

        @Override
        protected Object doInBackground(Object[] params) {
            BufferedReader br = null;
            String TMP =  "";
            try {
                br = new BufferedReader(new InputStreamReader(openFileInput("basejson.json")));
                String str;
                while ((str = br.readLine()) != null){ TMP += str ;}
            } catch (IOException e) {
                avoid = true;
                goToLoginActivity();
                e.printStackTrace();
            }
            if (TMP.equals("") || TMP.length() < 30) {
                avoid = true;
                goToLoginActivity();
            } else {
                try {
                    basejson = new JSONObject(TMP);
                    tabsFragmentAdapter = new TabsAdapter(getApplicationContext(), getSupportFragmentManager(), basejson);
                } catch (JSONException e) {
                    avoid = true;
                    goToLoginActivity();
                }
            }
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (!avoid) fillData();
        }
    }
}
