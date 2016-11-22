package com.ihtiyar.reactiveapp;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.ihtiyar.reactiveapp.model.User;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import rx.Observable;
import rx.Subscriber;
import rx.functions.Action1;
import rx.functions.Func1;

public class MainActivity extends AppCompatActivity {

    @BindView(R.id.tMain)
    TextView tMain;
    @BindView(R.id.btn_do_subscribe)
    Button button;

    @BindView(R.id.radio_active)
    RadioGroup radioGroup;
    @BindView(R.id.radio_basic)
    RadioButton radioBasic;
    @BindView(R.id.radio_map)
    RadioButton radioMap;
    @BindView(R.id.radio_more_map)
    RadioButton radioMoreMap;
    @BindView(R.id.custom_data)
    RadioButton radioCustomData;

    private User user;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        final Observable<String> myObservable =
                Observable.just("Hello, world!");
        final Observable<Integer> moreObservable =
                Observable.just(1, 2, 3, 4, 5);

        user = new User("rosid", "ocittwo@gmail.com");
        final Observable<User> userObservable = Observable.just(user);

        final Observable<List<User>> listObservable = Observable.create(new Observable.OnSubscribe<List<User>>() {
            @Override
            public void call(Subscriber<? super List<User>> subscriber) {
                for (int i = 0; i < 5; i++) {
                    List<User> data = new ArrayList<User>();
                    data.add(new User("User" + Integer.toString(i), "email@mail.com"));
                    subscriber.onNext(data);
                }
            }
        });

        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int id = radioGroup.getCheckedRadioButtonId();
                switch (id){
                    case R.id.radio_basic:
                        myObservable.subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                tMain.setText(s);
                            }
                        });
                        break;
                    case R.id.radio_map:
                        userObservable.map(new Func1<User, String>() {
                            @Override
                            public String call(User user) {
                                return "Nama : " + user.name + "\n" + "Email : " + user.email;
                            }
                        }).subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                tMain.setText(s);
                            }
                        });
                        break;
                    case R.id.radio_more_map:
                        final StringBuilder stringBuilder = new StringBuilder();
                        moreObservable.map(new Func1<Integer, Integer>() {
                            @Override
                            public Integer call(Integer integer) {
                                return integer + 1;
                            }
                        }).map(new Func1<Integer, String>() {
                            @Override
                            public String call(Integer integer) {
                                int origin = integer - 1;
                                stringBuilder.append("Angka " + origin + " di tambah 1 = ");
                                stringBuilder.append(integer + "\n");
                                return stringBuilder.toString();
                            }
                        }).subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                tMain.setText(s);
                            }
                        });
                        break;
                    case R.id.custom_data:
                        final StringBuilder sb = new StringBuilder();
                        listObservable.map(new Func1<List<User>, String>() {
                            @Override
                            public String call(List<User> users) {
                                for (int i = 0; i < users.size(); i++) {
                                    sb.append("Nama : " + users.get(i).name + "\n" + "Email : " + users.get(i).email + "\n");
                                }
                                return sb.toString();
                            }
                        }).subscribe(new Action1<String>() {
                            @Override
                            public void call(String s) {
                                tMain.setText(s);
                            }
                        });
                        break;
                }

            }
        });


    }
}
