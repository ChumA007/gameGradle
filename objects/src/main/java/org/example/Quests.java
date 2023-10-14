package org.example;

import java.util.ArrayList;
import java.util.List;

public class Quests {
    private List<Quest> quests;

    public Quests() {
        quests = new ArrayList<>();
    }

    public List<Quest> getQuests() {
        return quests;
    }

    public void addQuest(Quest quest) {
        quests.add(quest);
    }

    public void removeQuest(Quest quest) {
        quests.remove(quest);
    }

    public List<Quest> getOperationalQuests() {
        List<Quest> operationalQuests = new ArrayList<>();
        for (Quest quest : quests) {
            if (quest.getDeadline() != -1) {
                operationalQuests.add(quest);
            }
        }
        return operationalQuests;
    }

    public List<Quest> getPermanentQuests() {
        List<Quest> permanentQuests = new ArrayList<>();
        for (Quest quest : quests) {
            if (quest.getDeadline() == -1) {
                permanentQuests.add(quest);
            }
        }
        return permanentQuests;
    }

    public void markQuestCompleted(Quest quest) {
        quest.setCompleted(true);
    }

    public String toString() {
        if (quests.size() != 0 && this.quests != null) {
            String str = "  Permanent quests:\n     ";
            String str1 = "  Operational quests:\n     ";
            for (Quest q : quests) {
                if (getPermanentQuests().contains(q)) {
                    str += "    " + q.toString() + "\n";
                }
                if (getOperationalQuests().contains(q)) {
                    str1 += "   " + q.toString() + "\n";
                }
            }
            return str += str1;
        }
        return " - \n";
    }
}
