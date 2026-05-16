package engine.objects.components.interaction;

import engine.core.global.Global;
import engine.objects.components.interaction.interactions.DialogueInteraction;
import engine.objects.components.interaction.interactions.FightInteraction;
import engine.objects.components.interaction.interactions.Interaction;
import engine.objects.components.interaction.interactions.StoryFlagInteraction;
import engine.objects.core.Component;
import org.json.JSONArray;
import org.json.JSONObject;

public class InteractionComponent extends Component {
  private static final String DIALOGUE_SOURCE = "npc/talker";

  private final InteractionSequence sequence;

  public InteractionComponent(JSONArray interactionSequence) {
    this.sequence = new InteractionSequence();

    for (Object interaction : interactionSequence) {
      JSONObject jsonObject = (JSONObject) interaction;

      if (!jsonObject.has("preqFlags")) {
        Interaction interactionInstance = createInteraction(jsonObject);

        this.sequence.addInteraction(interactionInstance);
      } else if (Global.checkPreq(jsonObject.getJSONObject("preqFlags"))) {
        for (Object sequenceContent : jsonObject.getJSONArray("sequence")) {
          Interaction interactionInstance = createInteraction((JSONObject) sequenceContent);

          this.sequence.addInteraction(interactionInstance);
        }
      }
    }
  }

  private static Interaction createInteraction(JSONObject interactionData) {
    JSONObject parameters = interactionData.getJSONObject("parameters");

    Interaction interaction =  switch (interactionData.getString("type")) {
      case "dialogue" -> new DialogueInteraction(DIALOGUE_SOURCE, parameters.getString("text"));
      case "fight" -> new FightInteraction(parameters.getString("fightId"));
      case "storyFlag" -> new StoryFlagInteraction(parameters.getString("flag"));
      default -> null;
    };

    assert interaction != null;
    return interaction;
  }

  public void onInteract() {
    this.sequence.startSequence();
  }
}
