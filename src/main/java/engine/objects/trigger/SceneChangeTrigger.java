package engine.objects.trigger;

public class SceneChangeTrigger extends Trigger {
	@Override
	public void trigger() {
		System.out.println("SceneChangeTrigger triggered: " + this.parameter);
	}
}
