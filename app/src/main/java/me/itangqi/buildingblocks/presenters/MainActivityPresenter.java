package me.itangqi.buildingblocks.presenters;

import android.net.Uri;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import me.itangqi.buildingblocks.domain.application.App;
import me.itangqi.buildingblocks.domain.utils.Constants;
import me.itangqi.buildingblocks.domain.utils.PrefUtils;
import me.itangqi.buildingblocks.domain.utils.VersionUtils;
import me.itangqi.buildingblocks.model.DailyModel;
import me.itangqi.buildingblocks.view.IMainActivity;

/**
 * MainActivity的Presenter， 主要完成app全局的事情，清理缓存，或者更新app
 * 大部分方法只在app启动的时候调用一次
 * Created by Troy on 2015/9/26.
 */
public class MainActivityPresenter {

    public static final String TAG = "MainActivityPresenter";

    private DailyModel mDailyModel;
    private IMainActivity mMainActivity;

    public MainActivityPresenter(IMainActivity iMainActivity) {
        this.mMainActivity = iMainActivity;
        this.mDailyModel = DailyModel.newInstance();
    }

    public void clearCache() {
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -Constants.PAGE_COUNT);
        int before = Integer.parseInt(Constants.simpleDateFormat.format(calendar.getTime()));
        int totalDeleted = mDailyModel.clearOutdatedDB(before);
        mDailyModel.clearOutdatedPhoto(before);
        Log.d(TAG, "totalDeleted--->" + totalDeleted);
        if (totalDeleted > 0) {
            mMainActivity.showSnackBar("清理了" + totalDeleted + "条过期数据", 1500);
        }
    }

    /**
     * 识别服务器上的xml文件来确认是否有新版
     * 相应的新版url写在xml里的<url>标签
     * 通过替换自己的xml文件来达到替换相应的更新源
     */
    public void checkUpdate() {
        if (PrefUtils.isAutoUpdate()) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    String urlStr = "https://raw.githubusercontent.com/troyliu0105/BuildingBlocks/dev/app/bbupdate.xml";
                    int versionCode = 0;
                    String versionName = null;
                    String apkUrl = null;
                    List<String> desc = new ArrayList<>();
                    try {
                        URL url = new URL(urlStr);
                        HttpURLConnection connection = (HttpURLConnection) url.openConnection();
                        connection.setConnectTimeout(3000);
                        connection.setReadTimeout(3000);
                        connection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/45.0.2454.101 Safari/537.36");
                        connection.setRequestProperty("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
                        connection.setRequestProperty("Upgrade-Insecure-Requests", "1");
                        connection.setRequestMethod("GET");
                        InputStream inputStream = connection.getInputStream();
                        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));
                        File tmp = new File(App.getContext().getCacheDir(), "update.xml");
                        if (tmp.exists()) {
                            boolean hasDeleted = tmp.delete();
                            Log.d(TAG, "旧的update.xml" + (hasDeleted ? "已被删除" : "删除失败"));
                        }
                        FileWriter writer = new FileWriter(tmp);
                        BufferedWriter bufferedWriter = new BufferedWriter(writer);
                        char[] buffer = new char[1024];
                        int hasRead;
                        while ((hasRead = reader.read(buffer)) != -1) {
                            bufferedWriter.write(buffer, 0, hasRead);
                            bufferedWriter.newLine();
                        }
                        bufferedWriter.close();
                        writer.close();
                        inputStream.close();
                        reader.close();
                        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder builder = factory.newDocumentBuilder();
                        Document document = builder.parse(tmp);
                        Element bb = (Element) document.getElementsByTagName("update").item(0);
                        versionCode = Integer.parseInt(bb.getElementsByTagName("versionCode").item(0).getFirstChild().getNodeValue());
                        versionName = bb.getElementsByTagName("versionName").item(0).getFirstChild().getNodeValue();
                        apkUrl = bb.getElementsByTagName("url").item(0).getFirstChild().getNodeValue();
                        NodeList descNodes = bb.getElementsByTagName("description");
                        for (int i = 0; i < descNodes.getLength(); i++) {
                            desc.add(descNodes.item(i).getFirstChild().getNodeValue());
                        }
                    } catch (IOException | ParserConfigurationException | SAXException e) {
                        e.printStackTrace();
                    }
                    if (versionCode > VersionUtils.getVerisonCode()) {
                        mMainActivity.showUpdate(versionCode, versionName, apkUrl, desc);
                    }
                }
            }).start();
        }
    }

    public void handleCrashLog() {
        if (PrefUtils.isCrashedLastTime()) {
            Uri uri = Uri.parse(PrefUtils.getCrashUri());
            Log.d(TAG, "crash uri--->" + uri);
            mMainActivity.showSnackBarWithAction("上次我好像坏掉了ಥ_ಥ", 3000, uri);
            PrefUtils.setCrash(false);
        }
    }
}
