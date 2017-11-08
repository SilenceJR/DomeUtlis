package com.duimy.dmimsdk.model;

import org.jivesoftware.smack.packet.Message;
import org.jivesoftware.smack.util.XmlStringBuilder;
import org.xutils.db.annotation.Column;
import org.xutils.db.annotation.Table;

/**
 * Created by haley on 2017/7/17.
 */

@Table(name = "T_TextBody")
public class DMTextMessageBody extends DMMessageBody {

    private String textBoadyTag = "textbody";

    @Column(name = "_textbody")
    private String textbody;

    public String getTextbody() {
        return textbody;
    }

    public void setTextbody(String textbody) {
        this.textbody = textbody;
    }

    public DMTextMessageBody() {
        this.setBodyType(DMMessageBody.Body_Type_Text);
    }

    public DMTextMessageBody(String textbody) {
        this.textbody = textbody;
        this.setBodyType(DMMessageBody.Body_Type_Text);
    }

    @Override
    public CharSequence toXML() {

        XmlStringBuilder buf = new XmlStringBuilder();
        buf.openElement(getElementName());

        buf.element(textBoadyTag, textbody);

        buf.closeElement(getElementName());

        return buf;
    }

    @Override
    public String toString() {
        return "DMTextMessageBody{" +
                "textbody='" + textbody + '\'' +
                '}';
    }
}
