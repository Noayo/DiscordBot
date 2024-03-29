package me.noayo.discordbot.myCommands;

import me.noayo.discordbot.command.Command;
import me.noayo.discordbot.DiscordBot;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.*;

import java.awt.*;
import java.util.ArrayList;
import java.util.Arrays;

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
                builder.setThumbnail(user.getAvatarUrl());
                builder.setTitle("informations sur le serveur", null);
                builder.setDescription("Le message a été envoyé depuis le channel "+channel.getName());
                builder.addField("Informations sur le joueur","le compte a été créé le : " , true);
                builder.setTimestamp(user.getCreationTime());
                builder.setColor(Color.yellow);

                channel.sendMessage(builder.build()).queue();
            }
        }
    }
    @Command(name = "commandes", type = Command.ExecutorType.USER)
    private void commandes(User user, MessageChannel channel) {
        if (channel instanceof TextChannel) {
            TextChannel textChannel;
            textChannel = (TextChannel) channel;
            if (textChannel.getGuild().getSelfMember().hasPermission(Permission.MESSAGE_EMBED_LINKS)) {
                EmbedBuilder builder = new EmbedBuilder();
                builder.setTitle("info", null);
                builder.setDescription("donne des information sur vous et le serveur");
                builder.addField("yop","dit 'yop'" , true);
                builder.addField("@bots math","pour l utiliser : mettre '@bots math #'un nombre' 'un nombre''+, - ou /''un nombre' " , true);
                builder.setTimestamp(user.getCreationTime());
                builder.setColor(Color.pink);

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
