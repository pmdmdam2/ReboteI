package com.example.rebote;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Point;
import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ImageView;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ImageView ivPelota = findViewById(R.id.ivPelota);
        //se obtiene el alto de la imagen de la pelota en pixels
        int altoImagen = ivPelota.getDrawable().getIntrinsicHeight();

        //se obtiene el alto de la pantalla
        Point punto = new Point();
        getWindowManager().getDefaultDisplay().getSize(punto);

        //se construye la animación
        BounceAnimation bounceAnimation = new BounceAnimation(ivPelota);
        //se asigna la distancia del rebote
        bounceAnimation.setBounceDistance(punto.y - (altoImagen/2));
        //se lanza la animación
        bounceAnimation.animate();
    }
}
