package engine;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;
import java.util.Vector;

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
            } catch (FileNotFoundException e)
            {
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
        Vector<Result> results = new Vector<>();

        for (Doc doc : getDocs() ) {
            Result result = new Result(doc, q.matchAgainst(doc));
            results.add(result);
        }

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
