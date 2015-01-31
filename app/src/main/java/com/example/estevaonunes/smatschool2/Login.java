package com.example.estevaonunes.smatschool2;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;
import org.json.JSONException;
import org.json.JSONObject;
import java.io.IOException;


public class Login extends Activity {

    private EditText username;
    private EditText password;
    private Button btEstrar;
    private TextView esqueceu;
    private String url = "http://www.fjn.edu.br";
    private String response;
    private String token;
    private JSONObject resposta = null;
    public static final String PREFERENCES = "Preferencias";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.activity_login);

        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
        token = settings.getString("Token", "");

        username = (EditText) findViewById(R.id.username);
        password = (EditText) findViewById(R.id.password);
        esqueceu = (TextView) findViewById(R.id.esqueceu);
        btEstrar = (Button) findViewById(R.id.btEntrar);

        final int testeConexao = verConexao(Login.this);

        esqueceu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                switch (testeConexao) {

                    case 0:
                        Toast.makeText(Login.this, "Você não está conectado a internet.\nConect e tente novamente.", Toast.LENGTH_LONG).show();
                        break;

                    case 1:
                        Toast.makeText(Login.this, "Esqueceu.\nComo vamos tratar esse esqueceu a senha?", Toast.LENGTH_LONG).show();
                        break;

                    case 2:
                        Toast.makeText(Login.this, "Talvez demore um pouco.\nConect na wifi para melhores resultados.", Toast.LENGTH_LONG).show();
                        Toast.makeText(Login.this, "Esqueceu.\nComo vamos tratar esse esqueceu a senha?", Toast.LENGTH_LONG).show();
                        break;

                }

            }

        });

        btEstrar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if (verificaInput()) {

                    switch (testeConexao) {

                        case 0:
                            Toast.makeText(Login.this, "Você não está conectado a internet.\nConect e tente novamente.", Toast.LENGTH_LONG).show();
                            break;

                        case 1:
                            new LoginAsyncTask().execute(url);
                            break;

                        case 2:
                            Toast.makeText(Login.this, "Talvez demore um pouco.\nConect na wifi para melhores resultados.", Toast.LENGTH_LONG).show();
                            new LoginAsyncTask().execute(url);
                            break;

                    }

                } else {

                    Toast.makeText(Login.this, "Opa!\nTá faltando preencher alguma coisa.", Toast.LENGTH_LONG).show();

                }

            }

        });

    }

    @Override
    protected void onStop() {
        super.onStop();

        SharedPreferences settings = getSharedPreferences(PREFERENCES, 0);
        SharedPreferences.Editor editor = settings.edit();
        editor.putString("Token", token);

        editor.commit();

    }

    protected boolean verificaInput() {

        boolean retorno = true;
        String user = username.getText().toString().trim();
        String pass = password.getText().toString().trim();

        if ((user.equals("")) || (pass.equals(""))) {
            retorno = false;
        }

        return retorno;

    }

    public int verConexao(Context context) {

        int con = 0;
        try {
            ConnectivityManager cm = (ConnectivityManager)
                    context.getSystemService(Context.CONNECTIVITY_SERVICE);

            if (cm.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).isConnected()) {
                con = 2;
            } else if (cm.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnected()) {
                con = 1;
            } else {
                con = 0;
            }

        } catch (Exception e) {
            Toast.makeText(context, e.getMessage(), Toast.LENGTH_LONG).show();
        }

        return con;

    }

    class LoginAsyncTask
            extends AsyncTask<String, Void, String> {

        private ProgressDialog progressDialog;

        protected void onPreExecute() {

            progressDialog = new ProgressDialog(Login.this);
            progressDialog.setIndeterminate(true);
            progressDialog.setCancelable(false);
            progressDialog.setMessage("Aguarde...");
            progressDialog.show();

        }

        @Override
        protected String doInBackground(String... strings) {

           /* ConexaoHTTP httpConnection = new ConexaoHTTP();

            try {

                JSONObject obj = new JSONObject();
                obj.put("username", username.getText().toString());
                obj.put("password", password.getText().toString());

                response = httpConnection.post(url, obj.toString(), null);
                resposta = new JSONObject(response);
                token = resposta.getString("Token");
                Log.i("RESPONSE", "Resposta: " + response);

            } catch (IllegalStateException e) {
                e.printStackTrace();
            } catch (IOException e) {
                e.printStackTrace();
            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            return response;
        }

        protected void onPostExecute(String result) {
            String condicao = "TRUE";

           /* try {

                if (resposta != null) {
                    condicao = resposta.getString("Login").toString();
                }

            } catch (JSONException e) {
                e.printStackTrace();
            }*/

            if (condicao.equals("TRUE")) {
                startActivity(new Intent(Login.this,Menu.class));
                progressDialog.dismiss();
            } else {
                progressDialog.dismiss();
                Toast.makeText(Login.this, "Há algo errado!\nConfira os dados e tente novamente.", Toast.LENGTH_LONG).show();
            }

        }

    }
}
