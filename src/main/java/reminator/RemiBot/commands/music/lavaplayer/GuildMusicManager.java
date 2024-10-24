package reminator.RemiBot.commands.music.lavaplayer;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;

public class GuildMusicManager {

    private final AudioPlayer audioPlayer;
    private final TrackScheduler trackScheduler;
    private final AudioPlayerSendHandler sendHandler;

    public GuildMusicManager(AudioPlayerManager manager) {
        this.audioPlayer = manager.createPlayer();
        this.trackScheduler = new TrackScheduler(this.audioPlayer);
        this.audioPlayer.addListener(this.trackScheduler);
        this.sendHandler = new AudioPlayerSendHandler(this.audioPlayer);
    }

    public AudioPlayerSendHandler getSendHandler() {
        return sendHandler;
    }

    public TrackScheduler getTrackScheduler() {
        return trackScheduler;
    }

    public AudioPlayer getAudioPlayer() {
        return audioPlayer;
    }

    public void stop() {
        audioPlayer.stopTrack();
        trackScheduler.clearQueue();
    }

    public void setDisplayChannel(MessageChannel channel) {
        trackScheduler.setDisplayChannel(channel);
    }
}
