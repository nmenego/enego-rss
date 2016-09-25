package com.company.rss;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;

import javax.xml.stream.XMLEventReader;
import javax.xml.stream.XMLInputFactory;
import javax.xml.stream.XMLStreamException;
import javax.xml.stream.events.Characters;
import javax.xml.stream.events.XMLEvent;

/**
 * This class will handle the parsing of our XML from the RSS Feed.
 * Created by nmenego on 9/25/16.
 */
public class FeedParser {

    private URL url;
    private String[] excludeStrings;

    private static final String TITLE = "title";
    private static final String DESCRIPTION = "description";
    private static final String LINK = "link";
    private static final String ITEM = "item";
    private static final String PUB_DATE = "pubDate";
    private static final String GUID = "guid";

    public FeedParser(String channelUrl) {
        try {
            this.url = new URL(channelUrl);
        } catch (MalformedURLException e) {
            System.err.println("Malformed URL.");
        }
    }

    /**
     * Read the RSS feed provided by the initialized url.
     *
     * @param excludeStrings items containing these strings are excluded
     * @return a Channel object containing the Channel information
     */
    public Channel read(String[] excludeStrings) {
        this.excludeStrings = excludeStrings;
        Channel channel = null;
        InputStream is = null;
        try {
            boolean isChannelHeader = true;
            String description = "";
            String title = "";
            String link = "";
            String lastBuildDate = "";
            String docs = "";
            String generator = "";
            String guid = "";
            String pubdate = "";

            // this part here is adapted from http://www.vogella.com/tutorials/RSSFeed/article.html
            // Initialize stax
            XMLInputFactory inputFactory = XMLInputFactory.newInstance();
            inputFactory.setProperty(XMLInputFactory.IS_COALESCING, true); // to handle special HTML chars
            is = url.openStream();
            XMLEventReader eventReader = inputFactory.createXMLEventReader(is);
            // read the XML document using cursor...
            while (eventReader.hasNext()) {
                XMLEvent event = eventReader.nextEvent();
                if (event.isStartElement()) {
                    String tag = event.asStartElement().getName().getLocalPart();
                    if (ITEM.equals(tag)) {
                        if (isChannelHeader) {
                            isChannelHeader = false;
                            channel = new Channel(title, link, description, lastBuildDate, docs, generator);
                        }
                        event = eventReader.nextEvent();
                    } else if (TITLE.equals(tag)) {
                        title = getString(event, eventReader);
                    } else if (DESCRIPTION.equals(tag)) {
                        description = getString(event, eventReader);
                    } else if (LINK.equals(tag)) {
                        link = getString(event, eventReader);
                    } else if (GUID.equals(tag)) {
                        guid = getString(event, eventReader);
                    } else if (PUB_DATE.equals(tag)) {
                        pubdate = getString(event, eventReader);
                    }
                } else if (event.isEndElement()) {
                    // create an item when </item> is found...
                    if (event.asEndElement().getName().getLocalPart().equals(ITEM)) {
                        if (!hasExcludeWords(title, description)) {
                            Item item = new Item(title, description, link, guid, pubdate);
                            channel.getItems().add(item);
                            event = eventReader.nextEvent();
                            continue;
                        }
                    }
                }
            }
        } catch (XMLStreamException e) {
            // we can also printstacktrace here.
            System.err.print(e.getMessage());
        } catch (IOException e) {
            // we can also printstacktrace here.
            System.err.print(e.getMessage());
        }
        return channel;
    }

    // we may want to add other parameters for checking later
    // for now, I will only check title and description
    private boolean hasExcludeWords(String title, String description) {
        if (excludeStrings != null) {
            for (String excludeWord : excludeStrings) {

                // check title
                if (title.contains(excludeWord)) {
                    return true;
                }

                // check description
                if (description.contains(excludeWord)) {
                    return true;
                }
            }
        }
        return false;
    }

    // retrieve String contents of a tag.
    private String getString(XMLEvent event, XMLEventReader eventReader)
            throws XMLStreamException {
        String result = "";
        event = eventReader.nextEvent();
        if (event instanceof Characters) {
            result = event.asCharacters().getData();
        }
        return result;
    }
}

