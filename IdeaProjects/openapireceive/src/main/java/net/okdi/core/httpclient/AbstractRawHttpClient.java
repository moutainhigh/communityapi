package net.okdi.core.httpclient;

import org.apache.http.HttpEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.nio.charset.Charset;

public abstract class AbstractRawHttpClient implements RawHttpClient{

    private @Autowired CloseableHttpClient httpClient;

    private static final String CHARSET_UTF8 = "utf-8";

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractRawHttpClient.class);

    @Override
    public String post(String url, String rawRequestBody) {
        LOGGER.info("sendapi post请求地址 => " + url + ", 参数 => " + rawRequestBody);

        RawHttpHeader header = doGetHeader();
        CloseableHttpResponse response = null;
        HttpPost post = new HttpPost(url);
        post.setHeader("Content-Type", header.getContentType());
        post.setHeader(header.getTokenField(), header.getTokenStr());

        try {
            post.setEntity(new StringEntity(rawRequestBody, Charset.forName(CHARSET_UTF8)));
            response = httpClient.execute(post);
            int status = response.getStatusLine().getStatusCode();
            if (status >= 200 && status < 300) {
                HttpEntity entity = response.getEntity();
                String result = entity != null ? EntityUtils.toString(entity, CHARSET_UTF8) : null;
                LOGGER.info("sendapi Post请求结果 => " + result);
                return result;
            } else {
                LOGGER.error("url => " + url + "请求失败, 状态码 => " + status);
                return null;
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                if (response != null) {
                    response.close();
                    EntityUtils.consume(response.getEntity());
                }
                post.releaseConnection();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }


    abstract protected RawHttpHeader doGetHeader();

}
