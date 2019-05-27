/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package jp.co.run.tool.abstracts;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PushbackInputStream;

import org.apache.poi.POIXMLDocument;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hwpf.HWPFDocument;
import org.apache.poi.hwpf.extractor.WordExtractor;
import org.apache.poi.openxml4j.opc.OPCPackage;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.apache.poi.xwpf.usermodel.Document;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import jp.co.run.tool.common.CommonConsts;
import jp.co.run.tool.common.CommonUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class AbstractReadFile.
 *
 * @author TuMV
 * @param <T> the generic type
 */

public abstract class AbstractReadFile<T> {

    /**
     * Read file.
     *
     * @param path the path
     * @return the list
     */
    public T readFile(String path, int typeExcute) {
        // Readfile
        Workbook myWorkBook = null;
        WordExtractor extractor = null;
        InputStream is = null;
        T result = null;
        try {
            is = new FileInputStream(path);
            if (!is.markSupported()) {
                is = new PushbackInputStream(is, 8);
            }

            // Check file xls or xlsx
            if (POIFSFileSystem.hasPOIFSHeader(is)) {
                myWorkBook = new HSSFWorkbook(is);
            } else if (POIXMLDocument.hasOOXMLHeader(is)) {
                myWorkBook = new XSSFWorkbook(OPCPackage.open(is));
            }

            // Check type excute
            if (typeExcute == CommonConsts.TYPE_EXCUTE_CHECK) {
                result = getData(myWorkBook);
            } else {
                result = repairDocument(myWorkBook, CommonUtils.getNameFile(path));
            }

        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (is != null) {
                try {
                    is.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
            if (myWorkBook != null) {
            }
        }
        return result;
    }

    /**
     * Gets the data.
     *
     * @param myWorkBook the my work book
     * @return the data
     * @throws Exception the exception
     */
    public abstract T getData(Workbook myWorkBook) throws Exception;

    /**
     * Repair document.
     *
     * @param myWorkBook the my work book
     * @return the t
     * @throws Exception the exception
     */
    public abstract T repairDocument(Workbook myWorkBook, String nameFile) throws Exception;
}
