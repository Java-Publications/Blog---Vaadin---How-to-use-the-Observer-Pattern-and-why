package com.svenruppert.flow.views.observer;

import com.svenruppert.dependencies.core.logger.HasLogger;

import java.time.Instant;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;

import static java.util.concurrent.TimeUnit.SECONDS;

// Externer Produzent, der periodisch Nachrichten erzeugt
public class MessageProducer {
  private static ScheduledExecutorService scheduler;

  private MessageProducer() { }

  static void start(MessageService service) {
    scheduler = Executors.newSingleThreadScheduledExecutor();
    scheduler.scheduleAtFixedRate(
        new CronJob(service),
        0, 2, SECONDS);
  }

  static void stop() {
    if (scheduler != null) {
      scheduler.shutdownNow();
    }
  }

  public record CronJob(MessageService service)
      implements Runnable, HasLogger {
    @Override
    public void run() {
      logger().info("Next message will be sent..");
      service.newMessage("Tick @ " + Instant.now());
    }
  }
}