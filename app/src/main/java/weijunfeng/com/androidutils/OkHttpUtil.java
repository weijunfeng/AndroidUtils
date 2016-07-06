package weijunfeng.com.androidutils;


import android.text.TextUtils;
import okhttp3.*;

import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class OkHttpUtil {

    public static final String CHARSET = "UTF_8";
    private static OkHttpClient okHttpClient = null;

    private OkHttpUtil() {
    }

    private static OkHttpClient getOkHttpClient() {
        if (okHttpClient == null) {
            synchronized (OkHttpUtil.class) {
                if (okHttpClient == null) {
                    okHttpClient = new OkHttpClient.Builder()//.addInterceptor()
                            .connectTimeout(10 * 1000, TimeUnit.MILLISECONDS).readTimeout(10 * 1000, TimeUnit.MILLISECONDS)
                            .writeTimeout(10 * 1000, TimeUnit.MILLISECONDS)//.cookieJar()
                            .build();
                }
            }
        }
        return okHttpClient;
    }

    private static String encodeParams(Map<String, String> params, String charser, boolean urlEncode) {
        StringBuilder stringBuilder = new StringBuilder();
        if (params != null && !params.isEmpty()) {
            int i = 0;
            for (Map.Entry<String, String> entry : params.entrySet()) {
                String key = entry.getKey();
                String value = entry.getValue();
                if (!TextUtils.isEmpty(value) && !TextUtils.isEmpty(key)) {
                    if (urlEncode) {
                        try {
                            key = URLEncoder.encode(key, charser);
                            value = URLEncoder.encode(value, charser);
                            stringBuilder.append(key)
                                    .append("=")
                                    .append(value);
                            if (i != params.size() - 1) {
                                stringBuilder.append("&");
                            }
                        } catch (UnsupportedEncodingException e) {
                            e.printStackTrace();
                        }
                    }
                }
                i++;
            }
        }
        return stringBuilder.toString();
    }

    private static String encodeParams(Map<String, String> params) {
        return encodeParams(params, CHARSET, true);
    }

    private static Response execute(Request request) throws IOException {
        Request request1 = request.newBuilder().addHeader("User-Agent", "Android").build();
        return getOkHttpClient().newCall(request1).execute();
    }

    public static Response doGet(String url, Map<String, String> params) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("url can't be null");
        }
        int index = url.lastIndexOf("?");
        if (index == -1) {
            url = url + "?";
        } else {
            url = url + "&";
        }
        String encodeParams = encodeParams(params);
        Request request = new Request.Builder().url(url + encodeParams).get().build();
        return execute(request);
    }

    public static Response doPost(String url, RequestBody body) throws IOException {
        if (url == null) {
            throw new IllegalArgumentException("url can't be null");
        }
        Request request = new Request.Builder().url(url).post(body).build();
        return execute(request);
    }

    public static Response doPost(String url, Map<String, String> params) throws IOException {
        FormBody.Builder formBody = new FormBody.Builder();
        if (params != null) {
            for (String key : params.keySet()) {
                String value = params.get(key);
                if (!TextUtils.isEmpty(value)) {
                    formBody.add(key, value);
                }
            }
        }
        return doPost(url, formBody.build());
    }

    public static Response doPost(String url, String contentType, File file) throws IOException {
        if (file == null) {
            throw new IllegalArgumentException("file can't be null");
        }
        String fileName = file.getName();
        fileName = fileName.substring(0, fileName.lastIndexOf("."));
        MediaType mediaType = MediaType.parse(contentType);
        RequestBody body = RequestBody.create(mediaType, file);
        MultipartBody.Builder builder = new MultipartBody.Builder().setType(MultipartBody.FORM)
                .addFormDataPart("file", fileName, body);
        return doPost(url, builder.build());
    }
}
