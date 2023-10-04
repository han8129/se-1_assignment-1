package engine;

public class Match implements Comparable<Match>
{
       private Doc d;
       private Word w;
       private int freq;
       private int firstIndex;

       public Match(Doc d, Word w, int freq, int firstIndex)
       {
              this.d = d;
              this.w = w;
              this.freq = freq;
              this.firstIndex = firstIndex;
       }

       public int getFreq()
       {
              return freq;
       }

       public int getFirstIndex()
       {
              return firstIndex;
       }

       @Override
       public int compareTo(Match o) {
              if (o.getFirstIndex() > firstIndex)
              {
                     return -1;
              }

              if ( o.getFirstIndex() < firstIndex )
              {
                     return 1;
              }

              return 0;
       }

       public Word getWord()
       {
              return w.clone();
       }
}
