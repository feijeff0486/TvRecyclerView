package com.gogh.rxretrofit;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.gogh.okrxretrofit.HttpClient;
import com.gogh.okrxretrofit.component.DaggerHttpComponent;
import com.gogh.okrxretrofit.conf.TimeOut;
import com.gogh.okrxretrofit.http.HttpModule;

import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        HttpModule httpModule = new HttpModule.Builder()
                .baseUrl("")
                .factory(GsonConverterFactory.create())
                .timeOut(new TimeOut())
                .build();
        DaggerHttpComponent.builder().httpModule(httpModule).build().inject(HttpClient.newInstance());

        /*RequestApi requestApi = HttpClient.newInstance().getRequestApi(RequestApi.class);
        Request.newInstance().request(requestApi.getEntity(), new OnResponseListener<Entity>() {
            @Override
            public void onCompleted() {

            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onNext(Entity o) {

            }
        });*/

    }

}
