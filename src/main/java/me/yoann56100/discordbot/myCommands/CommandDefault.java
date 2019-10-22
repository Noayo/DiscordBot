package me.yoann56100.discordbot.myCommands;

import me.yoann56100.discordbot.DiscordBot;
import me.yoann56100.discordbot.command.Calculatrice;
import me.yoann56100.discordbot.command.Command;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Message;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.User;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

public class CommandDefault {

    private final DiscordBot discordBot;

    public CommandDefault(DiscordBot discordBot) {
        this.discordBot = discordBot;
    }

    @Command(name = "stop", type = Command.ExecutorType.CONSOLE)
    private void stop() {
        discordBot.setRunning(false);

    }

    @Command(name = "info", type = Command.ExecutorType.USER)
    private void info(User user, MessageChannel channel) {
        if (channel instanceof TextChannel) {
            TextChannel textChannel;
            textChannel = (TextChannel) channel;
            if (textChannel.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setAuthor(user.getName(), null, user.getAvatarUrl()+"?size=256");
                builder.setTitle("informations");
                builder.setDescription("[>](1)le message a été envoyé depuis le channel "+channel.getName());
                builder.setColor(Color.blue);

                channel.sendMessage(builder.build()).queue();
            }
        }
    }

    @Command(name = "yop", type = Command.ExecutorType.USER)
    public void yop(User user, MessageChannel channel){
        channel.sendMessage("yop " + user.getAsMention()).complete();
    }

    @Command(name = "holla", type = Command.ExecutorType.USER)
    public void holla(User user, MessageChannel channel) {
        channel.sendMessage("holla " + user.getAsMention()).complete();
    }
    @Command(name = "math",type = Command.ExecutorType.USER)
    public void math(User user, MessageChannel channel, Message message){
        try
        {
            String content = message.getContent();
            ArrayList<String> contentList = new ArrayList<>(Arrays.asList(content.split(" ")));
            contentList.remove(0);
            contentList.remove(0);
            String[] arguments = new String[contentList.size()];
            arguments = contentList.toArray(arguments);

            String stringId = arguments[0].substring(1);
            int id = Integer.parseInt(stringId);

            String operation = arguments[1];
            float nbr1,nbr2,res = 0;

            int pos = operation.indexOf("/");
            if (pos != -1){
                String[] nombre = operation.split("/");
                nbr1 = Integer.parseInt(nombre[0]);
                nbr2 = Integer.parseInt(nombre[1]);
                res = nbr1 / nbr2;
            }

            pos = operation.indexOf("-");
            if (pos != -1){
                String[] nombre = operation.split("-");
                nbr1 = Integer.parseInt(nombre[0]);
                nbr2 = Integer.parseInt(nombre[1]);
                res = nbr1 - nbr2;
            }

            pos = operation.indexOf("*");
            if (pos != -1){
                String[] nombre = operation.split("\\*");
                nbr1 = Integer.parseInt(nombre[0]);
                nbr2 = Integer.parseInt(nombre[1]);
                res = nbr1 * nbr2;
            }

            pos = operation.indexOf("+");
            if (pos != -1){
                String[] nombre = operation.split("\\+");
                nbr1 = Integer.parseInt(nombre[0]);
                nbr2 = Integer.parseInt(nombre[1]);
                res = nbr1 + nbr2;
            }

            if (res%1==0)
            channel.sendMessage("!rep #"+id + " " + ((int)res)).complete();
            else
            channel.sendMessage("!rep #"+id + " " + (int)(res*100)/100f).complete();
        }
        catch (Exception e){
            e.printStackTrace();
        }
    }
}