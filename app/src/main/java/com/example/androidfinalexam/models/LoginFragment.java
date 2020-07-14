package com.example.androidfinalexam.models;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.androidfinalexam.R;
import com.example.androidfinalexam.UrlApi;
import com.example.androidfinalexam.activities.HomeActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private View view;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView txtBack;
    private String url = UrlApi.login;
    private ProgressDialog progressDialog;
    private LoginButton loginButton;
    private CallbackManager callbackManager;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        view = inflater.inflate( R.layout.activity_login, container, false);
        // functions
        initView();
        setOnClick();


        return view;

    }

    private void setOnClick() {

        txtBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace
                        (R.id.framauth_id, new RegisterFragmet()).commit();
            }
        });

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                    login();
                }
        });
    }

    private void initView() {
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        btnLogin = (Button) view.findViewById(R.id.btnLogin);
        txtBack = (TextView) view.findViewById(R.id.back_id);
        progressDialog = new ProgressDialog(getActivity());
        progressDialog.setMessage("Đang xác thực");
    }

    private void login() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ( jsonObject.getBoolean("status") ){

                        JSONObject object = jsonObject.getJSONObject("data");
                        int id = object.getInt("id");
                        String email = object.getString("email");
                        String password = object.getString("password");
                        String date = object.getString("created_at");

                        Intent intent = new Intent( getActivity(), HomeActivity.class );
                        intent.putExtra("id", String.valueOf(id));
                        intent.putExtra("email", email);
                        intent.putExtra("password", password);
                        intent.putExtra("date", date);
                        progressDialog.show();

                        startActivity(intent);

                    }else {
                        Toast.makeText(getActivity(), "Tài khoản hoặc mật khẩu sai !", Toast.LENGTH_SHORT).show();
                        edtEmail.setText("");
                        edtPassword.setText("");
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                Toast.makeText(getActivity(), "Lỗi " + error, Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();

                // get data from edittext
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                // check null
                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                    //edtEmail.setError("Invalid Email");
                    edtEmail.setFocusable(true);
                }else if (edtPassword.length() < 6){
                    edtPassword.setError("Password length at least 6 characters");
                    edtPassword.setFocusable(true);
                }else{
                    parrams.put("email", email);
                    parrams.put("password", password);
                }


                return parrams;
            }
        };
        queue.add(stringRequest);
    }



    private void loginFacebook(){

        callbackManager = CallbackManager.Factory.create();
        loginButton.setReadPermissions(Arrays.asList(
                "email"));
        loginButton.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                getInfo();
            }

            @Override
            public void onCancel() {

            }

            @Override
            public void onError(FacebookException error) {

            }
        });



    }

    private void getInfo() {
        GraphRequest request = GraphRequest.newMeRequest(AccessToken.getCurrentAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
            @Override
            public void onCompleted(JSONObject object, GraphResponse response) {
                Log.d("JSON", response.getJSONObject().toString());
                try {
                    Toast.makeText(getActivity(), "name" + object.getString("name"), Toast.LENGTH_SHORT).show();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
        Bundle parameters = new Bundle();
        parameters.putString("fields", "name, email");
        request.setParameters(parameters);
        request.executeAsync();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }


}
