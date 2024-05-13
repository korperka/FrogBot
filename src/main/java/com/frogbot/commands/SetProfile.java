package com.frogbot.commands;

import com.frogbot.ICommand;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import net.dv8tion.jda.api.interactions.components.ActionRow;
import net.dv8tion.jda.api.interactions.components.text.TextInput;
import net.dv8tion.jda.api.interactions.components.text.TextInputStyle;
import net.dv8tion.jda.api.interactions.modals.Modal;

import java.util.ArrayList;
import java.util.List;

public class SetProfile implements ICommand {
    @Override
    public String getName() {
        return "setprofile";
    }

    @Override
    public String getDescription() {
        return "Устанавливает профиль";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> data = new ArrayList<>();
        return data;
    }

    @Override
    public void handle(SlashCommandInteractionEvent event) {
        TextInput name = TextInput.create("name", "Имя", TextInputStyle.SHORT)
                .setRequired(false)
                .setMinLength(1)
                .setMaxLength(16)
                .setPlaceholder("Олег...")
                .build();

        TextInput description = TextInput.create("description", "Описание", TextInputStyle.PARAGRAPH)
                .setRequired(true)
                .setMinLength(1)
                .setMaxLength(500)
                .setPlaceholder("Опишите себя")
                .build();

        TextInput age = TextInput.create("age", "Возраст", TextInputStyle.SHORT)
                .setRequired(false)
                .setMinLength(1)
                .setMaxLength(2)
                .build();

        Modal modal = Modal.create("profile", "Профиль")
                .addActionRows(ActionRow.of(name), ActionRow.of(description), ActionRow.of(age))
                .build();

        event.replyModal(modal).queue();
        //->Listeners
    }
}
