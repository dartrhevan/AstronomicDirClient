package com.example.astronomicdirclient;


import com.example.astronomicdirclient.Model.StarLite;

import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.StringReader;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;




public final class XMLHelper {
    public static List<StarLite> DeserrializeStarList(String xmlList)
    {/*
        JAXBContext context = null;
        try {
            context = JAXBContext.newInstance(StarList.class);
            StarList arr = (StarList) context.createUnmarshaller().unmarshal(new StringReader(xmlList));
            return arr.StarLite;
        } catch (JAXBException e) {
            e.printStackTrace();
            return null;
        }*/
        Serializer serializer = new Persister();
        try {
            StarList list = serializer.read(StarList.class, xmlList);
            return list.getStarLite();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
    public static String SerializeStarList(List<StarLite> list) ///throws JAXBException
    {/*
        JAXBContext context = JAXBContext.newInstance(StarList.class);
        StringWriter sw = new StringWriter(  );
        context.createMarshaller().marshal( new StarList( list ), sw );
        return sw.toString();//arr.starList;*/
        Serializer serializer = new Persister();
        StringWriter sw = new StringWriter();
        try {
            serializer.write(new StarList(list), sw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    //@XmlType(name = "StarList")
   // @XmlRootElement(name = "ArrayOfStarLite")//(name = "ArrayOfStarLite")
    @Default(DefaultType.FIELD)
    @Root(name = "ArrayOfStarLite")
    public static class StarList {
        public List<StarLite> getStarLite() {
            return StarLite;
        }

        public void setStarLite(List<StarLite> starList) {
            this.StarLite = starList;
        }
        public StarList() {
            this.StarLite = new ArrayList<>( );
        }
        public StarList(List<StarLite> starList) {
            this.StarLite = starList;
        }
        //@XmlAnyElement
        //@XmlElementWrapper(name="ArrayOfStarLite", nillable = true)
        //@XmlElement(name = "StarLite")
        @ElementList(inline = true)
        private List<StarLite> StarLite;
    }
}
