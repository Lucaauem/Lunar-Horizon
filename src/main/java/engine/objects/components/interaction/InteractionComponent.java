package engine.objects.components.interaction;

import engine.objects.components.interaction.interactions.DialogueInteraction;
import engine.objects.components.interaction.interactions.FightInteraction;
import engine.objects.components.interaction.interactions.Interaction;
import engine.objects.core.Component;
import org.json.JSONArray;
import org.json.JSONObject;

public class InteractionComponent extends Component {
  private static final String DIALOGUE_SOURCE = "npc/talker";

  private final InteractionSequence sequence;

  public InteractionComponent(JSONArray interactionSequence) {
    this.sequence = new InteractionSequence();

    for (Object interaction : interactionSequence) {
      Interaction interactionInstance = createInteraction((JSONObject) interaction);

      if (interactionInstance == null) {
        System.err.println("WRONG INTERACTION TYPE: \"" + ((JSONObject) interaction).getString("type") + "\"");
        continue;
      }

      this.sequence.addInteraction(interactionInstance);
    }
  }

  private static Interaction createInteraction(JSONObject interaction) {
    JSONObject parameters = interaction.getJSONObject("parameters");

    return switch (interaction.getString("type")) {
      case "dialogue" -> new DialogueInteraction(DIALOGUE_SOURCE, parameters.getString("text"));
      case "fight" -> new FightInteraction(parameters.getString("fightId"));
      default -> null;
    };
  }

  public void onInteract() {
    this.sequence.startSequence();
  }
}
