package com.example.astronomicdirclient.XMLService;

import android.os.Build;
import android.support.annotation.RequiresApi;

import org.apache.commons.codec.DecoderException;
import org.apache.commons.codec.StringEncoder;
import org.apache.commons.codec.binary.Hex;
import org.joda.time.DateTime;
import org.simpleframework.xml.transform.Transform;

import java.io.ByteArrayInputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.util.Base64;

import javax.xml.bind.DatatypeConverter;

public class BytesFormatTransformer implements Transform<byte[]>
{
    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public byte[] read(String s) {
        /*
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encode(s.getBytes());*/
        return Base64.getDecoder().decode(s);
    }

    @RequiresApi(api = Build.VERSION_CODES.O)
    @Override
    public String write(byte[] b)
    {
        Base64.Encoder enc = Base64.getEncoder();
        return enc.encodeToString(b);
    }
}