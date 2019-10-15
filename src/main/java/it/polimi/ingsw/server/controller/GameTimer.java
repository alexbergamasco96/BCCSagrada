package it.polimi.ingsw.server.controller;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

public class GameTimer implements Observable<TimerObserver>{

    private ArrayList<TimerObserver> observers;
    private java.util.Timer timer;
    private int delay;
    private TimerHeader header;
    private boolean active;

    public GameTimer(int delay, TimerHeader header){
        observers = new ArrayList<TimerObserver>();
        this.delay=delay;
        this.header = header;
        active=true;
        timer = new java.util.Timer();
        startTimer();
    }

    public void startTimer(){
        timer.schedule(new TimerTask() {
            @Override
            public void run() {
                notifyTimerFinished();
            }
        }, delay);
    }

    @Override
    public void addObserver(TimerObserver observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(TimerObserver observer) {
        observers.remove(observer);
    }

    public void setNotActive(){
        active=false;
    }

    /**
     * notifies GameController on finish
     */

    public void notifyTimerFinished(){
        if(active) {
            for (TimerObserver obs : observers) {
                obs.onTimerFinished(header.toString());
            }
        }
    }


}
