package de.inf_schauer.digiDoc.imageProcessing;

import de.inf_schauer.javaCvGui.imageProcessing.GraphProc;

import java.util.logging.Logger;

import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;

import de.inf_schauer.javaCvGui.data.TristateEnum;

public class GraphProcTest {

    @BeforeClass
    public static void setUpBeforeClass() throws Exception {
    }

    @AfterClass
    public static void tearDownAfterClass() throws Exception {
    }

    @Before
    public void setUp() throws Exception {
    }

    @After
    public void tearDown() throws Exception {
    }

    @Test
    public void testinitCheckStateArr() {
        Logger log = Logger.getLogger("...");

        TristateEnum[][] arr = new TristateEnum[2][3];
        GraphProc.initCheckStateArr(arr, TristateEnum.FALSE);
        printCheckStateArr(arr);
        GraphProc.initCheckStateArr(arr, TristateEnum.TRUE);
        printCheckStateArr(arr);
        //fail("Not yet implemented");
    }

    private void printCheckStateArr(TristateEnum[][] arr) {
        Logger log = Logger.getLogger("...");
        int h = arr.length;
        int w = arr[0].length;
        for (int y = 0; y < h; y++) {
            for (int x = 0; x < w; x++) {
                log.info("[" + y + "," + x + "]" + arr[y][x]);
            }
        }
    }

}
