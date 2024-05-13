package com.frogbot.commands;

import com.frogbot.ICommand;
import com.frogbot.UserConfig;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.List;

public class Daily implements ICommand {
    @Override
    public String getName() {
        return "daily";
    }

    @Override
    public String getDescription() {
        return "Ежедневная награда";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        long today = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toEpochSecond() * 1000;
        UserConfig uc = new UserConfig(event.getMember().getId());
        try {
            uc.init();
            long timeout = 86400000;
            long daily = (Long) uc.getValue("daily");
            if (timeout - (today - daily) > 0) {
                event.reply("Вы уже брали ежедневную награду! Возвращайтесь позже").queue();
                uc.setValue("daily", today);
                return;
            }
            uc.setValue("coins", (Long) uc.getValue("coins") + 100);
            event.reply("Вы взяли ежедневную награду").queue();
            uc.setValue("daily", today);
        }catch(Exception ex){System.out.println(ex);}
    }
}
