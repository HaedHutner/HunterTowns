package dev.haedhutner.towns.service;

import dev.haedhutner.towns.model.Poll;
import dev.haedhutner.towns.model.Vote;
import dev.haedhutner.towns.model.entity.Nation;
import dev.haedhutner.towns.model.entity.TownPlot;
import com.google.inject.Inject;
import com.google.inject.Singleton;

import java.util.HashMap;
import java.util.Set;
import java.util.UUID;

@Singleton
public class PollService {

    private final HashMap<UUID, Poll> pollCache = new HashMap<>();

    @Inject
    PollService() {
    }

    public Poll getPollById(UUID id) {
        return pollCache.get(id);
    }

    public UUID createPoll(UUID pollCreator, String pollName, Set<UUID> voters, TownPlot homePlot, Nation nation) {
        UUID pollUUID = UUID.randomUUID();

        while (pollCache.containsKey(pollUUID)) {
            pollUUID = UUID.randomUUID();
        }

        Poll poll = new Poll();

        poll.setId(pollUUID);
        poll.setHomePlot(homePlot);
        poll.setVoters(voters);
        poll.setCreator(pollCreator);
        poll.setPollName(pollName);
        poll.setNation(nation);

        pollCache.put(pollUUID, poll);
        return pollUUID;
    }

    public void deletePoll(UUID id) {
        pollCache.remove(id);
    }

    public Set<Vote> getPollVotes(UUID id) {
        return pollCache.get(id).getVotes();
    }

    public void addVoteToPoll(Vote vote, UUID id) {
        pollCache.get(id).addVote(vote);
    }

}
