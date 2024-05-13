package com.frogbot.commands;

import com.frogbot.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class Ping implements ICommand {
    @Override
    public String getName() {
        return "ping";
    }

    @Override
    public String getDescription() {
        return "Понг!";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        long time = System.currentTimeMillis();
        event.reply("Понг:").setEphemeral(true)
                .flatMap(v ->
                        event.getHook().editOriginalFormat("Понг: %d мс", System.currentTimeMillis() - time)
                ).queue();
    }
}
