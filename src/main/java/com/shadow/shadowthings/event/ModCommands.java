package com.shadow.shadowthings.event;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.shadow.shadowthings.server.ModDataAttachments;
import com.shadow.shadowthings.soul.ModManaSyncPayload;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
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
                    // Subcommand: /souls set <amount>
                    .then(Commands.literal("set")
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
                                        PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana()));

                                        // 5. Send a chat message confirming it worked
                                        context.getSource().sendSuccess(() -> Component.literal("Set souls to: " + amount), false);

                                        return 1; // Brigadier requires returning an int (1 means success)
                                    })
                            )
                    )

                    // Subcommand: /souls add <amount>
                    .then(Commands.literal("add")
                            .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                    .executes(context -> {
                                        ServerPlayer player = context.getSource().getPlayerOrException();
                                        int amount = IntegerArgumentType.getInteger(context, "amount");

                                        var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
                                        manaData.addMana(amount);

                                        PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana()));
                                        context.getSource().sendSuccess(() -> Component.literal("Added " + amount + " souls!"), false);

                                        return 1;
                                    })
                            )
                    )

                    .then(Commands.literal("remove")
                            .then(Commands.argument("amount", IntegerArgumentType.integer(1))
                                    .executes(context -> {
                                        ServerPlayer player = context.getSource().getPlayerOrException();
                                        int amount = IntegerArgumentType.getInteger(context, "amount");

                                        var manaData = player.getData(ModDataAttachments.PLAYER_SOUL_MANA);
                                        manaData.removeMana(amount);

                                        PacketDistributor.sendToPlayer(player, new ModManaSyncPayload(manaData.getMana()));
                                        context.getSource().sendSuccess(() -> Component.literal("Removed " + amount + " souls!"), false);

                                        return 1;
                                    })
                            )
                    )
                )
        );
    }
}
