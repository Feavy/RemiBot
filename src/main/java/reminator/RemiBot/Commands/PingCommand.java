package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.guild.GuildMessageReceivedEvent;
import reminator.RemiBot.Categories.AutresCategorie;
import reminator.RemiBot.bot.RemiBot;

import java.awt.*;
import java.util.List;

public class PingCommand extends Command {

    public PingCommand() {
        this.setPrefix(RemiBot.prefix);
        this.setLabel("ping");
        this.setHelp(setHelp());
    }

    @Override
    public MessageEmbed setHelp() {
        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Commande ping");
        builder.appendDescription("Répond pong !");
        builder.addField("Signature", "`r!ping`", false);
        return builder.build();
    }

    @Override
    public void executerCommande(GuildMessageReceivedEvent event) {
        MessageChannel channel = event.getChannel();
        Member member = event.getMember();

        EmbedBuilder builder = new EmbedBuilder();
        builder.setColor(Color.RED);
        builder.setTitle("Pong !");

        if (member != null) {
            List<Activity> activities = member.getActivities();
            for (Activity a : activities) {
                if (a.getName().equalsIgnoreCase("Spotify")) {
                    RichPresence rp = a.asRichPresence();
                    if (rp != null) {
                        String message = member.getUser().getName() + " écoute " + rp.getDetails() + " de " + rp.getState();
                        builder.setFooter(message, member.getUser().getAvatarUrl());
                    }
                }
            }
        }

        channel.sendMessage(builder.build()).queue();
    }
}