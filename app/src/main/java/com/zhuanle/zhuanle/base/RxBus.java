package com.zhuanle.zhuanle.base;

import androidx.annotation.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

import io.reactivex.Observable;
import io.reactivex.subjects.PublishSubject;
import io.reactivex.subjects.Subject;

public class RxBus {
    private ConcurrentHashMap<Object, List<Subject>> maps = new ConcurrentHashMap<>();
    private static RxBus instance;


    private RxBus() {
    }
    private static class RxBusHolder{
        private static RxBus instance = new RxBus();
    }
    public static   RxBus getInstance() {
        return RxBusHolder.instance;
    }

    @SuppressWarnings("unchecked")
    public <T> Observable<T> register(@NonNull Object tag) {
        List<Subject> subjects = maps.get(tag);//如果maps没有这个chlass就添加进maps
        if (subjects == null) {
            subjects = new ArrayList<>();
            maps.put(tag, subjects);
        }
        Subject<T> subject = PublishSubject.<T>create();
        subjects.add(subject);
        return subject;
    }

    @SuppressWarnings("unchecked")
    public void unregister(@NonNull Object tag, @NonNull Observable observable) {
        List<Subject> subjects = maps.get(tag);
        if (subjects != null) {
            subjects.remove((Subject) observable);
            if (subjects.isEmpty()) {
                maps.remove(tag);
            }
        }
    }

    @SuppressWarnings("unchecked")
    public void post(@NonNull Object o) {
        post(o.getClass().getSimpleName(), o);
    }

    @SuppressWarnings("unchecked")
    public void post(@NonNull Object tag, @NonNull Object o) { //通知actitivy
        List<Subject> subjects = maps.get(tag);
        if (subjects != null && !subjects.isEmpty()) {
            for (Subject s : subjects) {
                s.onNext(o);
            }
        }
    }

}

