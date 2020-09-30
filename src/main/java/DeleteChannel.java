import net.dv8tion.jda.api.entities.GuildChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class DeleteChannel extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String[] args = BotUtilities.convertToArguments(event.getMessage().getContentRaw());
        if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "deletechannel")) {
            String id = args[1];
            GuildChannel channel = event.getGuild().getGuildChannelById(id);
            if(channel != null) {
                event.getChannel().sendMessage("Deleted channel " + channel.getName() + "!").queue();
                channel.delete().complete();
            } else {
                event.getChannel().sendMessage("That channel doesn't exist.").queue();
            }
        }
    }
}
