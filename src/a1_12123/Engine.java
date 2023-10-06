package engine;

import com.sun.media.sound.InvalidFormatException;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;


public class Engine {
    private Vector<Doc> docs = new Vector<>();

    public int loadDocs(String dirname) throws FileNotFoundException {
        File dir = new File(dirname);
        if( dir.list() == null)
            throw new NullPointerException("Directory given is null");

        for( File file : dir.listFiles())
        {
            try {
                docs.add(new Doc(file.getPath()));
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        return docs.size();
    }

    public Doc[] getDocs()
    {
        Doc[] temp = new Doc[docs.size()];
        for( int i = 0; i < docs.size(); i++ )
        {
            temp[i] = docs.get(i).clone();
        }

        return temp;
    }

    public List<Result> search(Query q)
    {
        List<Result> results = new Vector<>();

        for (Doc doc : getDocs() ) {
            List<Match> matches = q.matchAgainst(doc);
            if( 0 < matches.size() )
            {
                results.add(new Result(doc, matches));
            }
        }

        results.sort( Comparator.naturalOrder() );

        return results;
    }

    public String htmlResult(List<Result> results)
    {
        StringBuilder html = new StringBuilder();

        html.append(results.get(0).htmlHighlight());

        for (int i = 1; i < results.size(); i++) {
            html.append("\n");
            html.append(results.get(i).htmlHighlight());
        }

        return html.toString();
    }
}
