package de.karasuma.discordbot.conannews;

import java.util.HashMap;

import de.karasuma.discordbot.conannews.commandhandling.CommandHandler;
import de.karasuma.discordbot.conannews.commandhandling.CommandListener;
import de.karasuma.discordbot.conannews.consolecommands.*;
import net.dv8tion.jda.api.AccountType;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;

public class WikiBot extends DiscordBot{

	private final String ACTIVITY_NAME = "PokeWiki";

	public WikiBot(Main main, String name) {		
		super(main, name);
	}
	
	@Override
	public void init() {
		setBuilder(new JDABuilder(AccountType.BOT));
		getBuilder().setToken(Token.TOKEN_TEST);
		getBuilder().setAutoReconnect(true);
		getBuilder().addEventListeners(new CommandListener());

		getBuilder().setActivity(Activity.of(DEFAULT_ACTIVITY_TYPE, ACTIVITY_NAME));

		addCommands();
		setupConsoleCommands();
		startBot();
	}
	
	private void addCommands() {
		CommandHandler.commands.put("wiki", new CommandWiki(this));
		CommandHandler.commands.put("Wiki", new CommandWiki(this));
		CommandHandler.commands.put("WIKI", new CommandWiki(this));
		CommandHandler.commands.put("w", new CommandWiki(this));
		CommandHandler.commands.put("W", new CommandWiki(this));
	}
	
	private void setupConsoleCommands() {
		setConsoleCommands(new HashMap<>());
		getConsoleCommands().put("wiki_stop", new ConsoleCommandStop(this));
		getConsoleCommands().put("wiki_start", new ConsoleCommandStart(this));
		getConsoleCommands().put("wiki_update_gamename", new ConsoleCommandUpdateActivityName(this));
		getConsoleCommands().put("wiki_update_gametype", new ConsoleCommandUpdateActivityType(this));
	}

}
