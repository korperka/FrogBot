package com.frogbot.commands;

import com.frogbot.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class Say implements ICommand {
    @Override
    public String getName() {
        return "say";
    }

    @Override
    public String getDescription() {
        return "Пишет текст от лица бота";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.STRING, "text", "Текст, который скажет бот", true));
        return data;
    }


    @Override
    public void handle(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if(!(event.getMember().getRoles().contains(guild.getRoleById("1018228662446850108")) || event.getMember().getRoles().contains(guild.getRoleById("1018228769611333722")))) {
            event.reply("У тебя недостаточно прав для использования этой команды").setEphemeral(true).queue();
            return;
        }
        OptionMapping textArg = event.getOption("text");
        String text = textArg.getAsString();
        event.getChannel().sendMessage(text).queue();
        event.reply("Сообщение отправлено!").setEphemeral(true).queue();
    }
}
