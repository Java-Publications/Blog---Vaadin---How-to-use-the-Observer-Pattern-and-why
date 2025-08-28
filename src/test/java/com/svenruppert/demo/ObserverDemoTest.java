package com.svenruppert.demo;

import com.svenruppert.dependencies.core.logger.HasLogger;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class ObserverDemoTest {

  @Test
  void beobachter_erhaelt_updates_und_letzten_wert() {
    var sensor = new TemperaturSensor();
    var anzeigeA = new TemperaturAnzeige();
    var anzeigeB = new TemperaturAnzeige();

    sensor.addObserver(anzeigeA);
    sensor.addObserver(anzeigeB);

    sensor.setTemperatur(22);
    assertEquals(22, anzeigeA.lastObservedTemp);
    assertEquals(22, sensor.getTemperatur());

    sensor.setTemperatur(25);
    assertEquals(25, sensor.getTemperatur());
    assertEquals(25, anzeigeA.lastObservedTemp);
    assertEquals(25, anzeigeB.lastObservedTemp);

    sensor.removeObserver(anzeigeA);
    sensor.setTemperatur(21);
    assertEquals(21, sensor.getTemperatur());
    assertEquals(25, anzeigeA.lastObservedTemp);
    assertEquals(21, anzeigeB.lastObservedTemp);
  }

  /**
   * Minimalistische, eigene Observer-API (generisch, thread-sicher im Subject umgesetzt).
   */
  interface Observer<T> {
    void onUpdate(T value);
  }

  interface Subject<T> {
    void addObserver(Observer<T> o);
    void removeObserver(Observer<T> o);
    void notifyObservers(T value);
  }

  /**
   * Konkretes Subject: hält eine Temperatur und informiert Beobachter bei Änderungen.
   */
  static class TemperaturSensor
      implements Subject<Integer> {
    private final List<Observer<Integer>> observers = new CopyOnWriteArrayList<>();
    private int temperatur;

    @Override
    public void addObserver(Observer<Integer> o) { observers.add(o); }

    @Override
    public void removeObserver(Observer<Integer> o) { observers.remove(o); }

    @Override
    public void notifyObservers(Integer value) { observers.forEach(o -> o.onUpdate(value)); }

    public int getTemperatur() { return temperatur; }

    public void setTemperatur(int neueTemperatur) {
      this.temperatur = neueTemperatur;
      notifyObservers(temperatur);
    }
  }

  /**
   * Konkreter Observer: merkt sich den letzten Wert und loggt ihn.
   */
  static class TemperaturAnzeige
      implements Observer<Integer>, HasLogger {
    Integer lastObservedTemp;

    @Override
    public void onUpdate(Integer value) {
      lastObservedTemp = value;
      logger().info("Neue Temperatur: {}°C", value);
    }
  }
}