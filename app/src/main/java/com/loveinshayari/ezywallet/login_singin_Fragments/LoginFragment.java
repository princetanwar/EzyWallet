package com.loveinshayari.ezywallet.login_singin_Fragments;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
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
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loveinshayari.ezywallet.R;
import com.loveinshayari.ezywallet.activitys.Main2Activity;

import java.util.HashMap;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class LoginFragment extends Fragment implements View.OnClickListener {


    private NavController navController;
    private TextView singup;
    private Button login;


    EditText ed_email, ed_password;

    String str_email, str_password;

    String url = "https://princrtanwar.000webhostapp.com/testlogin.php";

    public LoginFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_login, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        navController = Navigation.findNavController(view);
        singup = view.findViewById(R.id.txtsignup);
        login = view.findViewById(R.id.btn_login);
        ed_email = view.findViewById(R.id.ed_email);
        ed_password = view.findViewById(R.id.ed_password);


        singup.setOnClickListener(this);
        login.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.txtsignup:
                navController.navigate(R.id.action_loginFragment_to_singupFragment);
                break;
            case R.id.btn_login:
                login();
                //    startActivity(new Intent(getContext(),Main2Activity.class));
                break;


        }
    }

    private void login() {

        final ProgressDialog progressDialog = new ProgressDialog(getContext());
        progressDialog.setMessage("Please wail...");
        progressDialog.setCancelable(false);

        if (ed_email.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
        } else if (ed_password.getText().toString().equals("")) {
            Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
        } else {

            progressDialog.show();


            str_email = ed_email.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();

            StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {

                    progressDialog.dismiss();
                    Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();
                    ed_email.setText("");
                    ed_password.setText("");
                    if (response.equalsIgnoreCase("logged in successfully")) {
                        store();
                        getActivity().finish();
                        startActivity(new Intent(getContext(), Main2Activity.class));
                    }

                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {

                    progressDialog.dismiss();

                    Toast.makeText(getContext(), error.getMessage().toString(), Toast.LENGTH_SHORT).show();

                }
            }) {
                @Override
                protected Map<String, String> getParams() throws AuthFailureError {
                    Map<String, String> params = new HashMap<String, String>();

                    params.put("email", str_email);
                    params.put("password", str_password);
                    return params;
                }
            };

            RequestQueue requestQueue = Volley.newRequestQueue(getContext());

            requestQueue.add(request);

        }

    }

    private void store() {
        SharedPreferences preferences = getActivity().getSharedPreferences("com.loveinshayari.ezywallet_login_status",Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString("login_status","on");
        editor.apply();
    }
}

