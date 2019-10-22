package de.karasuma.discordbot.conannews;

import de.karasuma.discordbot.conannews.commandhandling.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class CommandWiki implements Command {
	
	private String pokeWikiBaseURL = "https://www.pokewiki.de/";	
	private WikiBot wikibot;

	public CommandWiki(WikiBot wikiBot) {
		this.wikibot = wikiBot;
	}

	public boolean called(String[] args, MessageReceivedEvent event) {
		return false;
	}

	public void action(String[] args, MessageReceivedEvent event) {
		StringBuilder builder = new StringBuilder();
		String[] urls = null;
		builder.append(pokeWikiBaseURL);
		String indicatorTag = "";
		
		if (args.length > 0) {
			builder.append(args[0]);
			for (int i = 1; i < args.length; i++) {
				builder.append("_").append(args[i]);
			}
			String urlTemp = builder.toString();
			urls = urlTemp.split("#");
			
			// remove last char if it is _
			String lastChar = urls[0].substring(urls[0].length() - 1);
			if (lastChar.equals("_")) {
				String temp = urls[0].substring(0, urls[0].length() - 1);
				System.out.println(temp);
				urls[0] = temp;
			}
			
			
			if (urls.length > 1) {
				indicatorTag = searchForIDInWebsite(urls);
			}		
		}
		assert urls != null;
		event.getTextChannel().sendMessage(urls[0] + indicatorTag).queue();
	}

	private String searchForIDInWebsite(String[] urls) {
		BufferedReader br = null;
		String indicatorTag = "";
		
		try {
			URL url = new URL(urls[0]);
			br = new BufferedReader(new InputStreamReader(url.openStream(), StandardCharsets.UTF_8));
			String line = br.readLine();
			
			while (line != null) {
				if (line.contains("id=") && line.toLowerCase().contains(urls[1].toLowerCase())){
					System.out.println(line.toLowerCase());
					System.out.println(urls[1]);
					int indexOfID = line.indexOf("id=");
					int indexOfFirstQuote = line.indexOf("\"", indexOfID);
					int indexOfSecondQuote = line.indexOf("\"", indexOfFirstQuote + 1);
					indicatorTag = line.substring(indexOfFirstQuote + 1, indexOfSecondQuote);
					return "#" + indicatorTag;
				}
				
				line = br.readLine();
			}
			return indicatorTag;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return indicatorTag;
	}

	public void executed(boolean sucess, MessageReceivedEvent event) {

	}

	public String help() {
		return null;
	}


}
