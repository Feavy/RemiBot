package reminator.RemiBot.Commands.Japonais;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Commands.enums.Category;
import reminator.RemiBot.Commands.Command;
import reminator.RemiBot.Commands.Japonais.enums.Hiragana;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.*;

public class HiraganaCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.JAPONAIS;
    }

    @Override
    public String getPrefix() {
        return Command.super.getPrefix();
    }

    @Override
    public String getLabel() {
        return "hiragana";
    }

    @Override
    public String[] getAlliass() {
        return new String[0];
    }

    @Override
    public String getDescription() {
        return "Le premier qui trouve 10 hiragana gagne la partie !";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {

        long channelId = channel.getIdLong();
        Map<User, Integer> scores = new HashMap<>();

        final EmbedBuilder[] embedBuilder = {new EmbedBuilder().setTitle("Hiragana game ! Le premier qui trouve 10 hiragana gagne la partie !").setDescription("Que le meilleur gagne !")};
        EnvoiMessage.sendMessage(event, embedBuilder[0].build());
        final Hiragana[] hiragana = {Hiragana.getRandom()};

        embedBuilder[0] = new EmbedBuilder().setTitle(hiragana[0].japonais()).setDescription("Comment écrit-on ce hiragana en rômaji ?");
        EnvoiMessage.sendMessage(event, embedBuilder[0].build());


        event.getJDA().addEventListener(new ListenerAdapter() {
            @Override
            public void onMessageReceived(@NotNull MessageReceivedEvent event) {
                if (event.getAuthor().isBot()) return;
                if (event.getChannel().getIdLong() != channelId) return;

                String msg = event.getMessage().getContentRaw();
                User user = event.getAuthor();

                if(hiragana[0].roomaji().equalsIgnoreCase(msg)) {
                    EnvoiMessage.sendMessage(event, "** Bravo " + user.getAsMention() + "** :partying_face:\n" + hiragana[0].japonais() + " se lit bien " + hiragana[0].roomaji());
                    int score;
                    if (scores.containsKey(user)) {
                        score = scores.get(user) + 1;
                        scores.replace(user, score);
                    } else {
                        score = 1;
                        scores.put(user, score);
                    }
                    EnvoiMessage.sendMessage(event, user.getAsMention() + " a maintenant " + score + " points !");
                    if (score == 10) {
                        EnvoiMessage.sendMessage(event, user.getAsMention() + " a gagné !!! :partying_face: :partying_face: :partying_face:");
                        EmbedBuilder classement = new EmbedBuilder().setTitle("Classement des joueurs de la partie");
                        StringBuilder s = new StringBuilder();
                        for(Map.Entry<User, Integer> e : scores.entrySet()) {
                            s.append(e.getKey().getAsMention()).append(" : ").append(e.getValue()).append("\n");
                        }
                        classement.addField("Test", s.toString(), false);
                        EnvoiMessage.sendMessage(event, classement.build());
                        event.getJDA().removeEventListener(this);
                    } else {
                        hiragana[0] = Hiragana.getRandom();
                        embedBuilder[0] = new EmbedBuilder().setTitle(hiragana[0].japonais()).setDescription("Comment écrit-on ce hiragana en rômaji ?");
                        EnvoiMessage.sendMessage(event, embedBuilder[0].build());
                    }
                    return;
                }

                if(msg.equalsIgnoreCase("r!hiragana")) {
                    event.getJDA().removeEventListener(this);
                    return;
                }
            }
        });
    }
}