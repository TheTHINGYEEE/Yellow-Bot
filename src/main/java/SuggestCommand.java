import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SuggestCommand extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = BotUtilities.convertToArguments(event.getMessage().getContentRaw());
        if(event.getAuthor().isBot()) return;
        if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "suggest")) {
            if(!SetSuggestionsChannel.getSuggestionChannel().containsKey(event.getGuild())) { event.getChannel().sendMessage("This server doesn't have a suggestions channel yet!").queue(); return; }
            // the minecraft kind-of part...
            User user = event.getAuthor();
            if(!Cooldown.getCooldown().containsKey(user)) {
                if(event.getChannel().equals(SetSuggestionsChannel.getSuggestionChannel().get(event.getGuild()))) return;
                // puts the person in cool down.
                Cooldown.getCooldown().put(user, Cooldown.getTotalOnSeconds());
                // adds the suggestion
                StringBuilder builder = new StringBuilder();
                for(int i = 1; i < args.length; i++) {
                    builder.append(args[i] + " ");
                }
                String message = builder.toString();
                SuggestionsChannel.addSuggestion(event, message);
                // end of adding the user to the cool down.
            } else {
                // Tells the player to be patient.
                int seconds = Cooldown.getCooldown().get(user);
                event.getChannel().sendMessage("Please wait " + seconds + " seconds until you can suggest something again.").queue();
            }
        }
    }
}
