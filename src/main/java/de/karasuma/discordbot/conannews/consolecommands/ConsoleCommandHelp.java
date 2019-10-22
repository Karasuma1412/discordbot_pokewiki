package de.karasuma.discordbot.conannews.consolecommands;
import java.util.HashMap;
import java.util.Map.Entry;

public class ConsoleCommandHelp implements ConsoleCommand {

	private ConsoleCommandListenerRunnable consoleCommandsListener;

	public ConsoleCommandHelp(ConsoleCommandListenerRunnable consoleCommandListener) {
		this.consoleCommandsListener = consoleCommandListener;
	}

	@Override
	public String execute(String input) {
		StringBuilder message = new StringBuilder();
		HashMap<String, ConsoleCommand> commands = consoleCommandsListener.getCommands();
		for (Entry<String, ConsoleCommand> key : commands.entrySet()) {
			message.append(key.getKey()).append(": ").append(key.getValue().getDescription()).append("\n");
		}
		return message.toString();
	}

	@Override
	public String getDescription() {
		return "Get a description for all available commands";
	}

}
