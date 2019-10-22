package de.karasuma.discordbot.pokewiki;

import de.karasuma.discordbot.pokewiki.commandhandling.Command;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;

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
		
		try {
			URL url = new URL(urls[0]);
			Document doc = Jsoup.parse(url, 5000);
			Elements spans = doc.select("span");
			for (Element span : spans) {
				String id = span.attr("id");
				if (id.toLowerCase().contains(urls[1].toLowerCase())) {
					return "#" + id;
				}
			}
			return "";
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}

	public void executed(boolean sucess, MessageReceivedEvent event) {

	}

	public String help() {
		return null;
	}


}
