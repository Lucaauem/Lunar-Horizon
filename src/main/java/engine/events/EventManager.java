package engine.events;

import java.util.ArrayList;
import java.util.HashMap;

public class EventManager {
  private static EventManager instance;

  private final HashMap<String, ArrayList<Runnable>> subscriptions;

  private EventManager() {
    this.subscriptions = new HashMap<>();
  }

  public static EventManager getInstance() {
    if (instance == null) {
      instance = new EventManager();
    }
    return instance;
  }

  public void publish(String event) {
    if (!this.subscriptions.containsKey(event)) {
      return;
    }

    for (Runnable func : this.subscriptions.get(event)) {
      func.run();
    }
  }

  public void subscribe(String event, Runnable func) {
    this.subscriptions.putIfAbsent(event, new ArrayList<>());
    this.subscriptions.get(event).add(func);
  }
}
