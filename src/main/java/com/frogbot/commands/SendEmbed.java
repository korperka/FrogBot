package com.frogbot.commands;

import com.frogbot.ICommand;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.ArrayList;
import java.util.List;

public class SendEmbed implements ICommand {
    @Override
    public String getName() {
        return "sendembed";
    }

    @Override
    public String getDescription() {
        return "Отправляет embed сообщение";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        Guild guild = event.getGuild();
        if(!(event.getMember().getRoles().contains(guild.getRoleById("1018228662446850108")) || event.getMember().getRoles().contains(guild.getRoleById("1018228769611333722")))) {
            event.reply("У тебя недостаточно прав для использования этой команды").setEphemeral(true).queue();
            return;
        }
        TextInput title = TextInput.create("embed-title", "Заголовок", TextInputStyle.SHORT)
                .setRequired(true)
                .setMinLength(1)
                .setMaxLength(30)
                .build();

        TextInput description = TextInput.create("embed-desc", "Описание", TextInputStyle.PARAGRAPH)
                .setRequired(true)
                .setMinLength(1)
                .setMaxLength(4000)
                .build();

        Modal modal = Modal.create("send-embed", "Embed")
                .addActionRows(ActionRow.of(title), ActionRow.of(description))
                .build();

        event.replyModal(modal).queue();
        //->Listeners
    }
}
