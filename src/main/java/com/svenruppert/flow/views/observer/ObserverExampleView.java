package com.svenruppert.flow.views.observer;

import com.svenruppert.dependencies.core.logger.HasLogger;
import com.svenruppert.flow.MainLayout;
import com.vaadin.flow.component.AttachEvent;
import com.vaadin.flow.component.DetachEvent;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.router.Route;


// Vaadin-View, die nur Observer ist
@Route(value = "observer-example", layout = MainLayout.class)
public class ObserverExampleView
    extends VerticalLayout
    implements Observer<String>, HasLogger {

  private final Div log = new Div();
  private final MessageService service = ApplicationBootstrap.messageService();

  public ObserverExampleView() {
    //service.addObserver(this);
    add(
        new Div("Hier kommen die Logmeldungen... - start"),
        log,
        new Div("Hier kommen die Logmeldungen... - stop"));
  }

  @Override
  public void onAttach(AttachEvent attachEvent) {
    // Registrierung erst bei aktiver UI; vermeidet tote Observer und Duplikate
    service.addObserver(this);
  }

  @Override
  public void onDetach(DetachEvent detachEvent) {
    // Wichtig: Abmelden, damit keine Beobachter-Leichen entstehen
    service.removeObserver(this);
  }

  @Override
  public void onUpdate(String value) {
    logger().info("Neue Nachricht via onUpdate: {}", value);
    getUI()
        .ifPresentOrElse(
            ui -> {
              logger().info("UI vorhanden, update log");
              ui.access(() -> log.setText("Neue Nachricht: " + value));
            },
            () -> logger().warn("getUI() returned empty Optional"));

  }
}