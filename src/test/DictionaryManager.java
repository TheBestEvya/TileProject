package test;


import java.util.HashMap;

public class DictionaryManager {
    private HashMap<String,Dictionary> books;

    public DictionaryManager(){
        this.books = new HashMap<>();
    }
    public boolean query(String ...args){
        boolean wordExist = false;
        String word = args[args.length - 1];

        //iterate args except the last one
        for (int i = 0; i < args.length - 1; i++)
            //check if book not exist in books then add him
            if(!books.containsKey(args[i]))
                books.put(args[i], new Dictionary(args[i]));

        //iterate books dictionary and check if word exist
        for (Dictionary bookDictionary : books.values())
            if (bookDictionary.query(word))
                wordExist = true;

        return wordExist;
    }
    public boolean challage(String ...args){

        boolean wordExist = false;
        String word = args[args.length - 1];

        //iterate args except the last one
        for (int i = 0; i < args.length - 1; i++)
            //check if book not exist in books then add him
            if(!books.containsKey(args[i]))
                books.put(args[i], new Dictionary(args[i]));

        //iterate books dictionary and check if word exist
        for (Dictionary bookDictionary : books.values())
            if (bookDictionary.challenge(word))
                wordExist = true;

        return wordExist;
    }
}
