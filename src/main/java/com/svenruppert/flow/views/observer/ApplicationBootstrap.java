package com.svenruppert.flow.views.observer;

// Bootstrap: startet den Producer beim Hochfahren
final class ApplicationBootstrap {
  private static final MessageService SERVICE = new MessageService();

  static {
    MessageProducer.start(SERVICE);
  }

  private ApplicationBootstrap() {
  }

  static MessageService messageService() {
    return SERVICE;
  }
}