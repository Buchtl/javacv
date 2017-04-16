/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package de.inf_schauer.javaCvGui.interfaces;

import java.awt.image.BufferedImage;

/**
 *
 * @author Christian Schauer <christian.schauer at interface-ag.de>
 */
public interface I_Ocr {

    public String detect(BufferedImage bi);

    public String detectNumber(BufferedImage bi);
}
