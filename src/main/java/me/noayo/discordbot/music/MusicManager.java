package me.noayo.discordbot.music;

import java.util.HashMap;
import java.util.Map;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.core.entities.Guild;
import net.dv8tion.jda.core.entities.TextChannel;

public class MusicManager {

    private final AudioPlayerManager manager = new DefaultAudioPlayerManager();
    private final Map<String, MusicPlayer> players = new HashMap<>();

    public MusicManager(){
        AudioSourceManagers.registerRemoteSources(manager);
        AudioSourceManagers.registerLocalSource(manager);
    }

    public synchronized MusicPlayer getPlayer(Guild guild){
        if(!players.containsKey(guild.getId())) players.put(guild.getId(), new MusicPlayer(manager.createPlayer(), guild));
        return players.get(guild.getId());
    }

    public void loadTrack(final TextChannel channel, final String source){
        MusicPlayer player = getPlayer(channel.getGuild());

        channel.getGuild().getAudioManager().setSendingHandler(player.getAudioHandler());

        manager.loadItemOrdered(player, source, new AudioLoadResultHandler(){
            @Override
            public void trackLoaded(AudioTrack audioTrack) {
              channel.sendMessage("Ajout de la piste "+ audioTrack.getInfo().title+".").queue();
              player.playTrack(audioTrack);
            }

            @Override
            public void playlistLoaded(AudioPlaylist audioPlaylist) {
                StringBuilder builder = new StringBuilder();
                builder.append("Ajout de la playlist **").append(audioPlaylist.getName()).append("**\n");

                for (int i = 0; i < audioPlaylist.getTracks().size() && i < 5; i++){
                    AudioTrack track = audioPlaylist.getTracks().get(i);
                    builder.append("\n **->** ").append(track.getInfo().title);
                    player.playTrack(track);
                }

              channel.sendMessage(builder.toString()).queue();
            }

            @Override
            public void noMatches() {
                channel.sendMessage("La piste " + source + " n'a pas été trouvé.").queue();
            }

            @Override
            public void loadFailed(FriendlyException exeception) {
                channel.sendMessage("Impossible de jouer la piste (raison:" + exeception.getMessage()+")").queue();
            }
        }

        );
    }
}
