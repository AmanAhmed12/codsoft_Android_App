package com.example.my_quote_app;

import java.util.concurrent.ThreadLocalRandom;
import java.util.Random;


public class QuoteServ {
    Random r1=new Random();
    String getQuotes(){
        String [] quotes = {"Opportunities don't happen, you create them.",
                "Love your family, work super hard, live your passion.",
                "It is never too late to be what you might have been.",
                "It is better to fail in originality than to succeed in imitation.",
                "I never dreamed about success. I worked for it.",
                "Don’t let yesterday take up too much of today.",
                "Either you run the day or the day runs you.",
                "Setting goals is the first step in turning the invisible into the visible.",
                "Think like a queen. A queen is not afraid to fail.",
                "A witty woman is a treasure; a witty beauty is a power.",
                "He who conquers himself is the mightiest warrior.",
                "“One man with courage makes a majority.",
                "Education is the most powerful weapon which you can use to change the world.",
                "The most difficult thing is the decision to act; the rest is merely tenacity."};
        int quotesArrayLen = quotes.length;
        int randomNum = r1.nextInt(quotesArrayLen);
        return quotes[randomNum];

    }


}
