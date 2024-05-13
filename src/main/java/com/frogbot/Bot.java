package com.frogbot;

import com.frogbot.commands.*;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;

import java.io.FileReader;
import java.util.Collections;
import java.util.Scanner;

public class Bot extends ListenerAdapter
{
    public static void main(String[] args) throws Exception
    {
        FileReader fr = new FileReader("token.txt");
        Scanner scan = new Scanner(fr);
        String token = scan.nextLine();
        scan.close();
        fr.close();
        JDA jda = JDABuilder.createLight(token, Collections.emptyList())
                .addEventListeners(new Bot())
                .setActivity(Activity.playing("/ping"))
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MESSAGES)
                .build();
        CommandManager cm = new CommandManager();
        cm.addCommand(new Ping());
        cm.addCommand(new Random());
        cm.addCommand(new Frog());
        cm.addCommand(new Say());
        cm.addCommand(new Purge());
        cm.addCommand(new Warn());
        cm.addCommand(new RemoveWarn());
        cm.addCommand(new Pay());
        cm.addCommand(new SetProfile());
        cm.addCommand(new Profile());
        cm.addCommand(new Daily());
        cm.addCommand(new SendEmbed());
        cm.addCommand(new Avatar());
        jda.addEventListener(cm);
        jda.addEventListener(new Listeners());
    }
}