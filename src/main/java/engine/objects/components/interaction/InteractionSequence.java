package engine.objects.components.interaction;

import engine.objects.components.interaction.interactions.Interaction;
import java.util.ArrayList;
import java.util.Stack;

public class InteractionSequence {
  private final ArrayList<Interaction> interactions;
  private final Stack<Interaction> currentSequence = new Stack<>();

  public InteractionSequence() {
    this.interactions = new ArrayList<>();
  }

  protected void startSequence() {
    this.currentSequence.clear();

    for (int i=this.interactions.size() - 1; i>=0; i--) {
      this.currentSequence.push(this.interactions.get(i));
    }

    this.next();
  }

  public void next() {
    if (!this.currentSequence.isEmpty()) {
      this.currentSequence.pop().onInteract();
    }
  }

  public void addInteraction(Interaction interaction) {
    interaction.setSequence(this);
    this.interactions.add(interaction);
  }
}
