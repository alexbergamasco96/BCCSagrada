package it.polimi.ingsw.server.controller;

import it.polimi.ingsw.client.view.ClientObserver;

public interface Observable<T> {

    public void addObserver(T observer);
    public void removeObserver(T observer);

}
