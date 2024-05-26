package com.example.newww;

import android.os.Bundle;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

public class BloomActivity extends AppCompatActivity {

    private TextView bloomInfo, allergyInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bloom);

        bloomInfo = findViewById(R.id.tvBloomInfo);
        allergyInfo = findViewById(R.id.tvAllergyInfo);

        // 개화시기별 꽃 정보
        String bloomInformation = "봄: 벚꽃, 개나리, 진달래\n여름: 장미, 백합, 해바라기\n가을: 국화, 코스모스, 억새\n겨울: 동백, 매화";
        // 알레르기 주의 메시지
        String allergyInformation = "봄: 꽃가루 알레르기 주의\n여름: 풀 알레르기 주의\n가을: 진드기 알레르기 주의\n겨울: 건조한 공기로 인한 피부 알레르기 주의";

        bloomInfo.setText(bloomInformation);
        allergyInfo.setText(allergyInformation);
    }
}
