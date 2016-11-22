package com.ihtiyar.reactiveapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.functions.Action1;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.textView)
    TextView textView;
    @BindView(R.id.button)
    Button button;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final Observable<String> observable = Observable.just("Hello world");

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                    observable.subscribe(new Action1<String>() {
                        @Override
                        public void call(String s) {
                            textView.setText(s);
                        }
                    });
            }
        });


    }
}
