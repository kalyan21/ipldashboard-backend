package com.training.ipldashboard.data;

import com.training.ipldashboard.model.Team;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.BatchStatus;
import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;
import org.springframework.stereotype.Component;

import javax.persistence.EntityManager;
import javax.transaction.Transactional;
import java.util.HashMap;
import java.util.Map;

@Component
@Slf4j
public class JobCompletionNotificationListener extends JobExecutionListenerSupport {

    private final EntityManager entityManager;

    public JobCompletionNotificationListener(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    @Transactional
    public void afterJob(JobExecution jobExecution) {
        if (jobExecution.getStatus() == BatchStatus.COMPLETED) {
            log.info("!!! JOB FINISHED! Time to verify the results");
            createTeamData();
        }
    }

    private void createTeamData() {
        Map<String, Team> teamData = new HashMap<>();
        entityManager.createQuery("select m.team1, count(m) from Match as m group by m.team1", Object[].class)
                .getResultStream()
                .map(e -> new Team((String) e[0], (long) e[1]))
                .forEach(e -> teamData.put(e.getTeamName(), e));
        entityManager.createQuery("select m.team2, count(m) from Match as m group by m.team2", Object[].class)
                .getResultStream()
                .forEach(e -> {
                    Team team = teamData.get((String) e[0]);
                    team.setTotalMatches(team.getTotalMatches() + (long) e[1]);
                });
        entityManager.createQuery("select m.winner, count(m) from Match m group by m.winner", Object[].class)
                .getResultStream()
                .forEach(e -> {
                    Team team = teamData.get((String) e[0]);
                    if (team != null) team.setTotalWins((long) e[1]);
                });
        teamData.values().forEach(entityManager::persist);
    }
}
