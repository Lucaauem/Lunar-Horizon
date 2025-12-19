package de.luca.lunarhorizon.engine.objects.trigger;

public abstract class Trigger {
	protected String parameter;

	public abstract void trigger();

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}
}
