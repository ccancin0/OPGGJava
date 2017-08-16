package com.pages.controller;

import java.util.List;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.endpoints.league.constant.LeagueQueue;
import net.rithms.riot.api.endpoints.league.dto.LeagueList;
//import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;
import net.rithms.riot.api.endpoints.summoner.dto.Summoner;
import net.rithms.riot.constant.Platform;

@Controller
public class PagesController {

	@RequestMapping("/")
	public ModelAndView home() {
		return new ModelAndView("index", "title", "LoL Stats, Record Replay, Database, Guide, MMR - OP.GG");
	}

	@RequestMapping("/summoner")
	public ModelAndView summoner(@RequestParam String userName, ModelMap model) {
		ApiConfig config = new ApiConfig().setKey("RGAPI-3e57cb9f-6bae-43ae-8b7c-c6f203887945");
		RiotApi api = new RiotApi(config);

		try {
			Summoner summoner = api.getSummonerByName(Platform.NA, userName);
			List<LeagueList> name = api.getLeagueBySummonerId(Platform.NA, summoner.getId());
			Set<LeaguePosition> l = api.getLeaguePositionsBySummonerId(Platform.NA, summoner.getId());
			System.out.println("Wins: " + l);
			for (LeaguePosition temp : l) {
				if (temp.getQueueType().equalsIgnoreCase(LeagueQueue.RANKED_SOLO_5x5.toString())) {
					int wins = temp.getWins();
					System.out.println(wins);
					break;
				}
			}
			System.out.println(name.get(1));
			model.put("userName", userName);
			model.put("id", summoner.getId());
			model.put("summonerLvl", summoner.getSummonerLevel());
			model.put("profileIcon", summoner.getProfileIconId());
			// model.put("wins", wins);
		} catch (Exception e) {
			model.put("error", "summoner not found");
		}

		return new ModelAndView("summoner", "title",
				"Search a Summoner :: League of Legends (LOL) Search Summoner Stats");
	}
}
