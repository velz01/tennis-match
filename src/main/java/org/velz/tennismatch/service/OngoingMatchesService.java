package org.velz.tennismatch.service;

import org.velz.tennismatch.dto.MatchDto;
import org.velz.tennismatch.model.Match;

import java.util.Map;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;

public class OngoingMatchesService {

    private final Map<UUID, Match> onGoingMatches;

    public OngoingMatchesService() {
        this.onGoingMatches = new ConcurrentHashMap<>();
    }

    public UUID add(Match match) {
        UUID uuid = UUID.randomUUID();
        onGoingMatches.put(uuid, match);

        return uuid;
    }
    public Match get(UUID uuid) {
        return onGoingMatches.get(uuid);
    }
}
