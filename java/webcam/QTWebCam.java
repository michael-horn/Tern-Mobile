/*
 * @(#) QTWebCam.java
 * 
 * Tern Tangible Programming System
 * Copyright (C) 2009 Michael S. Horn 
 * 
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 * 
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package webcam;

import quicktime.QTSession;
import quicktime.io.QTFile;
import quicktime.qd.*;
import quicktime.std.sg.*;
import quicktime.std.image.GraphicsExporter;
import quicktime.std.StdQTConstants;
import quicktime.std.StdQTConstants4;

import javax.imageio.ImageIO;
import java.io.File;
import java.awt.image.BufferedImage;


public class QTWebCam {

   protected SequenceGrabber grabber;
   protected SGVideoChannel channel;
   protected QDRect bounds;

   public QTWebCam() { }

   public void initialize() throws WebCamException {
      try {
         QTSession.open();
      } catch (Exception x) {
         x.printStackTrace();
         QTSession.close();
      }
   }

   
   public void uninitialize() {
      QTSession.close();
   }


   public boolean isCameraOpen() {
      return true;
   }

   
   public void openCamera() throws WebCamException {
      try {
         this.grabber  = new SequenceGrabber();
         this.channel  = new SGVideoChannel(grabber);
         this.bounds   = channel.getSrcVideoBounds();
         this.channel.setBounds(bounds);
      }
      catch (Exception x) {
         throw new WebCamException(x.getMessage());
      }
   }

   
   public void closeCamera() {
   }


   public void showConfigurationDialog() {
      try { 
         this.channel.settingsDialog();
      } catch (Exception x) { ; }
   }

   
   public BufferedImage capture(String filename) throws WebCamException {
      try {
         File file = new File(filename);
         Pict image = grabber.grabPict(bounds, 0, 1);
         GraphicsExporter exporter = new GraphicsExporter(
            quicktime.std.StdQTConstants4.kQTFileTypePNG);
         exporter.setInputPicture(image);
         exporter.setOutputFile(new QTFile(file));
         exporter.doExport();

         return ImageIO.read(file);
         
      } catch (Exception x) {
         throw new WebCamException(x.getMessage());
      }
   }
   
}
