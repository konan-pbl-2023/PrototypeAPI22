package com.example.prototypeapi22.game.entity.obstacle;

import com.example.prototypeapi22.R;
import com.example.prototypeapi22.game.Game;
import com.example.prototypeapi22.game.entity.Entity;
import com.example.prototypeapi22.game.entity.EntityPlayer;

public class EntityBird extends EntityObstacle {

    public EntityBird(int posX, int posY, int width, int height) {
        super(Game.getInstance().getResourceLoader().getResource(R.drawable.bird), posX, posY, width, height);
    }

    @Override
    public void onCollideTo(Entity entity) {
        if (entity instanceof EntityPlayer) {
            EntityPlayer player = (EntityPlayer) entity;
            player.setGameOver(true);
        }
    }
}