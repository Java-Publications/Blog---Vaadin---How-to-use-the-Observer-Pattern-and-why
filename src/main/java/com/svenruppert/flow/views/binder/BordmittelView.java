package com.svenruppert.flow.views.binder;

import com.svenruppert.flow.MainLayout;
import com.vaadin.flow.component.button.Button;
import com.vaadin.flow.component.html.Div;
import com.vaadin.flow.component.orderedlayout.VerticalLayout;
import com.vaadin.flow.component.textfield.TextField;
import com.vaadin.flow.data.binder.Binder;
import com.vaadin.flow.router.Route;

@Route(value = "observer-bordmittel", layout = MainLayout.class)
public class BordmittelView
    extends VerticalLayout {
  public BordmittelView() {
    var name = new TextField("Name");
    var log = new Div();
    var speichern = new Button("Speichern");

    // (1) Komponenteninterner Observer: reagiert auf ValueChangeEvent
    name.addValueChangeListener(
        e -> log.setText("UI → UI: Name geändert zu '" + e.getValue() + "'"));

    // (2) Binder als bidirektionale Beobachtung zwischen UI und Modell
    var binder = new Binder<>(Person.class);
    var model = new Person();
    binder.forField(name).bind(Person::getName, Person::setName);
    binder.setBean(model);

    // (3) Aktion: liest aktuellen Modellzustand (vom Binder versorgt)
    speichern.addClickListener(
        e -> log.setText("Model → Action: gespeicherter Name = "
                         + model.getName()));
    add(name, speichern, log);
  }

  static class Person {
    private String name;

    public String getName() {
      return name;
    }

    public void setName(String name) {
      this.name = name;
    }
  }
}