package game.objects.trigger;

import engine.objects.trigger.Trigger;
import game.shop.ShopEngine;

public class ShopTrigger extends Trigger {
  public void trigger() {
    new ShopEngine((String) this.getParameter("shopId"));
  }
}
