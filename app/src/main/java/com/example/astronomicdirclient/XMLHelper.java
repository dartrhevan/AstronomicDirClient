package com.example.astronomicdirclient;

import com.example.astronomicdirclient.Model.DateFormatTransformer;
import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.StarLite;

import org.joda.time.DateTime;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.StringReader;
import java.io.StringWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public final class XMLHelper {

    //public static final String DATE_FORMAT = "YYYY-MM-dd'T'kk:mm:ss.SSSSSSSXXX";//"EE MMM dd HH:mm:ss z YYYY";2019-11-04T15:47:55.4669753+05:00

    public static Star DeserializeStar(String star) {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(DateTime.class, new DateFormatTransformer());
        Serializer serializer = new Persister(m);
        try {
            Star star1 = serializer.read(Star.class, star);
            return star1;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static List<StarLite> DeserializeStarList(String xmlList)
    {
        Serializer serializer = new Persister();
        try {
            StarList list = serializer.read(StarList.class, xmlList);
            return list.getStarLite();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String SerializeStar(Star star) ///throws JAXBException
    {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(DateTime.class, new DateFormatTransformer());
        Serializer serializer = new Persister(m);
        StringWriter sw = new StringWriter();
        try {
            serializer.write(star, sw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

    public static String SerializeStarList(List<StarLite> list) ///throws JAXBException
    {
        Serializer serializer = new Persister();
        StringWriter sw = new StringWriter();
        try {
            serializer.write(new StarList(list), sw);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sw.toString();
    }

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
        @ElementList(inline = true)
        private List<StarLite> StarLite;
    }
}
