package engine;

import com.sun.media.sound.InvalidFormatException;

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

       public List<Match> getMatches()
       {
              return new ArrayList<>(matches);
       }

       public double getAverageFirstIndex()
       {
              Integer sum = 0;
              for (Match match : matches ) {
                     sum += match.getFirstIndex();
              }

              return sum / matches.size();
       }

       public int getTotalFrequency()
       {
              Integer sum = 0;
              for (Match match : matches ) {
                     sum += match.getFreq();
              }

              return sum;
       }

       public String htmlHighlight()
       {
              StringBuilder html = new StringBuilder();
              (html).append("<h3>" + htmlHighlight(doc.getTitle(), "u") + "</h3>");
              html.append("<p>" + htmlHighlight(doc.getBody(), "b") + "</p>");
              // System.out.println(html);
              return html.toString();
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
                                   html.append(word.htmlHighlight(htmlTag));
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
                     html.append(" ");

                     boolean found = false;
                     Word word = matchAgainst.get(i);

                     for ( Match match : matches )
                     {
                            if (word.equals(match.getWord()))
                            {
                                   html.append(word.htmlHighlight(htmlTag));
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
                     return -1;

              } else if (matches.size() < o.getMatches().size()) {
                     return 1;
              }

              if (getTotalFrequency() > o.getTotalFrequency())
              {
                     return -1;

              } else if (getTotalFrequency() < o.getTotalFrequency()) {
                     return 1;
              }

              if (getAverageFirstIndex() < o.getAverageFirstIndex())
              {
                     return -1;
              }
              else if( getAverageFirstIndex() > o.getAverageFirstIndex())
                     return 1;

              return 0;
       }

       public Doc getDoc() throws FileNotFoundException
       {
              return new Doc(doc.getPath());
       }
}
