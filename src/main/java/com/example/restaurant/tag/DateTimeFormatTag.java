package com.example.restaurant.tag;

import javax.servlet.jsp.JspException;
import java.io.IOException;
import java.sql.Timestamp;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.time.format.DateTimeFormatter;

public class DateTimeFormatTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger(DateTimeFormatTag.class);

    private static final String DATE_TIME_FORMAT_PATTERN = "HH:mm '&#183' dd-MM-yyyy";

    private Timestamp dateTime;

    public Timestamp getDateTime() {
        return dateTime;
    }

    public void setDateTime(Timestamp dateTime) {
        this.dateTime = dateTime;
    }

    @Override
    public int doStartTag() throws JspException {
        try {
            JspWriter out = pageContext.getOut();
            out.write(DateTimeFormatter.ofPattern(DATE_TIME_FORMAT_PATTERN).format(dateTime.toLocalDateTime()));
        } catch (IOException e) {
            LOGGER.error("Error while trying format date and time", e);
        }
        return SKIP_BODY;
    }
}
