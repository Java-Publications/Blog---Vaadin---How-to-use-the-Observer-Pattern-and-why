# Observer Pattern with Vaadin Flow – Examples

This repository contains accompanying source code for a technical article on the use of the Observer Pattern in combination with Vaadin Flow. The aim is to highlight the differences between the classical pattern and the Vaadin-specific implementation, as well as to demonstrate practical use cases.

# The examples illustrate:

## Classical Observer Pattern
Standalone implementations of Subject and Observer demonstrate the fundamental principle, decoupled event propagation, and testability outside a UI.

## Observer in Vaadin Flow
Use of Vaadin’s built-in mechanisms (ValueChangeListener, Binder) to observe UI state changes and integration with the component lifecycle (onAttach, onDetach).

## Combination of Observer Pattern and Vaadin Flow
A domain service distributes events to registered observers. A Vaadin view registers itself as an observer and updates its UI safely via UI.access(…).
This is complemented by an external MessageProducer which generates messages in separate threads and pushes them into the UI.

## Best Practices and Pitfalls
Recommendations regarding lifecycle management, thread safety, separation of domain and UI, logging, and memory discipline.

## Objective
The examples show how to achieve loose coupling and clean separation of concerns in modern web applications. They emphasise that the classical Observer Pattern remains valuable despite Vaadin Flow’s integrated mechanisms – particularly for domain-level events and external integrations.

## Usage
The examples are structured as standalone classes and can be executed directly within a Vaadin Flow application.
To run them, simply compile the application and open the respective routes in the browser (/observer-demo, /observer-example, …).