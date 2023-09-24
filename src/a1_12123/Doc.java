package engine;

import java.io.File;

public class Doc extends File {
    private String dir;
    
    public Doc(String name)
    {
        super(name);
    }

    @Override
    public Doc clone() {
        return new Doc(dir);
    }
}
