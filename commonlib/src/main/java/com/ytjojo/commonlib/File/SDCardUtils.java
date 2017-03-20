package com.ytjojo.commonlib.File;

import java.io.File;
import java.text.DecimalFormat;

import android.os.Environment;
import android.os.StatFs;

//SD卡相关的辅助类
public class SDCardUtils
{
	private SDCardUtils()
	{
		/* cannot be instantiated */
		throw new UnsupportedOperationException("cannot be instantiated");
	}

	/**
	 * 判断SDCard是否可用
	 * 
	 * @return
	 */
	public static boolean isSDCardEnable()
	{
		return Environment.getExternalStorageState().equals(
				Environment.MEDIA_MOUNTED);

	}

	   public static boolean isSdCardWrittenable() {

	        if (android.os.Environment.getExternalStorageState().equals(
	                android.os.Environment.MEDIA_MOUNTED)) {
	            return true;
	        }
	        return false;
	    }
	/**
	 * 获取SD卡路径
	 * 
	 * @return
	 */
	public static String getSDCardPath()
	{
		return Environment.getExternalStorageDirectory().getAbsolutePath()
				+ File.separator;
	}

	/**
	 * 获取SD卡的剩余容量 单位byte
	 * 
	 * @return
	 */
	@SuppressWarnings("deprecation")
	public static long getSDCardAllSize()
	{
		if (isSDCardEnable())
		{
			StatFs stat = new StatFs(getSDCardPath());
			// 获取空闲的数据块的数量
			long availableBlocks = (long) stat.getAvailableBlocks() - 4;
			// 获取单个数据块的大小（byte）
			long freeBlocks = stat.getAvailableBlocks();
			return freeBlocks * availableBlocks;
		}
		return 0;
	}

	/**
	 * 获取指定路径所在空间的剩余可用容量字节数，单位byte
	 * 
	 * @param filePath
	 * @return 容量字节 SDCard可用空间，内部存储可用空间
	 */
	@SuppressWarnings("deprecation")
	public static long getFreeBytes(String filePath)
	{
		// 如果是sd卡的下的路径，则获取sd卡可用容量
		if (filePath.startsWith(getSDCardPath()))
		{
			filePath = getSDCardPath();
		} else
		{// 如果是内部存储的路径，则获取内存存储的可用容量
			filePath = Environment.getDataDirectory().getAbsolutePath();
		}
		StatFs stat = new StatFs(filePath);
		long availableBlocks = (long) stat.getAvailableBlocks() - 4;
		return stat.getBlockSize() * availableBlocks;
	}

	   /**
     * 判断是否装有SD卡、是否可读写、是否有空间
     * 
     * @param size 需存入的文件大小，SD剩余空间必须大于该值
     * @return true可用，false不可用
     */
    public static boolean checkSDStatus(long size) {
        try {
            /* 读取SD卡大小 */
            File storage = Environment.getExternalStorageDirectory();
            StatFs stat = new StatFs(storage.getPath());
            long blocks = stat.getAvailableBlocks();
            long blocksize = stat.getBlockSize();

            /* 判断 */
            if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED) && (blocks * blocksize) > size) {
                return true;
            } else {
                return false;
            }
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }
	
	/**
	 * 获取系统存储路径
	 * 
	 * @return
	 */
	public static String getRootDirectoryPath()
	{
		return Environment.getRootDirectory().getAbsolutePath();
	}

	/**
     * 字节的大小，转成口头语
     * @param size
     * @return
     */
    public static String byte2Oral(double size) {
        DecimalFormat df = new DecimalFormat("0.0");
        StringBuffer datas = new StringBuffer();
        if (size < 1048576) {
            datas.append((int) (size / 1024)).append("KB");
        } else if (size > 1048576) {
            datas.append(df.format((size / 1048576))).append("MB");
        } else if (size < 1024) {
            datas.append(size).append("B");
        }
        return datas.toString();
    }
}
