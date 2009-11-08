/*
 * Created on Jan 29, 2005 by Adam. 
 *
 */
package net.crystalrudiments.common;

import java.io.IOException;
import java.util.HashMap;
import java.util.Iterator;

import org.jdom.Document;
import org.jdom.Element;
import org.jdom.input.SAXBuilder;

/**
 * GlobalOptions
 */
public final class GlobalOptions {

    
    public static final String OPTIONS_XML = "options.xml";
    
    private HashMap sounds = new HashMap();
    
    private HashMap images = new HashMap();

    private HashMap songs = new HashMap();
    
    private static GlobalOptions singleton = new GlobalOptions(OPTIONS_XML);
    
    public static GlobalOptions getDefaultOptions()
    {
        return singleton;
    }
    
    public GlobalOptions( String xmlFileName ) 
    {
        SAXBuilder builder = new SAXBuilder();
        Document doc = null;
        try {
            doc = builder.build( xmlFileName );
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        
        Element root = doc.getRootElement();
        for (Iterator nodes = root.getChildren().iterator(); nodes.hasNext(); ) {
            Element node = (Element) nodes.next();
            if (node.getAttributeValue("name").equals("sounds")) {
                for (Iterator sounds = node.getChildren().iterator(); 
	            		sounds.hasNext(); ) {
                    Element s = (Element) sounds.next();
	                /*for (Iterator iter = s.getAttributes().iterator(); iter.hasNext();) {
		                Attribute att = (Attribute) iter.next();
		                System.out.println( att.getName() + " " + att.getValue() );
		            }*/
                    this.sounds.put(s.getAttributeValue("name"),
                            s.getAttributeValue("file"));
                }
            }
            if (node.getAttributeValue("name").equals("images")) {
                for (Iterator it = node.getChildren().iterator(); 
	            		it.hasNext(); ) {
                    Element s = (Element) it.next();
                    this.images.put(s.getAttributeValue("name"),
                            s.getAttributeValue("file"));
                }
            }
	    if (node.getAttributeValue("name").equals("songs")) {
                for (Iterator it = node.getChildren().iterator(); 
	            		it.hasNext(); ) {
                    Element s = (Element) it.next();
                    this.songs.put(s.getAttributeValue("map"),
                            s.getAttributeValue("file"));
                }
            }
        }
        
    }
    
    public String getSoundFileName( String name ) {
        return (String) sounds.get( name );
    }
    
    public String getImageFileName( String name ) {
        return (String) images.get( name );
    } 

    public String getSongFileName( String map ) {
        return (String) songs.get( map.substring(0, 2) );
    }

    //public static void main(String[] args)  {
        /*SAXBuilder builder = new SAXBuilder();
        Document doc = builder.build(args[0]);

        System.out.println("All content:");
        Iterator itr = doc.getDescendants();
        while (itr.hasNext()) {
            Content c = (Content) itr.next();
            System.out.println(c);
        }*/
    //}
}
