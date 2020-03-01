package lesson3;

import java.util.*;

public class MyTest {
    public static void main(String[] args) {
        String[] words = {"люблю", "тебя", "петра", "творенье", "люблю", "твой", "строгий", "стройный", "вид",
                "люблю", "зимы", "твоей", "жестокой", "недвижный", "воздух", "и", "мороз", "мороз", "в",
                "последнюю", "субботу", "зимы", "в", "Санкт-Петербурге"};
        Set<String> setWords = new LinkedHashSet<>(Arrays.asList(words));

      //  Map<String, Integer> mapWords = new HashMap<>();
        for (String word : setWords ) {
            int count = 0;
            for (int i = 0; i < words.length ; i++) {
                if(words[i].equals(word)) count++;
            }
          //  mapWords.put(word, count); потом распечатать пары ключ - значение.
            System.out.println(word + " - " + count);
        }
    }
}