package com.example.androidapp.emnu

import android.lorenwang.tools.AtlwConfig
import android.lorenwang.tools.file.AtlwFileOptionUtil

/**
 * 功能作用：本地存储文件路径
 * 创建时间：2020-09-03 9:42 上午
 * 创建人：王亮（Loren）
 * 思路：
 * 方法：
 * 注意：
 * 修改人：
 * 修改时间：
 * 备注：
 *
 * @author 王亮（Loren）
 */
enum class LocalFileDirPathEnum(val localPath: String) {
    /**
     * apk下载地址
     */
    APK_DOWNLOAD(AtlwConfig.nowApplication.getExternalFilesDir("apks")!!.absolutePath),

    /**
     * 网页图片视频下载目录
     */
    WEB_IMAGE_VIDEO_DOWNLOAD(AtlwFileOptionUtil.getInstance().baseStorageDirPath + android.os.Environment.DIRECTORY_DCIM),

    /**
     * 网页文件下载目录
     */
    WEB_FILE_DOWNLOAD(AtlwConfig.nowApplication.getExternalFilesDir("web")!!.absolutePath),

    /**
     * 异常报错目录
     */
    CRASH(AtlwConfig.nowApplication.getExternalFilesDir("crash")!!.absolutePath),

    /**
     * 缩略图
     */
    THUMBNAIL(AtlwConfig.nowApplication.getExternalFilesDir("thumbnail")!!.absolutePath);

}
