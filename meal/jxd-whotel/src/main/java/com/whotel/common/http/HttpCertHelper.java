package com.whotel.common.http;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.ByteBuffer;
import java.nio.charset.Charset;
import java.security.KeyStore;
import java.util.ArrayList;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.zip.GZIPInputStream;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLSocketFactory;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.conn.ssl.SSLContexts;

import com.whotel.common.util.AssertUtil;

public class HttpCertHelper {

	 /** 
     * 请求方法 
     */  
    public enum Method {  
        GET, POST  
    }  
      
    /** 
     * 添加URL地址 
     * @param url 
     * @return 
     */  
    public static HttpCertHelper connect(String url, String certPath, String certId) {  
        HttpCertHelper http = new HttpCertHelper();  
        http.url(url);
        http.cert(certPath, certId);
        return http;  
    }  
  
    /** 
     * 替换cert
     * @return 
     */  
    public HttpCertHelper cert(String certPath, String certId) {  
        req.certPath(certPath);
        req.certId(certId);
        return this;  
    }  

	/** 
     * 添加URL地址 
     * @param url 
     * @return 
     */  
    public static HttpCertHelper connect(URL url) {  
        HttpCertHelper http = new HttpCertHelper();  
        http.url(url);  
        return http;  
    }  
  
    private HttpCertHelper.Request req;  
    private HttpCertHelper.Response res;  
  
    private HttpCertHelper() {  
        req = new Request();  
        res = new Response();  
    }  
  
    /** 
     * 替换URL地址 
     * @param url 
     * @return 
     */  
    public HttpCertHelper url(URL url) {  
        req.url(url);  
        return this;  
    }  
  
    /** 
     * 替换URL地址 
     * @param url 
     * @return 
     */  
    public HttpCertHelper url(String url) {  
    	AssertUtil.hasText(url, "Must supply a valid URL");
        try {  
            req.url(new URL(url));  
        } catch (MalformedURLException e) {  
            throw new IllegalArgumentException("Malformed URL: " + url, e);  
        }  
        return this;  
    }  
      
    /** 
     * 设置用户代理 
     * @param userAgent 
     * @return 
     */  
    public HttpCertHelper userAgent(String userAgent) {  
    	AssertUtil.notNull(userAgent, "User agent must not be null");  
        req.header("User-Agent", userAgent);  
        return this;  
    }  
  
    /** 
     * 设置超时时间 
     * @param millis 
     * @return 
     */  
    public HttpCertHelper timeout(int millis) {  
        req.timeout(millis);  
        return this;  
    }  
  
    /** 
     * 设置最大长度 
     * @param bytes 
     * @return 
     */  
    public HttpCertHelper maxBodySize(int bytes) {  
        req.maxBodySize(bytes);  
        return this;  
    }  
  
    /** 
     * 是否跟踪重定向(默认是) 
     * @param followRedirects 
     * @return 
     */  
    public HttpCertHelper followRedirects(boolean followRedirects) {  
        req.followRedirects(followRedirects);  
        return this;  
    }  
  
    /** 
     * 设置网站来路 
     * @param referrer 
     * @return 
     */  
    public HttpCertHelper referrer(String referrer) {  
    	AssertUtil.notNull(referrer, "Referrer must not be null");  
        req.header("Referer", referrer);  
        return this;  
    }  
  
    /** 
     * 设置请求方法 
     * @param method 
     * @return 
     */  
    public HttpCertHelper method(Method method) {  
        req.method(method);  
        return this;  
    }  
  
    /** 
     * 是否忽略Http响应异常 
     * @param ignoreHttpErrors 
     * @return 
     */  
    public HttpCertHelper ignoreHttpErrors(boolean ignoreHttpErrors) {  
        req.ignoreHttpErrors(ignoreHttpErrors);  
        return this;  
    }  
  
    /** 
     * 添加参数 
     * @param key 
     * @param value 
     * @return 
     */  
    public HttpCertHelper data(String key, String value) {  
        req.data(KeyVal.create(key, value));  
        return this;  
    }  
  
    /** 
     * 添加参数 
     * @param data 
     * @return 
     */  
    public HttpCertHelper data(Map<String, String> data) {  
    	AssertUtil.notNull(data, "Data map must not be null");  
        for (Map.Entry<String, String> entry : data.entrySet()) {  
            req.data(KeyVal.create(entry.getKey(), entry.getValue()));  
        }  
        return this;  
    }  
  
  
    /** 
     * 添加标头(header)信息 
     * @param name 
     * @param value 
     * @return 
     */  
    public HttpCertHelper header(String name, String value) {  
        req.header(name, value);  
        return this;  
    }  
  
    /** 
     * 添加cookie信息 
     * @param name 
     * @param value 
     * @return 
     */  
    public HttpCertHelper cookie(String name, String value) {  
        req.cookie(name, value);  
        return this;  
    }  
  
    /** 
     * 添加cookie信息 
     * @param cookies 
     * @return 
     */  
    public HttpCertHelper cookies(Map<String, String> cookies) {  
    	AssertUtil.notNull(cookies, "Cookie map must not be null");  
        for (Map.Entry<String, String> entry : cookies.entrySet()) {  
            req.cookie(entry.getKey(), entry.getValue());  
        }  
        return this;  
    }  
  
    /** 
     * 通过get方法获取文本信息 
     * @return 
     * @throws IOException 
     */  
    public HttpCertHelper.Response get() throws Exception {  
        req.method(Method.GET);  
        res = Response.execute(req);  
        return res;  
    }  
  
    /** 
     * 通过post方法获取文本信息 
     * @return 
     * @throws IOException 
     */  
    public HttpCertHelper.Response post() throws Exception {  
        req.method(Method.POST);  
        res = Response.execute(req);  
        return res;  
    }  
    /** 
     * 通过post方法获取文本信息 
     * @return 
     * @throws IOException 
     */  
    public HttpCertHelper.Response post(String data) throws Exception {  
        req.method(Method.POST);  
        res = Response.execute(req,data);  
        return res;  
    } 
    /** 
     * 获取请求对象 
     * @return 
     */  
    public HttpCertHelper.Request request() {  
        return req;  
    }  
  
    /** 
     * 设置请求对象 
     * @return 
     */  
    public HttpCertHelper request(HttpCertHelper.Request request) {  
        req = request;  
        return this;  
    }  
      
    /** 
     * 设置编码(默认UTF-8) 
     * @return 
     */  
    public HttpCertHelper charset(String charset) {  
    	AssertUtil.isTrue(Charset.isSupported(charset), String.format("The charset of '%s' is not supported", charset));  
        req.charset(charset);  
        return this;  
    }  
  
    @SuppressWarnings({ "unchecked", "rawtypes" })  
    private static abstract class Base<T extends Base> {  
        private URL url;  
        private Method method;  
        private Map<String, String> headers;  
        private Map<String, String> cookies;  
  
        private Base() {  
            headers = new LinkedHashMap<String, String>();  
            cookies = new LinkedHashMap<String, String>();  
        }  
  
        public URL url() {  
            return url;  
        }  
  
        public T url(URL url) {  
        	AssertUtil.notNull(url, "URL must not be null");  
            this.url = url;  
            return (T) this;  
        }  
  
        public Method method() {  
            return method;  
        }  
  
        public T method(Method method) {  
        	AssertUtil.notNull(method, "Method must not be null");  
            this.method = method;  
            return (T) this;  
        }  
  
        public String header(String name) {  
        	AssertUtil.notNull(name, "Header name must not be null");  
            return getHeaderCaseInsensitive(name);  
        }  
  
        public T header(String name, String value) {  
        	AssertUtil.hasText(name, "Header name must not be empty");  
        	AssertUtil.notNull(value, "Header value must not be null");  
            removeHeader(name); // ensures we don't get an "accept-encoding" and a "Accept-Encoding"  
            headers.put(name, value);  
            return (T) this;  
        }  
  
        public boolean hasHeader(String name) {  
            AssertUtil.hasText(name, "Header name must not be empty");  
            return getHeaderCaseInsensitive(name) != null;  
        }  
  
        public T removeHeader(String name) {  
            AssertUtil.hasText(name, "Header name must not be empty");  
            Map.Entry<String, String> entry = scanHeaders(name); // remove is case insensitive too  
            if (entry != null) {  
                headers.remove(entry.getKey()); // ensures correct case  
            }  
            return (T) this;  
        }  
  
        public Map<String, String> headers() {  
            return headers;  
        }  
  
        private String getHeaderCaseInsensitive(String name) {  
            AssertUtil.notNull(name, "Header name must not be null");  
            // quick evals for common case of title case, lower case, then scan for mixed  
            String value = headers.get(name);  
            if (value == null) {  
                value = headers.get(name.toLowerCase());  
            }  
            if (value == null) {  
                Map.Entry<String, String> entry = scanHeaders(name);  
                if (entry != null) {  
                    value = entry.getValue();  
                }  
            }  
            return value;  
        }  
  
        private Map.Entry<String, String> scanHeaders(String name) {  
            String lc = name.toLowerCase();  
            for (Map.Entry<String, String> entry : headers.entrySet()) {  
                if (entry.getKey().toLowerCase().equals(lc)) {  
                    return entry;  
                }  
            }  
            return null;  
        }  
  
        public String cookie(String name) {  
            AssertUtil.notNull(name, "Cookie name must not be null");  
            return cookies.get(name);  
        }  
  
        public T cookie(String name, String value) {  
            AssertUtil.hasText(name, "Cookie name must not be empty");  
            AssertUtil.notNull(value, "Cookie value must not be null");  
            cookies.put(name, value);  
            return (T) this;  
        }  
  
        public boolean hasCookie(String name) {  
            AssertUtil.hasText(name,"Cookie name must not be empty");  
            return cookies.containsKey(name);  
        }  
  
        public T removeCookie(String name) {  
            AssertUtil.hasText(name,"Cookie name must not be empty");  
            cookies.remove(name);  
            return (T) this;  
        }  
  
        public Map<String, String> cookies() {  
            return cookies;  
        }  
    }  
  
    public static class Request extends Base<Request> {  
          
        private int timeoutMilliseconds;  
        private int maxBodySizeBytes;  
        private boolean followRedirects;  
        private Collection<HttpCertHelper.KeyVal> data;
        private boolean ignoreHttpErrors = false;  
        private String charset;  
        private String certPath;
        private String certId;
  
        private Request() {  
            timeoutMilliseconds = 3000;  
            maxBodySizeBytes = 3 * 1024 * 1024; // 1MB  
            followRedirects = true;  
            data = new ArrayList<HttpCertHelper.KeyVal>();  
            super.method = Method.GET;  
            super.headers.put("Accept-Encoding", "gzip");  
        }  
  
        public void certId(String certId) {
			this.certId = certId;
		}

		public void certPath(String certPath) {
			this.certPath = certPath;
		}
		
		public String certId() {
			return certId;
		}

		public String certPath() {
			return certPath;
		}

		public int timeout() {  
            return timeoutMilliseconds;  
        }  
  
        public Request timeout(int millis) {  
            AssertUtil.isTrue(millis >= 0, "Timeout milliseconds must be 0 (infinite) or greater");  
            timeoutMilliseconds = millis;  
            return this;  
        }  
  
        public int maxBodySize() {  
            return maxBodySizeBytes;  
        }  
  
        public HttpCertHelper.Request maxBodySize(int bytes) {  
            AssertUtil.isTrue(bytes >= 0, "maxSize must be 0 (unlimited) or larger");  
            maxBodySizeBytes = bytes;  
            return this;  
        }  
  
        public boolean followRedirects() {  
            return followRedirects;  
        }  
  
        public HttpCertHelper.Request followRedirects(boolean followRedirects) {  
            this.followRedirects = followRedirects;  
            return this;  
        }  
  
        public boolean ignoreHttpErrors() {  
            return ignoreHttpErrors;  
        }  
  
        public HttpCertHelper.Request ignoreHttpErrors(boolean ignoreHttpErrors) {  
            this.ignoreHttpErrors = ignoreHttpErrors;  
            return this;  
        }  
  
        public Request data(HttpCertHelper.KeyVal keyval) {  
            AssertUtil.notNull(keyval, "Key val must not be null");  
            data.add(keyval);  
            return this;  
        }  
  
        public Collection<HttpCertHelper.KeyVal> data() {  
            return data;  
        }  
          
        public String charset() {  
        	if(StringUtils.isBlank(charset)) {
        		return "UTF-8";
        	}
            return charset;  
        }  
          
        public void charset(String charset) {  
            this.charset = charset;  
        }  
          
    }  
  
    public static class Response extends Base<Response> {  
          
        private static final String defaultCharset = "UTF-8"; // used if not found in header or meta charset  
          
        private static final int MAX_REDIRECTS = 20;  
        private int statusCode;  
        private String statusMessage;  
        private ByteBuffer byteData;  
        private String charset = "UTF-8";  
        private String contentType;  
        private boolean executed = false;  
        private int numRedirects = 0;  
        @SuppressWarnings("unused")  
        private HttpCertHelper.Request req;  
  
        Response() {  
            super();  
        }  
  
        private Response(Response previousResponse) throws IOException {  
            super();  
            if (previousResponse != null) {  
                numRedirects = previousResponse.numRedirects + 1;  
                if (numRedirects >= MAX_REDIRECTS) {  
                    throw new IOException(String.format("Too many redirects occurred trying to load URL %s", previousResponse.url()));  
                }  
            }  
        }  
        private static Response execute(HttpCertHelper.Request req,String data) throws Exception {  
            return execute(req, null,data);  
        }  
        private static Response execute(HttpCertHelper.Request req) throws Exception {  
            return execute(req, null,null);  
        }  
  
        private static Response execute(HttpCertHelper.Request req, Response previousResponse,String data) throws Exception {  
              
            AssertUtil.notNull(req, "Request must not be null");  
            String protocol = req.url().getProtocol(); 
            if (!protocol.equals("http") && !protocol.equals("https")) {  
                throw new MalformedURLException("Only http & https protocols supported");  
            }  
            // set up the request for execution  
            if (req.method() == HttpCertHelper.Method.GET && req.data().size() > 0) {  
                serialiseRequestUrl(req); // appends query string  
            }  
            HttpURLConnection conn = createConnection(req);  
            Response res;  
            try {  
                conn.connect();  
                if (req.method() == Method.POST) {  
                	writePost(req,data, conn.getOutputStream());  
                }  
                int status = conn.getResponseCode();  
                boolean needsRedirect = false;  
                if (status != HttpURLConnection.HTTP_OK) {  
                    if (status == HttpURLConnection.HTTP_MOVED_TEMP || status == HttpURLConnection.HTTP_MOVED_PERM || status == HttpURLConnection.HTTP_SEE_OTHER) {  
                        needsRedirect = true;  
                    } else if (!req.ignoreHttpErrors()) {  
                        throw new HttpStatusException("HTTP error fetching URL", status, req.url().toString());  
                    }  
                }  
                res = new Response(previousResponse);  
                res.setupFromConnection(conn, previousResponse);  
                if (needsRedirect && req.followRedirects()) {  
                    req.method(Method.GET); // always redirect with a get. any data param from original req are dropped.  
                    req.data().clear();  
                    req.url(new URL(req.url(), res.header("Location")));  
                    for (Map.Entry<String, String> cookie : res.cookies().entrySet()) { // add response cookies to request (for e.g. login posts)  
                        req.cookie(cookie.getKey(), cookie.getValue());  
                    }  
                    return execute(req, res,null);  
                }  
                res.req = req;  
  
                InputStream dataStream = null;  
                InputStream bodyStream = null;  
                try {  
                    dataStream = conn.getErrorStream() != null ? conn.getErrorStream() : conn.getInputStream();  
                    bodyStream = res.hasHeader("Content-Encoding") && res.header("Content-Encoding").equalsIgnoreCase("gzip") ?  
                            new BufferedInputStream(new GZIPInputStream(dataStream)) :  
                            new BufferedInputStream(dataStream);  
  
                    res.byteData = readToByteBuffer(bodyStream, req.maxBodySize());  
                    if(req.charset == null) {  
                        res.charset = getCharsetFromContentType(res.contentType); // may be null, readInputStream deals with it  
                    } else {  
                        res.charset = req.charset;  
                    }  
                } finally {  
                    if (bodyStream != null) {   
                        bodyStream.close();  
                    }  
                    if (dataStream != null) {  
                        dataStream.close();  
                    }  
                }  
            } finally {  
                conn.disconnect();  
            }  
  
            res.executed = true;  
            return res;  
        }  
  
        public int statusCode() {  
            return statusCode;  
        }  
  
        public String statusMessage() {  
            return statusMessage;  
        }  
  
        public String charset() {  
            return charset;  
        }  
          
        public String contentType() {  
            return contentType;  
        }  
  
        public String html() throws Exception {  
              
            AssertUtil.isTrue(executed, "Request must be executed (with .execute(), .get(), or .post() before parsing response");  
              
            String content = null;  
              
            if(charset == null) {  
                  
                content = Charset.forName(defaultCharset).decode(byteData).toString();  
                  
                Pattern pattern = Pattern.compile("<meta[^>]*content=\"(.+?)\"[^>]*>");  
                  
                Matcher matcher = pattern.matcher(content);  
                  
                String foundCharset = null;  
                  
                while (matcher.find()) {  
                    foundCharset = getCharsetFromContentType(matcher.group(1));  
                    if(foundCharset != null) {  
                        break;  
                    }  
                }  
                  
                if(foundCharset == null) {  
                    pattern = Pattern.compile("<meta[^>]*charset=\"(.+?)\"[^>]*>");  
                    matcher = pattern.matcher(content);  
                    while (matcher.find()) {  
                        foundCharset = matcher.group(1);  
                        if(foundCharset != null) {  
                            break;  
                        }  
                    }  
                }  
                  
                if(foundCharset != null) {  
                    charset = foundCharset;  
                    content = Charset.forName(foundCharset).decode(byteData).toString();  
                }  
            } else {  
                content = Charset.forName(charset).decode(byteData).toString();  
            }  
              
            if (content.length() > 0 && content.charAt(0) == 65279) {  
                content = content.substring(1);  
            }  
            byteData.rewind();  
              
            return content;  
        }  
          
        public void toFile(String to) {  
              
            AssertUtil.notNull(to, "Data value must not be null");  
              
            makeDirs(to);  
              
            generateFile(to);  
              
        }  
          
        private static void makeDirs(String path) {  
            try {  
                int i = path.lastIndexOf("/");  
                if (i < 1) {  
                    i = path.lastIndexOf("\\");  
                }  
                path = path.substring(0, i);  
                File file = new File(path);  
                if (!file.exists()) {  
                    file.mkdirs();  
                }  
            } catch (Exception e) {  
                e.printStackTrace();  
            }  
        }  
          
        private void generateFile(String to) {  
            OutputStream bos = null;  
            try {  
                bos = new BufferedOutputStream(new FileOutputStream(to));  
                if(byteData != null) {  
                    bos.write(byteData.array(), 0, byteData.array().length);  
                }  
                bos.flush();  
            } catch (FileNotFoundException e) {  
                e.printStackTrace();  
            } catch (IOException e) {  
                e.printStackTrace();  
            } finally {  
                if (bos != null) {  
                    try {  
                        bos.close();  
                    } catch (IOException e) {  
                        e.printStackTrace();  
                    }  
                }  
            }  
        }  
  
        // set up connection defaults, and details from request  
        private static HttpURLConnection createConnection(HttpCertHelper.Request req) throws Exception { 
        	 
        	
            HttpURLConnection conn = (HttpURLConnection) req.url().openConnection();  
            conn.setRequestMethod(req.method().name());  
            conn.setInstanceFollowRedirects(false); // don't rely on native redirection support  
            conn.setConnectTimeout(req.timeout());  
            conn.setReadTimeout(req.timeout());  
            if (req.method() == Method.POST) {  
                conn.setDoOutput(true);  
            }  
            if (req.cookies().size() > 0) {  
                conn.addRequestProperty("Cookie", getRequestCookieString(req));  
            }  
            for (Map.Entry<String, String> header : req.headers().entrySet()) {  
                conn.addRequestProperty(header.getKey(), header.getValue());  
            }  
            if(req.url().getProtocol().equals("https")){
            	 KeyStore keyStore  = KeyStore.getInstance("PKCS12");
                 FileInputStream instream = new FileInputStream(new File(req.certPath()));
                 char[] certIds = req.certId().toCharArray(); 
                 try {
                     keyStore.load(instream, certIds);
                 } finally {
                     instream.close();
                 }

                 // Trust own CA and all self-signed certs
                 SSLContext sslcontext = SSLContexts.custom()
                         .loadKeyMaterial(keyStore, certIds)
                         .build();
              
                SSLSocketFactory ssf = sslcontext.getSocketFactory(); 
                
                SSLEngine engine = sslcontext.createSSLEngine();
//                engine.setNeedClientAuth(true); //ssl双向认证
//                engine.setUseClientMode(false);
//                engine.setWantClientAuth(true);
                engine.setEnabledProtocols(new String[]{"TLSv1"});
               
               
                //
                HttpsURLConnection conn2=(HttpsURLConnection)conn;
                conn2.setSSLSocketFactory(ssf);  
            }
            return conn;  
        }  
  
        // set up url, method, header, cookies  
        private void setupFromConnection(HttpURLConnection conn, HttpCertHelper.Response previousResponse) throws IOException {  
            super.method = HttpCertHelper.Method.valueOf(conn.getRequestMethod());  
            super.url = conn.getURL();  
            statusCode = conn.getResponseCode();  
            statusMessage = conn.getResponseMessage();  
            contentType = conn.getContentType();  
  
            Map<String, List<String>> resHeaders = conn.getHeaderFields();  
            processResponseHeaders(resHeaders);  
  
            // if from a redirect, map previous response cookies into this response  
            if (previousResponse != null) {  
                for (Map.Entry<String, String> prevCookie : previousResponse.cookies().entrySet()) {  
                    if (!hasCookie(prevCookie.getKey())) {  
                        cookie(prevCookie.getKey(), prevCookie.getValue());  
                    }  
                }  
            }  
        }  
  
        private void processResponseHeaders(Map<String, List<String>> resHeaders) {  
            for (Map.Entry<String, List<String>> entry : resHeaders.entrySet()) {  
                String name = entry.getKey();  
                if (name == null) {  
                    continue; // http/1.1 line  
                }  
  
                List<String> values = entry.getValue();  
                if (name.equalsIgnoreCase("Set-Cookie")) {  
                    for (String value : values) {  
                        if (value == null){  
                            continue;  
                        }  
                        TokenQueue cd = new TokenQueue(value);  
                        String cookieName = cd.chompTo("=").trim();  
                        String cookieVal = cd.consumeTo(";").trim();  
                        if (cookieVal == null) {  
                            cookieVal = "";  
                        }  
                        // ignores path, date, domain, secure et al. req'd?  
                        // name not blank, value not null  
                        if (cookieName != null && cookieName.length() > 0) {  
                            cookie(cookieName, cookieVal);  
                        }  
                    }  
                } else { // only take the first instance of each header  
                    if (!values.isEmpty()) {  
                        header(name, values.get(0));  
                    }  
                }  
            }  
        }  
  
        private static void writePost(HttpCertHelper.Request req,String strData, OutputStream outputStream) throws IOException {  
        	
        	Collection<HttpCertHelper.KeyVal> data = req.data();
        	
            OutputStreamWriter w = new OutputStreamWriter(outputStream, req.charset());  
            
            if(strData!=null&&!strData.trim().equals("")){
            	 w.write(strData); 
            }
            boolean first = true; 
            if(data!=null&&data.size()>0){
	            for (HttpCertHelper.KeyVal keyVal : data) {  
	                if (!first) {  
	                    w.append('&');  
	                } else {  
	                    first = false;  
	                }  
	                  
	                w.write(URLEncoder.encode(keyVal.key(), req.charset()));  
	                w.write('=');  
	                w.write(URLEncoder.encode(keyVal.value(), req.charset()));  
	            }
            }
            w.close();  
        }  
//        private static void writePost(String data, OutputStream outputStream) throws IOException {  
//            OutputStreamWriter w = new OutputStreamWriter(outputStream, defaultCharset);  
//            w.write(data); 
//            w.close();  
//        }   
        private static String getRequestCookieString(HttpCertHelper.Request req) {  
            StringBuilder sb = new StringBuilder();  
            boolean first = true;  
            for (Map.Entry<String, String> cookie : req.cookies().entrySet()) {  
                if (!first) {  
                    sb.append("; ");  
                } else {  
                    first = false;  
                }  
                sb.append(cookie.getKey()).append('=').append(cookie.getValue());  
            }  
            return sb.toString();  
        }  
  
        // for get url reqs, serialise the data map into the url  
        private static void serialiseRequestUrl(HttpCertHelper.Request req) throws IOException {  
            URL in = req.url();  
            StringBuilder url = new StringBuilder();  
            boolean first = true;  
            // reconstitute the query, ready for appends  
            url.append(in.getProtocol())  
                .append("://")  
                .append(in.getAuthority()) // includes host, port  
                .append(in.getPath())  
                .append("?");  
            if (in.getQuery() != null) {  
                url.append(in.getQuery());  
                first = false;  
            }  
            for (HttpCertHelper.KeyVal keyVal : req.data()) {  
                if (!first) {  
                    url.append('&');  
                } else {  
                    first = false;  
                }  
                url.append(URLEncoder.encode(keyVal.key(), req.charset()))  
                    .append('=')  
                    .append(URLEncoder.encode(keyVal.value(), req.charset()));  
            }  
            req.url(new URL(url.toString()));  
            req.data().clear(); // moved into url as get params  
        }  
    }  
      
    private static class TokenQueue {  
        private String queue;  
        private int pos = 0;  
          
        /** 
         Create a new TokenQueue. 
         @param data string of data to back queue. 
         */  
        public TokenQueue(String data) {  
            AssertUtil.notNull(data);  
            queue = data;  
        }  
  
        /** 
         * Is the queue empty? 
         * @return true if no data left in queue. 
         */  
        public boolean isEmpty() {  
            return remainingLength() == 0;  
        }  
          
        private int remainingLength() {  
            return queue.length() - pos;  
        }  
  
        /** 
         * Tests if the next characters on the queue match the sequence. Case insensitive. 
         * @param seq String to check queue for. 
         * @return true if the next characters match. 
         */  
        public boolean matches(String seq) {  
            return queue.regionMatches(true, pos, seq, 0, seq.length());  
        }  
  
        /** 
         * Tests if the queue matches the sequence (as with match), and if they do, removes the matched string from the 
         * queue. 
         * @param seq String to search for, and if found, remove from queue. 
         * @return true if found and removed, false if not found. 
         */  
        public boolean matchChomp(String seq) {  
            if (matches(seq)) {  
                pos += seq.length();  
                return true;  
            } else {  
                return false;  
            }  
        }  
  
        /** 
         * Consume one character off queue. 
         * @return first character on queue. 
         */  
        public char consume() {  
            return queue.charAt(pos++);  
        }  
  
        /** 
         * Pulls a string off the queue, up to but exclusive of the match sequence, or to the queue running out. 
         * @param seq String to end on (and not include in return, but leave on queue). <b>Case sensitive.</b> 
         * @return The matched data consumed from queue. 
         */  
        public String consumeTo(String seq) {  
            int offset = queue.indexOf(seq, pos);  
            if (offset != -1) {  
                String consumed = queue.substring(pos, offset);  
                pos += consumed.length();  
                return consumed;  
            } else {  
                return remainder();  
            }  
        }  
          
        /** 
         * Pulls a string off the queue (like consumeTo), and then pulls off the matched string (but does not return it). 
         * <p> 
         * If the queue runs out of characters before finding the seq, will return as much as it can (and queue will go 
         * isEmpty() == true). 
         * @param seq String to match up to, and not include in return, and to pull off queue. <b>Case sensitive.</b> 
         * @return Data matched from queue. 
         */  
        public String chompTo(String seq) {  
            String data = consumeTo(seq);  
            matchChomp(seq);  
            return data;  
        }  
  
        /** 
         Consume and return whatever is left on the queue. 
         @return remained of queue. 
         */  
        public String remainder() {  
            StringBuilder accum = new StringBuilder();  
            while (!isEmpty()) {  
                accum.append(consume());  
            }  
            return accum.toString();  
        }  
          
        @Override
		public String toString() {  
            return queue.substring(pos);  
        }  
    }  
  
    public static class KeyVal {  
          
        private String key;  
        private String value;  
  
        public static KeyVal create(String key, String value) {  
            AssertUtil.hasText(key, "Data key must not be empty");  
            AssertUtil.notNull(value, "Data value must not be null");  
            return new KeyVal(key, value);  
        }  
  
        private KeyVal(String key, String value) {  
            this.key = key;  
            this.value = value;  
        }  
  
        public KeyVal key(String key) {  
            AssertUtil.hasText(key, "Data key must not be empty");  
            this.key = key;  
            return this;  
        }  
  
        public String key() {  
            return key;  
        }  
  
        public KeyVal value(String value) {  
            AssertUtil.notNull(value, "Data value must not be null");  
            this.value = value;  
            return this;  
        }  
  
        public String value() {  
            return value;  
        }  
  
    }  
      
    @SuppressWarnings("unused")  
    private static class HttpStatusException extends IOException {  
  
        private static final long serialVersionUID = -2926428810498166324L;  
        private int statusCode;  
        private String url;  
  
        public HttpStatusException(String message, int statusCode, String url) {  
            super(message);  
            this.statusCode = statusCode;  
            this.url = url;  
        }  
  
        public int getStatusCode() {  
            return statusCode;  
        }  
  
        public String getUrl() {  
            return url;  
        }  
  
        @Override
		public String toString() {  
            return super.toString() + ". Status=" + statusCode + ", URL=" + url;  
        }  
    }  
      
    @SuppressWarnings("unused")  
    private static class UnsupportedMimeTypeException extends IOException {  
          
        private static final long serialVersionUID = 2535952512520299658L;  
        private String mimeType;  
        private String url;  
  
        public UnsupportedMimeTypeException(String message, String mimeType, String url) {  
            super(message);  
            this.mimeType = mimeType;  
            this.url = url;  
        }  
  
        public String getMimeType() {  
            return mimeType;  
        }  
  
        public String getUrl() {  
            return url;  
        }  
  
        @Override
		public String toString() {  
            return super.toString() + ". Mimetype=" + mimeType + ", URL="+url;  
        }  
    }  
      
    private static ByteBuffer readToByteBuffer(InputStream inStream, int maxSize) throws IOException {  
        AssertUtil.isTrue(maxSize >= 0, "maxSize must be 0 (unlimited) or larger");  
        final boolean capped = maxSize > 0;  
        byte[] buffer = new byte[0x20000];  
        ByteArrayOutputStream outStream = new ByteArrayOutputStream(0x20000);  
        int read;  
        int remaining = maxSize;  
  
        while (true) {  
            read = inStream.read(buffer);  
            if (read == -1) break;  
            if (capped) {  
                if (read > remaining) {  
                    outStream.write(buffer, 0, remaining);  
                    break;  
                }  
                remaining -= read;  
            }  
            outStream.write(buffer, 0, read);  
        }  
        ByteBuffer byteData = ByteBuffer.wrap(outStream.toByteArray());  
        return byteData;  
    }  
      
    private static String getCharsetFromContentType(String contentType) {  
        if (contentType == null) {  
            return null;  
        }  
        Pattern charsetPattern = Pattern.compile("(?i)\\bcharset=\\s*\"?([^\\s;\"]*)");  
        Matcher m = charsetPattern.matcher(contentType);  
        if (m.find()) {  
            String charset = m.group(1).trim();  
            if (Charset.isSupported(charset)) {  
                return charset;  
            }  
            charset = charset.toUpperCase(Locale.ENGLISH);  
            if (Charset.isSupported(charset)) {  
                return charset;  
            }  
        }  
        return null;  
    }  
    public static void main(String args[]) throws Exception{
    	//获取文本信息
    	//String str = HttpCertHelper.connect("https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=APPID&secret=APPSECRET").timeout(3000).get().html();
    	//System.out.println(str.toString());
//    	//保存到本地
//    	HttpCertHelper.connect("http://www.autohome.com.cn/364/").timeout(3000).get().toFile("D://001.html");
//    	
//    	//图片本地化(破解防盗链)
//    	HttpCertHelper.connect("http://www.autoimg.cn/upload/2013/2/25/t_201302251823083624136.jpg")
//    			.referrer("http://www.autohome.com.cn/").timeout(3000).get().toFile("D://001.jpg");
    }
}
