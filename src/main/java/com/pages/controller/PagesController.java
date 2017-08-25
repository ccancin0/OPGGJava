package com.pages.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import net.rithms.riot.api.ApiConfig;
import net.rithms.riot.api.ApiMethod;
import net.rithms.riot.api.RiotApi;
import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.league.constant.LeagueQueue;
//import net.rithms.riot.api.RiotApiException;
import net.rithms.riot.api.endpoints.league.dto.LeaguePosition;
import net.rithms.riot.api.endpoints.match.dto.Match;
import net.rithms.riot.api.endpoints.match.dto.MatchList;
import net.rithms.riot.api.endpoints.match.dto.MatchReference;
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
		ApiConfig config = new ApiConfig().setKey("RGAPI-3b3c7e37-4790-4fc4-a619-b71975db8f2a");
		RiotApi api = new RiotApi(config);

		try {

			Summoner summoner = api.getSummonerByName(Platform.NA, userName);
			// List<LeagueList> name = api.getLeagueBySummonerId(Platform.NA,
			// summoner.getId());
			Set<LeaguePosition> league = api.getLeaguePositionsBySummonerId(Platform.NA, summoner.getId());
			Map<String, Integer> ranks = new HashMap<String, Integer>();
			MatchList a = api.getRecentMatchListByAccountId(Platform.NA, summoner.getAccountId());
			List<MatchReference> b = a.getMatches();

			ranks.put("I", 1);
			ranks.put("II", 2);
			ranks.put("III", 3);
			ranks.put("IV", 4);
			ranks.put("V", 5);
			String soloTier = "", soloRank = "", soloLeague = "";
			int soloWins = 0, soloLoses = 0, soloLp = 0, soloWinRatio = 0;
			List<String> champs = new ArrayList<String>();

			String flexTier = "", flexRank = "", flexLeague = "";
			int flexWins = 0, flexLoses = 0, flexLp = 0, flexWinRatio = 0;

			for (MatchReference temp : b) {
				champs.add(api.getDataChampion(Platform.NA, temp.getChampion()).getName());
			}

			for (LeaguePosition temp : league) {
				if (temp.getQueueType().equalsIgnoreCase(LeagueQueue.RANKED_SOLO_5x5.toString())) {
					soloTier = temp.getTier().toLowerCase();
					soloRank = temp.getRank();
					soloLeague = temp.getLeagueName();
					soloLp = temp.getLeaguePoints();
					soloWins = temp.getWins();
					soloLoses = temp.getLosses();
					soloWinRatio = (int) Math.floor((double) soloWins / (soloWins + soloLoses) * 100);
					break;
				}
			}

			for (LeaguePosition temp : league) {
				if (temp.getQueueType().equalsIgnoreCase(LeagueQueue.RANKED_FLEX_SR.toString())) {
					flexTier = temp.getTier().toLowerCase();
					flexRank = temp.getRank();
					flexLeague = temp.getLeagueName();
					flexLp = temp.getLeaguePoints();
					flexWins = temp.getWins();
					flexLoses = temp.getLosses();
					flexWinRatio = (int) Math.floor((double) flexWins / (flexWins + flexLoses) * 100);
					break;
				}
			}

			userName = summoner.getName();
			model.put("userName", summoner.getName());
			model.put("profileIcon", summoner.getProfileIconId());
			model.put("soloRank", ranks.get(soloRank));
			model.put("soloTier", soloTier);
			model.put("soloLeague", soloLeague);
			model.put("soloLp", soloLp);
			model.put("soloWins", soloWins);
			model.put("soloLoses", soloLoses);
			model.put("soloWinRatio", soloWinRatio);

			model.put("flexRank", ranks.get(flexRank));
			model.put("flexTier", flexTier);
			model.put("flexLeague", flexLeague);
			model.put("flexLp", flexLp);
			model.put("flexWins", flexWins);
			model.put("flexLoses", flexLoses);
			model.put("flexWinRatio", flexWinRatio);
			
			model.put("champs", champs);
		} catch (RiotApiException e) {
			model.put("error", e.getMessage());
		}

		if (model.containsKey("error")) {
			return new ModelAndView("summoner", "title",
					"Search a Summoner :: League of Legends (LOL) Search Summoner Stats");
		}

		return new ModelAndView("summoner", "title", userName + " - Summoner search results - League of Legends");
	}
}
