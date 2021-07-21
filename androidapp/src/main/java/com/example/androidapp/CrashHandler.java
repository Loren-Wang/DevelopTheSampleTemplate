package com.example.androidapp;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.lorenwang.tools.AtlwConfig;
import android.lorenwang.tools.file.AtlwFileOptionUtil;
import android.os.Build;
import android.util.Log;

import com.example.androidapp.emnu.LocalFileDirPathEnum;
import com.example.androidapp.manager.LoginUserManager;

import org.jetbrains.annotations.NotNull;

import java.io.File;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.lang.Thread.UncaughtExceptionHandler;
import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javabase.lorenwang.tools.common.JtlwDateTimeUtils;

/**
 * 功能：程序崩溃时崩溃log日志的打印，打印地址在 AppCommon.PROJECT_FILE_DIR + "crash/";中
 * 创建时间 2016.1.14
 * 创建人：wangliang_dev
 */
public class CrashHandler implements UncaughtExceptionHandler {
    /**
     * 系统默认的UncaughtException处理类
     */
    private UncaughtExceptionHandler mDefaultHandler;
    private final String TAG = getClass().getName();
    private static volatile CrashHandler optionsInstance;

    private CrashHandler() {
        if (AtlwConfig.nowApplication != null) {
            Log.i(TAG, "CrashHandler init: success");
            // 获取系统默认的UncaughtException处理器
            mDefaultHandler = Thread.getDefaultUncaughtExceptionHandler();
            // 设置该CrashHandler为程序的默认处理器
            Thread.setDefaultUncaughtExceptionHandler(this);
        } else {
            Log.i(TAG, "CrashHandler init: fail");
        }
    }

    public static CrashHandler getInstance() {
        if (optionsInstance == null) {
            synchronized (CrashHandler.class) {
                if (optionsInstance == null) {
                    optionsInstance = new CrashHandler();
                }
            }
        }
        return optionsInstance;
    }

    /**
     * 用来存储设备信息和异常信息
     */
    private final HashMap<String, String> infos = new HashMap<>();

    @Override
    public void uncaughtException(@NotNull Thread thread, @NotNull Throwable ex) {
        if (!handleException(ex) && mDefaultHandler != null) {
            // 如果用户没有处理则让系统默认的异常处理器来处理
            mDefaultHandler.uncaughtException(thread, ex);
        } else {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                Log.e(TAG, "error : ", e);
            } catch (OutOfMemoryError error) {
                Log.e(TAG, "outofmemory : ", error);
            }
            // 退出程序
            android.os.Process.killProcess(android.os.Process.myPid());
            try {
                System.exit(1);
            } catch (Exception ignored) {
            }
        }
    }


    private boolean handleException(Throwable ex) {
        if (ex == null) {
            return false;
        }
        // 收集设备参数信息
        collectDeviceInfo();
        // 保存日志文件
        try {
            saveCatchInfo2File(ex);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return true;
    }

    /**
     * 手机设备信息
     */
    public void collectDeviceInfo() {
        try {
            PackageManager pm = AtlwConfig.nowApplication.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(AtlwConfig.nowApplication.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
                if (LoginUserManager.getInstance().checkUserIsLogin()) {
                    infos.put("userDetailInfo", "ubId" + LoginUserManager.getInstance().getLoginUserInfo().getUserId());
                } else {
                    infos.put("userDetailInfo", "未登录异常");
                }
            }
        } catch (NameNotFoundException e) {
            Log.e(TAG, "an error occured when collect package info", e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Log.d(TAG, field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Log.e(TAG, "an error occured when collect crash info", e);
            }
        }
    }

    /**
     * 保存异常信息
     */
    private void saveCatchInfo2File(Throwable ex) {

        StringBuilder sb = new StringBuilder();
        Iterator<Map.Entry<String, String>> iterator = infos.entrySet().iterator();
        Map.Entry<String, String> entry;
        while (iterator.hasNext()) {
            entry = iterator.next();
            String key = entry.getKey();
            String value = entry.getValue();
            sb.append(key).append("=").append(value).append("\n");
        }
        Writer writer = new StringWriter();
        PrintWriter printWriter = new PrintWriter(writer);
        ex.printStackTrace(printWriter);
        Throwable cause = ex.getCause();
        while (cause != null) {
            cause.printStackTrace(printWriter);
            cause = cause.getCause();
        }
        printWriter.close();
        String result = writer.toString();
        sb.append(result);

        File file = new File(LocalFileDirPathEnum.CRASH.getLocalPath() + "/" +
                JtlwDateTimeUtils.getInstance().getFormatNowTimeToMillisecond("yyyy-MM-dd-HH-mm-ss") + ".log");
        AtlwFileOptionUtil.getInstance().writeToFile(false, file, sb.toString(), "UTF-8", true);
    }
}
