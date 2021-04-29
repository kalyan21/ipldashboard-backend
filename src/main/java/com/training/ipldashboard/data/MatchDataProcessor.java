package com.training.ipldashboard.data;

import com.training.ipldashboard.model.Match;
import org.springframework.batch.item.ItemProcessor;

import java.time.LocalDate;


public class MatchDataProcessor implements ItemProcessor<MatchInput, Match> {
    @Override
    public Match process(MatchInput item) throws Exception {
        Match match = new Match();
        match.setId(Long.parseLong(item.getId()));
        match.setCity(item.getCity());
        match.setDate(LocalDate.parse(item.getDate()));
        match.setPlayerOfMatch(item.getPlayer_of_match());
        match.setVenue(item.getVenue());
        match.setTeam1(item.getTeam1());
        match.setTeam2(item.getTeam2());
        match.setTossWinner(item.getToss_winner());
        match.setWinner(item.getWinner());
        match.setResult(item.getResult());
        match.setResultMargin(item.getResult_margin());
        match.setUmpire1(item.getUmpire1());
        match.setUmpire2(item.getUmpire2());
        return match;
    }
}
