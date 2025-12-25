package com.skyblock.pingbreaker.blacklist;

import net.minecraft.block.Block;
import net.minecraft.init.Blocks;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class BlockBlacklist {
    private static final Block[] BlockBlacklist = new Block[] {
            Blocks.stone_button, Blocks.chest, Blocks.trapped_chest, Blocks.lever, Blocks.bedrock, Blocks.skull
    };

    // Create a HashSet from the array once (static initialization)
    private static final Set<Block> BlockBlacklistSet = new HashSet<>(Arrays.asList(BlockBlacklist));

    public static boolean isBlacklisted(Block block) {
        return BlockBlacklistSet.contains(block);
    }
}
