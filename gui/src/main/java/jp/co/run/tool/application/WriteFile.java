package jp.co.run.tool.application;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.nio.charset.StandardCharsets;

import org.apache.poi.ss.usermodel.Workbook;

import jp.co.run.tool.common.CommonUtils;

// TODO: Auto-generated Javadoc
/**
 * The Class WriteFile.
 */
public class WriteFile {

    /**
     * Write result.
     *
     * @param str
     *            the str
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void writeResult(String str) throws IOException {
        String currentTime = CommonUtils.getCurrentTime();
        OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream("FinalInspection_" + currentTime + ".txt"), StandardCharsets.UTF_8);
        writer.write(str);
        writer.close();
    }

    /**
     * Write result.
     *
     * @param str
     *            the str
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    public void writeResultRepairDocument(String str) throws IOException {
        String currentTime = CommonUtils.getCurrentTime();
        OutputStreamWriter writer = new OutputStreamWriter(
                new FileOutputStream("RepairDocument_" + currentTime + ".txt"), StandardCharsets.UTF_8);
        writer.write(str);
        writer.close();
    }

    /**
     * Write workbook.
     *
     * @param workbook
     *            the workbook
     * @return true, if successful
     */
    public static boolean writeWorkbook(Workbook workbook, String nameFile) {
        FileOutputStream outFile;
        try {
            outFile = new FileOutputStream(new File(nameFile));
            workbook.write(outFile);
            outFile.close();
        } catch (FileNotFoundException e) {
            return false;
        } catch (IOException e) {
            return false;
        }
        return true;
    }
}
