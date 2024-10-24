package reminator.RemiBot.commands;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.utils.FileUpload;
import reminator.RemiBot.commands.enums.Category;
import reminator.RemiBot.commands.manager.Command;
import reminator.RemiBot.commands.manager.CommandExecutedEvent;
import reminator.RemiBot.utils.RandomImagePicker;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.util.List;

public class BilalCommand implements Command {

    private RandomImagePicker randomImagePicker;

    {
        try {
            randomImagePicker = new RandomImagePicker("images");
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
    }

    @Override
    public Category getCategory() {
        return Category.BILAL;
    }

    @Override
    public String getLabel() {
        return "bilal";
    }

    @Override
    public String[] getAlliass() {
        return new String[]{"b"};
    }

    @Override
    public String getDescription() {
        return "Affiche une super photo de Bilal tirée au hasard :heart:";
    }

    @Override
    public void execute(CommandExecutedEvent event) {

        MessageChannel channel = event.getTextChannel();

        /*
        if (!event.isFromGuild()) {
            EnvoiMessage.sendMessage(event, "Tu ne peux pas faire ça en privé.");
            return;
        }
         */

        EmbedBuilder embed = new EmbedBuilder();
        InputStream file;
        try {
            file = new FileInputStream(randomImagePicker.getRandomImage());
            embed.setImage("attachment://bilal.png") // we specify this in sendFile as "cat.png"
                    .setDescription("BILAAAL :heart:");
            channel.sendFiles(FileUpload.fromData(file, "bilal.png")).setEmbeds(embed.build()).queue();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
