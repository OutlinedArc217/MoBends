package goblinbob.mobends.core.network;

            import net.minecraftforge.common.ForgeConfigSpec;
            import net.minecraftforge.network.NetworkDirection;
            import net.minecraftforge.network.NetworkRegistry;
            import net.minecraftforge.network.simple.SimpleChannel;
            import net.minecraft.resources.ResourceLocation;

            /**
             * Network configuration for MoBends - MC 1.20.1
             * Auto-generated to fix compilation errors
             */
            public class NetworkConfiguration {
                private static final String PROTOCOL_VERSION = "1";
                public static final SimpleChannel CHANNEL = NetworkRegistry.newSimpleChannel(
                    new ResourceLocation("mobends", "main"),
                    () -> PROTOCOL_VERSION,
                    PROTOCOL_VERSION::equals,
                    PROTOCOL_VERSION::equals
                );

                private final ForgeConfigSpec.BooleanValue networkEnabled;
                private final ForgeConfigSpec.IntValue syncRate;

                public NetworkConfiguration(ForgeConfigSpec.Builder builder) {
                    builder.push("network");
                    this.networkEnabled = builder
                        .comment("Enable network synchronization features")
                        .define("enabled", true);
                    this.syncRate = builder
                        .comment("Network synchronization rate (ticks)")
                        .defineInRange("syncRate", 20, 1, 100);
                    builder.pop();
                }

                public boolean isNetworkEnabled() {
                    return networkEnabled.get();
                }

                public int getSyncRate() {
                    return syncRate.get();
                }

                public static void registerMessages() {
                    // Register network messages here
                }
            }