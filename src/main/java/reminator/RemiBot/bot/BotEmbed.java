package reminator.RemiBot.bot;

import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.RichPresence;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.internal.utils.tuple.Pair;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public enum BotEmbed {

    BASE(o -> new EmbedBuilder().setColor(RemiBot.color)),

    BASE_USER(o -> {
        if (!(o instanceof User user))
            return BotEmbed.BASE.getBuilder(null);
        return BotEmbed.BASE.getBuilder(null).setFooter(user.getName(), user.getAvatarUrl());
    }),

    MUSIQUE(o -> {
        if (!(o instanceof AudioTrack track))
            return BotEmbed.BASE.getBuilder(null);

        User user = (User) track.getUserData();
        AudioTrackInfo info = track.getInfo();

        EmbedBuilder builder = BASE_USER.getBuilder(user)
                .setAuthor("Now playing", user.getAvatarUrl())
                .setTitle("`" + info.title + "`", info.uri)

                .addField("Demandé par", user.getAsMention(), true)
                .addField("Auteur", info.author, true)
                .addField("Durée", formatTime(track.getDuration()), true)
                ;
        return builder;
    }),

    SPOTIFY(o -> {
        if (!(o instanceof Member member))
            return BotEmbed.BASE.getBuilder(null);
        EmbedBuilder builder = BotEmbed.BASE.getBuilder(null);
        List<Activity> activities = member.getActivities();
        for (Activity a : activities) {
            if (a.getName().equalsIgnoreCase("Spotify")) {
                addActivitySpotify(builder, a, member);
            }
        }
        return builder;
    }),
    ;

    private final Function<Object, EmbedBuilder> f;

    BotEmbed(Function<Object, EmbedBuilder> function) {
        f = function;
    }

    public EmbedBuilder getBuilder(Object o) {
        return f.apply(o);
    }

    private static void addActivitySpotify(EmbedBuilder builder, Activity a, Member member) {
        RichPresence rp = a.asRichPresence();
        if (rp != null) {
            try {
                builder.setImage(Objects.requireNonNull(rp.getLargeImage()).getUrl());
            } catch (NullPointerException ignored) {}
            String message = member.getUser().getName() + " écoute " + rp.getDetails() + " de " + rp.getState();
            builder.setFooter(message, member.getUser().getAvatarUrl());
        }
    }

    private static String formatTime(long duration) {
        long hours = duration / TimeUnit.HOURS.toMillis(1);
        long minutes = duration / TimeUnit.MINUTES.toMillis(1);
        long secondes = duration % TimeUnit.MILLISECONDS.toMillis(1) / TimeUnit.SECONDS.toMillis(1);

        return String.format("%02d:%02d:%02d", hours, minutes, secondes);
    }
}
