package engine.objects.entities;

public interface EntityTemplate {
  Entity build(EntityFactory factory, String parameter);
}
