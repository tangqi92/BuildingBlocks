package me.itangqi.buildingblocks.utils;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.ActionBarActivity;

import butterknife.ButterKnife;
import butterknife.OnClick;
import me.itangqi.buildingblocks.R;

/**
 * Created by tangqi on 7/29/15.
 */
public class ShareActivity extends ActionBarActivity {
   @OnClick(R.id.sms)
   public void shareWithSMS() {
       sendSMS("itangqi.me");
   }
    @OnClick(R.id.email)
    public void shareWithEmail() {
        sendMail("itangqi.me");
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_test);
        ButterKnife.bind(this);
    }

    /**
     * 弹出分享列表
     */

    public void showShareDialog(){
        AlertDialog.Builder builder = new AlertDialog.Builder(ShareActivity.this);
        builder.setTitle("选择分享类型");
        builder.setItems(new String[]{"邮件","短信","其他"}, new DialogInterface.OnClickListener() {

            @Override
            public void onClick(DialogInterface dialog, int which) {
                // TODO Auto-generated method stub
                dialog.dismiss();
                switch (which) {
                    case 0:	//邮件
                        sendMail("http://www.google.com.hk/");
                        break;

                    case 1:	//短信
                        sendSMS("http://www.google.com.hk/");
                        break;

                    case 3:	//调用系统分享
                        Intent intent=new Intent(Intent.ACTION_SEND);
                        intent.setType("text/plain");
                        intent.putExtra(Intent.EXTRA_SUBJECT,"分享");
                        intent.putExtra(Intent.EXTRA_TEXT, "我正在浏览这个,觉得真不错,推荐给你哦~ 地址:"+"http://www.google.com.hk/");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(Intent.createChooser(intent, "share"));
                        break;

                    default:
                        break;
                }

            }
        });
        builder.setNegativeButton( "取消" ,  new  DialogInterface.OnClickListener() {
            @Override
            public   void  onClick(DialogInterface dialog,  int  which) {
                dialog.dismiss();
            }
        });
        builder.create().show();
    }



    public void sendMail(String emailUrl){
        Intent email = new Intent(android.content.Intent.ACTION_SEND);
        email.setType("plain/text");

        String emailBody = "我正在浏览这个,觉得真不错,推荐给你哦~ 地址:" + emailUrl;
        //邮件主题
        email.putExtra(android.content.Intent.EXTRA_SUBJECT, "邮件");
        //邮件内容
        email.putExtra(android.content.Intent.EXTRA_TEXT, emailBody);

        startActivityForResult(Intent.createChooser(email,  "请选择邮件发送内容" ), 1001 );
    }


    /**
     * 发短信
     */
    public  void  sendSMS(String webUrl){
        String smsBody = "我正在浏览这个,觉得真不错,推荐给你哦~ 地址:" + webUrl;
        Uri smsToUri = Uri.parse( "smsto:" );
        Intent sendIntent =  new  Intent(Intent.ACTION_VIEW, smsToUri);
        //sendIntent.putExtra("address", "123456"); // 电话号码，这行去掉的话，默认就没有电话
        //短信内容
        sendIntent.putExtra( "sms_body", smsBody);
        sendIntent.setType( "vnd.android-dir/mms-sms" );
        startActivityForResult(sendIntent, 1002 );
    }
}
