package reminator.RemiBot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import org.json.JSONObject;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.HTTPRequest;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Mateo implements Command {

    private final List<MessageChannel> ecoutes = new ArrayList<>();
    Timer timer;

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "mateo";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"m", "dream-plume", "dp", "d-p", "gatel", "matel", "matel-gatel", "mateo-gatel", "mg", "m-g"};
    }

    @Override
    public String getDescription() {
        return "Commande de Matéo";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        User author = event.getAuthor();
        MessageChannel channel = event.getChannel();

        if (author.getIdLong() != 264490592610942976L && author.getIdLong() != 329712193249476609L) {
            channel.sendMessage("Commande réservée à Matéo !").queue();
            return;
        }
        User user = author;

        if (ecoutes.contains(channel)) {
            channel.sendMessage("Arrêt de la commande").queue();
            timer.cancel();
            timer.purge();
            ecoutes.remove(channel);
        } else {
            ecoutes.add(channel);
            channel.sendMessage("Début de la commande").queue();
            final String[] idSave = {""};

            timer = new Timer();
            timer.schedule(new TimerTask() {
                @Override
                public void run() {


                    try {
                        String p = new HTTPRequest("https://twitter.com/i/api/graphql/L15nBTK_B0O_NMEpnsH4MQ/UserTweets?variables=%7B%22userId%22%3A%22611141533%22%2C%22count%22%3A20%2C%22withHighlightedLabel%22%3Atrue%2C%22withTweetQuoteCount%22%3Atrue%2C%22includePromotedContent%22%3Atrue%2C%22withTweetResult%22%3Afalse%2C%22withReactions%22%3Afalse%2C%22withUserResults%22%3Afalse%2C%22withVoice%22%3Afalse%2C%22withNonLegacyCard%22%3Atrue%2C%22withBirdwatchPivots%22%3Afalse%7D")
                                .withHeader("accept-language", "fr-FR,fr;q=0.9,en-US;q=0.8,en;q=0.7")
                                .withHeader("authorization", "Bearer AAAAAAAAAAAAAAAAAAAAANRILgAAAAAAnNwIzUejRCOuH5E6I8xnZz4puTs%3D1Zv7ttfk8LF81IUq16cHjhLTvJu4FA33AGWWjCpTnA")
                                .withHeader("cookie", "personalization_id=\"v1_oRZugRsMECI++UDH3RMehQ==\"; guest_id=v1%3A162085596435924474; ct0=e368e290cf71d6a224ee53f9f3e3cc1d; _sl=1; gt=1395068176887697413; _twitter_sess=BAh7CSIKZmxhc2hJQzonQWN0aW9uQ29udHJvbGxlcjo6Rmxhc2g6OkZsYXNo%250ASGFzaHsABjoKQHVzZWR7ADoPY3JlYXRlZF9hdGwrCFV6qYV5AToMY3NyZl9p%250AZCIlMmQxNjUxYjg2NTMzNjdlODk1ODQ4YTg5NTM4NTAwMGI6B2lkIiVlYzQz%250ANjYwZjU0ZTRkNDFiMmQ3ZTkwMzlkYjg0MTgzMg%253D%253D--58c2ea640e03f262e4068570c5242774c9fefded")
                                .withHeader("referer", "https://twitter.com/JulieOudet")
                                .withHeader("sec-ch-ua", "\" Not A;Brand\";v=\"99\", \"Chromium\";v=\"90\", \"Google Chrome\";v=\"90\"")
                                .withHeader("sec-ch-ua-mobile", "?0")
                                .withHeader("sec-fetch-dest", "empty")
                                .withHeader("sec-fetch-mode", "cors")
                                .withHeader("sec-fetch-site", "same-origin")
                                .withHeader("user-agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/90.0.4430.212 Safari/537.36")
                                .withHeader("x-csrf-token", "e368e290cf71d6a224ee53f9f3e3cc1d")
                                .withHeader("x-guest-token", "1395068176887697413")
                                .withHeader("x-twitter-active-user", "yes")
                                .withHeader("x-twitter-client-language", "fr")
                                .GET();
                        System.out.println(p);

                        JSONObject page = new JSONObject(p);
                        String conversation = page.getJSONObject("timeline").getString("id");
                        String id = conversation.split("-")[1];
                        if (!idSave[0].equals(id)) {
                            idSave[0] = id;
                            JSONObject tweet = page.getJSONObject("globalObjects").getJSONObject("tweets").getJSONObject(id);


                            EmbedBuilder builder = new EmbedBuilder();
                            builder.setColor(Color.RED);
                            builder.setTitle("Julie Oudet");
                            builder.addField("Nouveau / dernier tweet", tweet.getString("full_text"), false);
                            builder.setFooter(user.getName(), user.getAvatarUrl());
                            channel.sendMessageEmbeds(builder.build()).queue();
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }, 0, 5000);
        }
    }
}
