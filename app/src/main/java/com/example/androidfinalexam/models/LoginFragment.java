package com.example.androidfinalexam.models;

import android.content.Intent;
import android.os.Bundle;
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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class LoginFragment extends Fragment {

    private View view;
    private EditText edtEmail, edtPassword;
    private Button btnLogin;
    private TextView txtBack;
    private String url = UrlApi.login;

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

                        startActivity(intent);

                    }else {
                        Toast.makeText(getActivity(), "Thất bại", Toast.LENGTH_SHORT).show();
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



}
