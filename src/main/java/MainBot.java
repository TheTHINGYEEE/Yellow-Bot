import javax.security.auth.login.LoginException;

public class MainBot {

    public static String token = "NzU5NjQ3OTM5MjYwOTczMDU2.X3AjRg.fct694hCIhpjEqYKn_ghW3Thxgc";

    public static void main(String[] args) throws LoginException {
        new BotUtilities(token, "!", new MainBot());
        BotUtilities.startBot();
        BotUtilities.getBot().addEventListener(new DiscordListeners());
    }
}
