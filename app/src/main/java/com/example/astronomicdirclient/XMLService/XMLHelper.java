package com.example.astronomicdirclient.XMLService;

import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.StarLite;

import org.joda.time.DateTime;
import org.simpleframework.xml.Default;
import org.simpleframework.xml.DefaultType;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;
import org.simpleframework.xml.transform.RegistryMatcher;

import java.io.StringWriter;
import java.util.ArrayList;
import java.util.List;

public final class XMLHelper {

    public static Star DeserializeStar(String star) {
        RegistryMatcher m = new RegistryMatcher();
        m.bind(DateTime.class, new DateFormatTransformer());
        m.bind(byte[].class, new BytesFormatTransformer());
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
        m.bind(byte[].class, new BytesFormatTransformer());
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
