package engine.objects.components.interaction.interactions;

import engine.core.global.Global;
import engine.scenes.SceneManager;

public class StoryFlagInteraction extends Interaction {
  private final String storyFlag;

  public StoryFlagInteraction(String storyFlag) {
    this.storyFlag = storyFlag;
  }

  @Override
  public void onInteract() {
    Global.setStoryFlag(storyFlag);
    SceneManager.getInstance().getCurrentScene().reload();
  }
}
