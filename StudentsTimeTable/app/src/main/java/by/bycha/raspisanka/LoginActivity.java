package by.bycha.raspisanka;


import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import by.bycha.raspisanka.DTO.ServerConnections;

public class LoginActivity extends AppCompatActivity {
    private String id;
    private String data;
    private String login;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_REVERSE_PORTRAIT);
        setContentView(R.layout.activity_login);
    }

    public void onLoginClick(View V){
        AutoCompleteTextView loginView = (AutoCompleteTextView) findViewById(R.id.autoCompleteTextView);
        login = loginView.getText().toString();
        if (login.length()  < 3) {
            Toast.makeText(getApplicationContext() , "Логин должен быть длиннее" , Toast.LENGTH_LONG).show();
        }
        LoginUser loginUser = new LoginUser();
        loginUser.execute();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        setResult(RESULT_CANCELED , new Intent());
        finish();
    }

    class LoginUser extends AsyncTask{

        @Override
        protected Object doInBackground(Object[] params) {
            id = ServerConnections.loginUser(login);
            data = ServerConnections.getData(id);
            return null;
        }

        @Override
        protected void onPostExecute(Object o) {
            super.onPostExecute(o);
            if (data != null) {
                Intent intent= new Intent();
                intent.putExtra("id" , id);
                intent.putExtra("json" , data);
                setResult(RESULT_OK, intent);
                finish();
            } else {
                Toast.makeText(getApplicationContext() , "Проверьте правильность логина" , Toast.LENGTH_LONG).show();

            }
        }

    }
}
