/*******************************************************************************
 * Copyright (c) 2000, 2003 IBM Corporation and others.
 * All rights reserved. This program and the accompanying materials 
 * are made available under the terms of the Common Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/cpl-v10.html
 * 
 * Contributors:
 *     IBM Corporation - initial API and implementation
 *******************************************************************************/
package org.eclipse.text.tests.link;

import junit.framework.TestCase;

import org.eclipse.jface.text.BadLocationException;
import org.eclipse.jface.text.Document;
import org.eclipse.jface.text.DocumentEvent;
import org.eclipse.jface.text.IDocument;
import org.eclipse.jface.text.Position;
import org.eclipse.jface.text.link.LinkedPosition;
import org.eclipse.jface.text.link.LinkedPositionGroup;


public class LinkedPositionTest extends TestCase {

	public void setUp() {
		fDoc= new Document(GARTEN1);
		fPos= new LinkedPosition(fDoc, 3, 10);
	}
	
	public void testCreate() {
		new LinkedPosition(fDoc, 1, 9);
		new LinkedPosition(new Document(), 123, 234);
	}
	
	public void testNullCreate() {
		try {
			new LinkedPosition(null, 1, 9);
		} catch (Throwable e) {
			return;
		}
		assertFalse(true);
	}

	/*
	 * Class to test for boolean includes(int)
	 */
	public void testIncludesint() {
		assertEquals(true, fPos.includes(3));
		assertEquals(true, fPos.includes(6));
		assertEquals(true, fPos.includes(13));
		assertEquals(false, fPos.includes(2));
		assertEquals(false, fPos.includes(15));
	}

	public void testGetDocument() {
		assertEquals(fDoc, fPos.getDocument());
	}

	/*
	 * Class to test for boolean overlapsWith(LinkedPosition)
	 */
	public void testOverlapsWithLinkedPosition() {
		LinkedPosition pos= new LinkedPosition(fDoc, 0, 2);
		assertEquals(false, fPos.overlapsWith(pos));
		
		pos= new LinkedPosition(fDoc, 0, 3);
		assertEquals(false, fPos.overlapsWith(pos));
		
		pos= new LinkedPosition(fDoc, 1, 4);
		assertEquals(true, fPos.overlapsWith(pos));
		
		pos= new LinkedPosition(fDoc, 3, 5);
		assertEquals(true, fPos.overlapsWith(pos));
		
		pos= new LinkedPosition(fDoc, 5, 7);
		assertEquals(true, fPos.overlapsWith(pos));
		
		pos= new LinkedPosition(fDoc, 7, 6);
		assertEquals(true, fPos.overlapsWith(pos));
		
		pos= new LinkedPosition(fDoc, 7, 7);
		assertEquals(true, fPos.overlapsWith(pos));
		
		pos= new LinkedPosition(fDoc, 13, 1);
		assertEquals(false, fPos.overlapsWith(pos));
		
		pos= new LinkedPosition(fDoc, 14, 4);
		assertEquals(false, fPos.overlapsWith(pos));
	}

	/*
	 * Class to test for boolean includes(DocumentEvent)
	 */
	public void testIncludesDocumentEvent() {
		DocumentEvent de= new DocumentEvent(fDoc, 0, 2, "ignore");
		assertEquals(false, fPos.includes(de));
		
		de= new DocumentEvent(fDoc, 0, 3, "ignore");
		assertEquals(false, fPos.includes(de));
		
		de= new DocumentEvent(fDoc, 1, 4, "ignore");
		assertEquals(false, fPos.includes(de));
		
		de= new DocumentEvent(fDoc, 3, 5, "ignore");
		assertEquals(true, fPos.includes(de));
		
		de= new DocumentEvent(fDoc, 5, 7, "ignore");
		assertEquals(true, fPos.includes(de));
		
		de= new DocumentEvent(fDoc, 7, 6, "ignore");
		assertEquals(true, fPos.includes(de));
		
		de= new DocumentEvent(fDoc, 7, 7, "ignore");
		assertEquals(false, fPos.includes(de));
		
		de= new DocumentEvent(fDoc, 13, 1, "ignore");
		assertEquals(false, fPos.includes(de));
		
		de= new DocumentEvent(fDoc, 14, 4, "ignore");
		assertEquals(false, fPos.includes(de));
	}

	/*
	 * Class to test for boolean includes(LinkedPosition)
	 */
	public void testIncludesLinkedPosition() {
		LinkedPosition pos= new LinkedPosition(fDoc, 0, 2);
		assertEquals(false, fPos.includes(pos));
		
		pos= new LinkedPosition(fDoc, 0, 3);
		assertEquals(false, fPos.includes(pos));
		
		pos= new LinkedPosition(fDoc, 1, 4);
		assertEquals(false, fPos.includes(pos));
		
		pos= new LinkedPosition(fDoc, 3, 5);
		assertEquals(true, fPos.includes(pos));
		
		pos= new LinkedPosition(fDoc, 5, 7);
		assertEquals(true, fPos.includes(pos));
		
		pos= new LinkedPosition(fDoc, 7, 6);
		assertEquals(true, fPos.includes(pos));
		
		pos= new LinkedPosition(fDoc, 7, 7);
		assertEquals(false, fPos.includes(pos));
		
		pos= new LinkedPosition(fDoc, 13, 1);
		assertEquals(false, fPos.includes(pos));
		
		pos= new LinkedPosition(fDoc, 14, 4);
		assertEquals(false, fPos.includes(pos));
	}

	public void testGetContent() throws BadLocationException {
		LinkedPosition p= new LinkedPosition(fDoc, 1, 9);
		assertEquals("MARGARETE", p.getContent());
		
		p= new LinkedPosition(fDoc, 42, 5);
		assertEquals("FAUST", p.getContent());
		
		fDoc.replace(42, 2, "");
		assertEquals("UST:\n", p.getContent()); // not linked!
		
		fDoc.set(GARTEN1);
		assertEquals("FAUST", p.getContent());
	}
	
	public void testBadLocationContent() {
		LinkedPosition p= new LinkedPosition(new Document(), 23, 3);
		try {
			p.getContent();
		} catch (BadLocationException e) {
			return;
		}
		assertFalse(true);
		
		p= new LinkedPosition(fDoc, 23, 3);
		fDoc.set("");
		try {
			p.getContent();
		} catch (BadLocationException e) {
			return;
		}
		assertFalse(true);
	}

	public void testGetSequenceNumber() {
		LinkedPosition p= new LinkedPosition(fDoc, 1, 9);
		assertEquals(LinkedPositionGroup.NO_STOP, p.getSequenceNumber());
		
		p= new LinkedPosition(fDoc, 1, 9, 18);
		assertEquals(18, p.getSequenceNumber());
	}

	public void testSetSequenceNumber() {
		fPos.setSequenceNumber(28);
		assertEquals(28, fPos.getSequenceNumber());
	}
	
	public void testEquals() {
		LinkedPosition p1= new LinkedPosition(fDoc, 1, 9);
		LinkedPosition p2= new LinkedPosition(fDoc, 1, 9);
		
		assertEquals(p1, p2);
	}
	
	public void testNotEquals() {
		LinkedPosition p1= new LinkedPosition(fDoc, 1, 9);
		LinkedPosition p2= new LinkedPosition(fDoc, 1, 10);
		
		assertFalse(p1.equals(p2));
	}
	
	public void testNotEqualsPosition() {
		LinkedPosition p1= new LinkedPosition(fDoc, 1, 9);
		Position p2= new Position(1, 9);
		
		assertFalse(p1.equals(p2));
	}
	
	public void testNotEqualsPositionSymmetry() {
//		LinkedPosition p1= new LinkedPosition(fDoc, 1, 9);
//		Position p2= new Position(1, 9);
//		
//		// breaking equals contract
//		assertFalse(p2.equals(p1));
	}
	
	public void testNotEqualsDifferentDoc() {
		LinkedPosition p1= new LinkedPosition(fDoc, 1, 9);
		IDocument doc= new Document();
		LinkedPosition p2= new LinkedPosition(doc, 1, 9);
		
		assertFalse(p1.equals(p2));
	}
	
	private static final String GARTEN1= 
		"	MARGARETE:\n" + 
		"	Versprich mir, Heinrich!\n" + 
		"	 \n" + 
		"	FAUST:\n" + 
		"	Was ich kann!\n" + 
		"	 \n" + 
		"	MARGARETE:\n" + 
		"	Nun sag, wie hast du\'s mit der Religion?\n" + 
		"	Du bist ein herzlich guter Mann,\n" + 
		"	Allein ich glaub, du h�ltst nicht viel davon.\n" + 
		"	 \n" + 
		"	FAUST:\n" + 
		"	La� das, mein Kind! Du f�hlst, ich bin dir gut;\n" + 
		"	F�r meine Lieben lie�\' ich Leib und Blut,\n" + 
		"	Will niemand sein Gef�hl und seine Kirche rauben.\n" + 
		"	 \n" + 
		"	MARGARETE:\n" + 
		"	Das ist nicht recht, man mu� dran glauben.\n" + 
		"	 \n" + 
		"	FAUST:\n" + 
		"	Mu� man?\n" + 
		"	 \n" + 
		"	MARGARETE:\n" + 
		"	Ach! wenn ich etwas auf dich konnte! Du ehrst auch nicht die heil\'gen Sakramente.\n" + 
		"	 \n" + 
		"	FAUST:\n" + 
		"	Ich ehre sie.\n" + 
		"	 \n" + 
		"	MARGARETE:\n" + 
		"	Doch ohne Verlangen. Zur Messe, zur Beichte bist du lange nicht gegangen.\n" + 
		"	Glaubst du an Gott?\n" + 
		"	 \n" + 
		"	FAUST:\n" + 
		"	Mein Liebchen, wer darf sagen: Ich glaub an Gott?\n" + 
		"	Magst Priester oder Weise fragen,\n" + 
		"	Und ihre Antwort scheint nur Spott\n" + 
		"	�ber den Frager zu sein.\n" + 
		"	 \n" + 
		"	MARGARETE:\n" + 
		"	So glaubst du nicht?\n" + 
		"	 \n" + 
		"	FAUST:\n" + 
		"	Mi�h�r mich nicht, du holdes Angesicht!\n" + 
		"	Wer darf ihn nennen?\n" + 
		"	Und wer bekennen:\n" + 
		"	�Ich glaub ihn!�?\n" + 
		"	Wer empfinden,\n" + 
		"	Und sich unterwinden\n" + 
		"	Zu sagen: �Ich glaub ihn nicht!�?\n" + 
		"	Der Allumfasser,\n" + 
		"	Der Allerhalter,\n" + 
		"	Fa�t und erh�lt er nicht\n" + 
		"	Dich, mich, sich selbst?\n" + 
		"	W�lbt sich der Himmel nicht da droben?\n" + 
		"	Liegt die Erde nicht hier unten fest?\n" + 
		"	Und steigen freundlich blickend\n" + 
		"	Ewige Sterne nicht herauf?\n" + 
		"	Schau ich nicht Aug in Auge dir,\n" + 
		"	Und dr�ngt nicht alles\n" + 
		"	Nach Haupt und Herzen dir,\n" + 
		"	Und webt in ewigem Geheimnis\n" + 
		"	Unsichtbar sichtbar neben dir?\n" + 
		"	Erf�ll davon dein Herz, so gro� es ist,\n" + 
		"	Und wenn du ganz in dem Gef�hle selig bist,\n" + 
		"	Nenn es dann, wie du willst,\n" + 
		"	Nenn\'s Gl�ck! Herz! Liebe! Gott\n" + 
		"	Ich habe keinen Namen\n" + 
		"	Daf�r! Gef�hl ist alles;\n" + 
		"	Name ist Schall und Rauch,\n" + 
		"	Umnebelnd Himmelsglut.\n";
	private IDocument fDoc;
	private LinkedPosition fPos;
}
