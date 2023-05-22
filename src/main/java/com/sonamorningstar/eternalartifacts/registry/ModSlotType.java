package com.sonamorningstar.eternalartifacts.registry;

import com.sonamorningstar.eternalartifacts.EternalArtifacts;
import net.minecraft.resources.ResourceLocation;
import top.theillusivec4.curios.api.SlotTypeMessage.Builder;

import java.util.Optional;

public enum ModSlotType {
    FEET("feet", 225, new ResourceLocation(EternalArtifacts.MOD_ID, "slots/empty_feet_slot")),
    MAGIC_FEATHER("magic_feather", 180, new ResourceLocation(EternalArtifacts.MOD_ID, "slots/empty_magic_feather_slot"));

    final String id;
    final int priority;
    final ResourceLocation icon;

    ModSlotType(String id, int priority, ResourceLocation icon) {
        this.id = id;
        this.priority = priority;
        this.icon = icon;
    }

    public static Optional<ModSlotType> findPreset(String id) {
        try {
            return Optional.of(ModSlotType.valueOf(id.toUpperCase()));
        } catch (IllegalArgumentException e) {
            return Optional.empty();
        }
    }

    public String getIdentifier() {return this.id; }

    public String getLangIdentifier() { return "curios.identifier." + getIdentifier(); }

    public ResourceLocation getIcon() { return this.icon; }

    public Builder getMessageBuilder() {
        return new Builder(this.id).priority(this.priority).icon(this.icon);
    }
}
