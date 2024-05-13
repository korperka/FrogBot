package com.frogbot.commands;

import com.frogbot.ICommand;
import com.frogbot.UserConfig;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.User;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.buttons.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

public class Warn implements ICommand {
    @Override
    public String getName() {
        return "warn";
    }

    @Override
    public String getDescription() {
        return "Выдаёт варн пользователю";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "Пользователь которому нужно выдать варн", true));
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
        User warnUser = argUser.getAsUser();
        Member warnMember = argUser.getAsMember();
        if(warnMember == null) {
            event.reply("Пользователь не найден").setEphemeral(true).queue();
            return;
        }
        if(warnUser.isBot()){
            event.reply("Нельзя выдать варн боту").setEphemeral(true).queue();
            return;
        }
        if(warnUser.equals(event.getUser())){
            event.reply("Нельзя выдать варн самому себе").setEphemeral(true).queue();
            return;
        }
        if(warnMember.getRoles().contains(guild.getRoleById("1018228662446850108")) || warnMember.getRoles().contains(guild.getRoleById("1018228769611333722"))){
            event.reply("Нельзя выдать варн админу или модератору").setEphemeral(true).queue();
            return;
        }
       try {
           UserConfig uc = new UserConfig(warnMember.getId());
           long warns = (Long) uc.getValue("warns")+1;
           uc.setValue("warns", warns);
           String amount = (warns == 1) ? "варн" : "варна";
           if (warns < 3) {
               event.reply(warnUser.getAsMention() + " выдан варн. Теперь у него " + warns + " " + amount).queue();
           } else {
               Button yes = Button.success("warns-ban", "Да");
               Button no = Button.danger("not-warns-ban", "Нет");
               event.reply("У " + warnUser.getAsMention() + " " + warns + "/3 варна. Забанить его?")
                       .setActionRow(yes, no).queue();
               event.getInteraction().getHook().deleteOriginal().queueAfter(30, TimeUnit.SECONDS);
               //-->Listeners
           }
       }catch(Exception ex){
           System.out.println(ex);
       }
    }
}
