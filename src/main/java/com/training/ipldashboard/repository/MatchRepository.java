package com.training.ipldashboard.repository;

import com.training.ipldashboard.model.Match;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;

import java.util.List;

public interface MatchRepository extends CrudRepository<Match, Long> {

    List<Match> getByTeam1OrTeam2OrderByDateDesc(String teamName1, String teamName2, Pageable pageable);

    default List<Match> findLatestMatchesByTeamName(String teamName, int resultSize) {
        Pageable pageable = PageRequest.of(0, resultSize);
        return getByTeam1OrTeam2OrderByDateDesc(teamName, teamName, pageable);
    }
}
