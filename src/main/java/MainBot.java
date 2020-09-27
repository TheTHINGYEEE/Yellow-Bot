import javax.security.auth.login.LoginException;

public class MainBot {

    public static String token = "token that is going to be shitty blurred";

    public static void main(String[] args) throws LoginException {
        new BotUtilities(token, "!", new MainBot());
        BotUtilities.startBot();
        BotUtilities.getBot().addEventListener(new DiscordListeners());
    }
}
