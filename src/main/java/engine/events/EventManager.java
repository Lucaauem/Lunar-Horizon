package engine.events;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.function.Consumer;

public class EventManager {
  private static EventManager instance;

  private final HashMap<Class<? extends Event>, ArrayList<Consumer<? extends Event>>> subscriptions;

  private EventManager() {
    this.subscriptions = new HashMap<>();
  }

  public static EventManager getInstance() {
    if (instance == null) {
      instance = new EventManager();
    }
    return instance;
  }

  @SuppressWarnings("unchecked")
  public <T extends Event> void publish(T event) {
    Class<? extends Event> type = event.getClass();

    if (!this.subscriptions.containsKey(type)) {
      return;
    }

    for (Consumer<? extends Event> func : subscriptions.get(type)) {
      ((Consumer<T>) func).accept(event);
    }
  }

  public <T extends Event> void subscribe(Class<T> event, Consumer<T> func) {
    this.subscriptions.putIfAbsent(event, new ArrayList<>());
    this.subscriptions.get(event).add(func);
  }
}
