package com.frogbot.commands;

import com.frogbot.ICommand;
import com.frogbot.UserConfig;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Profile implements ICommand {
    @Override
    public String getName() {
        return "profile";
    }

    @Override
    public String getDescription() {
        return "Показывает профиль пользователя";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "Пользователь, профиль которого нужно показать", true));
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        OptionMapping argUser = event.getOption("user");
        User user = argUser.getAsUser();
        if(user == null){
            event.reply("Пользователь не найден");
            return;
        }
        UserConfig uc = new UserConfig(user.getId());
        try {
            uc.init();
            String name = (String) uc.getValue("name");
            String description = (String) uc.getValue("description");
            long age = (Long) uc.getValue("age");
            long coins = (long) uc.getValue("coins");
            long warns = (long) uc.getValue("warns");
            String title = (name.equals("")) ? user.getName() : name + " (" + user.getName() + ")";
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(title)
                    .setDescription(description)
                    .setColor(new Color(50, 255, 180))
                    .setThumbnail(user.getAvatarUrl())
                    .addField("Койны", Long.toString(coins), false);
            if(age != 0L) embed.addField("Возраст", Long.toString(age), false);
            if(warns != 0L) embed.addField("Варны", Long.toString(warns), false);
            event.replyEmbeds(embed.build()).queue();
        }catch (Exception ex) {System.out.println(ex);}
    }
}
