package com.sonamorningstar.eternalartifacts.utils;

import net.minecraft.world.entity.player.Player;

public class XpHelperUtil {

    private XpHelperUtil() {

    }

    public static int getPlayerXP(Player player) {

        return getTotalXpForLevel(player.experienceLevel) + getExtraPlayerXp(player);
    }

    public static int getLevelPlayerXP(Player player) {

        return getTotalXpForLevel(player.experienceLevel);
    }

    public static int getExtraPlayerXp(Player player) {

        return Math.round(player.experienceProgress * player.getXpNeededForNextLevel());
    }

    public static void setPlayerXP(Player player, int exp) {

        player.experienceLevel = 0;
        player.experienceProgress = 0.0F;
        player.totalExperience = 0;

        addXPToPlayer(player, exp);
    }

    public static void setPlayerLevel(Player player, int level) {

        player.experienceLevel = level;
        player.experienceProgress = 0.0F;
    }

    public static void addXPToPlayer(Player player, int exp) {

        int i = Integer.MAX_VALUE - player.totalExperience;
        if (exp > i) {
            exp = i;
        }
        player.experienceProgress += (float) exp / (float) player.getXpNeededForNextLevel();
        for (player.totalExperience += exp; player.experienceProgress >= 1.0F; player.experienceProgress /= (float) player.getXpNeededForNextLevel()) {
            player.experienceProgress = (player.experienceProgress - 1.0F) * (float) player.getXpNeededForNextLevel();
            addXPLevelToPlayer(player, 1);
        }
    }

    public static void addXPLevelToPlayer(Player player, int levels) {

        player.experienceLevel += levels;

        if (player.experienceLevel < 0) {
            player.experienceLevel = 0;
            player.experienceProgress = 0.0F;
            player.totalExperience = 0;
        }
    }

    public static int getTotalXpForLevel(int level) {
        return level >= 32 ? (9 * level * level - 325 * level + 4440) / 2 : level >= 17 ? (5 * level * level - 81 * level + 720) / 2 : (level * level + 6 * level);
    }
}
