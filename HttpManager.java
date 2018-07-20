import com.alibaba.fastjson.JSON;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;
import okhttp3.ResponseBody;

/**
 * @author zhengyanda
 */
public class HttpManager {

    private OkHttpClient client;

    private MediaType jsonType;


    private HttpManager() {
        client = new OkHttpClient();
        jsonType = MediaType.parse("application/json");
    }

    public static HttpManager getInstace() {
        return Holder.instance;
    }

    /**
     * Okhttp post 同步方法
     *
     * @param url    请求的目标地址
     * @param params 请求数据
     * @return 服务器的返回数据
     * @throws Exception 方法抛出的异常包括IO异常、请求失败异常以及返回数据的空异常。
     */
    public String post(String url, JSONObject params) throws Exception {

        RequestBody requestBody = RequestBody.create(jsonType, params.toString());
        Request request = new Request
                .Builder()
                .post(requestBody)
                .url(url)
                .build();

        Response response = client.newCall(request).execute();
        if (!response.isSuccessful()) {
            throw new Exception(response.message());
        }

        ResponseBody body = response.body();
        if (body == null) {
            throw new Exception("response.body() is null");
        }

        return body.string();
    }

    /**
     * Okhttp post 异步方法
     *
     * @param url      请求的目标地址
     * @param params   请求数据
     * @param callback 回调
     */
    public void post(String url, JSONObject params, final Class clazz, final HttpCallback callback) {

        RequestBody requestBody = RequestBody.create(jsonType, params.toString());
        Request request = new Request
                .Builder()
                .post(requestBody)
                .url(url)
                .build();

        client = new OkHttpClient();
        final Call c = client.newCall(request);
        c.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                callback.onFailure(e.toString());
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                if (!response.isSuccessful()) {
                    callback.onFailure(response.message());
                    return;
                }
                if (clazz == null) {
                    callback.onSuccess(null);
                    return;
                }
                ResponseBody body = response.body();
                if (body == null) {
                    callback.onFailure("response.body() is null");
                    return;
                }
                String result = body.string();
                
                callback.onSuccess(JSON.parseObject(result, clazz));

                body.close();
            }
        });
    }

    public interface HttpCallback {
        void onSuccess(Object result);

        void onFailure(String e);
    }

    private static class Holder {
        private static HttpManager instance = new HttpManager();
    }
}
