package reminator.RemiBot.commands.perso;

import de.svenjacobs.loremipsum.LoremIpsum;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.entities.User;
import reminator.RemiBot.Services.reactionpersonne.Users;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.*;

public class RemiSpam implements Command {

    private final List<MessageChannel> ecoutes = new ArrayList<>();
    private Timer timerSpam;
    private LoremIpsum lorem = new LoremIpsum();

    @Override
    public Category getCategory() {
        return Category.PERSO;
    }

    @Override
    public String getLabel() {
        return "remi-spam";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"rs"};
    }

    @Override
    public String getDescription() {
        return "Reminator spam.";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        MessageChannel channel = event.getChannel();
        User author = event.getAuthor();

        if (!author.getId().equals(Users.REMINATOR.getId())) {
            channel.sendMessage("Commande réservée à Rémi !").queue();
            return;
        }

        if (ecoutes.contains(channel)) {
            channel.sendMessage("Arrêt du spam").queue();
            ecoutes.remove(channel);
            timerSpam.cancel();
            timerSpam.purge();
        } else {
            channel.sendMessage("Début du spam").queue();
            ecoutes.add(channel);

            timerSpam = new Timer();
            timerSpam.schedule(new TimerTask() {
                @Override
                public void run() {
                    String content = lorem.getWords(1, new Random().nextInt(50));
                    String nonce = "";
                    String payload = "{\"content\": \"" + content + "\", \"nonce\": \"" + nonce + "\", \"tts\": \"false\"}";
                    String idChannel = channel.getId();
                    String requestUrl="https://discord.com/api/v9/channels/" + idChannel + "/messages";
                    sendPostRequest(requestUrl, payload);
                }
            }, 0, (long) (2.2  * 1000));
        }
    }

    public String sendPostRequest(String requestUrl, String payload) {
        StringBuilder jsonString = new StringBuilder();
        try {
            URL url = new URL(requestUrl);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();

            connection.setDoInput(true);
            connection.setDoOutput(true);
            connection.setRequestMethod("POST");
            connection.setRequestProperty("Accept", "application/json");
            connection.setRequestProperty("Content-Type", "application/json");
            connection.setRequestProperty("authority", "discord.com");
            connection.setRequestProperty("authorization", Users.REMINATOR.getAuthorization());
            connection.setRequestProperty("content-length", String.valueOf(payload.length()));
            OutputStreamWriter writer = new OutputStreamWriter(connection.getOutputStream(), "UTF-8");
            writer.write(payload);
            writer.close();
            System.out.println("bonjour");

            BufferedReader br = new BufferedReader(new InputStreamReader(connection.getInputStream()));
            System.out.println("test");
            String line;
            while ((line = br.readLine()) != null) {
                jsonString.append(line);
            }
            br.close();

            connection.disconnect();
        } catch (Exception ignored) {

        }
        return jsonString.toString();
    }
}
