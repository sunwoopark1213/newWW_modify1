package com.example.newww;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.appcompat.app.AppCompatActivity;

public class RecommendActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.recommend);

        TextView recommendationTitle = findViewById(R.id.recommendationTitle);
        ImageView outfitImage = findViewById(R.id.outfitImage);
        TextView outfitDescription = findViewById(R.id.outfitDescription);

        Intent intent = getIntent();
        float currentTemp = intent.getFloatExtra("CURRENT_TEMP", 20);

        if (currentTemp < 0) {
            outfitImage.setImageResource(R.drawable.outfit_winter);
            outfitDescription.setText("매우 춥습니다. 두꺼운 코트, 장갑, 목도리를 착용하세요.");
        } else if (currentTemp < 10) {
            outfitImage.setImageResource(R.drawable.outfit_fall);
            outfitDescription.setText("쌀쌀합니다. 따뜻한 재킷을 입고 겹쳐 입는 것을 고려하세요.");
        } else if (currentTemp < 20) {
            outfitImage.setImageResource(R.drawable.outfit_spring);
            outfitDescription.setText("시원합니다. 가벼운 재킷이나 스웨터가 적당합니다.");
        } else if (currentTemp < 30) {
            outfitImage.setImageResource(R.drawable.outfit_summer);
            outfitDescription.setText("따뜻합니다. 가볍고 통기성 좋은 옷을 입으세요.");
        } else {
            outfitImage.setImageResource(R.drawable.outfit_hot);
            outfitDescription.setText("매우 덥습니다. 반바지와 티셔츠로 시원하게 지내세요.");
        }
    }
}
