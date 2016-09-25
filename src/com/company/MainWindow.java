package com.company;

import com.company.rss.Channel;
import com.company.rss.ChannelFileWriter;
import com.company.rss.FeedParser;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * This will serve as our main window for display purposes.
 * Created by nmenego on 9/25/16.
 */
public class MainWindow extends JFrame {
    public MainWindow() {

        setTitle("Enego RSS");
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setSize(700, 250);
        setLayout(new GridLayout(0, 2));

        // rss feeds
        JLabel label = new JLabel("RSS Feeds (use newline as separator)", SwingConstants.CENTER);
        JTextArea urlTextarea = new JTextArea();
        urlTextarea.setText("http://tech.uzabase.com/rss\nhttp://www.feedforall.com/sample-feed.xml\n");
        add(label);
        add(urlTextarea);

        // word exclusions
        JLabel label2 = new JLabel("Words to exclude (use newline as separator)", SwingConstants.CENTER);
        JTextArea excludeTextarea = new JTextArea();
        excludeTextarea.setText("NewsPicks\n");
        add(label2);
        add(excludeTextarea);

        JCheckBox checkBox = new JCheckBox("save output to file");
        add(checkBox);

        // button
        JButton button = new JButton("Retrieve RSS");
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                MainWindow.this.setEnabled(false);
                // do retrieval here.
                String[] rssUrls = retrieveInfo(urlTextarea);
                String[] excludes = retrieveInfo(excludeTextarea);
                if (rssUrls != null) {
                    for (String rssUrl : rssUrls) {
                        FeedParser feedParser = new FeedParser(rssUrl);
                        Channel feed = feedParser.read(excludes);
                        System.out.println(feed);
                        System.out.println("Feed size: " + (feed.getItems() != null ? feed.getItems().size() : 0));
                        // FIXME: uncomment to printout items in STDOUT
//                        for (Item item : feed.getItems()) {
//                            System.out.println(item);
//                        }

                        if (checkBox.isSelected()) {
                            // save output to file.
                            ChannelFileWriter fw = new ChannelFileWriter(feed);
                            fw.write("rss.out");
                        }
                    }
                }

                MainWindow.this.setEnabled(true);
                JOptionPane.showMessageDialog(MainWindow.this, "Process Complete!");
            }

            // retrieves new line separated info from textarea
            private String[] retrieveInfo(JTextArea textArea2) {
                String raw = textArea2.getText();
                String[] arr = raw.split("\\r?\\n");
                return arr;
            }
        });
        add(button);


        setVisible(true);
    }

    public static void main(String[] args) {
        MainWindow window = new MainWindow();
    }
}
