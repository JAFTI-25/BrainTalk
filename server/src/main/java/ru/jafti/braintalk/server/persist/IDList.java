package ru.jafti.braintalk.server.persist;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.UUID;



public class IDList {
    public File idList;
    public FileWriter writer;

    public void createIDList() throws IOException{
        this.idList =new File("~/.braintalk", "talker_id.txt");
        this.writer = new FileWriter ("~/braintalk/talker_id",true);
        boolean success_create_file = idList.createNewFile();
        assert (success_create_file);
    }

    public void addId(UUID talker_uuid) throws IOException {
        this.writer.write(talker_uuid.toString());
    }
}
