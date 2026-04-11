package engine.objects.components.interaction;

import engine.objects.core.Component;

public class InteractionComponent extends Component implements Interactable {
  private final Interactable interactAtion;

  public InteractionComponent(Interactable action) {
    this.interactAtion = action;
  }

  @Override
  public void onInteract() {
    this.interactAtion.onInteract();
  }
}
