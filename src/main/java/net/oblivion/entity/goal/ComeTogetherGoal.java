package net.oblivion.entity.goal;

import net.minecraft.entity.ai.goal.Goal;
import net.minecraft.entity.mob.PathAwareEntity;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class ComeTogetherGoal extends Goal {
    private final PathAwareEntity entity;
    @Nullable
    private PathAwareEntity leader;
    private final double speed;
    private int delay;
    private int cooldown;

    public ComeTogetherGoal(PathAwareEntity entity, double speed) {
        this.entity = entity;
        this.speed = speed;
    }

    @Override
    public boolean canStart() {
        if (this.cooldown >= 0) {
            this.cooldown--;
            return false;
        } else if (this.entity.isAttacking()) {
            return false;
        } else {
            List<? extends PathAwareEntity> list = this.entity.getWorld().getNonSpectatingEntities(this.entity.getClass(), this.entity.getBoundingBox().expand(12.0, 4.0, 12.0));
            PathAwareEntity entityEntity = null;
            double d = Double.MAX_VALUE;

            for (PathAwareEntity entityEntity2 : list) {
                double e = this.entity.squaredDistanceTo(entityEntity2);
                if (!(e > d)) {
                    d = e;
                    entityEntity = entityEntity2;
                    break;
                }
            }

            if (entityEntity == null) {
                return false;
            } else if (d < 9.0) {
                return false;
            } else {
                this.leader = entityEntity;
                return true;
            }
        }
    }

    @Override
    public boolean shouldContinue() {
        if (!this.leader.isAlive()) {
            return false;
        } else {
            double d = this.entity.squaredDistanceTo(this.leader);
            return !(d < 9.0) && !(d > 256.0);
        }
    }

    @Override
    public void start() {
        this.delay = 0;
    }

    @Override
    public void stop() {
        this.leader = null;
        this.cooldown = 200 + this.entity.getRandom().nextInt(300);
    }

    @Override
    public void tick() {
        if (--this.delay <= 0) {
            this.delay = this.getTickCount(10);
            this.entity.getNavigation().startMovingTo(this.leader, this.speed);
        }
    }
}

