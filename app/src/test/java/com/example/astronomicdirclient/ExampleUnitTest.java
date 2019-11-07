package com.example.astronomicdirclient;

import com.example.astronomicdirclient.Model.Distance;
import com.example.astronomicdirclient.Model.Moon;
import com.example.astronomicdirclient.Model.Planet;
import com.example.astronomicdirclient.Model.PlanetType;
import com.example.astronomicdirclient.Model.Star;
import com.example.astronomicdirclient.Model.StarLite;
import com.example.astronomicdirclient.Model.UnitType;
import com.example.astronomicdirclient.XMLService.XMLHelper;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.StringDecoder;
import org.apache.commons.codec.binary.Hex;
import org.joda.time.DateTime;
import org.junit.Test;
import org.simpleframework.xml.Serializer;
import org.simpleframework.xml.core.Persister;

import java.io.IOException;
import java.io.Serializable;
import java.io.StringReader;
import java.io.StringWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Base64;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import javax.xml.bind.DatatypeConverter;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {

    @Test
    public void starSerializeTest() throws Exception {
        String source = "<?xml version=\"1.0\"?>\n" +
                "<Star xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <Planets>\n" +
                "    <Planet>\n" +
                "      <Moons>\n" +
                "        <Moon>\n" +
                "          <Moons />\n" +
                "          <HasAtmosphere>false</HasAtmosphere>\n" +
                "          <Type>Moon</Type>\n" +
                "          <Photo />\n" +
                "           <Galaxy>этта</Galaxy>\n" +
                "          <Name>Фобос</Name>\n" +
                "          <MiddleDistance />\n" +
                "          <Radius>0</Radius>\n" +
                "          <Temperature>0</Temperature>\n" +
                "          <InventingDate>2019-11-04T15:48:33.1833597+05:00</InventingDate>\n" +
                "          <PlanetOwner>Марс</PlanetOwner>\n" +
                "        </Moon>\n" +
                "      </Moons>\n" +
                "      <HasAtmosphere>false</HasAtmosphere>\n" +
                "      <Type>Tought</Type>\n" +
                "       <Star>Звезда по имнни Солнце</Star>\n" +
                "      <Photo />\n" +
                "      <Name>Марс</Name>\n" +
                "      <MiddleDistance />\n" +
                "      <Radius>0</Radius>\n" +
                "       <Galaxy>этта</Galaxy>\n" +
                "      <Temperature>0</Temperature>\n" +
                "      <InventingDate>2019-11-04T15:48:24.5039792+05:00</InventingDate>\n" +
                "    </Planet>\n" +
                "  </Planets>\n" +
                "  <Galaxy>этта</Galaxy>\n" +
                "  <Photo />\n" +
                "  <Name>Звезда по имнни Солнце</Name>\n" +
                "  <MiddleDistance />\n" +
                "  <Radius>5000000</Radius>\n" +
                "  <Temperature>0</Temperature>\n" +
                "  <InventingDate>2019-11-04T15:47:55.4669753+05:00</InventingDate>\n" +
                "</Star>";
        HashSet<Planet> planets = new HashSet<>();
        Planet planet = new Planet("Mars", "Sun", "Milky Way");
        planet.setInventingDate(new DateTime(2019, 11, 04, 0, 0));
        planet.setHasAtmosphere(false);
        planet.setType(PlanetType.Tought);
        planet.getMoons().add(new Moon("Фобос", planet));
        final Star expected = new Star("этта", null, "Звезда по имнни Солнце", new Distance(0, null),
                5000000, 0, DateTime.parse("2019-11-04T15:47:55.4669753+05:00"), planets);
        final Star actual = XMLHelper.DeserializeStar(source);
        assertEquals(expected, actual);
    }

    static class Example {
        public Date dt = new Date();
    }

    @Test
    public void serializeBytesTest() {
        String source = "4A4A4A";
        byte[] b = DatatypeConverter.parseHexBinary(source);
        String s = DatatypeConverter.printHexBinary(b);
        assertEquals(source, s);
    }

    @Test
    public void serializeBytes2Test() throws DecoderException {
        String s = "4A4A4A";
        byte[] b = Hex.decodeHex(s.toCharArray());
        String st = String.valueOf(Hex.encodeHex(b)).toUpperCase();
        assertEquals(s, st);
    }

    @Test
    public void serializeRealBytesTest() throws UnsupportedEncodingException {
        String source = "/9j/4AAQSkZJRgABAQEAAAAAAAD";
        byte[] b = source.getBytes("UTF8");

        String act = new String(b);
        assertEquals(source, act);
    }

    @Test
    public void serializeRealBytes2Test() throws UnsupportedEncodingException {
        String source = "/9j/4AAQSkZJRgABAQEAAAAAAAD";
        Base64.Encoder enc = Base64.getEncoder();
        byte[] b =  enc.encode(source.getBytes());
        Base64.Decoder dec = Base64.getDecoder();
        String act = new String(dec.decode(b));
        assertEquals(source, act);
    }

    @Test
    public void simpleDownloadAndParseTest() throws IOException {
        String xml = Downloader.DownloadStar(1);
        Star st = XMLHelper.DeserializeStar(xml);
        assertNotEquals(null, st);
        assertNotEquals(new Star(), st);
        assertEquals("sdsdffdss", st.getName());
        assertEquals("wqwwq", st.getGalaxy());
    }

    @Test
    public void dateTimeTest() throws Exception {
        //DateTime dt = DateTime.now();
        DateTime dt = DateTime.parse("2019-11-04T15:47:55.4669753+05:00");
        Date d = new Date(2019, 11, 4);
        assertEquals(d.getYear(), dt.getYear());
        assertEquals(d.getMonth(), dt.getMonthOfYear());
        assertEquals(d.getDay(), dt.getDayOfMonth());
        assertEquals(15, dt.getHourOfDay());
        assertEquals(47, dt.getMinuteOfHour());
        assertEquals(55, dt.getSecondOfMinute());
        assertEquals("2019-11-04T15:47:55.466+05:00", dt.toString());
    }


    @Test
    public void listSerializeTest() {
        List<StarLite> stars = new ArrayList<>(  );
        stars.add( new StarLite( 0, "a", "b" ) );
        stars.add( new StarLite( 1, "ad", "b" ) );
        stars.add( new StarLite( 1, "ca", "b" ) );
        String string = XMLHelper.SerializeStarList(stars);
        assertEquals("<ArrayOfStarLite>\n" +
                "   <StarLite>\n" +
                "      <Id>0</Id>\n" +
                "      <Name>a</Name>\n" +
                "      <Galaxy>b</Galaxy>\n" +
                "   </StarLite>\n" +
                "   <StarLite>\n" +
                "      <Id>1</Id>\n" +
                "      <Name>ad</Name>\n" +
                "      <Galaxy>b</Galaxy>\n" +
                "   </StarLite>\n" +
                "   <StarLite>\n" +
                "      <Id>1</Id>\n" +
                "      <Name>ca</Name>\n" +
                "      <Galaxy>b</Galaxy>\n" +
                "   </StarLite>\n" +
                "</ArrayOfStarLite>", string);
    }

    @Test
    public void simpleStarSerializeTest() {
        String expected = "<?xml version=\"1.0\"?>\n" +
                "<Star>\n" +
                "  <Planets>\n" +
                "  </Planets>\n" +
                "  <Galaxy>этта</Galaxy>\n" +
                "  <Photo />\n" +
                "  <Name>Звезда по имнни Солнце</Name>\n" +
                "  <MiddleDistance>\n" +
                "   <Value>10</Value>\n" +
                "   <Unit>Kilometers</Unit\n" +
                "  </MiddleDistance>\n" +
                "  <Radius>5000000</Radius>\n" +
                "  <Temperature>0</Temperature>\n" +
                "  <InventingDate>3919-12-04T00:00:00.0000000+05</InventingDate>\n" +
                "</Star>";
        final Star source = new Star("этта", new byte[0], "Звезда по имнни Солнце", new Distance(10, UnitType.Kilometers), 5000000, 0, new DateTime(2019, 11, 04, 0, 0));
        String actual = XMLHelper.SerializeStar(source);
        assertEquals(expected, actual);
    }

    @Test
    public void listDeserializeTest() {
        String source = "<?xml version=\"1.0\" encoding=\"utf-16\"?>\n" +
                "<ArrayOfStarLite xmlns:xsi=\"http://www.w3.org/2001/XMLSchema-instance\" xmlns:xsd=\"http://www.w3.org/2001/XMLSchema\">\n" +
                "  <StarLite>\n" +
                "    <Id>1</Id>\n" +
                "    <Name>sdsdffdss</Name>\n" +
                "    <Galaxy>wqwwq</Galaxy>\n" +
                "  </StarLite>\n" +
                "  <StarLite>\n" +
                "    <Id>3</Id>\n" +
                "    <Name>erty</Name>\n" +
                "    <Galaxy>xcvbnm</Galaxy>\n" +
                "  </StarLite>\n" +
                "  <StarLite>\n" +
                "    <Id>4</Id>\n" +
                "    <Name>ewrhfdhf</Name>\n" +
                "    <Galaxy>dsdsds</Galaxy>\n" +
                "  </StarLite>\n" +
                "</ArrayOfStarLite>";
        List<StarLite> stars = new ArrayList<>(  );
        stars.add( new StarLite( 1, "sdsdffdss", "wqwwq" ) );
        stars.add( new StarLite( 3, "erty", "xcvbnm" ) );
        stars.add( new StarLite( 4, "ewrhfdhf", "dsdsds" ) );
        List<StarLite> real = XMLHelper.DeserializeStarList(source);
        assertArrayEquals(stars.toArray(), real.toArray());
    }

}