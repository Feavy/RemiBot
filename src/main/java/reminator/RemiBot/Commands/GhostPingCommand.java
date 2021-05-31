package reminator.RemiBot.Commands;

import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.requests.restaction.MessageAction;
import org.jetbrains.annotations.NotNull;
import reminator.RemiBot.Categories.enums.Category;
import reminator.RemiBot.utils.EnvoiMessage;

import java.util.List;

public class GhostPingCommand implements Command {

    @Override
    public Category getCategory() {
        return Category.AUTRE;
    }

    @Override
    public String getLabel() {
        return "ghost-ping";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"g-p", "gp"};
    }

    @Override
    public String getDescription() {
        return "Envoie le message que vous voulez sur tous les salons puis les supprime";
    }

    @Override
    public void execute(@NotNull MessageReceivedEvent event, User author, MessageChannel channel, List<String> args) {
        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }
        if (event.getMember() == null) {
            return;
        }
        if (!event.getMember().getUser().getId().equals("368733622246834188")) {
            event.getChannel().sendMessage("Tu n'as pas la permission pour faire cette commande.").queue();
            return;
        }
        event.getMessage().delete().queue();
        Guild guild = event.getGuild();
        StringBuilder message = new StringBuilder();
        if (args.size() < 2) {
            message = new StringBuilder("Coucou");
        } else {
            for (int i = 1; i < args.size(); i++) {
                message.append(args.get(i)).append(" ");
            }
        }
        for (TextChannel c : guild.getTextChannels()) {
            MessageAction action = c.sendMessage(message);
            action.flatMap(Message::delete).queue();
        }
    }
}
