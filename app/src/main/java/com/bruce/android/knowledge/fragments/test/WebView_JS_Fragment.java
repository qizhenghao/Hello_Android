package com.bruce.android.knowledge.fragments.test;

import android.net.ConnectivityManager;
import android.net.DhcpInfo;
import android.net.NetworkInfo;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.bruce.android.knowledge.R;
import com.bruce.android.knowledge.base.KnowApplication;
import com.bruce.android.knowledge.fragments.BaseFragment;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.NetworkInterface;
import java.net.SocketException;
import java.util.Enumeration;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import butterknife.BindView;

import static android.content.Context.CONNECTIVITY_SERVICE;
import static android.content.Context.WIFI_SERVICE;

/**
 * Created by qizhenghao on 17/4/13.
 */
public class WebView_JS_Fragment extends BaseFragment {

    @BindView(R.id.native2H5_btn)
    public Button mButton;
    @BindView(R.id.fragment_webview_js_wb)
    public WebView mWebView;

    @Override
    protected int getLayoutId() {
        return R.layout.fragment_webview_js_layout;
    }

    @Override
    protected void initView() {

    }

    @Override
    protected void initData() {
        mWebView.loadUrl("file:///android_asset/html/webview2js.html");
        mWebView.setBackgroundColor(0);//强制背景透明，如果html页面也无背景色则可以做出透明效果
        WebSettings webSettings = mWebView.getSettings();
        webSettings.setJavaScriptEnabled(true);
//        webSettings.setJavaScriptCanOpenWindowsAutomatically(true);//对应于js函数的window.open
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.addJavascriptInterface(this, "toast");
    }

    @android.webkit.JavascriptInterface
    public void showToast(String s) {
        Toast.makeText(getActivity(), Html.fromHtml(s), Toast.LENGTH_SHORT).show();

        getWifiInfo();
    }


    public WifiManager wifiManager;

    public ConnectivityManager connectManager;

    public NetworkInfo netinfo;

    public WifiInfo wifiinfo;

    public DhcpInfo dhcpinfo;//动态主机配置协议信息的对象，获得IP等网关信息

    public String WIFIINFO;

//    private TextView wifitext;

//ArrayList<ScanResult> list;

    private Button jumptoset;

    private String PSW;

    String WIFIPSW = new String();

    String wifipassword;


    private void getWifiInfo() {
//        获取当前连接WIFI信息:

        wifiManager = (WifiManager) KnowApplication.context.getSystemService(WIFI_SERVICE);

        connectManager = (ConnectivityManager)KnowApplication.context.getSystemService(CONNECTIVITY_SERVICE);

        netinfo = connectManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);

        dhcpinfo = wifiManager.getDhcpInfo();

        wifiinfo=wifiManager.getConnectionInfo();

        String wifiname = wifiinfo.getSSID();

        try {

            wifipassword = GetPSW(wifiname);

        } catch (Exception e) {

// TODO Auto-generated catch block

            e.printStackTrace();

            Log.e("TAG", "GetPSW error");

        }

        String mac = getLocalMacAddressFromIp();

        WIFIINFO= "当前网络信息如下:"+"\n"+wifiinfo.getSSID()+"\n"+"====================="+"\n"

                +"ip:   "+intToIp(dhcpinfo.ipAddress)+"\n"

                +"mask:   "+String.valueOf(dhcpinfo.netmask)+"\n"

                +"netgate:   "+String.valueOf(dhcpinfo.gateway)+"\n"

                +"dns:   "+String.valueOf(dhcpinfo.dns1)+"\n"

                +"密码:   "+wifipassword+"\n"

                +"MAC:   "+mac+"\n";


Log.e("TAG", WIFIINFO);

        mButton.setText(WIFIINFO);
    }

    private static String intToIp(int i) {

        return (i & 0xFF ) + "." +
                ((i >> 8 ) & 0xFF) + "." +
                ((i >> 16 ) & 0xFF) + "." +
                ( i >> 24 & 0xFF) ;
    }

    @Override
    protected void initListener() {
        mButton.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                mWebView.loadUrl("javascript:actionFromNative(" + "1" + ")");
                mWebView.loadUrl("javascript:actionFromNative(" + "'来自Native的内容'" + ")");
                mWebView.loadUrl("javascript:actionFromNative(" + "mWebView" + ")");
                mWebView.loadUrl("javascript:actionFromNative(" + mWebView + ")");
            }
        });
    }

    @Override
    public void refresh() {

    }

        private String GetPSW(String wifiname) throws Exception {


            Process process = null;

            DataOutputStream dataOutputStream = null;

            DataInputStream dataInputStream = null;

            StringBuffer wifiPSW = new StringBuffer();

            try{

                process = Runtime.getRuntime().exec("su");

                dataOutputStream = new DataOutputStream(process.getOutputStream());

                dataInputStream = new DataInputStream(process.getInputStream());

                dataOutputStream.writeBytes("cat /data/misc/wifi/wpa_supplicant.conf\n");

                dataOutputStream.writeBytes("exit\n");

                dataOutputStream.flush();

                InputStreamReader inputStreamReader = new InputStreamReader(dataInputStream,"UTF-8");

                BufferedReader bufferedReader = new BufferedReader(inputStreamReader);

                String line = null;

                while((line=bufferedReader.readLine())!= null){

                    wifiPSW.append(line);

                }

                bufferedReader.close();

                inputStreamReader.close();

                process.waitFor();

            }catch(Exception e){

                Log.e("TAG", "not root");

                throw e;

            }finally{

                try{

                    if(dataOutputStream!=null){

                        dataOutputStream.close();

                    }

                    if(dataInputStream!=null){

                        dataInputStream.close();

                    }

                    process.destroy();

                }catch(Exception e){

                    throw e;

                }

            }

/*匹配password*/

            Log.e("TAG", "PATTERN the password");

            Pattern network = Pattern.compile("network=\\{([^\\}]+)\\}",Pattern.DOTALL);

            Matcher networkMatcher = network.matcher(wifiPSW.toString());

            while(networkMatcher.find()){

                String networkBlock = networkMatcher.group();

                Pattern ssid = Pattern.compile("ssid=\""+wifiname+"\"");

                Matcher ssidMatcher = ssid.matcher(networkBlock);

                if(ssidMatcher.find()){

                    Pattern psk = Pattern.compile("psk=\"([^\"]+)\"");

                    Matcher pskMatcher = psk.matcher(networkBlock);

                    if(pskMatcher.find()){

                        PSW=pskMatcher.group(1);

                    }else{

                        PSW="无密码";

                    }

                }

                else{

                    Log.e("TAG", "do not find the ssid");

                }

            }

            Log.e("TAG", PSW);

            return PSW;

        }

    /**
     * 根据IP地址获取MAC地址
     *
     * @return
     */
    private static String getLocalMacAddressFromIp() {
        String strMacAddr = null;
        try {
            //获得IpD地址
            InetAddress ip = getLocalInetAddress();
            byte[] b = NetworkInterface.getByInetAddress(ip).getHardwareAddress();
            StringBuffer buffer = new StringBuffer();
            for (int i = 0; i < b.length; i++) {
                if (i != 0) {
                    buffer.append(':');
                }
                String str = Integer.toHexString(b[i] & 0xFF);
                buffer.append(str.length() == 1 ? 0 + str : str);
            }
            strMacAddr = buffer.toString().toUpperCase();
        } catch (Exception e) {

        }

        return strMacAddr;
    }

    /**
     * 获取移动设备本地IP
     *
     * @return
     */
    private static InetAddress getLocalInetAddress() {
        InetAddress ip = null;
        try {
            //列举
            Enumeration<NetworkInterface> en_netInterface = NetworkInterface.getNetworkInterfaces();
            while (en_netInterface.hasMoreElements()) {//是否还有元素
                NetworkInterface ni = (NetworkInterface) en_netInterface.nextElement();//得到下一个元素
                Enumeration<InetAddress> en_ip = ni.getInetAddresses();//得到一个ip地址的列举
                while (en_ip.hasMoreElements()) {
                    ip = en_ip.nextElement();
                    if (!ip.isLoopbackAddress() && ip.getHostAddress().indexOf(":") == -1)
                        break;
                    else
                        ip = null;
                }

                if (ip != null) {
                    break;
                }
            }
        } catch (SocketException e) {

            e.printStackTrace();
        }
        return ip;
    }
}
