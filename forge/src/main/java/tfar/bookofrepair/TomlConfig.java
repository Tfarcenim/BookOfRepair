package tfar.bookofrepair;

import net.minecraftforge.common.ForgeConfigSpec;
import org.apache.commons.lang3.tuple.Pair;

public class TomlConfig {

    public static final Server SERVER;
    public static final ForgeConfigSpec SERVER_SPEC;

    static {
        final Pair<Server, ForgeConfigSpec> specPair2 = new ForgeConfigSpec.Builder().configure(Server::new);
        SERVER_SPEC = specPair2.getRight();
        SERVER = specPair2.getLeft();
    }

    public static class Server {
        public static ForgeConfigSpec.IntValue book_xp;


        public Server(ForgeConfigSpec.Builder builder) {
            builder.push("general");
            book_xp = builder.defineInRange("book_xp",6464,1,1000000000);
            builder.pop();
        }
    }
}
