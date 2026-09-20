package com.shadow.shadowthings.screen;

import com.shadow.shadowthings.ShadowThings;
import com.shadow.shadowthings.screen.custom.SoulCoreMenu;
import com.shadow.shadowthings.screen.custom.SoulCrafterMenu;
import com.shadow.shadowthings.screen.custom.SoulCrucibleMenu;
import com.shadow.shadowthings.screen.custom.SoulFurnaceMenu;
import net.minecraft.core.registries.Registries;
import net.minecraft.world.inventory.AbstractContainerMenu;
import net.minecraft.world.inventory.MenuType;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.extensions.IMenuTypeExtension;
import net.neoforged.neoforge.network.IContainerFactory;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

public class ModMenuTypes {

    public static final DeferredRegister<MenuType<?>> MENUS =
            DeferredRegister.create(Registries.MENU, ShadowThings.MODID);

    public static final DeferredHolder<MenuType<?>, MenuType<SoulCoreMenu>> SOUL_CORE_MENU =
            registerMenuType("soul_core_menu", SoulCoreMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<SoulCrafterMenu>> SOUL_CRAFTER_MENU =
            registerMenuType("soul_crafter_menu", SoulCrafterMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<SoulFurnaceMenu>> SOUL_FURNACE_MENU =
            registerMenuType("soul_furnace_menu", SoulFurnaceMenu::new);

    public static final DeferredHolder<MenuType<?>, MenuType<SoulCrucibleMenu>> SOUL_CRUCIBLE_MENU =
            registerMenuType("soul_crucible_menu", SoulCrucibleMenu::new);



    private static <T extends AbstractContainerMenu>DeferredHolder<MenuType<?>,MenuType<T>> registerMenuType(String name,
                                                                                                             IContainerFactory<T> factory){
        return MENUS.register(name, () -> IMenuTypeExtension.create(factory));
    }

    public static void register(IEventBus eventBus){
        MENUS.register(eventBus);
    }
}
