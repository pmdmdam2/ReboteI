package com.example.rebote;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.animation.TimeInterpolator;
import android.graphics.Point;
import android.hardware.display.DisplayManager;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.view.animation.Animation;

/**
 * Produce un número determinado de rebotes de la vista durante un tiempo indicado
 */
public class BounceAnimation extends Animation {

    private float bounceDistance;
    private int numOfBounces, bounceCount = 0;
    private TimeInterpolator interpolator;
    private long duration;
    private AnimationListener listener;
    private View view;

    /**
     * Constructor especializado para animar la vista indicada
     * @param view La vista sobre la que se generará la animación.
     */
    public BounceAnimation(View view) {
        this.view = view;
        numOfBounces = 10;
        interpolator = new AccelerateDecelerateInterpolator();
        duration = 2000;
        listener = null;
    }

    /**
     * Inicia la animación
     */
    public void animate() {
        //se calcula la duración de un rebote
        long singleBounceDuration = duration / numOfBounces / 4; //4 traslaciones
        //se corrige la duración del rebote en caso de obtener una duración 0
        if (singleBounceDuration == 0)
            singleBounceDuration = 1;
        //se inicializa el coreógrafo
        final AnimatorSet bounceAnim = new AnimatorSet();
        //se asignan las animaciones al coreógrafo, 4 traslaciones secunciales
        bounceAnim.playSequentially(ObjectAnimator.ofFloat(view,
                View.TRANSLATION_Y, bounceDistance), ObjectAnimator.ofFloat(
                view, View.TRANSLATION_Y, -bounceDistance), ObjectAnimator
                        .ofFloat(view, View.TRANSLATION_Y, bounceDistance),
                ObjectAnimator.ofFloat(view, View.TRANSLATION_Y, 0));
        //se asigna el interpolador de movimiento
        bounceAnim.setInterpolator(interpolator);
        //se asigna la duración
        bounceAnim.setDuration(singleBounceDuration);
        //se obtiene el contenedor padre de la vista objeto de la animación
        ViewGroup parentView = (ViewGroup) view.getParent(),
                rootView = (ViewGroup) view
                .getRootView();

        while (!parentView.equals(rootView)) {
            parentView.setClipChildren(false);
            parentView = (ViewGroup) parentView.getParent();
        }

        //se liberan las vistas del contenedor para que se puedan mover libremente
        rootView.setClipChildren(false);

        //suscribe la animación a la interfaz de evento del coreógrafo para saber qué hacer
        //en distintos momentos de la animación.
        bounceAnim.addListener(new AnimatorListenerAdapter() {
            //control de parada de la animación, cuando se realicen todos los rebotes
            @Override
            public void onAnimationEnd(Animator animation) {
                bounceCount++;
                if (bounceCount == numOfBounces) {
                    if (getListener() != null) {
                        getListener().onAnimationEnd(BounceAnimation.this);
                    }
                } else {
                    bounceAnim.start();
                }
            }
        });
        bounceAnim.start();
    }

    /**
     * Devuelve la distancia máxima del rebote
     * @return float Distancia máxima del rebote
     */
    public float getBounceDistance() {
        return bounceDistance;
    }

    /**
     * Asigna la distancia máxima del rebote
     * @param bounceDistance Distancia del rebote
     */
    public void setBounceDistance(float bounceDistance) {
        this.bounceDistance = bounceDistance;
    }

    /**
     * Devuelve el número de rebotes
     * @return Número de rebotes
     */
    public int getNumOfBounces() {
        return numOfBounces;
    }

    /**
     * Asigna el número de rebotes
     * @param numOfBounces Número de rebotes
     */
    public void setNumOfBounces(int numOfBounces) {
        this.numOfBounces = numOfBounces;
    }

    /**
     * Asigna un interpolador
     * @param interpolator Interpolador para la animación
     */
    public void setInterpolator(TimeInterpolator interpolator) {
        this.interpolator = interpolator;
    }

    /**
     * Devuleve la duración de la animación
     * @return long Duración de la animación
     */
    public long getDuration() {
        return duration;
    }

    /**
     * Asigna la duración de la animación
     * @param duration Asigna la duración de la animación
     */
    public void setDuration(long duration) {
        this.duration = duration;

    }

    /**
     * Devuleve el oyente de eventos de la animación
     * @return AnimationListener Es el oyente de evento de la animación
     */
    public AnimationListener getListener() {
        return listener;
    }

    /**
     * Asigna el oyente de evento de la a
     * @param listener Oyente de evento de la animación
     */
    public void setListener(AnimationListener listener) {
        this.listener = listener;
    }

}
