package com.example.restaurant.tag;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.TagSupport;
import java.io.IOException;
import java.util.HashMap;

public class BucketProductsCounterTag extends TagSupport {
    private static final Logger LOGGER = LogManager.getLogger(BucketProductsCounterTag.class);

    private HashMap<Long, Integer> bucket;

    public HashMap<Long, Integer> getBucket() {
        return bucket;
    }

    public void setBucket(HashMap<Long, Integer> bucket) {
        this.bucket = bucket;
    }

    @Override
    public int doStartTag() {
        try {
            JspWriter out = pageContext.getOut();
            out.write(bucket.values().stream().reduce(0, Integer::sum).toString());
        } catch (IOException e) {
            LOGGER.error("Error while trying count products in bucket", e);
        }
        return SKIP_BODY;
    }
}
