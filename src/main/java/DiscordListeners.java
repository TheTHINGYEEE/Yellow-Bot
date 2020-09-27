import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class DiscordListeners extends ListenerAdapter {

    public HashMap<Guild, TextChannel> joinleavechannel = new HashMap<Guild, TextChannel>();

    public String[] joinsentences = {"OMG! [member] is here!", "MOOOM! [member] is waiting for you!", "Oh, hi [member] hope you brought the pizza.",
            "[member] just joined our gang!", "I am gonna YEEET [member]!", "Oh no! Buckle up! We're going to [member]'s house!",
            "[member] joined the game.", "[member]? Why are you here?"};

    public String[] leavemessages = {"OMG! [member] has left!", "MOOOM! [member] is not here anymore!", "Oh, hi again [member], see you next time.",
            "Goodbye! [member] just left our gang!", "I am gonna YEEET [member] to his house!", "Oh no! [member] has stole our car! Get him!",
            "[member] left the game.", "[member]? Why did you leave?"};

    @Override
    public void onGuildMessageReceived(GuildMessageReceivedEvent event) {
        String message = event.getMessage().getContentRaw();
        String[] args = BotUtilities.convertToArguments(message);

        if(args.length == 1) {
            if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "ping")) {
                event.getChannel().sendMessage("Pong!").queue();
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "pong")) {
                event.getChannel().sendMessage("Ping!").queue();
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "removejoinleavechannel")) {
                if(joinleavechannel.containsKey(event.getGuild())) {
                    joinleavechannel.remove(event.getGuild());
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setColor(Color.GREEN);
                    embedBuilder.setDescription("Successfully removed the join-leave channel!");

                    event.getChannel().sendMessage(embedBuilder.build()).queue();
                } else {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setColor(Color.RED);
                    embedBuilder.setDescription("There is nothing to remove. No channels set.");

                    event.getChannel().sendMessage(embedBuilder.build()).queue();
                }
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "source")) {
                event.getChannel().sendMessage("```Not yet set! LMAO```").queue();
            }
        } else if(args.length == 2) {
            if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "setjoinleavechannel")) {
                String id = args[1];
                TextChannel channel = event.getGuild().getTextChannelById(id);
                if(channel != null) {
                    if(!joinleavechannel.containsKey(event.getGuild())) {
                        joinleavechannel.put(event.getGuild(), channel);
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setColor(Color.GREEN);
                        embedBuilder.setDescription("Successfully set the join-leave channel to #" + channel.getName() + "!");

                        event.getChannel().sendMessage(embedBuilder.build()).queue();
                    } else {
                        EmbedBuilder embedBuilder = new EmbedBuilder();
                        embedBuilder.setColor(Color.RED);
                        embedBuilder.setDescription("This server has already set a channel! Channel: #" + joinleavechannel.get(event.getGuild()).getName());

                        event.getChannel().sendMessage(embedBuilder.build()).queue();
                    }
                } else {
                    EmbedBuilder embedBuilder = new EmbedBuilder();
                    embedBuilder.setColor(Color.RED);
                    embedBuilder.setDescription("There is no such text channel like that in this server!");

                    event.getChannel().sendMessage(embedBuilder.build()).queue();
                }
            }
        }
    }
    public void onGuildMemberJoin(GuildMemberJoinEvent event) {
        int randomnum = new Random().nextInt(joinsentences.length);
        TextChannel channel = joinleavechannel.get(event.getGuild());

        if(channel != null) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.YELLOW);
            embed.setDescription(joinsentences[randomnum].replace("[member]", event.getUser().getName()));

            channel.sendMessage(embed.build()).queue();
        }
    }
    public void onGuildMemberLeave(GuildMemberLeaveEvent event) {
        int randomnum = new Random().nextInt(leavemessages.length);
        TextChannel channel = joinleavechannel.get(event.getGuild());

        if(channel != null) {
            EmbedBuilder embed = new EmbedBuilder();
            embed.setColor(Color.RED);
            embed.setDescription(leavemessages[randomnum].replace("[member]", event.getUser().getName()));

            channel.sendMessage(embed.build()).queue();
        }
    }
}
