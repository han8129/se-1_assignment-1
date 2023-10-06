package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Objects;
import java.util.Scanner;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static java.lang.System.in;
import static java.lang.System.out;

// Suffix and prefix can be any character that is not alphanumeric
public class Word
{
       private String prefix;
       private String text;
       private String suffix;
       public final Pattern prefixPattern = Pattern.compile("^[^a-zA-Z0-9\\s]*");
       public final Pattern suffixPattern = Pattern.compile("(('(s|es|ve|d)){0,1}[^a-zA-Z0-9\\s]*)$");
       public final Pattern textPattern = Pattern.compile("^[a-zA-Z]+(-[a-zA-Z]+)*$");
       private Integer index;
       public static Set<String> stopWords = new TreeSet<>();

       private Word( String text )
       {
              this.prefix = getMatch(text, prefixPattern);
              this.suffix = getMatch(text,suffixPattern);

              int textStart = this.prefix.length();
              int textEnd = text.length() - this.suffix.length();

              String newText = "";

              if ((textEnd - textStart) > 0)
                    newText = text.substring( textStart ,textEnd );

              String matchNewText = getMatch(newText, textPattern);
              if( newText.length() != matchNewText.length() )
              {
                     this.text = text;
                     this.prefix = "";
                     this.suffix = "";
              } else {
                     this.text = matchNewText;
              }
       }

       private Word( String text, int index )
       {
              this.prefix = getMatch(text, prefixPattern);
              this.suffix = getMatch(text,suffixPattern);

              int textStart = this.prefix.length();
              int textEnd = text.length() - this.suffix.length();

              String newText = "";

              if ((textEnd - textStart) > 0)
                     newText = text.substring( textStart ,textEnd );

              String matchNewText = getMatch(newText, textPattern);
              if( newText.length() != matchNewText.length() )
              {
                     this.text = text;
                     this.prefix = "";
                     this.suffix = "";
              } else {
                     this.text = matchNewText;
              }

              if( index < 0)
                     throw new IndexOutOfBoundsException();

              this.index = index;
       }

       public static Word createWord( String text )
       {
              return new Word(text);
       }

       public static Word createWord( String text, int index )
       {
              return new Word(text, index);
       }

       public int getIndex()
       {
              if ( null == index)
              {
                     throw new NullPointerException("Word created with out an index");
              }
              return index.intValue();
       }

       private String getMatch( String text, Pattern pattern )
       {
              Matcher match = pattern.matcher( text );

              if ( ! match.find() )
                     return "";

              return match.group(0);
       }

       public boolean isKeyword()
       {
                     if( Word.stopWords.contains(this.toString().toLowerCase()))
                     {
                            return false;
                     }

                     if( "".equals(getMatch(this.text, textPattern)))
                     {
                            return false;
                     }

              return true;
       }

       public static boolean loadStopWords(String fileName)
       {
              try {
                     File file = new File(fileName);
                     Scanner reader = new Scanner( file );

                     while(reader.hasNextLine())
                     {
                            Word.stopWords.add(reader.nextLine());
                     }

                     return true;
              } catch (FileNotFoundException e)
              {
                     e.printStackTrace();
                     out.println(fileName + " not found");
                     return false;
              }
       }

       public String getText()
       {
              return text;
       }

       @Override
       public boolean equals(Object o)
       {
              try {
                     return text.equalsIgnoreCase(((Word) o).getText());

              } catch (IllegalArgumentException e)
              {
                     out.println();
                     e.printStackTrace();
                     return false;
              }
       }

       @Override
       public String toString()
       {
              return prefix + text + suffix;
       }

       public Word clone()
       {
              Word word = (null == index)
                     ? new Word(toString())
                     : new Word(toString(), index);

              return word;
       }

       public String htmlHighlight(String tag) {
              return  "<" + tag + ">" + prefix
              + text + suffix + "</" + tag + ">" ;
       }

       public String getPrefix() {
              return prefix;
       }

       public String getSuffix() {
              return suffix;
       }
}
