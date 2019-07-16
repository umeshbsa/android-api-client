package com.app.apiclient.ui;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Toast;

import com.app.apiclient.R;
import com.app.apiclient.model.apimodel.output.User;
import com.app.apiclient.model.base.CommonApiResponse;
import com.app.apiclient.model.base.Errors;
import com.app.apiclient.network.APICallHandler;
import com.app.apiclient.network.APICallManager;
import com.app.apiclient.network.APIType;

import java.io.File;

public class MainActivity extends AppCompatActivity implements View.OnClickListener, APICallHandler {


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        findViewById(R.id.btn_post_method).setOnClickListener(this);
        findViewById(R.id.btn_get_method).setOnClickListener(this);
        findViewById(R.id.btn_get_method_query).setOnClickListener(this);
        findViewById(R.id.btn_path_method).setOnClickListener(this);
        findViewById(R.id.btn_image_upload).setOnClickListener(this);
        findViewById(R.id.btn_post_method).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_post_method:
                callPostAPI();
                break;

            case R.id.btn_get_method:
                callGetMethod();
                break;

            case R.id.btn_get_method_query:
                callGetQueryMethod();
                break;

            case R.id.btn_path_method:
                getPath();
                break;

            case R.id.btn_image_upload:
                callUploadProfile(null);
                break;

            case R.id.btn_delete_method:
                break;
        }
    }


    private void callPostAPI() {
        APICallManager manager = new APICallManager(APIType.POST, this);
        manager.postMethod("email", "password", "deviceid");
    }

    private void callGetMethod() {
        APICallManager manager = new APICallManager(APIType.GET, this);
        manager.getMethod();
    }


    private void callGetQueryMethod() {
        APICallManager manager = new APICallManager(APIType.GET_QUERY, this);
        manager.getMethodWithQuery("userid", 13123123123l);
    }

    private void getPath() {
        APICallManager manager = new APICallManager(APIType.PATH, this);
        manager.getPath("userid");
    }

    private void callUploadProfile(File profileImageFile) {
        APICallManager manager = new APICallManager(APIType.UPLOAD_IMAGE, this);
        manager.uploadProfile(profileImageFile);
    }

    @Override
    public void onSuccess(APIType apiType, Object response) {

        switch (apiType) {
            case POST:
                // Get user data
                User user = (User) response;
                break;

            case GET:
                // Get user data
                CommonApiResponse commonApiResponse = (CommonApiResponse) response;
                break;

            case GET_QUERY:
                // Get user data
                commonApiResponse = (CommonApiResponse) response;
                break;

            case PATH:
                commonApiResponse = (CommonApiResponse) response;
                break;

            case UPLOAD_IMAGE:
                commonApiResponse = (CommonApiResponse) response;
                break;
        }
    }

    @Override
    public void onFailure(APIType apiType, int code, Errors errors) {
        Toast.makeText(MainActivity.this, errors.errorMessage, Toast.LENGTH_LONG).show();
    }
}
