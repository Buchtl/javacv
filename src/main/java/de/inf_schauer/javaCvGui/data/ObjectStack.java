package de.inf_schauer.javaCvGui.data;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;

import de.inf_schauer.javaCvGui.interfaces.IObjectStack;

public class ObjectStack<E> implements IObjectStack<E> {

    ArrayList<E> elements;
    int pos;

    public ObjectStack() {
        init();
    }

    public void init() {
        this.elements = new ArrayList<>();
        int pos = 0;
    }

    @Override
    public E getLastElement() {
        if (!isEmpty()) {
            return getElementAt(getLastIndex());
        } else {
            return null;
        }
    }

    @Override
    public void removeLastElement() {
        if (!isEmpty()) {
            elements.remove(getLastIndex());
        }
    }

    @Override
    public void push(E object) {
        elements.add(object);
        this.setPosLast();
    }

    @Override
    public E pop() {
        E result = getLastElement();
        removeLastElement();
        this.setPosLast();
        return result;
    }

    @Override
    public E getElementAt(int pos) {
        if (isPosInRange(pos)) {
            return elements.get(pos);
        } else {
            return null;
        }
    }

    @Override
    public void removeElementAt(int pos) {
        if (isPosInRange(pos)) {
            elements.remove(pos);
        }
    }

    @Override
    public void removeBeginAt(int start) {
        int lastIdx = getLastIndex();
        if (start <= lastIdx && !isEmpty()) {
            for (int i = lastIdx; i >= start; i--) {
                elements.remove(i);
            }
        }
    }

    @Override
    public void removeElement(E element) {
        while (elements.contains(element)) {
            elements.remove(element);
        }
    }

    @Override
    public List<E> getElements(E element) {
        List<E> result = new ArrayList<>();
        for (E e : elements) {
            if (e.equals(element)) {
                result.add(e);
            }
        }
        return result;
    }

    @Override
    public void insertElement(int i, E element) {
        elements.add(i, element);
    }

    @Override
    public E getCurrentElement() {
        if (!isEmpty()) {
            return elements.get(pos);
        } else {
            return null;
        }
    }

    @Override
    public void setCurrentPosition(int pos) {
        if (pos < size()) {
            this.pos = pos;
        }
    }

    @Override
    public void removeFollowUps() {
        removeBeginAt(pos + 1);
    }

    @Override
    public E goForward() {
        int lastIdx = getLastIndex();
        if (isPosInRange(pos) && pos < lastIdx) {
            pos++;
            return getElementAt(pos);
        } else {
            return null;
        }
    }

    @Override
    public E goBack() {
        if (isPosInRange(pos) && !isEmpty() && !isStart()) {
            pos--;
            return getElementAt(pos);
        } else {
            return null;
        }
    }

    @Override
    public E getNext() {
        if (!isEmpty() && pos < getLastIndex()) {
            return elements.get(pos + 1);
        } else {
            return null;
        }
    }

    @Override
    public E getPrevious() {
        if (!isEmpty() && pos > 0) {
            return elements.get(pos - 1);
        } else {
            return null;
        }
    }

    @Override
    public int getLastIndex() {
        return elements.size() - 1;
    }

    @Override
    public boolean isEmpty() {
        return elements.size() < 1;
    }

    @Override
    public boolean isStart() {
        return pos == 0;
    }

    @Override
    public boolean isEnd() {
        return !isEmpty() && pos == getLastIndex();
    }

    @Override
    public int size() {
        return elements.size();
    }

    @Override
    public void clear() {
        elements.clear();
    }

    private void setPosLast() {
        pos = getLastIndex();
    }

    @Override
    public boolean add(E element) {
        removeBeginAt(pos + 1);
        push(element);
        return true;
    }

    @Override
    public void add(int index, E element) {
        // TODO Auto-generated method stub

    }

    @Override
    public boolean addAll(int index, Collection<? extends E> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean contains(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E get(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int indexOf(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public Iterator<E> iterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public int lastIndexOf(Object o) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public ListIterator<E> listIterator() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public ListIterator<E> listIterator(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean remove(Object o) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E remove(int index) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        // TODO Auto-generated method stub
        return false;
    }

    @Override
    public E set(int index, E element) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<E> subList(int fromIndex, int toIndex) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Object[] toArray() {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public <T> T[] toArray(T[] a) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        elements.addAll(c);
        return true;
    }

    @Override
    public void reset() {
        E tmp = getCurrentElement();
        init();
        add(tmp);
    }

    private boolean isPosInRange(int pos) {
        return !isEmpty() && pos >= 0 && pos <= getLastIndex();
    }

}
