package com.duimy.dmimsdk.utils;

import android.util.Xml;

import com.duimy.dmimsdk.model.DMImageMessageBody;
import com.duimy.dmimsdk.model.DMLocationMessageBody;
import com.duimy.dmimsdk.model.DMMessageBody;
import com.duimy.dmimsdk.model.DMTextMessageBody;
import com.duimy.dmimsdk.model.DMVoiceMessageBody;

import org.xmlpull.v1.XmlPullParser;

import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * Created by haley on 2017/7/18.
 */

public class DMXMLParser {

    public static DMMessageBody XMLParse(InputStream inputStream, int type) {
        DMMessageBody body = null;

        XmlPullParser parser = Xml.newPullParser();

        Class clazz;

        switch (type) {
            case DMMessageBody.Body_Type_Text:
                clazz = DMTextMessageBody.class;
                body = new DMTextMessageBody();
                break;
            case DMMessageBody.Body_Type_Location:
                clazz = DMLocationMessageBody.class;
                body = new DMLocationMessageBody();
                break;
            case DMMessageBody.Body_Type_Image:
                clazz = DMImageMessageBody.class;
                body = new DMImageMessageBody();
                break;
            case DMMessageBody.Body_Type_Voice:
                clazz = DMVoiceMessageBody.class;
                body = new DMVoiceMessageBody();
                break;
            default:
                clazz = DMMessageBody.class;
                break;
        }

        if (body == null) {
            return null;
        }

        Method[] methods = clazz.getMethods();

        try {
            parser.setInput(inputStream, "UTF-8");
            int eventType = parser.getEventType();
            while (eventType != XmlPullParser.END_DOCUMENT) {
                if (eventType == XmlPullParser.START_TAG) {
                    String parserName = parser.getName();
                    for (Method method : methods) {
                        String name = method.getName();
                        if (name.startsWith("set") && name.substring(3)
                                .equalsIgnoreCase(parserName)) {
                            parser.next();
                            method.invoke(body, parser.getText());
                        }
                    }
                }
                eventType = parser.next();
            }
            return body;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return body;
    }

}
