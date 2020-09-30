import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.TextChannel;

import javax.security.auth.login.LoginException;

public class BotUtilities {

    private static String token;
    private static String prefix;
    private static Object mainClass;
    private static JDA bot;

    public BotUtilities(String token, String prefix, Object mainClass) {
        BotUtilities.token = token;
        BotUtilities.prefix = prefix;
        BotUtilities.mainClass = mainClass;
    }
    public static void startBot() {
        try {
            bot = new JDABuilder().setToken(token).build();
        } catch (LoginException e) {
            e.printStackTrace();
        }
    }
    public static JDA getJDA() {
        return bot;
    }
    public static String getPrefix() {
        return prefix;
    }
    public static String getToken() {
        return token;
    }
    public static String[] convertToArguments(String string) { return string.split(" "); }
    public static boolean messageEquals(String message, String command) { boolean aa = (message.equalsIgnoreCase(command)); return aa; }
    public static Object getMainInstance() { return mainClass; }
    public static void addEventListener(Object listener) { bot.addEventListener(listener); }

}
