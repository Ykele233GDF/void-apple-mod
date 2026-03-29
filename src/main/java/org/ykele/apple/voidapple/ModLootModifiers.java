package org.ykele.apple.voidapple;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraftforge.common.loot.IGlobalLootModifier;
import net.minecraftforge.common.loot.LootModifier;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class ModLootModifiers {
    public static final DeferredRegister<Codec<? extends IGlobalLootModifier>> LOOT_MODIFIER_SERIALIZERS =
            DeferredRegister.create(ForgeRegistries.Keys.GLOBAL_LOOT_MODIFIER_SERIALIZERS, VoidApple.MODID);

    public static final RegistryObject<Codec<AddItemModifier>> ADD_VOID_APPLE =
            LOOT_MODIFIER_SERIALIZERS.register("add_void_apple", () -> AddItemModifier.CODEC);

    public static void register(IEventBus bus) {
        LOOT_MODIFIER_SERIALIZERS.register(bus);
    }

    public static class AddItemModifier extends LootModifier {
        public static final Codec<AddItemModifier> CODEC = RecordCodecBuilder.create(inst ->
                LootModifier.codecStart(inst).and(
                        ForgeRegistries.ITEMS.getCodec().fieldOf("item").forGetter(m -> m.item)
                ).apply(inst, AddItemModifier::new));

        private final Item item;

        protected AddItemModifier(LootItemCondition[] conditionsIn, Item item) {
            super(conditionsIn);
            this.item = item;
        }

        @Override
        protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
            generatedLoot.add(new ItemStack(item));
            return generatedLoot;
        }

        @Override
        public Codec<? extends IGlobalLootModifier> codec() {
            return CODEC;
        }
    }
}