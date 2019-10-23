package me.noayo.discordbot;

import me.noayo.discordbot.command.CommandMap;
import me.noayo.discordbot.event.BotListener;
import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

import javax.security.auth.login.LoginException;
import java.util.Scanner;

public class DiscordBot implements Runnable {
    private final JDA jda;
    private final CommandMap commandMap = new CommandMap(this);
    private final Scanner scanner = new Scanner(System.in);

    private boolean running;

    public DiscordBot() throws LoginException, RateLimitedException {
        jda = new JDABuilder(AccountType.BOT).setToken(Config.TOKEN).buildAsync();
        jda.addEventListener(new BotListener(commandMap));
        System.out.println("Bot connecté");
    }
    public JDA getJda() {
        return jda;
    }

    public void setRunning(boolean running) {
        this.running = running;
    }

    @Override
    public void run() {
        running = true;

        while (running) {
            if (scanner.hasNextLine()) commandMap.commandConsole(scanner.nextLine());
        }
        scanner.close();
        System.out.println("bot stopped.");
        jda.shutdown ();
        System.exit(0);
        }

    public static void main(String[] args) {
        try {
            DiscordBot discordBot = new DiscordBot();
            new Thread(discordBot,"bot").start();
        } catch (LoginException | RateLimitedException e) {
            e.printStackTrace();
        }

    }
}

