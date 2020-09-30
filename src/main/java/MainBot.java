public class MainBot {

    private static String token = "another blurred crap";

    public static void main(String[] args) {
        System.out.println("Loading bot... via JDA");
        new BotUtilities(token, "y!y", new MainBot());
        BotUtilities.startBot();
        System.out.println("Loading listeners...");
        BotUtilities.addEventListener(new SuggestionsChannel());
        BotUtilities.addEventListener(new SetSuggestionsChannel());
        BotUtilities.addEventListener(new DeleteChannel());
        BotUtilities.addEventListener(new DiscordListeners());
        BotUtilities.addEventListener(new SuggestCommand());

        System.out.println("Starting 'suggest' cooldown...");
        Cooldown cooldown = new Cooldown(5, 30);
        cooldown.startCooldown();
        System.out.println("Done!");
    }
}
