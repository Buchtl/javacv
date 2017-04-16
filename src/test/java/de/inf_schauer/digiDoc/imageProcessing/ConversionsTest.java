package de.inf_schauer.digiDoc.imageProcessing;

import static org.junit.Assert.*;

import org.junit.Test;

import de.inf_schauer.javaCvGui.imageProcessing.Conversions;

public class ConversionsTest {
	
	@Test 
	/**
	 * Testing conversion fo 1 point
	 */
	public void testPointToMillimeter(){
		int expected = 254/72;
		assertEquals(expected,Conversions.pointsToMM(1));
	}

	@Test
	public void testPointToPixel() {
		int pts = 72;
		int ppi = 72; // so pixels and points equal
		assertEquals(pts, Conversions.pointToPixel(ppi, pts));
	}

}
