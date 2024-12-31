package com.simple.todoCheck;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;

import java.util.Calendar;

public class MainActivity extends AppCompatActivity {

    private Button checkDateButton;
    private SharedPreferences sharedPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // 버튼 초기화
        checkDateButton = findViewById(R.id.checkDateButton);

        // shared 초기화
        sharedPreferences = getSharedPreferences("AppPrefs", MODE_PRIVATE);

        // 초기 버튼 상태 설정
        updateButtonVisibility();

        // 버튼 클릭 리스너 설정
        checkDateButton.setOnClickListener(v -> {
            // 오늘 날짜 저장 (버튼 숨기기 처리)
            saveTodayAsLastHiddenDate();
            updateButtonVisibility();
        });

        sharedPreferences.edit().clear();
    }

    private void updateButtonVisibility() {
        Calendar calendar = Calendar.getInstance();
        int todayDayOfYear = calendar.get(Calendar.DAY_OF_YEAR); // 연도 내에서 오늘의 일수
        int todayDayOfMonth = calendar.get(Calendar.DAY_OF_MONTH); // 달의 오늘 날짜

        int lastHiddenDay = sharedPreferences.getInt("lastHiddenDay", -1);

        // 디버그용 로그
        System.out.println("Today (Day of Year): " + todayDayOfYear);
        System.out.println("Today (Day of Month): " + todayDayOfMonth);
        System.out.println("Last Hidden Day: " + lastHiddenDay);

        // 29-31일은 무조건 버튼 숨기기
        if (todayDayOfMonth >= 29 && todayDayOfMonth <= 31){
            checkDateButton.setVisibility(View.GONE);
            return;
        }

        if (lastHiddenDay == todayDayOfYear) {
            checkDateButton.setVisibility(View.GONE);
        } else {
            checkDateButton.setVisibility(View.VISIBLE);
        }
    }



    private void saveTodayAsLastHiddenDate(){
        // 현재 날짜 가져오기
        Calendar calendar = Calendar.getInstance();
        int today = calendar.get(Calendar.DAY_OF_YEAR); // 연도 내에서 오늘의 일수

        // shared에 저장
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putInt("lastHiddenDay", today);
        editor.apply();

        // 버튼 숨기기
        checkDateButton.setVisibility(View.GONE);
    }

}