package client;

import java.util.ArrayList;
import java.util.List;

public class MessageHistory {
    private List<String> list;
    private final int COUNT_MASSAGE = 100;

    public MessageHistory() {
        this.list = new ArrayList<>(100);
    }

    public List<String> getList() {
        return list;
    }

    public void writeMassage(String massage) {
        if (list.size() >= COUNT_MASSAGE) {
            list.remove(0);
        }
        list.add(massage);
    }

    public String getStringMassage() {
        StringBuilder sb = new StringBuilder();
        for (String s : list) {
            sb.append(s);
        }
        return sb.toString();
    }

}
