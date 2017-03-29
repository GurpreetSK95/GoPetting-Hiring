package me.gurpreetsk.gopetting.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.Window;
import android.view.WindowManager;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.SignInButton;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import me.gurpreetsk.gopetting.PettingApplication;
import me.gurpreetsk.gopetting.model.Data;
import me.gurpreetsk.gopetting.model.DataTable;
import me.gurpreetsk.gopetting.utils.Constants;
import me.gurpreetsk.gopetting.R;

public class LoginActivity extends AppCompatActivity {

    @BindView(R.id.button_signin)
    SignInButton signInButton;
    @BindView(R.id.relativelayout_login)
    RelativeLayout relativeLayoutLogin;

    GoogleSignInOptions gso;
    GoogleApiClient googleApiClient;
    SharedPreferences preferences;

    private static final int RC_SIGN_IN = 1;
    private static final String TAG = LoginActivity.class.getSimpleName();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        preferences = PreferenceManager.getDefaultSharedPreferences(LoginActivity.this);
        if (preferences.getBoolean(Constants.IS_USER_LOGGED_IN, false)) {
            startActivity(new Intent(LoginActivity.this, MainActivity.class));
        } else {
            setContentView(R.layout.activity_login);
            ButterKnife.bind(this);

            gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                    .requestEmail()
                    .build();
            googleApiClient = new GoogleApiClient.Builder(this)
                    .enableAutoManage(this, new GoogleApiClient.OnConnectionFailedListener() {
                        @Override
                        public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {
                            Toast.makeText(LoginActivity.this, "Connection failed",
                                    Toast.LENGTH_SHORT).show();
                            Log.e(TAG, "onConnectionFailed: " + connectionResult.getErrorMessage());
                        }
                    })
                    .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                    .build();

            signInButton.setSize(SignInButton.SIZE_STANDARD);
        }
    }


    @Override
    protected void onStop() {
        super.onStop();
        finish();
    }


    @OnClick(R.id.button_signin)
    public void signin() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }


    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            handleSignInResult(result);
        }
    }


    private void handleSignInResult(GoogleSignInResult result) {
        Log.d(TAG, "handleSignInResult:" + result.isSuccess());
        if (result.isSuccess()) {
            try {
                String acct = result.getSignInAccount().getDisplayName();
                Toast.makeText(this, getString(R.string.welcome) + " " + acct,
                        Toast.LENGTH_SHORT).show();
            } catch (NullPointerException e) {
                e.printStackTrace();
            }
            fetchDataFromServer();
            preferences.edit().putBoolean(Constants.IS_USER_LOGGED_IN, true).apply();
        } else {
            Toast.makeText(this, getString(R.string.failed_login), Toast.LENGTH_SHORT).show();
        }
    }


    public void fetchDataFromServer() {
        final ProgressDialog progressDialog = new ProgressDialog(LoginActivity.this);
        progressDialog.setMessage(getString(R.string.logging_in));
        progressDialog.show();
        JsonObjectRequest dataArrayRequest = new JsonObjectRequest(Request.Method.GET,
                Constants.URL,
                null,
                new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray dataArray = response.getJSONArray("data");
                            Log.i(TAG, "onResponse: " + dataArray.toString());
                            for (int i = 0; i < dataArray.length(); i++) {
                                JSONObject object = (JSONObject) dataArray.get(i);
                                Data data = new Data(object.get("name").toString(),
                                        object.get("startDate").toString(),
                                        object.get("endDate").toString(),
                                        object.get("url").toString(),
                                        object.get("icon").toString(),
                                        object.get("loginRequired").toString(),
                                        object.get("objType").toString()
                                );
                                try {
                                    getContentResolver().insert(DataTable.CONTENT_URI,
                                            DataTable.getContentValues(data, true));
                                } catch (Exception e) {
                                    Log.e(TAG, "onResponse: ", e);
                                }
                            }
                            progressDialog.dismiss();
                            startActivity(new Intent(LoginActivity.this, MainActivity.class));
                        } catch (JSONException e) {
                            e.printStackTrace();
                            Toast.makeText(LoginActivity.this, "Can't log in", Toast.LENGTH_SHORT).show();
                            progressDialog.dismiss();
                        }
                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.e(TAG, "onErrorResponse: ", error);
                        progressDialog.dismiss();
                        Toast.makeText(LoginActivity.this, "Can't log in", Toast.LENGTH_SHORT).show();
                    }
                }
        );
        PettingApplication.getInstance().addToQueue(dataArrayRequest);
    }
}
