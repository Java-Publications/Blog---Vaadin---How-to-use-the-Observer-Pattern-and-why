package com.svenruppert.flow.views.observer;

import com.svenruppert.dependencies.core.logger.HasLogger;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

// Domänen-Service
public class MessageService
    implements Subject<String> , HasLogger {
  private final List<Observer<String>> observers = new CopyOnWriteArrayList<>();

  @Override
  public void addObserver(Observer<String> o) {
    logger().info("New Observer added: {}", o);
    observers.add(o);
  }

  @Override
  public void removeObserver(Observer<String> o) {
    logger().info("Observer removed: {}", o);
    observers.remove(o);
  }

  @Override
  public void notifyObservers(String value) {
    logger().info("Notifying {} observers with the value {} ", observers.size(), value);
    observers.forEach(o -> o.onUpdate(value));
  }

  public void newMessage(String msg) {
    notifyObservers(msg);
  }
}