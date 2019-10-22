package de.karasuma.discordbot.pokewiki;

import java.util.HashMap;

import javax.security.auth.login.LoginException;

import de.karasuma.discordbot.pokewiki.consolecommands.ConsoleCommand;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class DiscordBot {

	private Main main;
	private JDABuilder builder;
	private JDA jda;

	protected final Activity.ActivityType DEFAULT_ACTIVITY_TYPE = Activity.ActivityType.DEFAULT;
	protected final String DEFAULT_ACTIVITY_NAME = "Empfangskomitee";
	private Activity.ActivityType activityType = DEFAULT_ACTIVITY_TYPE;
	private String activityName = DEFAULT_ACTIVITY_NAME;
	private HashMap<String, ConsoleCommand> consoleCommands;
	private String name;

	public DiscordBot(Main main, String name) {
		this.setMain(main);
		this.name = name;
	}

	public void init() {

	}

	public String shutDownBot() {
		if (jda != null && jda.getStatus() == JDA.Status.SHUTDOWN) {
			return "Bot is already OFFLINE";
		}
		jda.shutdown();

		if (allBotsOffline()) {
			return "All bots OFFLINE. Type 'exit' to close application";
		}

		return "Bot has been shut down";
	}

	private boolean allBotsOffline() {
		boolean allOffline = true;
		for (DiscordBot bot : getMain().getBots().values()) {
			if (bot.getJDA().getStatus() != JDA.Status.SHUTDOWN) {
				allOffline = false;
			}
		}
		return allOffline;
	}

	public String startBot() {
		if (jda != null && jda.getStatus() == JDA.Status.CONNECTED) {
			return "Bot is already ONLINE";
		}
		try {
			jda = getBuilder().build();
			jda.awaitReady();
		} catch (LoginException | InterruptedException e1) {
			e1.printStackTrace();
		}
		return "Bot has been successfully started";
	}

	private void updateGameStatus() {
		if(getBuilder() != null && activityType != null && activityName != null) {
			if (jda != null) {
				jda.getPresence().setActivity(Activity.of(activityType, activityName));
			}
			getBuilder().setActivity(Activity.of(activityType, activityName));
			getMain().getFileReaderAndWriter().createJSONFile(activityType, activityName);
		}
	}

	public HashMap<String, ConsoleCommand> getConsoleCommands() {
		return consoleCommands;
	}
	public void setActivityName(String input) {
		activityName = input;
		updateGameStatus();
	}
	public String getActivityName() {
		return activityName;
	}

	@Override
	public String toString() {
		return name;
	}

	public JDA getJDA() {
		return jda;
	}

	public Main getMain() {
		return main;
	}

	public void setMain(Main main) {
		this.main = main;
	}

	public JDABuilder getBuilder() {
		return builder;
	}

	public void setBuilder(JDABuilder builder) {
		this.builder = builder;
	}

	public void setConsoleCommands(HashMap<String, ConsoleCommand> consoleCommands) {
		this.consoleCommands = consoleCommands;
	}

	public void setActivityType(Activity.ActivityType activityType) {
		this.activityType = activityType;
		updateGameStatus();
	}
}
