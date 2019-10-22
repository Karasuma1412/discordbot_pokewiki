package de.karasuma.discordbot.conannews.consolecommands;

import de.karasuma.discordbot.conannews.DiscordBot;

public class ConsoleCommandUpdateActivityName implements ConsoleCommand {
	
	private DiscordBot bot;

	public ConsoleCommandUpdateActivityName(DiscordBot bot) {
		this.bot = bot;
	}

	@Override
	public String execute(String input) {
		String oldGameName = bot.getActivityName();
		try {
			bot.setActivityName(input.trim());
		} catch (IllegalArgumentException e) {
			bot.setActivityName(oldGameName);
			return "Invalide Input";			
		}		
		return "Updated game name to " + input;
	}

	@Override
	public String getDescription() {
		return "Update game name of " + bot.toString();
	}
}
