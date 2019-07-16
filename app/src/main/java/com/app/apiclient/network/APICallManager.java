package com.app.apiclient.network;

import android.os.Handler;

import com.app.apiclient.App;
import com.app.apiclient.R;
import com.app.apiclient.model.base.BaseResponse;
import com.app.apiclient.model.base.Errors;

import java.io.File;
import java.net.SocketException;
import java.net.SocketTimeoutException;
import java.net.UnknownHostException;

import okhttp3.MediaType;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * All api method
 *
 * @param <T> model class
 */
public class APICallManager<T> implements Callback<BaseResponse<T>>, APIStatusCode {

    private Handler handler;
    private APIType mCallType;
    private APICallHandler mAPICallHandler;
    private Call mCall;
    private Runnable runnable;

    public APICallManager(APIType callType, APICallHandler callHandler) {
        this.mCallType = callType;
        this.mAPICallHandler = callHandler;

        if (runnable != null && handler != null) {
            handler.removeCallbacks(runnable);
        }

        if (handler == null) {
            handler = new Handler();
        }

        setRunnable();

        handler.postDelayed(runnable, 40000);
    }

    private void setRunnable() {
        runnable = new Runnable() {
            @Override
            public void run() {
                int errorCode = 0;
                String message = "Network has some problem. please try again...";
                Errors errors = new Errors();
                errors.errorMessage = message;
                if (mAPICallHandler != null) {
                    mAPICallHandler.onFailure(mCallType, errorCode, errors);
                    handler.removeCallbacks(this);
                }
            }
        };
    }

    @Override
    public void onResponse(Call<BaseResponse<T>> call, Response<BaseResponse<T>> response) {
        if (runnable != null && handler != null) {
            handler.removeCallbacks(runnable);
        }
        if (response.code() == OK || response.code() == CREATED || response.code() == NO_CONTENT) {
            if (mAPICallHandler != null) {
                BaseResponse<T> body = response.body();
                if (body.statusCode == 1 && body.responseData != null) {
                    mAPICallHandler.onSuccess(mCallType, body.responseData);
                } else {
                    mAPICallHandler.onFailure(mCallType, response.code(), body.error);
                }
            }
        } else if (response.code() == AUTHENTICATION_FAILED) {
            Errors errors = new Errors();
            String errorMessage = App.getInstance().getResources().getString(R.string.invalid_credentials);
            errors.errorMessage = errorMessage;

            if (mAPICallHandler != null) {
                mAPICallHandler.onFailure(mCallType, response.code(), errors);
            }
        } else if (response.code() == BAD_REQUEST) {
            Errors errors = new Errors();
            String errorMessage = App.getInstance().getResources().getString(R.string.bad_request);
            errors.errorMessage = errorMessage;
            mAPICallHandler.onFailure(mCallType, response.code(), errors);
        } else {
            Errors errors = new Errors();
            String errorMessage = App.getInstance().getResources().getString(R.string.error_something_wrong);
            errors.errorMessage = errorMessage;

            if (mAPICallHandler != null) {
                mAPICallHandler.onFailure(mCallType, response.code(), errors);
            }
        }
    }

    @Override
    public void onFailure(Call<BaseResponse<T>> call, Throwable throwable) {
        if (runnable != null && handler != null) {
            handler.removeCallbacks(runnable);
        }
        int errorCode = 0;
        String message = "";
        if ((throwable instanceof UnknownHostException || throwable instanceof SocketException || throwable instanceof SocketTimeoutException)) {
            message = App.getInstance().getResources().getString(R.string.error_something_wrong);
        } else {
            message = throwable.getMessage();
        }

        Errors errors = new Errors();
        errors.errorMessage = message;
        if (mAPICallHandler != null) {
            mAPICallHandler.onFailure(mCallType, errorCode, errors);
        }
    }

    ///////////////// Write all api method ////////////////////////

    public void postMethod(String email, String password, String deviceId) {
        IApiRequest mApiInterface = APIHandler.getApiInterface();
        mCall = mApiInterface.postMethodAPI(email, password, deviceId);
        if (mCall != null) {
            mCall.enqueue(this);
        }
    }

    public void getMethod() {
        IApiRequest mApiInterface = APIHandler.getApiInterface();
        mCall = mApiInterface.getMethodAPI();
        if (mCall != null) {
            mCall.enqueue(this);
        }
    }

    public void getMethodWithQuery(String userId, long startDate) {
        IApiRequest mApiInterface = APIHandler.getApiInterface();
        mCall = mApiInterface.getMethodWithQueryAPI(userId, startDate);
        if (mCall != null) {
            mCall.enqueue(this);
        }
    }

    public void getPath(String userId) {
        IApiRequest mApiInterface = APIHandler.getApiInterface();
        mCall = mApiInterface.getPathAPI(userId);
        if (mCall != null) {
            mCall.enqueue(this);
        }
    }


    public void uploadProfile(File profilePic) {
        RequestBody imageBody = null;
        if (profilePic != null) {
            imageBody = RequestBody.create(MediaType.parse("image/png"), profilePic);
        }
        IApiRequest mApiInterface = APIHandler.getApiInterface();
        mCall = mApiInterface.updateProfileAPI(imageBody);
        if (mCall != null) {
            mCall.enqueue(this);
        }
    }

}

