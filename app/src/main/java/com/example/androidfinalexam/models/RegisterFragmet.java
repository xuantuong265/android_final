package com.example.androidfinalexam.models;

import android.os.Bundle;
import android.util.Patterns;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

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

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class RegisterFragmet extends Fragment {

    private View view;
    private TextView txtHaveAccount;
    private EditText edtEmail, edtPassword, edtPassword2;
    private Button btnRegister;
    private  String url = UrlApi.register;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        view = inflater.inflate( R.layout.activity_register, container, false);

        // functions
        initView();
        setOnClick();

        return view;
    }

    private void setOnClick() {
        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                registerUser();
            }
        });

        txtHaveAccount.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framauth_id, new LoginFragment()).commit();
            }
        });

    }

    private void registerUser() {

        RequestQueue queue = Volley.newRequestQueue(getActivity());
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                try {
                    JSONObject jsonObject = new JSONObject(response);
                    if ( jsonObject.getBoolean("status") ){

                        //set null edt
                        edtEmail.setText("");
                        edtPassword.setText("");
                        edtPassword2.setText("");

                        getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.framauth_id, new LoginFragment()).commit();

                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
            }
        }){
            @Override
            protected Map<String, String> getParams() throws AuthFailureError {
                Map<String, String> parrams = new HashMap<>();

                // get data from edittext
                String email = edtEmail.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();
                String password2 = edtPassword2.getText().toString().trim();

                // check null
//                if (!Patterns.EMAIL_ADDRESS.matcher(email).matches()){
////                    edtEmail.setError("Invalid Email");
////                    edtEmail.setFocusable(true);
////                }else if (edtPassword.length() < 6){
////                    edtPassword.setError("Password length at least 6 characters");
////                    edtPassword.setFocusable(true);
////                }else {
//                    parrams.put("email", email);
//                    parrams.put("password", password);
////                }

                parrams.put("email", email);
                parrams.put("password", password);

                return parrams;
            }
        };
        queue.add(stringRequest);


    }

    private void initView() {
        btnRegister = (Button) view.findViewById(R.id.btnRegister);
        edtEmail = (EditText) view.findViewById(R.id.edtEmail);
        edtPassword = (EditText) view.findViewById(R.id.edtPassword);
        edtPassword2 = (EditText) view.findViewById(R.id.edtPassword2);
        txtHaveAccount = (TextView) view.findViewById(R.id.had_account_id);
    }


}
