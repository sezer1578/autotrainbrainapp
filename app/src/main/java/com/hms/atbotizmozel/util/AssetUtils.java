package com.hms.atbotizmozel.util;

import android.content.Context;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;

/**
 * Created by Batuhan on 20.01.2018.
 */

public class AssetUtils {

    public static String getSalesContractContent(Context context) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream ınputStream = context.getAssets().open("sales_contract.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(ınputStream, "UTF-8"));
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuilder.append(str);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getPreInfoContractContent(Context context) {
        try {
            StringBuilder stringBuilder = new StringBuilder();
            InputStream ınputStream = context.getAssets().open("pre_info_contract.txt");
            BufferedReader reader = new BufferedReader(new InputStreamReader(ınputStream, "UTF-8"));
            String str;
            while ((str = reader.readLine()) != null) {
                stringBuilder.append(str);
            }
            reader.close();
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

}
