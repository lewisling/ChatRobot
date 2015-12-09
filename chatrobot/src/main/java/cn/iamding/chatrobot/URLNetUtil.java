package cn.iamding.chatrobot;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

/**
 * Created by XuDing on 2015/12/3 23:15 上午10:13.
 */
public class URLNetUtil {
    /**
     * 从终端输入一行字符串，按回车结束输入
     * @return 输入的字符串
     */
    public static String input() {
        Scanner scan = new Scanner(System.in);
        return scan.nextLine();
    }

    /**
     * 输出经过正则切割后的子串
     * @param result 切割后的子串
     */
    public static void regexOutput(String result) {
        String[] rs = result.split("text\":");
        String text = rs[1].substring(rs[1].indexOf("\"") + 1, rs[1].indexOf("\"}"));
        System.out.println(text);
    }

    /**
     * @param httpURL :请求接口
     * @return 返回结果
     */
    public static String request(String httpURL) {
        StringBuilder stringBuilder = new StringBuilder();//用于构造字符串
        try {
            URL url = new URL(httpURL);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            connection.connect();
            InputStream inputStream = connection.getInputStream();
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream, "UTF-8");
            BufferedReader bufferedReader = new BufferedReader(inputStreamReader);
            String tempString;
            while ((tempString=bufferedReader.readLine()) != null) {//从bufferedReader中逐行剪切出不含换行符\r或\n的数据到stringRead
                stringBuilder.append(tempString);//在字符串构造器中追加一行新读出的字符串
                stringBuilder.append("\r\n");//补上换行符
            }
            bufferedReader.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return stringBuilder.toString();
    }
}
