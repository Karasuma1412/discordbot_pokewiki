package de.karasuma.discordbot.pokewiki.consolecommands;

import de.karasuma.discordbot.pokewiki.DiscordBot;

public class ConsoleCommandStatus implements ConsoleCommand {

	private ConsoleCommandListenerRunnable consoleCommandListener;

	public ConsoleCommandStatus(ConsoleCommandListenerRunnable consoleCommandListener) {
		this.consoleCommandListener = consoleCommandListener;
	}

	@Override
	public String execute(String input) {
		StringBuilder message = new StringBuilder();
		for (DiscordBot bot : consoleCommandListener.getMain().getBots().values()) {
			//Print online status of all running bots
			message.append(bot.toString()).append(": ").append(bot.getJDA().getStatus()).append("\n");
		}
		return message.toString();
	}

	@Override
	public String getDescription() {
		return "Shows online status of all Bots";
	}

}
