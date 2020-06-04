package com.jerome.okhttpcapture.internal.utils;

import android.content.Context;
import android.os.AsyncTask;
import android.text.TextUtils;
import android.widget.Toast;

import com.jerome.okhttpcapture.InitCapture;
import com.jerome.okhttpcapture.internal.CaptureEntity;
import com.jerome.okhttpcapture.internal.ui.CaptureContentAdapter;
import com.jerome.okhttpcapture.internal.ui.UIItemEntity;
import com.jerome.okhttpcapture.internal.ui.UISubItemVH;
import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class GetCaptureDataUtils {

    public static void getData(Context context, final String parentFileName, final String fileName, final CallBack callBack){
        Toast.makeText(context,"正在加载，请稍后...",Toast.LENGTH_SHORT).show();
        new AsyncTask<Void,Void,List<CaptureContentAdapter.Entity>>(){

            @Override
            protected List<CaptureContentAdapter.Entity> doInBackground(Void... voids) {
                String str = InitCapture.get().getCacheUtils().getCaputre(parentFileName,fileName);
                Gson gson = new Gson();
                CaptureEntity captureEntity = gson.fromJson(str, CaptureEntity.class);

                List<CaptureContentAdapter.Entity> list = new ArrayList<>();
                if(captureEntity == null) return list;

                list.add(new CaptureContentAdapter.Entity("请求方式",captureEntity.requestMethod));
                list.add(new CaptureContentAdapter.Entity("请求URL",captureEntity.requestUrl));
                if(!TextUtils.isEmpty(captureEntity.requestHeader)) {
                    list.add(new CaptureContentAdapter.Entity("请求Header", captureEntity.requestHeader));
                }
                if(!TextUtils.isEmpty(captureEntity.requestBody)){
                    list.add(new CaptureContentAdapter.Entity("请求体",captureEntity.requestBody));
                }

                list.add(new CaptureContentAdapter.Entity("响应状态",captureEntity.responseStatus));
                list.add(new CaptureContentAdapter.Entity("响应Header",captureEntity.responseHeader));


                list.add(new CaptureContentAdapter.Entity("响应体",formatJson(captureEntity.responseBody)));

                return list;
            }

            private String formatJson(String str){
                String json;
                try{
                    if (str.startsWith("{")) {
                        JSONObject jsonObject = new JSONObject(str);
                        json = jsonObject.toString(4);
                    } else if (str.startsWith("[")) {
                        JSONArray jsonArray = new JSONArray(str);
                        json = jsonArray.toString(4);
                    } else {
                        json = str;
                    }

                }catch (Exception e){
                    json = str;
                }
                return json;
            }

            @Override
            protected void onPostExecute(List<CaptureContentAdapter.Entity> list) {
                super.onPostExecute(list);
                if(callBack != null){
                    callBack.success(list);
                }
            }
        }.execute();
    }


    public static void getSlidData(final CallBackSlide callBackSlide){
        new AsyncTask<Void,Void,List<UIItemEntity>>(){

            @Override
            protected List<UIItemEntity> doInBackground(Void... voids) {
                List<String> capture = InitCapture.get().getCacheUtils().getCapture();
                List<UIItemEntity> list = new ArrayList<>();
                for (int i = 0; i < capture.size(); i++) {
                    String str = capture.get(i);
                    UIItemEntity uiItemEntity = new UIItemEntity();
                    uiItemEntity.mExpanded = (i == 0);
                    uiItemEntity.name = str;
                    uiItemEntity.subFileList = getSubItemList(str);
                    if(uiItemEntity.subFileList.size() > 0) {
                        list.add(uiItemEntity);
                    }else{
                        InitCapture.get().getCacheUtils().deleteValidtyFileDir(str);
                    }
                }
                return list;
            }


            public List<UISubItemVH.SubEntity> getSubItemList(String parentFileName) {
                List<UISubItemVH.SubEntity> list = new ArrayList<>();
                List<String> capture = InitCapture.get().getCacheUtils().getCapture(parentFileName);
                for (String s : capture) {
                    if(InitCapture.get().getCacheUtils().checkValidity(s)){
                        list.add(new UISubItemVH.SubEntity(s, parentFileName));
                    }else{
                        InitCapture.get().getCacheUtils().deleteValidtyFileCapture(parentFileName,s);
                    }
                }
                return list;
            }

            @Override
            protected void onPostExecute(List<UIItemEntity> uiItemEntities) {
                super.onPostExecute(uiItemEntities);
                if(callBackSlide != null){
                    callBackSlide.success(uiItemEntities);
                }
            }
        }.execute();
    }

    public interface CallBack{
        void success(List<CaptureContentAdapter.Entity> list);
    }

    public interface CallBackSlide{
        void success(List<UIItemEntity> list);
    }
}
