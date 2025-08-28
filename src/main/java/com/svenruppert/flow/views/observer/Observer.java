package com.svenruppert.flow.views.observer;

public interface Observer<T> {
  void onUpdate(T value);
}