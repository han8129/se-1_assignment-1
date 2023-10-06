package engine;

import java.util.*;

/**
 * keyWord are word that are not stopWords
 */
public class Query {
       private List<Word> keywords;
       public Query(String searchPhrase)
       {
              keywords = new Vector<>();
              for( String rawWord : 
                     searchPhrase.split(" "))
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
                     Integer firstIndex = -1;

                     for (Word word : d.getTitle() )
                     {
                            if ( keyword.equals(word) )
                            {
                                   if (freq == 0)
                                   {
                                          try {

                                                 firstIndex = word.getIndex();
                                          } catch (NullPointerException e)
                                          {
                                                 e.printStackTrace();
                                          }
                                   }

                                   freq++;
                            }
                     }

                     for (Word word: d.getBody() )
                     {
                            if (keyword.equals(word))
                            {
                                   if (freq == 0)
                                   {
                                          try {
                                                 firstIndex = word.getIndex();
                                          } catch (NullPointerException e)
                                          {
                                                 e.printStackTrace();
                                          }
                                   }

                                   freq++;
                            }
                     }

                     if (freq > 0)
                     {
                            matches.add( new Match(d, keyword, freq, firstIndex));
                     }
              }

              if (matches.size() > 1)
              {
                     matches.sort( Comparator.naturalOrder() );
              }

//              if (matches.size() > 0)
//                     System.out.print("Matches: ");
//              for (Match match : matches)
//              {
//                     System.out.println(match.toString());
//              }

              return matches;
       }
}