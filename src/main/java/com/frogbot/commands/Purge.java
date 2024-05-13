package com.frogbot.commands;

import com.frogbot.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class Purge implements ICommand {
    @Override
    public String getName() {
        return "purge";
    }

    @Override
    public String getDescription() {
        return "Очищает чат";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.INTEGER, "num", "Кол-во сообщений для удаления", true));
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if(!(event.getMember().getRoles().contains(guild.getRoleById("1018228662446850108")) || event.getMember().getRoles().contains(guild.getRoleById("1018228769611333722"))))
        {
            event.reply("У тебя недостаточно прав для использования этой команды").setEphemeral(true).queue();
            return;
        }
        OptionMapping numArg = event.getOption("num");
        long num = numArg.getAsLong();
        if(num > 100)
        {
            event.reply("Кол-во сообщений не может быть больше 100").setEphemeral(true).queue();
            return;
        }
        if(num < 0)
        {
            event.reply("Кол-во сообщений не может быть отрицательным").setEphemeral(true).queue();
            return;
        }
        List<Message> messages = event.getChannel().getHistory().retrievePast((int) num).complete();
        event.getChannel().purgeMessages(messages);
        event.reply("Успешно удалено " + num + " сообщений").setEphemeral(true).queue();
    }
}
