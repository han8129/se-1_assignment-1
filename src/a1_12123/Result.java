package engine;

import javax.crypto.Mac;
import javax.swing.*;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class Result implements Comparable<Result> {
       private Doc doc;
       private List<Match> matches;

       public Result( Doc d, List<Match> matches )
       {
              this.doc = d;
              this.matches = matches;
       }

       public List<Match>  getMatches()
       {
              return new ArrayList<>(matches);
       }

       public double getAverageFirstIndex()
       {
              int sum = 0;
              for (Match match : matches ) {
                     sum += match.getFirstIndex();
              }

              return sum / matches.size();
       }

       public int getTotalFrequency()
       {
              int sum = 0;
              for (Match match : matches ) {
                     sum += match.getFreq();
              }

              return sum;
       }

       public String htmlHighlight()
       {
              StringBuilder html = new StringBuilder();
              (html).append(htmlHighlight(doc.getTitle(), "u"));
              html.append("\n");
              html.append(htmlHighlight(doc.getBody(), "b"));

              return null;
       }

       private String htmlHighlight(List<Word> matchAgainst, String htmlTag)
       {
              StringBuilder html = new StringBuilder();

              {
                     boolean found = false;
                     Word word = matchAgainst.get(0);

                     for ( Match match : matches )
                     {
                            if (word.equals(match.getWord()))
                            {
                                   html.append(word.htmlHighlight("u"));
                                   found = true;
                                   break;
                            }
                     }

                     if(! found )
                     {
                            html.append(word.toString());
                     }
              }

              for (int i = 1; i < matchAgainst.size(); i++ ) {
                     boolean found = false;
                     Word word = matchAgainst.get(i);

                     for ( Match match : matches )
                     {
                            if (word.equals(match.getWord()))
                            {
                                   html.append(word.htmlHighlight("u"));
                                   found = true;
                                   break;
                            }
                     }

                     if( ! found )
                     {
                            html.append(word.toString());
                     }
              }

              return html.toString();
       }

       public int compareTo(Result o) {
              if (matches.size() > o.getMatches().size())
              {
                     return 1;

              } else if (matches.size() < o.getMatches().size()) {
                     return -1;
              }

              if (getTotalFrequency() > o.getTotalFrequency())
              {
                     return 1;
              } else if (getTotalFrequency() < o.getTotalFrequency()) {
                     return -1;
              }

              if (getAverageFirstIndex() < o.getAverageFirstIndex())
              {
                     return 1;
              }
              else if( getAverageFirstIndex() > o.getAverageFirstIndex())
                     return -1;

              return 0;
       }

       public Doc getDoc() throws FileNotFoundException
       {
              return new Doc(doc.getPath());
       }
}
