package reminator.RemiBot.buttons;

import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.commands.music.lavaplayer.GuildMusicManager;
import reminator.RemiBot.commands.music.lavaplayer.PlayerManager;
import reminator.RemiBot.utils.EnvoiMessage;

public class PauseButton extends Button {
    public PauseButton() {
        super("pause", "", Emoji.fromUnicode("⏸️"));
    }

    @Override
    public void onClick(@NotNull ButtonInteractionEvent event) {

        Guild guild = event.getGuild();

        assert guild != null;
        GuildMusicManager musicManager = PlayerManager.getInstance().getMusicManager(guild);
        musicManager.getAudioPlayer().setPaused(true);
        event.getInteraction().editButton(Buttons.PLAY.getButton()).queue();
        new EnvoiMessage().sendGuild(event.getChannel(), event.getUser().getAsMention() + " a arrêté la lecture.");
    }
}
