/*-----------------------------------------------------------------------------+

 Filename			: LabelledColor.java

 Project				: fingerprint-recog
 Package				: application.gui.legend

 Developed by		: Thomas DEVAUX & Estelle SENAY
 (2007) Concordia University

 -------------------------

 This program is free software. You can redistribute it and/or modify it 
 under the terms of the GNU Lesser General Public License as published by 
 the Free Software Foundation. Either version 2.1 of the License, or (at your 
 option) any later version.

 This program is distributed in the hope that it will be useful, but WITHOUT 
 ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or 
 FITNESS FOR A PARTICULAR PURPOSE. See the GNU General Public License for 
 more details.

 +-----------------------------------------------------------------------------*/

package com.gy.fp;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;

import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.SwingConstants;
import javax.swing.border.Border;

public class LabelledColor extends JPanel {
	// ---------------------------------------------------------- CONSTANTS --//

	// ---------------------------------------------------------- VARIABLES --//
	private JPanel colorPanel; // Color panel
	private JLabel text; // Color legend

	// ------------------------------------------------------- CONSTRUCTORS --//

	/**
	 * Construct a LabelledColor
	 * 
	 * @param color
	 *            Color
	 * @param string
	 *            String
	 */
	public LabelledColor(Color color, String string) {
		// Create objects
		colorPanel = new JPanel();
		text = new JLabel();
		Border colorBorder = BorderFactory.createLineBorder(color.darker(), 3);

		// Set values
		colorPanel.setBackground(color);
		colorPanel.setBorder(colorBorder);
		text.setPreferredSize(new Dimension(1, 1));
		text.setMinimumSize(new Dimension(1, 1));

		text.setText(string);

		// Set options
		setBackground(Color.black);
		text.setForeground(Color.gray);
		text.setPreferredSize(new Dimension(1, 1));
		text.setVerticalAlignment(SwingConstants.CENTER);

		// Set layouts
		setLayout(new GridLayout(1, 2));

		// Add elements
		add(colorPanel);
		add(text);

	}

	// ------------------------------------------------------------ METHODS --//

	// ---------------------------------------------------- PRIVATE METHODS --//
}
