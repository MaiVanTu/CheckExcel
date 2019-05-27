package jp.co.run.tool.application;

import java.io.File;
import java.io.IOException;
import java.text.ParseException;

import jp.co.run.tool.common.CommonConsts;

// TODO: Auto-generated Javadoc
/**
 * The Class Application.
 */
public class Application {

    /**
     * Gets the data.
     *
     * @param directory the directory
     * @return the data
     * @throws ParseException the parse exception
     */
    public static void getData(String directory) throws ParseException {

        ReadFile readFile = new ReadFile();
        WriteFile writeFile = new WriteFile();
        File folder = new File(directory);
        String errorResult = readFile.listFilesForFolder(folder, CommonConsts.TYPE_EXCUTE_CHECK);
        try {
            writeFile.writeResult(errorResult);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    /**
     * Repair document.
     *
     * @param directory the directory
     * @throws ParseException the parse exception
     */
    public static void repairDocument(String directory) throws ParseException {

        ReadFile readFile = new ReadFile();
        WriteFile writeFile = new WriteFile();
        File folder = new File(directory);
        String errorResult = readFile.listFilesForFolder(folder, CommonConsts.TYPE_EXCUTE_REPAIR_DOCUMENT);
        try {
            writeFile.writeResultRepairDocument(errorResult);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }
}
