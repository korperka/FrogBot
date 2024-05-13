package com.frogbot;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.ModalInteractionEvent;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.events.interaction.component.ButtonInteractionEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionMapping;
import net.dv8tion.jda.api.interactions.commands.SlashCommandInteraction;

import java.awt.*;
import java.util.concurrent.TimeUnit;

public class Listeners extends ListenerAdapter {
    public Member member;
    public SlashCommandInteraction interaction;
    @Override
    public void onButtonInteraction(ButtonInteractionEvent event) {
        try {
            UserConfig uc = new UserConfig(event.getMember().getId());
        if(!event.getMember().equals(interaction.getMember())) {
            event.getUser().openPrivateChannel().complete().sendMessage("Ещё чего \uD83D\uDC80").queue();
            return;
        }
        if(event.getButton().getId().equals("warns-ban")) {
            event.getMessage().delete().queue();
            member.ban(1, TimeUnit.HOURS).queue();
            event.reply(member.getAsMention() + " был забанен по причине: 3/3 варнов").queue();
            uc.setValue("warns", 0);
        }
        if(event.getButton().getId().equals("not-warns-ban")) {
            event.getMessage().delete().queue();
            event.reply("Ладно, пусть живёт").setEphemeral(true).queue();
        }
        }catch(Exception ex) {
            System.out.println(ex);
        }
    }
    @Override
    public void onSlashCommandInteraction(SlashCommandInteractionEvent event) {
        UserConfig uc = new UserConfig(event.getMember().getId());
        try {uc.init();}
        catch(Exception ex) {System.out.println(ex);}
        if(event.getName().equals("warn")) {
            OptionMapping mapping = event.getOption("user");
            member = mapping.getAsMember();
            interaction = event.getInteraction();
        }
    }

    @Override
    public void onModalInteraction(ModalInteractionEvent event) {
        if(event.getModalId().equals("profile")) {
            UserConfig uc = new UserConfig(event.getMember().getId());
            String name = event.getValue("name").getAsString();
            String description = event.getValue("description").getAsString();
            String age = event.getValue("age").getAsString();
            try {
                if (!name.contains(" ")) uc.setValue("name", name);
                if(!age.contains(" ") && age.matches("-?\\d+(\\.\\d+)?") && Long.parseLong(age)>0) uc.setValue("age", Long.parseLong(age));
                uc.setValue("description", description);
            }catch(Exception ex){System.out.println(ex);}
            event.reply("Данные установлены! Используй /profile чтобы увидеть профиль").setEphemeral(true).queue();
        }
        if(event.getModalId().equals("send-embed")){
            EmbedBuilder embed = new EmbedBuilder()
                    .setTitle(event.getValue("embed-title").getAsString())
                    .setDescription(event.getValue("embed-desc").getAsString())
                    .setColor(new Color(50, 255, 180));
            event.getChannel().sendMessageEmbeds(embed.build()).queue();
            event.reply("Сообщение отправлено!").setEphemeral(true).queue();
        }
    }
}
