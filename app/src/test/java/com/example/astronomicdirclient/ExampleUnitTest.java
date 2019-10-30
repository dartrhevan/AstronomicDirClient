package com.example.astronomicdirclient;

import android.util.Xml;

import com.example.astronomicdirclient.Model.StarLite;

import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }


    @Test
    public void serrializeTest() {
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
    public void deserrializeTest() {
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
        List<StarLite> real = XMLHelper.DeserrializeStarList(source);
        assertArrayEquals(stars.toArray(), real.toArray());
    }
}