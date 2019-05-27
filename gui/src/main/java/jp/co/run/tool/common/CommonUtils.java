/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.run.tool.common;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;
import java.nio.channels.FileLock;
import java.nio.channels.OverlappingFileLockException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

// TODO: Auto-generated Javadoc
/**
 * The Class CommonUtils.
 *
 * @author TuMV
 */
public class CommonUtils {

    // private static final Logger logger = Logger.getLogger(CommonUtils.class);

    /**
     * Checks if is directory.
     *
     * @param path
     *            the path
     * @return true, if is directory
     */
    public static boolean isDirectory(String path) {
        File file = new File(path);
        return file.isDirectory();
    }

    /**
     * Checks if is vietnamese.
     *
     * @param content
     *            the content
     * @return true, if is vietnamese
     */
    public static boolean isVietnamese(String content) {
        try {
            // Create pattern check character Vietnamese
            Pattern p = Pattern.compile("([" + CommonConsts.VIETNAMESE_DIACRITIC_CHARACTERS + "])",
                    Pattern.CANON_EQ | Pattern.CASE_INSENSITIVE | Pattern.UNICODE_CASE);
            Matcher m = p.matcher(content);
            while (m.find()) {
                // Character Vietnamese exist
                return true;
            }
        } catch (Exception e) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is font not exactly.
     *
     * @param fontName
     *            the font name
     * @return true, if is font not exactly
     */
    public static boolean isFontNotExactly(String fontName) {
        // Font current user check
        String currentFont = CommonProperties.getFontCell();
        if (!currentFont.equals(fontName)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is font size not exactly.
     *
     * @param fontSize the font size
     * @return true, if is font size not exactly
     */
    public static boolean isFontSizeNotExactly(int fontSize) {
        // Font current user check
        int currentFontSize = CommonProperties.getFontSizeCell();
        if (currentFontSize != fontSize) {
            return true;
        }
        return false;
    }

    /**
     * Generic function to find the index of an element in an object array in Java.
     *
     * @param <T> the generic type
     * @param a the a
     * @param target the target
     * @return the int
     */
    public static <T> int findIndex(T[] a, T target) {
        return Arrays.asList(a).indexOf(target);
    }

    /**
     * Checks if is date format not exactly.
     *
     * @param format the format
     * @return true, if is date format not exactly
     */
    public static boolean isDateFormatNotExactly(String format) {
        // Date format want to check
        String currentFormat = CommonProperties.getFormatCell();
        if (!currentFormat.equals(format)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the date.
     *
     * @param date the date
     * @param formatDate the format date
     * @return the date
     */
    public static String formatDate(Date date, String formatDate) {
        SimpleDateFormat formatterDate = new SimpleDateFormat(formatDate);
        return formatterDate.format(date);
    }

    /**
     * Checks if all file in folder is ready.
     *
     * @param folder the folder
     * @return true, if is ready
     */
    public static boolean isReady(File folder) {
        // Check input params
        if (!folder.isDirectory()) {
            return false;
        }
        // Check all file in folder ready
        for (File fileEntry : folder.listFiles()) {
            if (fileEntry.isDirectory()) {
                isReady(fileEntry);
            } else {
                FileChannel channel = null;
                FileLock lock = null;
                try {
                    channel = new RandomAccessFile(fileEntry, "rw").getChannel();
                    lock = channel.tryLock();
                } catch (OverlappingFileLockException e) {
                    // File is used by another process
                    return false;
                } catch (FileNotFoundException e) {
                    // File is used by another process
                    return false;
                } catch (IOException e) {
                    // File is used by another process
                    return false;
                } finally {
                    try {
                        lock.release();
                    } catch (Exception e) {
                        // File is used by another process
                        return false;
                    }
                }
//                if (fileEntry.isHidden()) {
//                    return false;
//                }
            }
        }
        return true;
    }

    /**
     * Checks if is excel.
     *
     * @param file the file
     * @return true, if is excel
     */
    public static boolean isAcceptFile(File file) {
        String extenionFile = getFileExtension(file);
        if (CommonConsts.XLS_EXTENTION.equals(extenionFile)
                || CommonConsts.XLSX_EXTENTION.equals(extenionFile)) {
//                || CommonConsts.DOCX_EXTENTION.equals(extenionFile)    // Feature accept both doc and excel
//                || CommonConsts.DOC_EXTENTION.equals(extenionFile)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is excel.
     *
     * @param file the file
     * @return true, if is excel
     */
    public static boolean isExcel(String path) {
        File file = new File(path);
        String extenionFile = getFileExtension(file);
        if (CommonConsts.XLS_EXTENTION.equals(extenionFile)
                || CommonConsts.XLSX_EXTENTION.equals(extenionFile)) {
            return true;
        }
        return false;
    }

    /**
     * Checks if is doc.
     *
     * @param file the file
     * @return true, if is doc
     */
    public static boolean isDoc(String path) {
        File file = new File(path);
        String extenionFile = getFileExtension(file);
        if (CommonConsts.DOCX_EXTENTION.equals(extenionFile)
                || CommonConsts.DOC_EXTENTION.equals(extenionFile)) {
            return true;
        }
        return false;
    }

    /**
     * Gets the name file.
     *
     * @param path the path
     * @return the name file
     */
    public static String getNameFile(String path) {
        File file = new File(path);
        if (file.exists()) {
            return file.getName();
        }
        return CommonConsts.EMPTY;
    }

    /**
     * Gets the file extension.
     *
     * @param file the file
     * @return the file extension
     */
    private static String getFileExtension(File file) {
        String extension = "";

        try {
            if (file != null && file.exists()) {
                String name = file.getName();
                extension = name.substring(name.lastIndexOf("."));
            }
        } catch (Exception e) {
            extension = "";
        }

        return extension;

    }

    /**
     * Gets the current time.
     *
     * @return the current time
     */
    public static String getCurrentTime() {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss");
        LocalDateTime now = LocalDateTime.now();
        return dtf.format(now);
    }

    /**
     * Auto create path.
     *
     * @param path the path
     */
    public static void autoCreatePath(String path) {
        File files = new File(path);
        if (!files.exists()) {
            if (files.mkdir()) {
                System.out.println("<-- Multiple directories are created! -->");
            } else {
                System.out.println("<-- Failed to create multiple directories! -->");
            }
        } else {
            System.out.println("<-- Path exist -->");
        }
    }
}
