package me.noayo.discordbot.music;

import me.noayo.discordbot.command.Command;
import me.noayo.discordbot.command.Command.ExecutorType;
import net.dv8tion.jda.core.entities.TextChannel;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.User;
import net.dv8tion.jda.core.entities.VoiceChannel;
public class MusicCommand {

    private final MusicManager manager = new MusicManager();

    @Command(name="play", type=ExecutorType.USER)
    private void play(Guild guild, TextChannel textchannel, User user, String command){

        if(guild == null) return;


        if(!guild.getAudioManager().isConnected() && !guild.getAudioManager().isAttemptingToConnect()){
            VoiceChannel voiceChannel = guild.getMember(user).getVoiceState().getChannel();
            if(voiceChannel == null){
                textchannel.sendMessage("Vous devez etre connecté a un salon vocal.").queue();
                return;
            }
            guild.getAudioManager().openAudioConnection(voiceChannel);
        }

        manager.loadTrack(textchannel, command.replaceFirst("play", ""));
    }

    @Command(name="skip",type=ExecutorType.USER)
    private void skip(Guild guild, TextChannel textChannel){
        if (!guild.getAudioManager().isConnected() && !guild.getAudioManager().isAttemptingToConnect()){
            textChannel.sendMessage("Le player n'as pas de piste en cours.").queue();
            return;
        }

        manager.getPlayer(guild).skipTrack();
        textChannel.sendMessage("La lecture est passe a la piste suivante.").queue();
    }

    @Command(name="clear",type=ExecutorType.USER)
    private void clear(TextChannel textChannel){
        MusicPlayer player = manager.getPlayer(textChannel.getGuild());

        if (player.getListener().getTracks().isEmpty()){
            textChannel.sendMessage("il n y a pas de piste dans la liste d attente.").queue();
            return;
        }

        player.getListener().getTracks().clear();
        textChannel.sendMessage("La liste d attente a ete vide.").queue();
    }
}
