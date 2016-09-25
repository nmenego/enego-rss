package com.company.rss;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * A class to write Channel information to a file.
 * Created by nmenego on 9/25/16.
 */
public class ChannelFileWriter {
    private Channel channel;

    public ChannelFileWriter(Channel channel) {
        this.channel = channel;
    }

    /**
     * Write the rss feed to a file. Note: there's a chance that filenames will conflict.
     *
     * @param filename
     */
    public void write(String filename) {
        PrintWriter writer = null;
        try {
            String fn = filename + new SimpleDateFormat("yyyyMMddhhmmss'.txt'").format(new Date());

            writer = new PrintWriter(fn, "UTF-8");
            writer.println(channel);
            writer.println("Feed size: " + (channel.getItems() != null ? channel.getItems().size() : 0));
            for (Item item : channel.getItems()) {
                writer.println(item);
            }
            System.out.println("file written: " + fn);
            writer.close();
        } catch (FileNotFoundException e) {
            System.err.println("File not found!");
        } catch (UnsupportedEncodingException e) {
            System.err.println("Unsupported encoding!");
        }
    }
}
