package com.frogbot.commands;

import com.frogbot.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Frog implements ICommand {
    @Override
    public String getName() {
        return "frog";
    }

    @Override
    public String getDescription() {
        return "Случайная картинка с жабой";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        int num = (int) (10 + Math.random() * 55);
        String url = "http://www.allaboutfrogs.org/funstuff/random00/" + num + ".jpg";
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(50, 255, 180))
                .setImage(url)
                .setTitle("Жаба");
        event.replyEmbeds(eb.build()).queue();
    }
}
