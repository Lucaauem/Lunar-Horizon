package engine.objects.trigger;

import game.shop.ShopEngine;

public class ShopTrigger extends Trigger {
    @Override
    public void trigger() {
        // FIXME: Game crashed when entering
        ShopEngine shop = new ShopEngine(this.parameter);
    }
}
