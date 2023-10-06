package engine;

import com.sun.media.sound.InvalidFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.InvalidObjectException;
import java.nio.channels.FileLockInterruptionException;
import java.util.*;

public class Doc extends File {
       private List<Word> title;
       private List<Word> body;

       public Doc(String name) throws FileNotFoundException
       {
              super(name);
                     if ( ! this.exists() )
                     {
                            throw new FileNotFoundException("Path given " + name);
                     }

                     Scanner scanner = new Scanner( this );
                     try {
                            this.title = getWord(scanner.nextLine());
                            this.body = getWord(scanner.nextLine());
                     } catch( NoSuchElementException e)
                     {
                            e.printStackTrace();
                     }
       }

       @Override
       public Doc clone() {
              try {
                     return new Doc(this.getPath());
              } catch (FileNotFoundException e)
              {
                     e.printStackTrace();
                     return null;
              }
       }

       private List<Word> getWord(String text )
       {
              Vector<Word> temp = new Vector<Word>();
              int beginIndex = 0;
              int endIndex = 0;
              for(; endIndex < text.length(); endIndex++)
              {
                     if(text.charAt(endIndex) == ' ')
                     {
                            String rawWord = text.substring(beginIndex, endIndex);
                            Word word = Word.createWord( rawWord, beginIndex );
                            temp.add(word);
                            beginIndex = endIndex + 1;
                     }
              }

              if(endIndex == text.length() - 1)
              {
                     return temp;
              }

              String rawWord = text.substring(beginIndex, endIndex);
              Word lastWord = Word.createWord(rawWord, beginIndex);
              temp.add(lastWord);

              return temp;
       }

       public List<Word> getTitle()
       {
              List<Word> clone = new ArrayList<>(title.size());

              for (Word word: title ) {
                     clone.add(word.clone());
              }

              return clone;
       }

       public List<Word> getBody()
       {
              List<Word> clone = new ArrayList<>(body.size());
              for (Word word: body ) {
                     clone.add(word.clone());
              }
              return clone;
       }
}
