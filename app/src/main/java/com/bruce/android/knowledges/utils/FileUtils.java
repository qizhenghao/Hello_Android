package com.bruce.android.knowledges.utils;

import android.os.Environment;
import android.os.StatFs;

import java.io.File;

/**
 * Created by qizhenghao on 15-9-22.
 * <!-- 在SDCard中创建与删除文件权限 -->
 * <uses-permission android:name="android.permission.MOUNT_UNMOUNT_FILESYSTEMS"/>
 * <!-- 往SDCard写入数据权限 -->
 * <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE"/>
 */
public class FileUtils {

    /**
     * 检测是否存在SDCard
     */
    public static boolean isExistSDCard() {
        return (android.os.Environment.getExternalStorageState().equals(
                android.os.Environment.MEDIA_MOUNTED));
    }

    /**
     * 获取SDCard剩余空间
     *
     * @return byte or KB or MB
     */
    public long getSDFreeSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //空闲的数据块的数量
        long freeBlocks = sf.getAvailableBlocks();
        //返回SD卡空闲大小
        //return freeBlocks * blockSize;  //单位Byte
        //return (freeBlocks * blockSize)/1024;   //单位KB
        return (freeBlocks * blockSize) / 1024 / 1024; //单位MB
    }


    /**
     * 获取SDCard的size
     *
     * @return byte or KB or MB
     */
    public long getSDAllSize() {
        //取得SD卡文件路径
        File path = Environment.getExternalStorageDirectory();
        StatFs sf = new StatFs(path.getPath());
        //获取单个数据块的大小(Byte)
        long blockSize = sf.getBlockSize();
        //获取所有数据块数
        long allBlocks = sf.getBlockCount();
        //返回SD卡大小
        //return allBlocks * blockSize; //单位Byte
        //return (allBlocks * blockSize)/1024; //单位KB
        return (allBlocks * blockSize) / 1024 / 1024; //单位MB
    }



}
