/*
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 * DO NOT ALTER OR REMOVE COPYRIGHT NOTICES OR THIS FILE HEADER.
 * 
 * This code is free software; you can redistribute it and/or modify it
 * under the terms of the GNU General Public License version 2 only, as
 * published by the Free Software Foundation.  Oracle designates this
 * particular file as subject to the "Classpath" exception as provided
 * by Oracle in the LICENSE file that accompanied this code.
 * 
 * This code is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY; without even the implied warranty of MERCHANTABILITY or
 * FITNESS FOR A PARTICULAR PURPOSE.  See the GNU General Public License
 * version 2 for more details (a copy is included in the LICENSE file that
 * accompanied this code).
 * 
 * You should have received a copy of the GNU General Public License version
 * 2 along with this work; if not, write to the Free Software Foundation,
 * Inc., 51 Franklin St, Fifth Floor, Boston, MA 02110-1301 USA.
 * 
 * Please contact Oracle, 500 Oracle Parkway, Redwood Shores, CA 94065 USA
 * or visit www.oracle.com if you need additional information or have any
 * questions.
 */
package com.sun.tools.visualvm.heapviewer.console.r;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import javax.swing.JPanel;
import org.netbeans.lib.profiler.ui.UIUtils;

/**
 *
 * @author Jiri Sedlacek
 */
class RPlotPanel extends JPanel {
    
    private Image offscreenImage;
    
    
    RPlotPanel() {
        setOpaque(true);
        setBackground(UIUtils.getProfilerResultsBackground());
    }
    
    
    Image createPlotImage() {
        int w = getWidth();
        int h = getHeight();
        
        if (w <= 0 || h <= 0) {
            offscreenImage = null;
        } else {
            offscreenImage = createImage(getWidth(), getHeight());
        }
        
        return offscreenImage;
    }
    
    
    @Override
    public void paint(Graphics g) {
        super.paint(g);
        
        int w = getWidth();
        int h = getHeight();
        
        Image img = offscreenImage; // not synchronized, createPlotImage() called from worker thread
        if (img != null && w > 0 && h > 0 && g instanceof Graphics2D) {
            Graphics2D g2 = (Graphics2D)g;
            g2.drawImage(img, 0, 0, w, h, null);
        }
    }
 
    
}
