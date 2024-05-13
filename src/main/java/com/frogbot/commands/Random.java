package com.frogbot.commands;

import com.frogbot.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class Random implements ICommand {

    @Override
    public String getName() {
        return "random";
    }

    @Override
    public String getDescription() {
        return "Случайное число в указанном диапазоне";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.INTEGER, "min", "Минимум", true));
        data.add(new OptionData(OptionType.INTEGER, "max", "Максимум", true));
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        OptionMapping minArg = event.getOption("min");
        long min = minArg.getAsLong();
        OptionMapping maxArg = event.getOption("max");
        long max = maxArg.getAsLong();
        if(min > 2000000000 || max > 2000000000)
        {
            event.reply("Превышен лимит чисел").setEphemeral(true).queue();
            return;
        }
        if(min < 0 || max < 0)
        {
            event.reply("Числа не могут быть отрицательными").setEphemeral(true).queue();
            return;
        }
        double num = (int) (min + Math.random() * max);
        event.reply(Integer.toString((int) num)).queue();
    }
}
