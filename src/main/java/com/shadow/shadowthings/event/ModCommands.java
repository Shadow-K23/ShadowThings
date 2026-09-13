package com.shadow.shadowthings.event;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.shadow.shadowthings.block.entity.SoulCoreEntity;
import com.shadow.shadowthings.server.ModDataAttachments;
import com.shadow.shadowthings.network.ModManaSyncPayload;
import com.shadow.shadowthings.world.SoulCoreData;
import net.minecraft.commands.Commands;
import net.minecraft.core.BlockPos;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.PacketDistributor;

@EventBusSubscriber(modid = "shadowthings")
public class ModCommands {

    @SubscribeEvent
    public static void onCommandsRegister(RegisterCommandsEvent event) {

        // We register a new base command called "/souls"
        event.getDispatcher().register(Commands.literal("shadowthings")
                .requires(source -> source.hasPermission(2)) // Requires OP/Cheats enabled

                .then(Commands.literal("souls")
                    // Subcommand: /souls
                        //Core soul subcommands
                        .then(Commands.literal("core")
                                .then(Commands.literal("setSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0)) // Prevents negative numbers
                                                .executes(context -> {
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                                    // Commands always return a ServerLevel directly, no casting needed!
                                                    ServerLevel serverLevel = context.getSource().getLevel();

                                                    SoulCoreData data = SoulCoreData.get(serverLevel);

                                                    if (data.hasCore(player.getUUID())) {
                                                        BlockPos corePos = data.getCorePosition(player.getUUID());

                                                        if (serverLevel.getBlockEntity(corePos) instanceof SoulCoreEntity coreEntity) {
                                                            coreEntity.setSouls(amount);

                                                            // Move the success message INSIDE the if-statement
                                                            context.getSource().sendSuccess(() -> Component.literal("Set core souls to: " + amount), false);
                                                            return 1;
                                                        }
                                                    }
                                                    // If we reach this point, either they don't have a core, or the block is missing
                                                    context.getSource().sendFailure(Component.literal("You do not own a Soul Core!"));
                                                    return 0;
                                                })
                                        ))
                                .then(Commands.literal("addSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0)) // Prevents negative numbers
                                                .executes(context -> {
                                                            ServerPlayer player = context.getSource().getPlayerOrException();
                                                            int amount = IntegerArgumentType.getInteger(context, "amount");
                                                            // Commands always return a ServerLevel directly, no casting needed!
                                                            ServerLevel serverLevel = context.getSource().getLevel();

                                                            SoulCoreData data = SoulCoreData.get(serverLevel);

                                                            if (data.hasCore(player.getUUID())) {
                                                                BlockPos corePos = data.getCorePosition(player.getUUID());

                                                                if (serverLevel.getBlockEntity(corePos) instanceof SoulCoreEntity coreEntity) {
                                                                    coreEntity.addSouls(amount);

                                                                    // Move the success message INSIDE the if-statement
                                                                    context.getSource().sendSuccess(() -> Component.literal("Added " + amount + " souls to core."), false);
                                                                    return 1;
                                                                }
                                                            }
                                                            // If we reach this point, either they don't have a core, or the block is missing
                                                            context.getSource().sendFailure(Component.literal("You do not own a Soul Core!"));
                                                            return 0;
                                                        })
                                                ))
                                .then(Commands.literal("removeSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0)) // Prevents negative numbers
                                              .executes(context -> {
                                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                                                    // Commands always return a ServerLevel directly, no casting needed!
                                                                    ServerLevel serverLevel = context.getSource().getLevel();

                                                                    SoulCoreData data = SoulCoreData.get(serverLevel);

                                                                    if (data.hasCore(player.getUUID())) {
                                                                        BlockPos corePos = data.getCorePosition(player.getUUID());

                                                                        if (serverLevel.getBlockEntity(corePos) instanceof SoulCoreEntity coreEntity) {
                                                                            coreEntity.removeSouls(amount);

                                                                            // Move the success message INSIDE the if-statement
                                                                            context.getSource().sendSuccess(() -> Component.literal("Removed " + amount + " souls from core."), false);
                                                                            return 1;
                                                                        }
                                                                    }
                                                                    // If we reach this point, either they don't have a core, or the block is missing
                                                                    context.getSource().sendFailure(Component.literal("You do not own a Soul Core!"));
                                                                    return 0;
                                                                })
                                        ))
                                .then(Commands.literal("setMaxSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0)) // Prevents negative numbers
                                                .executes(context -> {
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                                    // Commands always return a ServerLevel directly, no casting needed!
                                                    ServerLevel serverLevel = context.getSource().getLevel();

                                                    SoulCoreData data = SoulCoreData.get(serverLevel);

                                                    if (data.hasCore(player.getUUID())) {
                                                        BlockPos corePos = data.getCorePosition(player.getUUID());

                                                        if (serverLevel.getBlockEntity(corePos) instanceof SoulCoreEntity coreEntity) {
                                                            coreEntity.setMaxSouls(amount);

                                                            // Move the success message INSIDE the if-statement
                                                            context.getSource().sendSuccess(() -> Component.literal("Set core max souls capacity to: " + amount), false);
                                                            return 1;
                                                        }
                                                    }
                                                    // If we reach this point, either they don't have a core, or the block is missing
                                                    context.getSource().sendFailure(Component.literal("You do not own a Soul Core!"));
                                                    return 0;
                                                })
                                        ))
                                .then(Commands.literal("addMaxSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0)) // Prevents negative numbers
                                                .executes(context -> {
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                                    // Commands always return a ServerLevel directly, no casting needed!
                                                    ServerLevel serverLevel = context.getSource().getLevel();

                                                    SoulCoreData data = SoulCoreData.get(serverLevel);

                                                    if (data.hasCore(player.getUUID())) {
                                                        BlockPos corePos = data.getCorePosition(player.getUUID());

                                                        if (serverLevel.getBlockEntity(corePos) instanceof SoulCoreEntity coreEntity) {
                                                            coreEntity.addMaxSouls(amount);

                                                            // Move the success message INSIDE the if-statement
                                                            context.getSource().sendSuccess(() -> Component.literal("Added " + amount + " max souls capacity to core."), false);
                                                            return 1;
                                                        }
                                                    }
                                                    // If we reach this point, either they don't have a core, or the block is missing
                                                    context.getSource().sendFailure(Component.literal("You do not own a Soul Core!"));
                                                    return 0;
                                                })
                                        ))
                                .then(Commands.literal("removeMaxSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0)) // Prevents negative numbers
                                                .executes(context -> {
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    int amount = IntegerArgumentType.getInteger(context, "amount");
                                                    // Commands always return a ServerLevel directly, no casting needed!
                                                    ServerLevel serverLevel = context.getSource().getLevel();

                                                    SoulCoreData data = SoulCoreData.get(serverLevel);

                                                    if (data.hasCore(player.getUUID())) {
                                                        BlockPos corePos = data.getCorePosition(player.getUUID());

                                                        if (serverLevel.getBlockEntity(corePos) instanceof SoulCoreEntity coreEntity) {
                                                            coreEntity.removeMaxSouls(amount);

                                                            // Move the success message INSIDE the if-statement
                                                            context.getSource().sendSuccess(() -> Component.literal("Removed " + amount + " max souls capacity from core."), false);
                                                            return 1;
                                                        }
                                                    }
                                                    // If we reach this point, either they don't have a core, or the block is missing
                                                    context.getSource().sendFailure(Component.literal("You do not own a Soul Core!"));
                                                    return 0;
                                                })
                                        ))
                        )
                        //Player soul subcommands
                        .then(Commands.literal("player")
                                .then(Commands.literal("setSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0)) // Prevents negative numbers
                                                .executes(context -> {
                                                        // 1. Get the player who typed the command
                                                        ServerPlayer player = context.getSource().getPlayerOrException();

                                                        // 2. Get the number they typed
                                                        int amount = IntegerArgumentType.getInteger(context, "amount");

                                                        // 3. Update the data
                                                        var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
                                                        manaData.setMana(amount); // Assumes you have a setMana method!

                                                        // 4. Sync to HUD
                                                    PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
                                                        // 5. Send a chat message confirming it worked
                                                        context.getSource().sendSuccess(() -> Component.literal("Set souls to: " + amount), false);

                                                        return 1; // Brigadier requires returning an int (1 means success)
                                                })
                                        )
                                )
                                .then(Commands.literal("addSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                                .executes(context -> {
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    int amount = IntegerArgumentType.getInteger(context, "amount");

                                                    var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
                                                    manaData.addMana(amount);

                                                    PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
                                                    context.getSource().sendSuccess(() -> Component.literal("Added " + amount + " souls!"), false);

                                                    return 1;
                                                })
                                        )
                                )

                                .then(Commands.literal("removeSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                                .executes(context -> {
                                                ServerPlayer player = context.getSource().getPlayerOrException();
                                                int amount = IntegerArgumentType.getInteger(context, "amount");

                                                var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
                                                manaData.removeMana(amount);

                                                    PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
                                                    context.getSource().sendSuccess(() -> Component.literal("Removed " + amount + " souls!"), false);

                                                return 1;
                                            })
                                        )
                                )
                                .then(Commands.literal("setMaxSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(0)) // Prevents negative numbers
                                                .executes(context -> {
                                                    // 1. Get the player who typed the command
                                                    ServerPlayer player = context.getSource().getPlayerOrException();

                                                    // 2. Get the number they typed
                                                    int amount = IntegerArgumentType.getInteger(context, "amount");

                                                    // 3. Update the data
                                                    var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
                                                    manaData.setMaxMana(amount); // Assumes you have a setMana method!

                                                    // 4. Sync to HUD
                                                    PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
                                                    // 5. Send a chat message confirming it worked
                                                    context.getSource().sendSuccess(() -> Component.literal("Set max souls to: " + amount), false);

                                                    return 1; // Brigadier requires returning an int (1 means success)
                                                })
                                        )
                                )
                                .then(Commands.literal("addMaxSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                                .executes(context -> {
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    int amount = IntegerArgumentType.getInteger(context, "amount");

                                                    var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
                                                    manaData.addMaxMana(amount);

                                                    PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
                                                    context.getSource().sendSuccess(() -> Component.literal("Added " + amount + " max souls capacity!"), false);

                                                    return 1;
                                                })
                                        )
                                )

                                .then(Commands.literal("removeMaxSouls")
                                        .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                                .executes(context -> {
                                                    ServerPlayer player = context.getSource().getPlayerOrException();
                                                    int amount = IntegerArgumentType.getInteger(context, "amount");

                                                    var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
                                                    manaData.removeMaxMana(amount);

                                                    PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana(), manaData.getMaxMana()));
                                                    context.getSource().sendSuccess(() -> Component.literal("Removed " + amount + " from souls capacity!"), false);

                                                    return 1;
                                                })
                                        )
                                )
                        )
                )
                //next command here
        );
    }
}
