package com.example.astronomicdirclient.XMLService;

import org.joda.time.DateTime;
import org.simpleframework.xml.transform.Transform;

import java.text.DateFormat;
import java.util.Date;

public class DateFormatTransformer implements Transform<DateTime>
{

    @Override
    public DateTime read(String value) throws Exception
    {
        return DateTime.parse(value);
    }

    @Override
    public String write(DateTime value) throws Exception
    {
        return value.toString();//dateFormat.format(value);
    }
}