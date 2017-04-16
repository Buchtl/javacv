package de.inf_schauer.javaCvGui.interfaces;

import java.util.List;

public interface IObjectStack<E> extends List<E> {

    public E getLastElement();

    public void push(E object);

    public E pop();

    public E getElementAt(int pos);

    public void removeLastElement();

    public void removeElementAt(int pos);

    public void removeBeginAt(int start);

    public void removeElement(E element);

    public List<E> getElements(E element);

    public void insertElement(int i, E element);

    public E getCurrentElement();

    public void setCurrentPosition(int pos);

    public void removeFollowUps();

    public E goForward();

    public E goBack();

    public E getNext();

    public E getPrevious();

    public int getLastIndex();

    public boolean isEmpty();

    public boolean isStart();

    public boolean isEnd();

    public int size();

    public void clear();

    public void reset();

}
