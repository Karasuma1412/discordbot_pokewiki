package de.karasuma.discordbot.pokewiki.commandhandling;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

public class CommandListener extends ListenerAdapter {

    public void onMessageReceived(MessageReceivedEvent event) {
        if(event.getMessage().getContentRaw().startsWith(CommandParser.PREFIX)
                && !event.getMessage().getAuthor().getId().equals(event.getJDA().getSelfUser().getId())) {
            CommandHandler.handleCommand(CommandParser.parse(event.getMessage().getContentRaw(), event));
        }
    }

}
