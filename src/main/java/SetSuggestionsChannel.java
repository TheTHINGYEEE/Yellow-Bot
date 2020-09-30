import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.channel.text.TextChannelDeleteEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.util.HashMap;

public class SetSuggestionsChannel extends ListenerAdapter {

    private static HashMap<Guild, TextChannel> suggestionchannel = new HashMap<Guild, TextChannel>();

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = BotUtilities.convertToArguments(event.getMessage().getContentRaw());
        if(args.length == 2) {
            if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "setsuggestionschannel")) {
                String id = args[1];
                TextChannel channel = event.getGuild().getTextChannelById(id);
                if(channel != null) {
                    if(!suggestionchannel.containsKey(event.getGuild())) {
                        suggestionchannel.put(event.getGuild(), channel);
                        event.getChannel().sendMessage("Successfully set the suggestions channel to #" + suggestionchannel.get(event.getGuild()).getName() + "!").queue();
                    } else {
                        event.getChannel().sendMessage("You already have set the suggestions channel to #" + suggestionchannel.get(event.getGuild()).getName()).queue();
                    }
                } else {
                    event.getChannel().sendMessage("That channel doesn't exist on this server.").queue();
                }
            }
        }
        if(args.length == 1) {
            if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "removesuggestionschannel")) {
                if(suggestionchannel.containsKey(event.getGuild())) {
                    event.getChannel().sendMessage("Successfully removed the suggestion channel " + suggestionchannel.get(event.getGuild()).getName() + "!").queue();
                    suggestionchannel.remove(event.getGuild());
                } else {
                    event.getChannel().sendMessage("You don't have a suggestions channel yet.").queue();
                }
            }
        }
    }
    // we want to delete the hashmap on the guild if someone deletes that channel while its on the hashmap.
    public void onTextChannelDelete(TextChannelDeleteEvent event) {
        // checks if the channel is in the hashmap
        if(event.getChannel().equals(suggestionchannel.get(event.getGuild()))) {
            suggestionchannel.remove(event.getGuild());
        }
    }
    public static HashMap<Guild, TextChannel> getSuggestionChannel() {
        return suggestionchannel;
    }
}
