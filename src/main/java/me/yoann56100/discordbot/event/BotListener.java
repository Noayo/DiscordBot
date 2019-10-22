package me.yoann56100.discordbot.event;

import me.yoann56100.discordbot.command.CommandMap;
import net.dv8tion.jda.core.entities.MessageChannel;
import net.dv8tion.jda.core.entities.Role;
import net.dv8tion.jda.core.events.Event;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.EventListener;

import java.util.List;


public class BotListener implements EventListener {

    private final CommandMap commandMap;

    public BotListener(CommandMap commandMap){
        this.commandMap = commandMap;
    }

    @Override
    public void onEvent(Event event) {
        System.out.println(event.getClass().getSimpleName());
        if (event instanceof MessageReceivedEvent) onMessage((MessageReceivedEvent) event);
    }


    private void onMessage(MessageReceivedEvent event) {
        if (event.getAuthor().equals(event.getJDA().getSelfUser())) return;
        String message = event.getMessage().getContent();
        if (message.startsWith(CommandMap.getTag())) {
            message = message.replaceFirst(CommandMap.getTag(), "");
            commandMap.commandUser(event.getAuthor(),message,event.getMessage());

        }
        List<Role> roles = event.getGuild().getRolesByName("bots", true);
        System.out.println(roles);
        Role role = roles.get(0);
        if (event.getMessage().getRawContent().startsWith(role.getAsMention())) {
            message = message.replaceFirst("@bots ", "");
            commandMap.commandUser(event.getAuthor(),message,event.getMessage());
        }
   }
}