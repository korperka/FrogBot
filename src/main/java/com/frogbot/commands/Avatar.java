package com.frogbot.commands;

import com.frogbot.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Avatar implements ICommand {
    @Override
    public String getName() {
        return "avatar";
    }

    @Override
    public String getDescription() {
        return "Аватарка пользователя";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "Пользователь, аватарку которого нужно получить", true));
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        OptionMapping argUser = event.getOption("user");
        User user = argUser.getAsUser();
        if(user == null) {
            event.reply("Пользователь не найден").setEphemeral(true).queue();
            return;
        }
        if(user.getAvatarUrl() == null){
            event.reply("Аватарка не найдена").setEphemeral(true).queue();
            return;
        }
        EmbedBuilder eb = new EmbedBuilder()
                .setColor(new Color(50, 255, 180))
                .setImage(user.getAvatarUrl())
                .setTitle("Аватар " + user.getName());
        if(user.getId().equals("385053595013218304")) eb.setImage("https://cdn.discordapp.com/emojis/1023180117209530408.webp?size=96&quality=lossless");
        if(user.getId().equals("838013453922074645")) eb.setImage("https://media.discordapp.net/attachments/1018927331408085053/1077870506390323300/2021-07-10_14-21-46__289358c4-e18a-11eb-9b46-95b634acbe01.jpg");
        event.replyEmbeds(eb.build()).queue();
    }
}
