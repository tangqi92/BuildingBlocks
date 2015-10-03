package me.itangqi.buildingblocks.presenters;

import android.os.Looper;
import android.util.Log;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
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
import me.itangqi.buildingblocks.domain.utils.VersionUtils;
import me.itangqi.buildingblocks.model.DailyModel;
import me.itangqi.buildingblocks.view.IMainActivity;

/**
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
        int today = Integer.parseInt(Constants.simpleDateFormat.format(calendar.getTime()));
        int totalDeleted = mDailyModel.clearOutdateCache(today - 7);
        long deletedSize = mDailyModel.clearOutdatePhoto(today - 7);
        if (totalDeleted > 0) {
            mMainActivity.showSnackBar("清理了" + totalDeleted + "条过期数据；" + "图片" + (deletedSize / 1024) + "KB", 1500);
        }
    }

    public void checkUpdate() {
        Log.d(TAG, "checkUpdate()");
        new Thread(new Runnable() {
            @Override
            public void run() {
                String urlStr = "http://7xk54v.com1.z0.glb.clouddn.com/app/bbupdatetest.xml";
                String name = null;
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
                    name = bb.getElementsByTagName("name").item(0).getFirstChild().getNodeValue();
                    versionCode = Integer.parseInt(bb.getElementsByTagName("versionCode").item(0).getFirstChild().getNodeValue());
                    versionName = bb.getElementsByTagName("versionName").item(0).getFirstChild().getNodeValue();
                    apkUrl = bb.getElementsByTagName("url").item(0).getFirstChild().getNodeValue();
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
