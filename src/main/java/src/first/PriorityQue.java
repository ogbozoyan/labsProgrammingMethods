package src.first;

import lombok.Data;

import java.util.ArrayList;

/**
 * @author ogbozoyan
 * @date 05.03.2023
 */
@Data
/*
x.compareTo(y) if y<x return >0
x.compareTo(y) if y>x return <0
x.compareTo(y) if y==x return 0
 */
public class PriorityQue<T extends Comparable<T>> implements PriorityQueue<T> {

    private Boolean MIN = false;
    private ArrayList<T> list;

    public PriorityQue() {
        this.list = new ArrayList<>();
        this.list.add(null);
    }

    public PriorityQue(Boolean MIN) {
        this();
        this.MIN = true;
    }

    @Override
    public void add(T t) {
        this.list.add(t);
        swim();
    }

    private void swim() {
        Integer n = this.list.size() - 1;
        while (n > 1 && check(this.list.get(n / 2), this.list.get(n))) {
            swap(n / 2, n);
            n = n / 2;
        }
    }

    @Override
    public T del() {
        if (!isEmpty()) {
            T t = this.list.get(1);
            swap(1, this.list.size() - 1);
            this.list.remove(this.list.size() - 1);
            sink(1);
            return t;
        }
        return null;
    }

    @Override
    public T peek() {
        if (!isEmpty()) {
            return this.list.get(1);
        }
        return null;
    }

    private void sink(Integer n) {
        Integer j;
        while (n * 2 <= this.list.size() - 1) {
            j = n * 2;
            if (j < this.list.size() - 1 && check(this.list.get(j), this.list.get(j + 1)))
                j++;
            if (!check(this.list.get(n), this.list.get(j)))
                break;
            swap(j, n);
            n = j;
        }
    }

    @Override
    public Boolean isEmpty() {
        return list.size() <= 1;
    }

    @Override
    public Integer size() {
        return this.list.size() - 1;
    }

    private Boolean check(T t1, T t2) {
        if (MIN) {
            return t1.compareTo(t2) > 0;
        } else {
            return t1.compareTo(t2) < 0;
        }
    }

    private void swap(Integer i, Integer j) {
        T swap = this.list.get(i);
        this.list.set(i, this.list.get(j));
        this.list.set(j, swap);
    }
}
