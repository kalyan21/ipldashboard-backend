package com.training.ipldashboard.controller;

import com.training.ipldashboard.model.Team;
import com.training.ipldashboard.repository.MatchRepository;
import com.training.ipldashboard.repository.TeamRepository;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/team")
public class TeamController {

    private final TeamRepository teamRepository;
    private final MatchRepository matchRepository;

    public TeamController(TeamRepository teamRepository, MatchRepository matchRepository) {
        this.teamRepository = teamRepository;
        this.matchRepository = matchRepository;
    }

    @GetMapping(value = "/{teamName}", produces = MediaType.APPLICATION_JSON_VALUE)
    public Team getTeamInfo(@PathVariable("teamName") String teamName) {
        Team team = teamRepository.findByTeamName(teamName);
        team.setRecentMatches(matchRepository.findLatestMatchesByTeamName(teamName, 4));
        return team;
    }
}
