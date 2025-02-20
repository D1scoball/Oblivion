package net.oblivion.entity.model;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.model.ModelPart;
import net.minecraft.client.render.entity.model.LargeTropicalFishEntityModel;
import net.oblivion.entity.Piranha;

@Environment(EnvType.CLIENT)
public class PiranhaModel extends LargeTropicalFishEntityModel<Piranha> {

    public PiranhaModel(ModelPart root) {
        super(root);
    }
}
