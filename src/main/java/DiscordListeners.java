import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.Permission;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberLeaveEvent;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import net.dv8tion.jda.api.events.message.priv.PrivateMessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.internal.requests.Route;

import java.awt.*;
import java.util.HashMap;
import java.util.Random;

public class DiscordListeners extends ListenerAdapter {

    public HashMap<Guild, TextChannel> joinleavechannel = new HashMap<Guild, TextChannel>();
    public HashMap<User, Boolean> oops1 = new HashMap<User, Boolean>();
    public HashMap<User, Boolean> oops2 = new HashMap<User, Boolean>();

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

        if(!event.getGuild().getSelfMember().hasPermission(Permission.MANAGE_CHANNEL) && event.getMessage().getContentRaw().toLowerCase().startsWith(BotUtilities.getPrefix())) {
            if(!event.getAuthor().isBot()) {
                event.getChannel().sendMessage("I don't have the permission to MANAGE_CHANNEL").queue();
                return;
            }
        }
        if(event.getAuthor().isBot()) return;
        if(!event.getMember().hasPermission(Permission.MANAGE_CHANNEL)) return;
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
                event.getChannel().sendMessage("Oh. You want to see my brain?").queue();
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.YELLOW);
                embedBuilder.setDescription("My Brain: https://github.com/TheTHINGYEEE/Yellow-Bot");

                event.getChannel().sendMessage("Here you go. Oh my.. Another surgery. Oh boy.").queue();
                event.getChannel().sendMessage(embedBuilder.build()).queue();
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "about")) {
                EmbedBuilder embedBuilder = new EmbedBuilder();
                embedBuilder.setColor(Color.YELLOW);
                embedBuilder.setTitle("About Yellow:");
                embedBuilder.addField("Author", "TheTHINGYEEEEE#4086", false);
                embedBuilder.addField("Favorite Color", "Yellow", false);
                embedBuilder.addField("Catchphrase", "'Huh? What the f--'", false);
                embedBuilder.addField("Smart?", "false", false);
                embedBuilder.setFooter("Yellow-Bot for the win.");

                event.getChannel().sendMessage(embedBuilder.build()).queue();
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "dontdoit7584")) {
                System.out.println("Cannot run command! No tokens set yet.");
                event.getChannel().sendMessage("Cannot run command! No tokens set yet.").queue();
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "69420")) {
                if(!oops1.containsKey(event.getAuthor())) {
                    oops1.put(event.getAuthor(), true);
                    event.getChannel().sendMessage("Unable to send message to user's private channel. Code: donttypethat").queue();
                } else {
                    event.getChannel().sendMessage("Ummm.. okay?").queue();
                }
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "counttextchannels")) {
                int textchannels = event.getGuild().getTextChannels().size();
                event.getChannel().sendMessage("There are a total of: " + textchannels + " text channels in this discord server!").queue();
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "countallchannels")) {
                int channels = event.getGuild().getChannels().size();
                int cat = event.getGuild().getCategories().size();
                int fInt = channels - cat;
                event.getChannel().sendMessage("There are a total of: " + fInt + " channels in this discord server!").queue();
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "countvoicechannels")) {
                int vc = event.getGuild().getVoiceChannels().size();
                event.getChannel().sendMessage("There are a total of: " + vc + " voice channels in this discord server!").queue();
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "countcategories")) {
                int cat = event.getGuild().getCategories().size();
                event.getChannel().sendMessage("There are a total of: " + cat + " categories in this discord server!").queue();
            } else if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "getallemotes")) {
                for(Emote emote : event.getGuild().getEmotes()) {
                    event.getChannel().sendMessage("Name: "+ emote.getName() + " ID: " + emote.getId()).queue();
                }
                Message msg = event.getMessage();
                if(event.getGuild().getEmoteById("760803533192036362") != null) {
                    msg.addReaction(event.getGuild().getEmoteById("760803533192036362")).complete();
                }
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
    public void onPrivateMessageReceived(PrivateMessageReceivedEvent event) {
        String[] args = BotUtilities.convertToArguments(event.getMessage().getContentRaw());
        if(BotUtilities.messageEquals(args[0], BotUtilities.getPrefix() + "donttypethat")) {
            // check if the user hasn't typed the command yet or gets the easter egg.
            if(oops1.containsKey(event.getAuthor())) {
                // checks if he has done the command !69420
                if(!oops2.containsKey(event.getAuthor())) {
                    // messages for the stupid easter egg finder.
                    event.getChannel()
                            .sendMessage("Umm.. how did you find that this is a thing?")
                            .queue();
                    event.getChannel()
                            .sendMessage("And also how did you find this?")
                            .queue();
                    event.getChannel()
                            .sendMessage("Ok fine. This is an easter egg.")
                            .queue();
                    event.getChannel()
                            .sendMessage("You just wasted your time.")
                            .queue();
                    event.getChannel()
                            .sendMessage("Ok you wasted your time, but still for finding this easter egg," +
                                    " you deserve this gift. ")
                            .queue();
                    // puts the user to the hashmap because we dont want the user to repeat this command.
                    oops2.put(event.getAuthor(), true);
                } else {
                    // Shut shut shut shut
                    event.getChannel().sendMessage("Ok. You did this already. Stop.").queue();
                }
            } else {
                // No. You have no gate pass.
                event.getChannel().sendMessage("Unknown command.").queue();
            }
        }
    }
}
