package com.frogbot.commands;

import com.frogbot.ICommand;
import com.frogbot.UserConfig;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.ArrayList;
import java.util.List;

public class Pay implements ICommand {
    @Override
    public String getName() {
        return "pay";
    }

    @Override
    public String getDescription() {
        return "Передаёт койны пользователю";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        data.add(new OptionData(OptionType.USER, "user", "Пользователь, которому нужно передать койны", true));
        data.add(new OptionData(OptionType.INTEGER, "coins", "Кол-во койнов, которые нужно передать", true));
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        OptionMapping argUser = event.getOption("user");
        Member payMember = argUser.getAsMember();
        OptionMapping argCoins = event.getOption("coins");
        int coins = argCoins.getAsInt();
        if(coins<0){
            event.reply("Нельзя передать отрицательное кол-во койнов").setEphemeral(true).queue();
            return;
        }
        if(payMember.getUser().isBot()){
            event.reply("Нельзя передать койны боту").setEphemeral(true).queue();
            return;
        }
        if(payMember.equals(event.getMember())){
            event.reply("Нельзя передать койны самому себе").setEphemeral(true).queue();
            return;
        }
        try{
            UserConfig payUc = new UserConfig(payMember.getId());
            UserConfig uc = new UserConfig(event.getMember().getId());
            long ucCoins = (Long) uc.getValue("coins");
            long payCoins = (Long) payUc.getValue("coins");
            if(ucCoins<coins){
                event.reply("У тебя недостаточно койнов").setEphemeral(true).queue();
                return;
            }
            payUc.setValue("coins", payCoins + coins);
            uc.setValue("coins", ucCoins - coins);
            event.reply(payMember.getAsMention() + " были переданы " + coins + " койнов от " + event.getMember().getAsMention()).queue();
        }
        catch (Exception ex) {System.out.println(ex);}
    }
}
