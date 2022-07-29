package pepjebs.nomorepurple.client;

import com.mojang.brigadier.arguments.StringArgumentType;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandManager;
import net.fabricmc.fabric.api.client.command.v2.ClientCommandRegistrationCallback;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class NoMorePurpleClientMod implements ClientModInitializer {

    public static final String MOD_ID = "no_more_purple";
    public static final String COMMAND_ID = "glint_color";
    public static final Logger LOGGER = LogManager.getLogger(MOD_ID);

    @Override
    public void onInitializeClient() {
        // Register the "/glintcolor <color>" command
        ClientCommandRegistrationCallback.EVENT.register((dispatcher, registryAccess) -> {
            dispatcher.register(
                    ClientCommandManager.literal(COMMAND_ID)
                            .then(ClientCommandManager.argument(COMMAND_ID, StringArgumentType.string())
                                .executes(context -> {
                                    String glintColor = context.getArgument(COMMAND_ID, String.class);
                                    LOGGER.info(glintColor);
                                    return 0;
                                })
                            )
            );
        });
    }
}
