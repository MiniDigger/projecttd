package me.minidigger.projecttd.utils;

import me.minidigger.projecttd.components.PathComponent;

/**
 * Created by Martin on 30/04/2017.
 */
public class Pair<A, B> {
    public A a;
    public B b;

    public Pair(A a, B b) {
        this.a = a;
        this.b = b;
    }

    public static <A, B> Pair<A, B> of(A a, B b) {
        return new Pair<>(a, b);
    }
}
