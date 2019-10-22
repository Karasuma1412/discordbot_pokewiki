package de.karasuma.discordbot.pokewiki.consolecommands;

public interface ConsoleCommand {

	String execute(String input);

	String getDescription();

}
