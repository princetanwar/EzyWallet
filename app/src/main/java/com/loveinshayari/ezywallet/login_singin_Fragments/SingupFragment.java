package com.loveinshayari.ezywallet.login_singin_Fragments;


import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.telephony.TelephonyManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.loveinshayari.ezywallet.R;

import java.util.HashMap;
import java.util.Map;

import static android.content.Context.TELEPHONY_SERVICE;


/**
 * A simple {@link Fragment} subclass.
 */
public class SingupFragment extends Fragment implements View.OnClickListener {


    EditText ed_email, ed_password;
    private Button register;
    String str_email, str_password,str_imei;
    String url = "https://princrtanwar.000webhostapp.com/test.php";

    TelephonyManager telephonyManager;

    public SingupFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_singup, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        ed_email = view.findViewById(R.id.ed_email);
        ed_password = view.findViewById(R.id.ed_password);
        register = view.findViewById(R.id.btn_register);

        register.setOnClickListener(this);

    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_register:
                register();
                break;
        }
    }

    private void register() {

        if (ContextCompat.checkSelfPermission(getContext(), Manifest.permission.READ_PHONE_STATE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(getActivity(), new String[]{Manifest.permission.READ_SMS, Manifest.permission.READ_PHONE_STATE}, 101);


        } else {
            telephonyManager = (TelephonyManager) getActivity().getSystemService(getContext().TELEPHONY_SERVICE);

            TelephonyManager telephonyManager = (TelephonyManager) getActivity().getSystemService(Context.TELEPHONY_SERVICE);
            telephonyManager.getDeviceId();

            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {


                String dt = telephonyManager.getImei();

                str_imei = dt;


            final ProgressDialog progressDialog = new ProgressDialog(getContext());
            progressDialog.setMessage("Please Wait...");
            progressDialog.setCancelable(false);


            str_email = ed_email.getText().toString().trim();
            str_password = ed_password.getText().toString().trim();

            if (str_email.equals("")) {
                Toast.makeText(getContext(), "Enter Email", Toast.LENGTH_SHORT).show();
            } else if (str_password.equals("")) {
                Toast.makeText(getContext(), "Enter Password", Toast.LENGTH_SHORT).show();
            } else {

                progressDialog.show();

                StringRequest request = new StringRequest(Request.Method.POST, url, new Response.Listener<String>() {
                    @Override
                    public void onResponse(String response) {
                        progressDialog.dismiss();
                        Toast.makeText(getContext(), response, Toast.LENGTH_SHORT).show();

                        ed_email.setText("");
                        ed_password.setText("");

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
                        params.put("nem", str_imei);
                        return params;
                    }
                };

                RequestQueue requestQueue = Volley.newRequestQueue(getContext());

                requestQueue.add(request);

            }

        }
        }
    }

}

