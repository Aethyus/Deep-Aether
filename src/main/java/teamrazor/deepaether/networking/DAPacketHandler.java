package teamrazor.deepaether.networking;

import com.aetherteam.nitrogen.network.BasePacket;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.network.NetworkRegistry;
import net.minecraftforge.network.simple.SimpleChannel;
import teamrazor.deepaether.DeepAether;

import java.util.function.Function;

public class DAPacketHandler {
    public static final SimpleChannel INSTANCE = NetworkRegistry.newSimpleChannel(new ResourceLocation(DeepAether.MODID, "main"), () -> "1", "1"::equals, "1"::equals);
    private static int index;


    public static synchronized void register() {
        register(DAPlayerSyncPacket.class, DAPlayerSyncPacket::decode);
        register(DAMoasyncPacket.class, DAMoasyncPacket::decode);
    }

    private static <MSG extends BasePacket> void register(Class<MSG> packet, Function<FriendlyByteBuf, MSG> decoder) {
        INSTANCE.messageBuilder(packet, index++).encoder(BasePacket::encode).decoder(decoder).consumerMainThread(BasePacket::handle).add();
    }
}
