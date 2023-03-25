package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

public class BackButton extends Button {

    public BackButton() {
        super("back", "", Emoji.fromUnicode("⏮️"));
    }
    @Override
    public void onClick(@NotNull ButtonInteractionEvent event) {

        Guild guild = event.getGuild();

        assert guild != null;
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        new EnvoiMessage().sendGuild(event.getChannel(), event.getUser().getAsMention() + " est retourné à la musique précédente.");
        musicManager.getTrackScheduler().previousTrack();
    }
}
