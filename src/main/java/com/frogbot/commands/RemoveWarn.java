package com.frogbot.commands;

import com.frogbot.ICommand;
import com.frogbot.UserConfig;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class RemoveWarn implements ICommand {
    @Override
    public String getName() {
        return "removewarn";
    }

    @Override
    public String getDescription() {
        return "Убирает варн у пользователя";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "Пользователь, у которого надо убрать варн", true));
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if(!(event.getMember().getRoles().contains(guild.getRoleById("1236997211955531837")) || event.getMember().getRoles().contains(guild.getRoleById("1018228769611333722")))) {
            event.reply("У тебя недостаточно прав для использования этой команды").setEphemeral(true).queue();
            return;
        }
        OptionMapping argUser = event.getOption("user");
        Member unWarnMember = argUser.getAsMember();
        if(unWarnMember == null) {
            event.reply("Пользователь не найден").setEphemeral(true).queue();
            return;
        }
        try {
            UserConfig uc = new UserConfig(unWarnMember.getId());
            long warns = (Long) uc.getValue("warns")-1;
            if(warns<0){
                event.reply("У пользователя нету варнов").setEphemeral(true).queue();
                return;
            }
            uc.setValue("warns", warns);
        }catch(Exception ex){
            System.out.println(ex);
        }
    }
}
