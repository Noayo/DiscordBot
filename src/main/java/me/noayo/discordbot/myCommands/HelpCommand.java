package me.noayo.discordbot.myCommands;

import java.awt.Color;

import me.noayo.discordbot.command.Command;
import me.noayo.discordbot.command.Command.ExecutorType;
import me.noayo.discordbot.command.CommandMap;
import me.noayo.discordbot.command.SimpleCommand;
import net.dv8tion.jda.core.EmbedBuilder;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.JDA;

public class HelpCommand {

    private final CommandMap commandMap;

    public HelpCommand(CommandMap commandMap) {
        this.commandMap = commandMap;
    }

    @Command(name = "help",type=ExecutorType.USER,description = "affiche la liste des commandes.")
    private void help(User user, MessageChannel channel){

        EmbedBuilder builder = new EmbedBuilder();
        builder.setTitle("Liste des commandes.", null);
        builder.setColor(Color.CYAN);

        for(SimpleCommand command : commandMap.getCommands()){
            if (command.getExecutorType() == ExecutorType.CONSOLE) continue;

            builder.addField(command.getName(), command.getDescription(), false);
        }

        if (!user.hasPrivateChannel()) user.openPrivateChannel().complete();
        user.getPrivateChannel().sendMessage(builder.build()).queue();

        channel.sendMessage(user.getAsMention()+", veullez regarder vos message prive.").queue();
    }

}
