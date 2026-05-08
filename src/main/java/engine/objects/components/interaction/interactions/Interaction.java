package engine.objects.components.interaction.interactions;

import engine.objects.components.interaction.InteractionSequence;

public abstract class Interaction {
  protected InteractionSequence currentSequence;

  public void setSequence(InteractionSequence sequence) {
    this.currentSequence = sequence;
  }

  protected void onInteractionEnd() {
    this.currentSequence.next();
  }

  public abstract void onInteract();
}
