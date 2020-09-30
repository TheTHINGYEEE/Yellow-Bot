import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class SuggestionsChannel extends ListenerAdapter {

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        if(SetSuggestionsChannel.getSuggestionChannel().containsKey(event.getGuild())) {
            if(SetSuggestionsChannel.getSuggestionChannel().get(event.getGuild()) != null) {
                if(event.getChannel().equals(SetSuggestionsChannel.getSuggestionChannel().get(event.getGuild()))) {
                    Message msg = event.getMessage();
                    EmbedBuilder embed = new EmbedBuilder();
                    embed.setDescription(msg.getContentRaw());
                    embed.setColor(event.getMember().getColor());
                    embed.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl());
                    TextChannel channel = SetSuggestionsChannel.getSuggestionChannel().get(event.getGuild());
                    channel.sendMessage(embed.build()).queue(message -> {
                        message.addReaction("✅").queue();
                        message.addReaction("❌").queue();
                    });
                    msg.delete().complete();
                }
            }
        }
    }
    public static void addSuggestion(GuildMessageReceivedEvent event, String suggestion) {
        if (event.getAuthor().isBot()) return;

        if (SetSuggestionsChannel.getSuggestionChannel().get(event.getGuild()) != null) {

            Message msg = event.getMessage();
            EmbedBuilder embed = new EmbedBuilder();
            embed.setDescription(suggestion);
            embed.setColor(event.getMember().getColor());
            embed.setAuthor(event.getAuthor().getName(), null, event.getAuthor().getEffectiveAvatarUrl());
            TextChannel channel = SetSuggestionsChannel.getSuggestionChannel().get(event.getGuild());
            channel.sendMessage(embed.build()).queue(message -> {
                message.addReaction("✅").queue();
                message.addReaction("❌").queue();
            });
            msg.delete().complete();
        }
    }
}
