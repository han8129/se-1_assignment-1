package engine;

import java.io.File;
import java.util.Vector;

public class Engine {
    private Vector<Doc> docs;

    public int loadDocs(String dirname)
    {
        File dir = new File(dirname);
        if( dir.list() == null)
            throw new NullPointerException("Directory given is null");

        for( String name : dir.list())
        {
            docs.add(new Doc(name));
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
}
