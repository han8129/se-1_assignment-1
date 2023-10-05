package engine;

import java.util.*;

import static java.util.Collections.sort;
import static java.util.Comparator.*;

/**
 * keyWord are word that are not stopWords
 */
public class Query {
       private List<Word> keywords;
       public Query(String searchPhrase)
       {
              keywords = new Vector<>();
              for( String rawWord : searchPhrase.split(" "))
              {
                     Word word = Word.createWord(rawWord);
                     if( word.isKeyword())
                     {
                            keywords.add(word);
                     }
              }
       }

       public List<Word> getKeywords()
       {
              List<Word> clone = new ArrayList<>(keywords.size());
              for (Word word : keywords ) {
                     clone.add(word.clone());
              }

              return clone;
       }

       public List<Match> matchAgainst(Doc d)
       {
              List<Match> matches = new Vector<>();

              for (Word keyword : keywords )
              {
                     int freq = 0;
                     Integer firstIndex = null;

                     for (Word word : d.getTitle() )
                     {
                            if ( keyword.equals(word) )
                            {
                                   freq++;

                                   if (firstIndex == null)
                                   {
                                          firstIndex = word.getIndex();
                                   }
                            }
                     }

                     for (Word word: d.getBody() )
                     {
                            if (keyword.equals(word))
                            {
                                   freq++;

                                   if (firstIndex == null)
                                   {
                                          firstIndex = word.getIndex();
                                   }
                            }
                     }

                     if (firstIndex  == null)
                     {
                            continue;
                     }

                     matches.add( new Match(d, keyword, freq, firstIndex));
              }

              matches.sort( Comparator.naturalOrder() );

              return matches;
       }
}